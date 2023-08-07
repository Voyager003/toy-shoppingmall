<template>
  <div class="bg-white">
    <header class="absolute inset-x-0 top-0 z-50">
      <nav class="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div class="flex lg:flex-1">
          <a href="/" class="-m-1.5 p-1.5">
            <span class="sr-only">Your Company</span>
            <img class="h-8 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600" alt="">
          </a>
        </div>
        <div class="flex lg:hidden">
          <button type="button" class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700">
            <span class="sr-only">Open main menu</span>
            <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
              <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
            </svg>
          </button>
        </div>
        <div class="hidden lg:flex lg:flex-1 lg:gap-x-12 lg:justify-end">
          <a href="https://github.com/Voyager003/toy-shoppingmall" class="text-sm font-semibold leading-6 text-gray-900" target="_blank">Github</a>
          <router-link to="/products" class="text-sm font-semibold leading-6 text-gray-900">Products</router-link>
          <router-link v-if="!isLoggedIn" to="/login" class="text-sm font-semibold leading-6 text-gray-900">Login<span aria-hidden="true">&rarr;</span></router-link>
          <button v-else @click="logout" class="text-sm font-semibold leading-6 text-gray-900 cursor-pointer">Logout</button>
        </div>
      </nav>
    </header>
  </div>
</template>

<script>
import { computed } from "vue";
import router from "@/router";
import { useAuthStore } from "@/stores/user-store";

export default {
  name: "MainHeader",
  setup() {
    const authStore = useAuthStore();

    const logout = () => {
      authStore.logout();
      localStorage.removeItem("accessToken");
      router.replace("/");
    };

    const isLoggedIn = computed(() => authStore.isLoggedIn);

    return {
      logout,
      isLoggedIn,
    };
  },
};

</script>

<style scoped>
</style>