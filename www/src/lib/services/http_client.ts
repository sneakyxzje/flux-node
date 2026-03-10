import { attempt, type Attempt } from '@duydang2311/attempt';

export interface HttpClientOptions {
	fetcher: (input: RequestInfo, init?: RequestInit) => Promise<Response>;
	headers?: HeadersInit;
	baseUrl?: string;
}

export class HttpClient {
	#options: HttpClientOptions;
	constructor(baseUrl: string, options?: HttpClientOptions) {
		this.#options = {
			baseUrl: baseUrl,
			fetcher: options?.fetcher || fetch,
			headers: options?.headers || {}
		};
	}
	async get<TResponse, TError = Error>(endpoint: string): Promise<Attempt<TResponse, TError>> {
		return this.#request(() => this.prepare(endpoint, 'GET'));
	}

	public async post<TRequest, TResponse, TError = Error>(
		endpoint: string,
		body: TRequest
	): Promise<Attempt<TResponse, TError>> {
		return this.#request(() => this.prepare<TRequest, TResponse>(endpoint, 'POST', body));
	}

	async prepare<TRequest, TResponse>(
		endpoint: string,
		method: string,
		body?: TRequest,
		headers?: HeadersInit
	): Promise<TResponse> {
		const url = `${this.#options.baseUrl}${endpoint}`;
		const finalHeaders = new Headers(this.#options.headers);
		if (headers) {
			new Headers(headers).forEach((v, k) => finalHeaders.set(k, v));
		}
		let finalBody: string | undefined = undefined;

		if (body) {
			finalBody = this.#serializeBody(body);
			finalHeaders.set('Content-Type', 'application/json');
		}

		const response = await this.#options.fetcher(url, {
			method,
			headers: finalHeaders,
			body: finalBody,
			credentials: 'include'
		});
		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}
		const contentType = response.headers.get('Content-Type');
		if (!contentType || !contentType.includes('application/json')) {
			return undefined as TResponse;
		}

		return response.json() as Promise<TResponse>;
	}

	async #request<TRes, TError = Error>(
		action: () => Promise<TRes>
	): Promise<Attempt<TRes, TError>> {
		return await attempt.async(action)((e: unknown) => e as TError);
	}

	#serializeBody = (data: unknown) => {
		if (typeof data === 'string') return data;
		if (typeof data === 'object') {
			return JSON.stringify(data);
		}
		return undefined;
	};
}
