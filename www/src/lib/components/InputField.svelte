<script>
	/** @type {{ label: string, type?: string, placeholder?: string, value?: string, name: string }} */
	let { label, type = 'text', placeholder = '', value = $bindable(''), name } = $props();
</script>

<div class="flex flex-col gap-1.5">
	<label for={name} class="text-[13px] font-bold text-gray-800">
		{label}
	</label>
	<input
		{type}
		{name}
		id={name}
		{placeholder}
		bind:value
		class="
      w-full rounded-lg border border-gray-200 bg-gray-50 px-3.5
      py-2.5 text-sm text-gray-800 placeholder-gray-300
      transition-all duration-200 outline-none
      focus:border-blue-500 focus:bg-white focus:ring-3 focus:ring-blue-500/10
    "
	/>
</div>
