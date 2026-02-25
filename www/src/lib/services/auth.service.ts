import { HttpClient } from '$lib/services/http_client';

interface LoginPayload {
	identifier: string;
	password: string;
}

const client = new HttpClient('http://localhost:8080/api/v1');

export const authService = {
	login: (payload: LoginPayload) => {
		console.log(payload);
		return client.post<LoginPayload, void>('/auth/login', payload);
	}
};
