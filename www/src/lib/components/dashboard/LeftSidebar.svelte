<script lang="ts">
	import { page } from '$app/state';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import * as m from '$lib/paraglide/messages.js';
	import { i18n } from '$lib/i18n';
	import { LayoutDashboard, Truck, Van, Thermometer, Bell, BarChart3, Globe } from 'lucide-svelte';

	type AvailableRoutes = Parameters<typeof i18n.resolveRoute>[0];

	const navItems = [
		{
			id: 'dashboard',
			get label() {
				return m.nav_dashboard();
			},
			href: '/dashboard',
			icon: LayoutDashboard
		},
		{
			id: 'delivery',
			get label() {
				return m.nav_delivery();
			},
			href: '/delivery',
			icon: Truck
		},
		{
			id: 'vehicle',
			get label() {
				return m.nav_vehicle();
			},
			href: '/vehicle',
			icon: Van
		},
		{
			id: 'telemetry',
			get label() {
				return m.nav_telemetry();
			},
			href: '/dashboard/telemetry',
			icon: Thermometer
		},
		{
			id: 'alerts',
			get label() {
				return m.nav_alerts();
			},
			href: '/dashboard/alerts',
			icon: Bell
		},
		{
			id: 'analytics',
			get label() {
				return m.nav_analytics();
			},
			href: '/dashboard/analytics',
			icon: BarChart3
		}
	];

	const isItemActive = (href: string) => i18n.route(page.url.pathname as AvailableRoutes) === href;

	const switchLangUrl = $derived.by(() => {
		const pathname = page.url.pathname as string;
		const isVi = pathname.startsWith('/vi/');
		const nextLang = isVi ? 'en' : 'vi';
		const canonicalPath = i18n.route(pathname as AvailableRoutes);
		return i18n.resolveRoute(canonicalPath as AvailableRoutes, nextLang) + page.url.search;
	});

	const isVietnamese = $derived((page.url.pathname as string).startsWith('/vi/'));
</script>

<Sidebar.Root collapsible="icon" class="dashboard-sidebar">
	<Sidebar.Header class="sidebar-logo-header">
		<Sidebar.Menu>
			<Sidebar.MenuItem>
				<Sidebar.MenuButton
					size="lg"
					class="sidebar-logo-btn"
					tooltipContent="FluxNode Intelligence"
				>
					<div class="logo-icon">
						<svg width="18" height="18" viewBox="0 0 24 24" fill="none">
							<rect x="2" y="2" width="9" height="9" rx="1.5" fill="#4ade80" />
							<rect x="13" y="2" width="9" height="9" rx="1.5" fill="#4ade80" opacity="0.6" />
							<rect x="2" y="13" width="9" height="9" rx="1.5" fill="#4ade80" opacity="0.6" />
							<rect x="13" y="13" width="9" height="9" rx="1.5" fill="#4ade80" opacity="0.3" />
						</svg>
					</div>
					<span class="logo-label">FluxNode</span>
				</Sidebar.MenuButton>
			</Sidebar.MenuItem>
		</Sidebar.Menu>
	</Sidebar.Header>

	<!-- Nav items -->
	<Sidebar.Content>
		<Sidebar.Group>
			<Sidebar.GroupContent>
				<Sidebar.Menu>
					{#each navItems as item (item.id)}
						<Sidebar.MenuItem>
							<Sidebar.MenuButton
								isActive={isItemActive(item.href)}
								tooltipContent={item.label}
								class="nav-menu-btn"
							>
								{#snippet child({ props })}
									<a href={item.href} {...props}>
										<svelte:component this={item.icon} size={18} />
										<span>{item.label}</span>
									</a>
								{/snippet}
							</Sidebar.MenuButton>
						</Sidebar.MenuItem>
					{/each}
				</Sidebar.Menu>
			</Sidebar.GroupContent>
		</Sidebar.Group>
	</Sidebar.Content>

	<Sidebar.Footer>
		<Sidebar.Menu>
			<Sidebar.MenuItem>
				<Sidebar.MenuButton
					tooltipContent={isVietnamese ? 'Switch to English' : 'Chuyển sang Tiếng Việt'}
					class="lang-btn"
					onclick={() => (window.location.href = switchLangUrl)}
				>
					<Globe size={16} />
					<span class="lang-label">
						{isVietnamese ? '🇻🇳 Tiếng Việt' : '🇬🇧 English'}
					</span>
				</Sidebar.MenuButton>
			</Sidebar.MenuItem>
		</Sidebar.Menu>
	</Sidebar.Footer>

	<Sidebar.Rail />
</Sidebar.Root>

<style>
	:global([data-slot='sidebar-menu-button'][data-active='true']) {
		background: color-mix(in oklch, var(--sidebar-primary) 12%, transparent) !important;
		color: var(--sidebar-primary) !important;
	}

	.logo-icon {
		width: 28px;
		height: 28px;
		background: color-mix(in oklch, var(--sidebar-primary) 8%, transparent);
		border: 1px solid color-mix(in oklch, var(--sidebar-primary) 25%, transparent);
		border-radius: 7px;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-shrink: 0;
	}

	.logo-label {
		font-size: 12px;
		font-weight: 800;
		letter-spacing: 0.05em;
		color: var(--sidebar-foreground);
	}

	.lang-label {
		font-size: 11px;
		font-weight: 600;
	}
</style>
