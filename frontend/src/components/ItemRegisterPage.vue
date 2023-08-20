<template>
  <section class="py-12 bg-white sm:py-16 lg:py-20">
    <div class="px-4 mx-auto sm:px-6 lg:px-8 max-w-7xl">
      <div class="max-w-md mx-auto text-center mb-8">
        <h2 class="text-2xl font-bold text-gray-900 sm:text-3xl">상품 등록</h2>
      </div>

      <form class="max-w-md mx-auto space-y-6" @submit.prevent="registerItem">
        <div>
          <label for="product-name" class="block text-sm font-medium text-gray-700">상품 명</label>
          <input v-model="name" type="text" id="product-name" :class="{ 'border-red-500': showErrors && !isNameValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && !isNameValid" class="mt-1 text-sm text-red-500">상품 명을 입력해주세요.</p>
        </div>

        <div>
          <label for="product-price" class="block text-sm font-medium text-gray-700">상품 가격</label>
          <input v-model="price" id="product-price" :class="{ 'border-red-500': showErrors && !isPriceValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && !isPriceValid" class="mt-1 text-sm text-red-500">1000원 이상 50000원 이하의 가격을 입력해주세요.</p>
        </div>

        <div>
          <label for="product-stock" class="block text-sm font-medium text-gray-700">상품 재고</label>
          <input v-model.number="stockQuantity" type="number" id="product-stock" :class="{ 'border-red-500': showErrors && !isStockQuantityValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && !isStockQuantityValid" class="mt-1 text-sm text-red-500">최소 1개 이상 10개 이하의 재고를 입력해주세요.</p>
        </div>

        <div>
          <label for="product-image" class="block text-sm font-medium text-gray-700">사진 업로드</label>
          <input type="file" id="product-image" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div>
          <label for="category" class="block text-sm font-medium text-gray-700">카테고리 선택</label>
          <select v-model="category" id="category" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            <option value="album">앨범</option>
            <option value="book">책</option>
            <option value="movie">영화</option>
          </select>
        </div>

        <div v-if="category === 'album'">
          <label for="artist" class="block text-sm font-medium text-gray-700">아티스트</label>
          <input v-model="categoryDetail" type="text" id="categoryDetail" :class="{ 'border-red-500': showErrors && !isCategoryDetailValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && category === 'album' && !isCategoryDetailValid" class="mt-1 text-sm text-red-500">아티스트를 입력해주세요.</p>
        </div>

        <div v-if="category === 'book'">
          <label for="author" class="block text-sm font-medium text-gray-700">저자</label>
          <input v-model="categoryDetail" type="text" id="categoryDetail" :class="{ 'border-red-500': showErrors && !isCategoryDetailValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && category === 'book' && !isCategoryDetailValid" class="mt-1 text-sm text-red-500">저자를 입력해주세요.</p>
        </div>

        <div v-if="category === 'movie'">
          <label for="director" class="block text-sm font-medium text-gray-700">감독</label>
          <input v-model="categoryDetail" type="text" id="categoryDetail" :class="{ 'border-red-500': showErrors && !isCategoryDetailValid }" class="mt-1 block w-full p-2.5 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <p v-if="showErrors && category === 'movie' && !isCategoryDetailValid" class="mt-1 text-sm text-red-500">감독을 입력해주세요.</p>
        </div>

        <div class="flex justify-end">
          <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
            상품 등록
          </button>
        </div>
      </form>
    </div>
  </section>
</template>

<script>
import { useProductStore } from "@/stores/item-store";
import axios from "axios";
import router from "@/router";

export default {
  name: "ItemRegisterPage",
  data() {
    return {
      name: "",
      price: null,
      stockQuantity: null,
      category: "album",
      categoryDetail: "",
      showErrors: false
    };
  },
  computed: {
    isNameValid() {
      return this.name.trim() !== "";
    },
    isPriceValid() {
      return this.price >= 1000 && this.price <= 50000;
    },
    isStockQuantityValid() {
      return this.stockQuantity >= 1 && this.stockQuantity <= 10;
    },
    isCategoryDetailValid() {
      if (this.category === 'album' || this.category === 'book' || this.category === 'movie') {
        return this.categoryDetail.trim() !== "";
      } else {
        return true;
      }
    },
  },
  methods: {
    async registerItem() {
      this.showErrors = true;
      if (
        !this.isNameValid ||
        !this.isPriceValid ||
        !this.isStockQuantityValid ||
        !this.isCategoryDetailValid
      ) {
        return;
      }
      const tokenData = JSON.parse(localStorage.getItem('accessToken'));
      const token = tokenData.token;
      try {
        const response = await axios.post(
          '/products/register',
          {
            name: this.name,
            price: this.price,
            stockQuantity: this.stockQuantity,
            category: this.category,
            categoryDetail: this.categoryDetail,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 200) {
          const productId = response.data;
          useProductStore().setProductDetails({
            name: this.name,
            price: this.price,
          });
          await router.push(`/products/${productId}`);
          alert('상품이 등록되었습니다.');
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style scoped>
</style>
