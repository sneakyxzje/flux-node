<script lang="ts">
	import { page } from '$app/state';
	import * as m from '$lib/paraglide/messages.js';

	const user = page.data.user;
</script>

<svelte:head>
	<title>Command Center | FluxNode Intelligence</title>
	<meta
		name="description"
		content="FluxNode Intelligence — Real-time AI-driven cold chain logistics command center"
	/>
</svelte:head>

<div class="dashboard">
	<div class="workspace">
		<div class="text-2xl font-bold">{m.dashboard_welcome({ name: user.fullname })}</div>
		<div class="mb-5 text-base-fg-4">{m.dashboard_subtitle()}</div>
	</div>
</div>

<style>
	:global(body) {
		margin: 0;
		overflow: hidden;
	}

	.dashboard {
		height: 100dvh;
		background: var(--background);
		color: var(--foreground);
		font-family: 'Geist', 'Inter', system-ui, sans-serif;
		overflow: hidden;
	}

	:global([data-slot='sidebar-wrapper']) {
		min-height: 100dvh !important;
		height: 100dvh !important;
	}

	:global(.sidebar-trigger-btn) {
		color: var(--muted-foreground);
		background: rgba(255, 255, 255, 0.05);
		border: 1px solid var(--border);
		border-radius: 6px;
		width: 30px;
		height: 30px;
	}
	:global(.sidebar-trigger-btn:hover) {
		color: var(--foreground);
		background: rgba(255, 255, 255, 0.08);
	}
</style>
