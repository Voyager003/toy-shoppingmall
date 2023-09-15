<template>
  <section class="text-gray-700 body-font overflow-hidden bg-white">
    <div class="container px-5 py-24 mx-auto">
      <div class="lg:w-4/5 mx-auto flex flex-wrap">
        <img alt="ecommerce" class="lg:w-1/2 w-full object-cover object-center rounded border border-gray-200"
             :src="'/products/' + item.imagePath" />
        <div class="lg:w-1/2 w-full lg:pl-10 lg:py-6 mt-6 lg:mt-0">
          <h1 class="text-gray-900 text-3xl title-font font-medium mb-1"> {{ item.name }} </h1>

          <p class="leading-relaxed">
            상품의 정보입니다.
          </p>

          <div class="flex mt-6 items-center pb-5 border-b-2 border-gray-200 mb-5">
            <div class="flex items-center">
              <span class="mr-3"> 재고: </span>
              <span class="text-gray-600">{{ item.stockQuantity }}</span>
            </div>
          </div>

          <span class="title-font font-medium text-2xl text-gray-900"> {{ item.price }} 원 </span>
          <button class="flex ml-auto text-white bg-red-500 border-0 py-2 px-6 focus:outline-none hover:bg-red-600 rounded">
            장바구니에 담기
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script lang="ts">
import { onMounted, reactive } from "vue";
import axios from "axios";

export default {
  name: "ItemDetail",
  setup() {
    const item = reactive({
      imagePath: "",
      name: "",
      price: 0,
      stockQuantity: 0
    });
    onMounted(async () => {
      try {
        const itemId = 1;
        const response = await axios.get(`/products/${itemId}`);
        item.imagePath = response.data.imagePath;
        item.name = response.data.name;
        item.price = response.data.price;
        item.stockQuantity = response.data.stockQuantity;
      } catch (error) {
        console.error("상품 정보를 가져오는 데 실패했습니다.", error);
      }
    });
    return { item };
  }
};
</script>

<style scoped>
</style>