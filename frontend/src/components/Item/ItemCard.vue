<template>
  <div class="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 mt-6">
    <div v-for="(item, index) in itemList" :key="'item-' + index" @click="goItemDetail(item.id)"
         class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden ml-4 mr-4">
      <div class="px-5 py-3">
        <h3 class="text-gray-700 uppercase">{{ item.name }}</h3>
        <span class="text-gray-500 mt-2">{{ item.price }} 원</span>
      </div>
    </div>
  </div>
  <Pagination :currentPage="currentPage" :totalPages="totalPages" :pageChange="pageChange" />
</template>

<script>
import Pagination from "@/components/Item/Pagination.vue";
import router from "@/router";
import axios from "axios";

export default {
  name: "ItemCard",
  props: ["itemList", "currentPage", "totalPages", "pageChange"],
  components: { Pagination },
  methods: {
    async goItemDetail(itemId) {
      try {
        await axios.get(`/products/${itemId}`);
        await router.push(`/products/${itemId}`);
      } catch (error) {
        console.error("상품 정보를 가져오는 도중 오류가 발생했습니다.");
      }
    },
  },
};
</script>
<style scoped>

</style>