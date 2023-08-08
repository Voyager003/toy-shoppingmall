<template>
  <section class="py-12 bg-white sm:py-16 lg:py-20">
    <div class="px-4 mx-auto sm:px-6 lg:px-8 max-w-7xl">
      <div class="max-w-md mx-auto text-center mb-8">
        <h2 class="text-2xl font-bold text-gray-900 sm:text-3xl">상품 등록</h2>
      </div>

      <form class="max-w-md mx-auto space-y-6">
        <div>
          <label for="product-name" class="block text-sm font-medium text-gray-700">상품 명</label>
          <input type="text" id="product-name" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div>
          <label for="product-price" class="block text-sm font-medium text-gray-700">상품 가격</label>
          <input type="number" id="product-price" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div>
          <label for="product-stock" class="block text-sm font-medium text-gray-700">상품 재고</label>
          <input type="number" id="product-stock" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div>
          <label for="product-image" class="block text-sm font-medium text-gray-700">사진 업로드</label>
          <input type="file" id="product-image" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div>
          <label for="category" class="block text-sm font-medium text-gray-700">카테고리 선택</label>
          <select v-model="selectedCategory" id="category" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            <option value="album">앨범</option>
            <option value="book">책</option>
            <option value="movie">영화</option>
          </select>
        </div>

        <div v-if="selectedCategory === 'album'">
          <label for="artist" class="block text-sm font-medium text-gray-700">아티스트</label>
          <input type="text" id="artist" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div v-if="selectedCategory === 'book'">
          <label for="author" class="block text-sm font-medium text-gray-700">저자</label>
          <input type="text" id="author" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div v-if="selectedCategory === 'movie'">
          <label for="director" class="block text-sm font-medium text-gray-700">감독</label>
          <input type="text" id="director" class="mt-1 block w-full p-2.5 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
        </div>

        <div class="flex justify-end">
          <button class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
            상품 등록
          </button>
        </div>
      </form>
    </div>
  </section>
</template>

<script>
export default {
  name: "ItemRegisterPage",
  data() {
    return {
      selectedCategory: "album"
    };
  }
}
</script>

<style scoped>
</style>
