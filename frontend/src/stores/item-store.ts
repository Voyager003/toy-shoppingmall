import { defineStore } from 'pinia';

export const useProductStore = defineStore({
  id: 'product',
  state: () => ({
    productDetails: { name: '', price: 0, imageUrl: '' },
  }),
  getters: {
    getProductDetails(): { name: string; price: number; imageUrl: string } {
      return this.productDetails;
    },
  },
  actions: {
    setProductDetails(product: { name: string; price: number; imageUrl: string }) {
      this.productDetails = product;
    },
  },
});
