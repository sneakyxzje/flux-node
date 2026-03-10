<script lang="ts">
	import type { Snippet } from 'svelte';
	import { toggleMode, mode } from 'mode-watcher';

	let {
		title = 'Control Tower',
		trigger
	}: {
		title?: string;
		trigger?: Snippet;
	} = $props();
</script>

<header class="topbar">
	<div class="topbar-left">
		{@render trigger?.()}
		<h1 class="page-title">{title}</h1>
	</div>

	<div class="topbar-right">
		<button class="icon-btn" title="Toggle theme" onclick={toggleMode}>
			{#if mode.current === 'dark'}
				<svg
					width="15"
					height="15"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
				>
					<circle cx="12" cy="12" r="5" />
					<line x1="12" y1="1" x2="12" y2="3" /><line x1="12" y1="21" x2="12" y2="23" />
					<line x1="4.22" y1="4.22" x2="5.64" y2="5.64" /><line
						x1="18.36"
						y1="18.36"
						x2="19.78"
						y2="19.78"
					/>
					<line x1="1" y1="12" x2="3" y2="12" /><line x1="21" y1="12" x2="23" y2="12" />
					<line x1="4.22" y1="19.78" x2="5.64" y2="18.36" /><line
						x1="18.36"
						y1="5.64"
						x2="19.78"
						y2="4.22"
					/>
				</svg>
			{:else}
				<svg
					width="15"
					height="15"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
				>
					<path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
				</svg>
			{/if}
		</button>
		<button class="icon-btn" title="Settings">
			<svg
				width="15"
				height="15"
				viewBox="0 0 24 24"
				fill="none"
				stroke="currentColor"
				stroke-width="2"
			>
				<circle cx="12" cy="12" r="3" />
				<path
					d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"
				/>
			</svg>
		</button>
		<button class="icon-btn notif-btn" title="Notifications">
			<svg
				width="15"
				height="15"
				viewBox="0 0 24 24"
				fill="none"
				stroke="currentColor"
				stroke-width="2"
			>
				<path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
				<path d="M13.73 21a2 2 0 0 1-3.46 0" />
			</svg>
			<span class="notif-badge">10</span>
		</button>
		<button class="avatar-btn" title="Profile">
			<div class="avatar">
				<svg
					width="16"
					height="16"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
				>
					<path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
					<circle cx="12" cy="7" r="4" />
				</svg>
			</div>
			<span class="online-dot"></span>
		</button>
	</div>
</header>

<style>
	.topbar {
		height: 48px;
		background: var(--card);
		border-bottom: 1px solid var(--border);
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 16px;
		flex-shrink: 0;
	}

	.topbar-left {
		display: flex;
		align-items: center;
		gap: 12px;
	}

	.page-title {
		font-size: 16px;
		font-weight: 700;
		color: var(--foreground);
		margin: 0;
	}

	.topbar-right {
		display: flex;
		align-items: center;
		gap: 6px;
	}

	.icon-btn {
		width: 32px;
		height: 32px;
		display: flex;
		align-items: center;
		justify-content: center;
		background: transparent;
		border: none;
		border-radius: 7px;
		color: var(--muted-foreground);
		cursor: pointer;
		transition:
			color 0.15s,
			background 0.15s;
		position: relative;
	}
	.icon-btn:hover {
		color: var(--foreground);
		background: var(--accent);
	}

	.notif-btn {
		position: relative;
	}

	.notif-badge {
		position: absolute;
		top: 2px;
		right: 2px;
		background: #f97316;
		color: white;
		font-size: 7px;
		font-weight: 800;
		width: 14px;
		height: 14px;
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		border: 1.5px solid var(--card);
	}

	.avatar-btn {
		position: relative;
		background: transparent;
		border: none;
		cursor: pointer;
		padding: 0;
		margin-left: 4px;
	}

	.avatar {
		width: 32px;
		height: 32px;
		border-radius: 50%;
		background: color-mix(in oklch, var(--primary) 12%, transparent);
		border: 1.5px solid color-mix(in oklch, var(--primary) 30%, transparent);
		display: flex;
		align-items: center;
		justify-content: center;
		color: var(--primary);
	}

	.online-dot {
		position: absolute;
		bottom: 1px;
		right: 1px;
		width: 8px;
		height: 8px;
		background: #22c55e;
		border-radius: 50%;
		border: 1.5px solid var(--card);
	}
</style>
