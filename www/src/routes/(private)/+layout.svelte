<script lang="ts">
	import { SidebarProvider, SidebarTrigger } from '$lib/components/ui/sidebar/index.js';
	import LeftSidebar from '$lib/components/dashboard/LeftSidebar.svelte';
	import TopBar from '$lib/components/dashboard/TopBar.svelte';

	let { children } = $props();
</script>

<svelte:head>
	<title>Command Center | FluxNode Intelligence</title>
	<meta
		name="description"
		content="FluxNode Intelligence — Real-time AI-driven cold chain logistics command center"
	/>
</svelte:head>

<div>
	<SidebarProvider style="--sidebar-width: 200px; --sidebar-width-icon: 52px;">
		<LeftSidebar />

		<div class="main-area">
			<TopBar title="FluxNode Intelligence">
				{#snippet trigger()}
					<SidebarTrigger class="sidebar-trigger-btn" />
				{/snippet}
			</TopBar>

			<div class="workspace p-5">
				{@render children()}
			</div>
		</div>
	</SidebarProvider>
</div>

<style>
	:global(body) {
		margin: 0;
		overflow: hidden;
	}

	:global([data-slot='sidebar-wrapper']) {
		min-height: 100dvh !important;
		height: 100dvh !important;
	}

	.main-area {
		display: flex;
		flex-direction: column;
		flex: 1;
		min-width: 0;
		overflow: hidden;
		height: 100dvh;
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
