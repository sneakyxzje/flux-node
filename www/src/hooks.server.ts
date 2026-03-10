// file initialized by the Paraglide-SvelteKit CLI - Feel free to edit it
import { sequence } from '@sveltejs/kit/hooks';
import { i18n } from '$lib/i18n';
import { PUBLIC_API_URL } from '$env/static/public';
import { redirect, type Handle } from '@sveltejs/kit';
import { HttpClient } from '$lib/services/http_client';
import type { User } from '$lib/models/user';
import { createAuthService } from '$lib/services/auth.service';
export const guard: Handle = async ({ event, resolve }) => {
	const originalFetch = event.fetch;
	const APP_ROUTE = '(private)';
	event.fetch = async (input, init) => {
		if (PUBLIC_API_URL.includes('localhost:8080')) {
			const options = { ...init };
			const headers = new Headers(options.headers);
			const currentCookie = event.cookies
				.getAll()
				.map((c) => `${c.name}=${c.value}`)
				.join('; ');
			if (!headers.has('cookie') && currentCookie) {
				headers.set('cookie', currentCookie);
			}
			options.headers = headers;
			return originalFetch(input, options);
		}
		return originalFetch(input, init);
	};

	event.locals.api = new HttpClient(PUBLIC_API_URL, { fetcher: event.fetch });
	const server = createAuthService(event.locals.api);
	const accessToken = event.cookies.get('accessToken');
	const refreshToken = event.cookies.get('refreshToken');
	if (accessToken) {
		const res = await event.locals.api.get<User>('/auth/me');
		if (res.ok) {
			event.locals.user = res.data;
		} else {
			console.log('Failed to fetch user data: ', res.error);
			if (
				(res.error.message.includes('401') || res.error.message.includes('403')) &&
				refreshToken
			) {
				await server.refresh();
			} else {
				console.log('Unexpected error');
			}
		}
	} else if (refreshToken) {
		const refreshed = await server.refresh();
		if (refreshed.ok) {
			event.cookies.set('accessToken', refreshed.data.accessToken, {
				httpOnly: true,
				path: '/',
				maxAge: 15 * 60
			});
			event.cookies.set('refreshToken', refreshed.data.refreshToken, {
				httpOnly: true,
				path: '/',
				maxAge: 7 * 24 * 60 * 60
			});
			const retryMe = await event.locals.api.get<User>('/auth/me');
			if (retryMe.ok) {
				event.locals.user = retryMe.data;
			}
		}
	}
	const isAppRoute = event.route.id?.includes(APP_ROUTE);
	if (!event.locals.user && isAppRoute) {
		throw redirect(303, i18n.resolveRoute('/login'));
	}
	if (event.locals.user && event.url.pathname === '/login') {
		throw redirect(303, '/dashboard');
	}
	if (event.locals.user && event.url.pathname === '/') {
		throw redirect(303, '/dashboard');
	}
	return resolve(event);
};
// add your own hooks as part of the sequence here
export const handle = sequence(i18n.handle(), guard);
