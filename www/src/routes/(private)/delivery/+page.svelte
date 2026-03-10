<script lang="ts">
	import Button from '$lib/components/ui/button/button.svelte';
	import * as m from '$lib/paraglide/messages.js';
</script>

<div class="flex justify-between">
	<h1>Delivery</h1>

	<a href="/delivery/create">
		<Button class="bg-primary font-bold text-base-fg-1">
			{m.create_shipment_title()}
		</Button>
	</a>
</div>
