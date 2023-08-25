<template>
  <section class="py-12 bg-white sm:py-16 lg:py-20">
    <div class="px-2 mx-auto sm:px-6 lg:px-8 max-w-7xl">
      <div class="max-w-md mx-auto text-center mb-8">
        <h2 class="text-2xl font-bold text-gray-900 sm:text-3xl">상품 목록</h2>
      </div>

      <form>
        <label for="default-search" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
        <div class="relative">
          <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
            </svg>
          </div>
          <input type="search" id="default-search" class="block w-full p-4 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="상품을 검색하세요." required>
          <button type="submit" class="text-white absolute right-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search</button>
        </div>
      </form>

      <div class="mt-4 flex justify-end">
        <button @click="goItemRegisterPage" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
          상품 등록
        </button>
      </div>
    </div>
    <div class="px-2 mx-auto sm:px-6 lg:px-8 max-w-7xl">
      <h3 class="text-gray-600 text-2xl font-medium">Products</h3>
      <div class="grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-3 mt-6">
        <div class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden">
          <div class="flex items-end justify-end h-56 w-full bg-cover" :style="'background-image: url(/products/' + products.imageUrl + ')'">
          </div>
          <div class="px-5 py-3">
            <h3 class="text-gray-700 uppercase"> {{ products.name }}</h3>
            <span class="text-gray-500 mt-2">{{ products.price }} 원</span>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script lang="ts">

import router from "@/router";
import { useProductStore } from "@/stores/item-store";

export default {
  name: "ProductsPage",
  computed: {
    products(): { name: string; price: number; imageUrl: string } {
      const productStore = useProductStore();
      return productStore.productDetails;
    },
  },
  methods: {
    goItemRegisterPage() {
      const accessToken = localStorage.getItem("accessToken");

      if (accessToken === null) {
        alert("로그인이 필요한 기능입니다.");
        router.push("/login");
        return;
      }

      const tokenData: { roles: string[] } = JSON.parse(accessToken);
      const roles = tokenData ? tokenData.roles : [];

      if (roles.length === 0) {
        alert("로그인이 필요한 기능입니다.");
        router.push("/login");
      } else if (roles.includes("ROLE_SELLER")) {
        router.push("/products/write");
      } else {
        alert("판매자만 등록이 가능합니다.");
      }
    }
  },
}
</script>

<style scoped>
</style>