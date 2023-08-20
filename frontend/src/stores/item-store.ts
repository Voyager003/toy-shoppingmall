import { defineStore } from 'pinia';

export const useProductStore = defineStore({
  id: 'product',
  state: () => ({
    productDetails: { name: '', price: 0 },
  }),
  getters: {
    getProductDetails(): { name: string; price: number } {
      return this.productDetails;
    },
  },
  actions: {
    setProductDetails(product: { name: string; price: number }) {
      this.productDetails = product;
    },
  },
});
