<script lang="ts">
	import * as m from '$lib/paraglide/messages.js';
	import { authService } from '$lib/services/auth.service';

	let activeTab = $state('signin');
	let identifier = $state('');
	let password = $state('');
	let isLoading = $state(false);
	let errorMsg = $state('');

	const setTab = (tab: string) => {
		activeTab = tab;
	};

	const handleSubmit = async (e: Event) => {
		isLoading = true;
		errorMsg = '';
		e.preventDefault();
		const result = await authService.login({ identifier, password });
		if (!result.ok) {
			errorMsg = 'Email hoặc mật khẩu không đúng';
			return;
		}
		window.location.href = '/dashboard';
	};
</script>

<div class="flex min-h-screen overflow-hidden bg-[#0a0000]">
	<div class="relative z-10 flex w-[460px] flex-shrink-0 flex-col bg-white">
		<div
			class="absolute inset-x-0 top-0 h-[130px] bg-primary [clip-path:ellipse(120%_100%_at_50%_0%)]"
		></div>

		<div class="relative z-20 mt-30 flex flex-1 flex-col px-10 py-8">
			<h1 class="text-3xl font-extrabold text-[#1a1a2e]">
				{activeTab === 'signin' ? m.login_title_signin() : m.login_title_signup()}
			</h1>
			<p class="mt-1 mb-7 text-[13.5px] text-gray-500">
				{m.login_subtitle()}
			</p>

			<div class="mb-7 flex cursor-pointer rounded-xl bg-gray-100 p-1">
				<button
					onclick={() => setTab('signin')}
					class="flex-1 cursor-pointer rounded-lg py-2 text-sm font-bold transition-all {activeTab ===
					'signin'
						? 'bg-white text-[#1a1a2e] shadow-sm'
						: 'text-gray-500'}"
				>
					{m.login_tab_signin()}
				</button>
				<button
					onclick={() => setTab('signup')}
					class="flex-1 cursor-pointer rounded-lg py-2 text-sm font-bold transition-all {activeTab ===
					'signup'
						? 'bg-white text-[#1a1a2e] shadow-sm'
						: 'text-gray-500'}"
				>
					{m.login_tab_signup()}
				</button>
			</div>

			<form onsubmit={handleSubmit} class="space-y-4">
				{#if activeTab === 'signup'}
					<div>
						<label for="name" class="mb-1.5 block text-xs font-bold text-[#1a1a2e]"
							>{m.login_field_name()}</label
						>
						<input
							type="text"
							id="name"
							placeholder={m.login_field_name_placeholder()}
							class="w-full rounded-lg border-[1.5px] border-gray-200 bg-gray-50 px-3.5 py-2.5 text-sm transition-all outline-none focus:border-[#2563EB] focus:bg-white focus:ring-4 focus:ring-blue-500/10"
							required
						/>
					</div>
				{/if}

				<div>
					<label for="email" class="mb-1.5 block text-xs font-bold text-[#1a1a2e]"
						>{m.login_field_email()}</label
					>
					<input
						type="text"
						id="email"
						value={identifier}
						onchange={(e) => (identifier = (e.target as HTMLInputElement).value)}
						placeholder={m.login_field_email_placeholder()}
						class="w-full rounded-lg border-[1.5px] border-gray-200 bg-gray-50 px-3.5 py-2.5 text-sm transition-all outline-none focus:border-[#2563EB] focus:bg-white focus:ring-4 focus:ring-blue-500/10"
					/>
				</div>

				<div>
					<label for="password" class="mb-1.5 block text-xs font-bold text-[#1a1a2e]"
						>{m.login_field_password()}</label
					>
					<input
						type="password"
						id="password"
						value={password}
						onchange={(e) => (password = (e.target as HTMLInputElement).value)}
						placeholder={m.login_field_password_placeholder()}
						class="w-full rounded-lg border-[1.5px] border-gray-200 bg-gray-50 px-3.5 py-2.5 text-sm transition-all outline-none focus:border-[#2563EB] focus:bg-white focus:ring-4 focus:ring-blue-500/10"
						required
					/>
				</div>

				<button
					type="submit"
					class="w-full cursor-pointer rounded-xl bg-primary py-3 text-[15px] font-extrabold text-white transition-all hover:bg-primary-hover active:scale-[0.98]"
				>
					{activeTab === 'signin' ? m.login_btn_signin() : m.login_btn_signup()}
				</button>
			</form>

			<div
				class="my-7 flex items-center gap-3 text-[12px] font-semibold tracking-widest text-gray-400 before:h-px before:flex-1 before:bg-gray-200 after:h-px after:flex-1 after:bg-gray-200"
			>
				{m.login_divider()}
			</div>

			<button
				class="flex w-full items-center justify-center gap-2.5 rounded-xl border-[1.5px] border-gray-200 bg-white py-3 text-sm font-bold text-[#1a1a2e] transition-all hover:bg-blue-50 hover:shadow-sm"
			>
				<svg width="18" height="18" viewBox="0 0 18 18" xmlns="http://www.w3.org/2000/svg">
					<path
						d="M17.64 9.2c0-.637-.057-1.251-.164-1.84H9v3.481h4.844c-.209 1.125-.843 2.078-1.796 2.717v2.258h2.908c1.702-1.567 2.684-3.874 2.684-6.615z"
						fill="#4285F4"
					/>
					<path
						d="M9 18c2.43 0 4.467-.806 5.956-2.18l-2.908-2.259c-.806.54-1.837.86-3.048.86-2.344 0-4.328-1.584-5.036-3.711H.957v2.332C2.438 15.983 5.482 18 9 18z"
						fill="#34A853"
					/>
					<path
						d="M3.964 10.71c-.18-.54-.282-1.117-.282-1.71s.102-1.17.282-1.71V4.958H.957C.347 6.173 0 7.548 0 9s.348 2.827.957 4.042l3.007-2.332z"
						fill="#FBBC05"
					/>
					<path
						d="M9 3.58c1.321 0 2.508.454 3.44 1.345l2.582-2.58C13.463.891 11.426 0 9 0 5.482 0 2.438 2.017.957 4.958L3.964 6.29C4.672 4.163 6.656 3.58 9 3.58z"
						fill="#EA4335"
					/>
				</svg>
				{m.login_google()}
			</button>
		</div>
	</div>
</div>
