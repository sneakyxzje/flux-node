import type { User } from '$lib/models/user';
import { HttpClient } from '$lib/services/http_client';
import { defaultClient } from '$lib/services/http_instance';

interface LoginPayload {
	identifier: string;
	password: string;
}

interface RefreshResult {
	accessToken: string;
	refreshToken: string;
}
export const createAuthService = (client: HttpClient) => ({
	login: (payload: LoginPayload) => {
		return client.post<LoginPayload, void>('/auth/login', payload);
	},
	getMe: () => {
		return client.get<User>('/auth/me');
	},
	refresh: () => client.post<void, RefreshResult>('/auth/refresh', undefined)
});

export const authService = createAuthService(defaultClient);
