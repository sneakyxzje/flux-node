<script lang="ts">
	import * as m from '$lib/paraglide/messages.js';
	import {
		type NominatimResult,
		ShipmentPriority,
		type CreateShipmentRequest
	} from '$lib/models/shipment';
	import {
		Thermometer,
		Truck,
		Clock,
		FileText,
		MapPin,
		Plus,
		ChevronRight,
		AlertCircle
	} from 'lucide-svelte';
	import { debounce } from '$lib/utils/debounce';
	import { onMount } from 'svelte';
	import 'leaflet/dist/leaflet.css';
	import type * as Leaflet from 'leaflet';
	import { defaultClient } from '$lib/services/http_instance';
	let L: typeof Leaflet;
	let mapElement = $state<HTMLElement>();

	let map = $state<L.Map>();
	onMount(async () => {
		const leaflet = await import('leaflet');
		L = leaflet.default;
		if (mapElement) {
			map = L.map(mapElement).setView([10.762622, 106.660172], 13); // Center TP.HCM
			L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
		}
	});
	let marker: L.Marker;
	let destMarker: L.Marker;
	let routeLine: L.Polyline;
	$effect(() => {
		if (!map) return;

		const origin: [number, number] = [payload.originLat, payload.originLng];
		const dest: [number, number] = [payload.destinationLat, payload.destinationLng];

		if (payload.originLat !== 0) {
			if (marker) marker.setLatLng(origin);
			else marker = L.marker(origin).addTo(map);
		}

		if (payload.destinationLat !== 0) {
			if (destMarker) destMarker.setLatLng(dest);
			else destMarker = L.marker(dest).addTo(map);
		}

		if (payload.originLat !== 0 && payload.destinationLat !== 0) {
			const points = [origin, dest];
			if (routeLine) routeLine.setLatLngs(points);
			else
				routeLine = L.polyline(points, { color: '#22c55e', weight: 3, dashArray: '5, 10' }).addTo(
					map
				);

			map.fitBounds(points, { padding: [50, 50] });
		}
	});

	let payload = $state<CreateShipmentRequest>({
		name: '',
		originAddress: '',
		originLat: 0,
		originLng: 0,
		destinationAddress: '',
		destinationLat: 0,
		destinationLng: 0,
		minTemp: 2.0,
		maxTemp: 8.0,
		temperature: 5.0,
		estimatedPickupTime: '',
		estimatedDeliveryTime: '',
		priority: ShipmentPriority.MEDIUM,
		vehicleIds: []
	});

	let suggestion = $state<NominatimResult[]>([]);
	const searchLocation = async (query: string) => {
		if (query.length < 3) return;

		const response = await fetch(
			`https://nominatim.openstreetmap.org/search?q=${query}&format=json&limit=5`
		);
		if (response.ok) {
			const data: NominatimResult[] = await response.json();
			suggestion = data;
			console.log(suggestion);
		} else {
			console.log('Error');
		}
	};
	const searchDebounce = debounce(searchLocation, 1000);

	let activeField = $state<'origin' | 'destination' | null>(null);

	const selectLocation = (item: NominatimResult) => {
		if (activeField === 'origin') {
			payload.originAddress = item.display_name;
			payload.originLat = parseFloat(item.lat);
			payload.originLng = parseFloat(item.lon);
		} else if (activeField === 'destination') {
			payload.destinationAddress = item.display_name;
			payload.destinationLat = parseFloat(item.lat);
			payload.destinationLng = parseFloat(item.lon);
		}

		suggestion = [];
		activeField = null;
	};
	const handleSubmit = async () => {
		const result = await defaultClient.post('/shipments', payload);
		if (result.ok) {
			alert('Create shipment successfully');
		} else {
			console.error('Network Error:', result.error);
			alert('Something went wrong');
		}
	};
</script>

<svelte:head>
	<title>{m.shipment_title()} | FluxNode Intelligence</title>
</svelte:head>

<div class="flex h-dvh flex-col overflow-hidden bg-background text-foreground">
	<header class="flex shrink-0 items-center justify-between border-b border-border px-5 py-3">
		<div class="flex items-center gap-3.5">
			<div>
				<h1 class="m-0 text-lg leading-tight font-bold">{m.shipment_title()}</h1>
				<p class="m-0 mt-0.5 text-xs text-muted-foreground">
					{m.shipment_subtitle()}
				</p>
			</div>
		</div>
		<div class="flex gap-2.5">
			<button
				class="cursor-pointer rounded-lg border border-border bg-transparent px-4 py-2 text-sm font-medium text-foreground transition-all hover:bg-accent"
			>
				{m.shipment_btn_save_draft()}
			</button>
			<button
				onclick={handleSubmit}
				class="flex cursor-pointer items-center gap-1.5 rounded-lg border-none bg-primary px-4 py-2 text-sm font-semibold text-base-fg-1 transition-all hover:-translate-y-px hover:shadow-lg"
			>
				<Plus size={16} />
				{m.shipment_btn_create()}
			</button>
		</div>
	</header>

	<div class="grid flex-1 grid-cols-[400px_1fr] overflow-hidden">
		<div class="flex flex-col gap-6 overflow-y-auto border-r border-border p-5">
			<!-- Shipment Name -->
			<section class="flex flex-col gap-3">
				<div
					class="flex items-center gap-1.5 text-[10px] font-bold tracking-widest text-muted-foreground uppercase"
				>
					<FileText size={13} />
					{m.shipment_name()}
				</div>
				<input
					type="text"
					placeholder={m.shipment_name_placeholder()}
					bind:value={payload.name}
					class="w-full rounded-lg border border-border bg-input px-3.5 py-2.5 text-sm transition-all outline-none focus:border-primary focus:ring-2 focus:ring-primary/20"
				/>
			</section>

			<!-- Route Selection -->
			<section class="flex flex-col gap-3">
				<div
					class="flex items-center gap-1.5 text-[10px] font-bold tracking-widest text-muted-foreground uppercase"
				>
					<MapPin size={13} />
					{m.shipment_origin()} & {m.shipment_destination()}
				</div>
				<div class="flex flex-col gap-4">
					<div class="relative flex items-center gap-3">
						<div class="h-2.5 w-2.5 shrink-0 rounded-full bg-primary ring-4 ring-primary/20"></div>
						<input
							type="text"
							bind:value={payload.originAddress}
							oninput={() => {
								activeField = 'origin';
								searchDebounce(payload.originAddress);
							}}
							placeholder={m.shipment_origin_placeholder()}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
						{#if suggestion.length > 0 && activeField === 'origin'}
							<div
								class="absolute top-full right-0 left-0 z-[100] mt-1 overflow-hidden rounded-xl border border-border bg-card shadow-2xl backdrop-blur-xl"
							>
								{#each suggestion as item (item.place_id)}
									<button
										class="w-full border-b border-border px-4 py-3 text-left text-sm transition-all last:border-0 hover:bg-primary/10 hover:text-primary"
										onclick={() => selectLocation(item)}
									>
										<div class="truncate font-medium">{item.display_name}</div>
										<div class="truncate text-[10px] opacity-60">{item.display_name}</div>
									</button>
								{/each}
							</div>
						{/if}
					</div>
					<div class="relative flex items-center gap-3">
						<div
							class="h-2.5 w-2.5 shrink-0 rounded-full bg-[oklch(0.645_0.246_16.439)] ring-4 ring-orange-500/20"
						></div>
						<input
							type="text"
							bind:value={payload.destinationAddress}
							oninput={() => {
								activeField = 'destination';
								searchDebounce(payload.destinationAddress);
							}}
							placeholder={m.shipment_destination_placeholder()}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
						{#if suggestion.length > 0 && activeField === 'destination'}
							<div
								class="absolute top-full right-0 left-0 z-[100] mt-1 overflow-hidden rounded-xl border border-border bg-card shadow-2xl backdrop-blur-xl"
							>
								{#each suggestion as item (item.place_id)}
									<button
										class="w-full border-b border-border px-4 py-3 text-left text-sm transition-all last:border-0 hover:bg-primary/10 hover:text-primary"
										onclick={() => selectLocation(item)}
									>
										<div class="truncate font-medium">{item.display_name}</div>
										<div class="truncate text-[10px] opacity-60">{item.display_name}</div>
									</button>
								{/each}
							</div>
						{/if}
					</div>
				</div>
			</section>

			<!-- Cold Chain Configuration -->
			<section class="flex flex-col gap-3">
				<div
					class="flex items-center gap-1.5 text-[10px] font-bold tracking-widest text-muted-foreground uppercase"
				>
					<Thermometer size={13} />
					{m.shipment_min_temp()} & {m.shipment_max_temp()}
				</div>
				<div class="grid grid-cols-2 gap-3">
					<div class="space-y-1.5">
						<label class="text-[11px] font-medium text-muted-foreground"
							>{m.shipment_min_temp()} (°C)</label
						>
						<input
							type="number"
							step="0.1"
							bind:value={payload.minTemp}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
					</div>
					<div class="space-y-1.5">
						<label class="text-[11px] font-medium text-muted-foreground"
							>{m.shipment_max_temp()} (°C)</label
						>
						<input
							type="number"
							step="0.1"
							bind:value={payload.maxTemp}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
					</div>
				</div>
			</section>

			<!-- Schedule SLA -->
			<section class="flex flex-col gap-3">
				<div
					class="flex items-center gap-1.5 text-[10px] font-bold tracking-widest text-muted-foreground uppercase"
				>
					<Clock size={13} />
					{m.shipment_pickup_time()} & {m.shipment_delivery_time()}
				</div>
				<div class="space-y-3">
					<div class="space-y-1.5">
						<label class="text-[11px] font-medium text-muted-foreground"
							>{m.shipment_pickup_time()}</label
						>
						<input
							type="datetime-local"
							bind:value={payload.estimatedPickupTime}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
					</div>
					<div class="space-y-1.5">
						<label class="text-[11px] font-medium text-muted-foreground"
							>{m.shipment_delivery_time()}</label
						>
						<input
							type="datetime-local"
							bind:value={payload.estimatedDeliveryTime}
							class="w-full rounded-lg border border-border bg-input px-3 py-2 text-sm outline-none focus:border-primary"
						/>
					</div>
				</div>
			</section>
		</div>

		<div class="flex flex-col gap-5 overflow-y-auto bg-accent/5 p-5">
			<div class="overflow-hidden rounded-xl border border-border bg-card shadow-sm">
				<div
					class="border-b border-border bg-muted/30 px-3.5 py-2.5 text-[10px] font-bold tracking-widest text-muted-foreground uppercase"
				>
					MAP OVERVIEW
				</div>
				<div bind:this={mapElement} class="relative h-[250px] bg-[#0d1117]">
					<div class="flex h-full items-center justify-center text-muted-foreground/30">
						{#if !payload.originAddress && !payload.destinationAddress}
							<div class="flex flex-col items-center gap-2">
								<AlertCircle size={32} />
								<p class="text-xs italic">Enter origin & destination to preview route</p>
							</div>
						{:else}
							<svg class="absolute inset-0 h-full w-full text-foreground/20" viewBox="0 0 400 250">
								<path
									d="M 60 180 C 120 160, 160 100, 220 110 C 280 120, 320 90, 360 80"
									stroke="currentColor"
									stroke-width="2"
									stroke-dasharray="6,3"
									fill="none"
								/>
								<circle cx="60" cy="180" r="5" fill="var(--color-primary)" />
								<circle cx="360" cy="80" r="5" fill="oklch(0.645 0.246 16.439)" />
							</svg>
							<div
								class="absolute bottom-10 left-4 max-w-[140px] truncate rounded-full border border-border bg-card px-2.5 py-1 text-[10px] font-medium"
							>
								{payload.originAddress || 'Origin'}
							</div>
							<div
								class="absolute top-10 right-4 max-w-[140px] truncate rounded-full border border-border bg-card px-2.5 py-1 text-[10px] font-medium"
							>
								{payload.destinationAddress || 'Destination'}
							</div>
						{/if}
					</div>
				</div>
			</div>

			<div class="rounded-xl border border-border bg-card p-4 shadow-sm">
				<div class="mb-3 text-[10px] font-bold tracking-widest text-muted-foreground uppercase">
					{m.shipment_priority()}
				</div>
				<div class="flex gap-2">
					{#each Object.values(ShipmentPriority) as p (p)}
						<button
							onclick={() => (payload.priority = p)}
							class="flex-1 rounded-lg border py-2 text-xs font-bold transition-all {payload.priority ===
							p
								? 'border-primary bg-primary text-white shadow-md'
								: 'border-border bg-transparent text-muted-foreground hover:bg-accent'}"
						>
							{p === ShipmentPriority.LOW
								? m.shipment_priority_low()
								: p === ShipmentPriority.MEDIUM
									? m.shipment_priority_medium()
									: m.shipment_priority_high()}
						</button>
					{/each}
				</div>
			</div>

			<div class="rounded-xl border border-border bg-card p-4 shadow-sm">
				<div class="mb-3 text-[10px] font-bold tracking-widest text-muted-foreground uppercase">
					{m.shipment_vehicles()}
				</div>
				<button
					class="flex w-full cursor-pointer items-center gap-3 rounded-lg border border-dashed border-border bg-transparent p-4 text-sm text-muted-foreground transition-all hover:border-primary hover:text-primary"
				>
					<Truck size={18} />
					<span>{m.shipment_select_vehicle()}</span>
					<ChevronRight size={14} class="ml-auto" />
				</button>
			</div>
		</div>
	</div>
</div>
