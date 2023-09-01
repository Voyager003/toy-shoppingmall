<template>
  <div class="grid gap-6 grid-cols-1 xl:grid-cols-3 mt-6">
    <div class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden">
      <div class="flex items-end justify-end h-56 w-full bg-cover" :style="'background-image: url(/products/' + item.imagePath + ')'">
      </div>
      <div class="px-5 py-3">
        <h3 class="text-gray-700 uppercase">{{ item.name }}</h3>
        <span class="text-gray-500 mt-2"> {{ item.price }} 원 </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProductsList",
  props: {
    item: Object
  },
  setup() {
    return {};
  },
}
</script>

<style scoped>
</style>