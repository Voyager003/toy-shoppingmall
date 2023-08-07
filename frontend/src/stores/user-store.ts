import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: !!localStorage.getItem('accessToken'),
  }),
  actions: {
    login(token: string) {
      this.isLoggedIn = true;
      localStorage.setItem('accessToken', JSON.stringify(token));
      alert("로그인 되었습니다!");
    },
    logout() {
      this.isLoggedIn = false;
      localStorage.removeItem('accessToken');
      alert("로그아웃 되었습니다!");
    },
  },
});
