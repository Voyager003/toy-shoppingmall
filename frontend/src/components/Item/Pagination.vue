<template>
  <nav aria-label="Page navigation">
    <ul class="list-style-none flex justify-center">
      <li>
        <a @click="onPageChange(currentPage - 1)" class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-600 transition-all duration-300 hover:bg-neutral-100 dark:text-white dark:hover:bg-neutral-700 dark:hover:text-white"> Previous </a>
      </li>
      <li v-for="(paging, index) in pages" :key="index">
        <a @click="onPageChange(paging - 1)" :class="paging - 1 === currentPage ? 'currentPage' : ''" class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-600 transition-all duration-300 hover:bg-neutral-100 dark:text-white dark:hover:bg-neutral-700 dark:hover:text-white">
          {{ paging }}
        </a>
      </li>
      <li>
        <a @click="onPageChange(currentPage + 1)" class="relative block rounded bg-transparent px-3 py-1.5 text-sm text-neutral-600 transition-all duration-300 hover:bg-neutral-100 dark:text-white dark:hover:bg-neutral-700 dark:hover:text-white"> Next </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'Pagination',
  props: ['currentPage', 'totalPages', 'pageChange'],
  data() {
    return {};
  },
  computed: {
    pages: function() {
      const list = [];
      for (let index = this.startPage; index <= this.endPage; index += 1) { list.push(index); }
      return list;
    },
    startPage() {
      return parseInt(this.currentPage / 5) * 5 + 1;
    },
    endPage() {
      let lastPage = parseInt(this.currentPage / 5) * 5 + 5;
      return lastPage <= this.totalPages ? lastPage : this.totalPages;
    }
  },
  methods: {
    onPageChange(val) {
      if (val < 0) {
        alert('첫 페이지입니다.');
        return;
      }
      if (val >= this.totalPages) {
        alert('마지막 페이지입니다.');
        return;
      }
      const param = {
        requestPage: val,
      };
      this.pageChange(param);
    }
  }
}
</script>

<style scoped>
</style>