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

          <input type="number" v-model.number="count" min="1" max="5" class="mr-2 w-16 h-8 border border-gray-300 rounded focus:outline-none focus:border-blue-500" />

          <span class="title-font font-medium text-2xl text-gray-900"> {{ item.price }} 원 </span>

          <button @click="orderItem" class="flex ml-auto text-white bg-red-500 border-0 py-2 px-6 focus:outline-none hover:bg-red-600 rounded">
            구매하기
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
import axios from "axios";
import { useAuthStore } from "@/stores/user-store";
import router from "@/router";

export default {
  name: "ItemDetail",
  setup() {
    const item = reactive({
      imagePath: "",
      name: "",
      price: 0,
      stockQuantity: 0
    });

    const itemId = ref(null);
    const count = ref(1);

    onMounted(async () => {
      try {
        itemId.value = 1;
        const response = await axios.get(`/products/${itemId.value}`);
        item.imagePath = response.data.imagePath;
        item.name = response.data.name;
        item.price = response.data.price;
        item.stockQuantity = response.data.stockQuantity;
      } catch (error) {
        console.error("상품 정보를 가져오는 데 실패했습니다.", error);
      }
    });
    const orderItem = async () => {
      const authStore = useAuthStore();
      if (!authStore.isLoggedIn) {
        alert("로그인이 필요합니다.");
        router.push("/login");
        return;
      }
      try {
        const orderRequest = {
          itemId: itemId.value,
          count: count.value
        };
        const tokenData = JSON.parse(localStorage.getItem('accessToken'));
        const token = tokenData.token;
        const response = await axios.post('/api/orders', orderRequest, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.status === 200) {
          alert('상품을 성공적으로 주문했습니다.');
          await router.replace(`/products`);
        }
      } catch (error) {
        console.error('주문을 실패했습니다.', error);
      }
    };

    return { item, itemId, count, orderItem };
  },
};
</script>

<style scoped>
</style>
