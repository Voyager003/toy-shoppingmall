<template>
  <section class="py-12 bg-white sm:py-16 lg:py-20">
    <div class="px-4 mx-auto sm:px-6 lg:px-8 max-w-7xl">
      <div class="max-w-md mx-auto text-center mb-8">
        <h2 class="text-2xl font-bold text-gray-900 sm:text-3xl">상품 목록</h2>
        <p class="mt-4 text-base font-normal leading-7 text-gray-600">원하는 상품을 등록하거나 구매할 수 있습니다.</p>
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

      <div class="mt-8 flex justify-end">
        <router-link to="/products/write" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
          상품 등록
        </router-link>
      </div>

      <div class="mt-6 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
        <div v-for="(product, index) in products" :key="index" class="group relative">
          <div class="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-80">
            <img :src="product.image" :alt="product.name" class="h-full w-full object-cover object-center lg:h-full lg:w-full">
          </div>
          <div class="mt-4 flex justify-between">
            <div>
              <h3 class="text-sm text-gray-700">
                <a href="#">
                  <span aria-hidden="true" class="absolute inset-0"></span>
                  {{ product.name }}
                </a>
              </h3>
              <p class="mt-1 text-sm text-gray-500">{{ product.category }}</p>
            </div>
            <p class="text-sm font-medium text-gray-900">${{ product.price }}</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
name: "ProductsPage",
  data() {
    return {
      products: [
        {
          name: "Basic Tee",
          category: "Clothing",
          price: 35,
          image: "https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg"
        },
      ]
    };
  }
}
</script>

<style scoped>

</style>