<template>
  <div class="flex items-center justify-center h-screen">
    <div class="w-full max-w-sm p-6 m-auto bg-white rounded-lg shadow-md dark:bg-gray-800">
      <div class="flex justify-center mx-auto">
        <img class="w-auto h-7 sm:h-8" src="https://merakiui.com/images/logo.svg" alt="">
      </div>

      <form class="mt-6" @submit.prevent="signup">
        <div>
          <label for="email" class="block text-sm text-gray-800 dark:text-gray-200">이메일</label>
          <input v-model="email" type="text" class="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-lg dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40" />
        </div>
        <div class="mt-4">
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm text-gray-800 dark:text-gray-200">비밀번호</label>
          </div>
          <input v-model="password" type="password" class="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-lg dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40" />
        </div>

        <div class="mt-4 flex">
          <div class="flex items-center mr-28">
            <input id="seller-checkbox" v-model="role" type="radio" value="ROLE_SELLER" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
            <label for="seller-checkbox" class="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">판매자</label>
          </div>
          <div class="flex items-center">
            <input id="customer-checkbox" v-model="role" type="radio" value="ROLE_CUSTOMER" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
            <label for="customer-checkbox" class="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">구매자</label>
          </div>
        </div>
        <div class="mt-6">
          <button type="submit" class="w-full px-6 py-2.5 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-gray-800 rounded-lg hover:bg-gray-700 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-50">
            회원가입
          </button>
        </div>
      </form>
      <div class="flex items-center justify-between mt-4">
        <span class="w-1/5 border-b dark:border-gray-600 lg:w-1/5"></span>
        <a class="text-xs text-center text-gray-500 uppercase dark:text-gray-400">
          or login with Social Media
        </a>
        <span class="w-1/5 border-b dark:border-gray-400 lg:w-1/5"></span>
      </div>
      <div class="flex items-center mt-6 -mx-2">
        <button type="button" class="flex items-center justify-center w-full px-6 py-2 mx-2 text-sm font-medium text-white transition-colors duration-300 transform bg-blue-500 rounded-lg hover:bg-blue-400 focus:bg-blue-400 focus:outline-none">
          <svg class="w-4 h-4 mx-2 fill-current" viewBox="0 0 24 24">
            <path d="M12.24 10.285V14.4h6.806c-.275 1.765-2.056 5.174-6.806 5.174-4.095 0-7.439-3.389-7.439-7.574s3.345-7.574 7.439-7.574c2.33 0 3.891.989 4.785 1.849l3.254-3.138C18.189 1.186 15.479 0 12.24 0c-6.635 0-12 5.365-12 12s5.365 12 12 12c6.926 0 11.52-4.869 11.52-11.726 0-.788-.085-1.39-.189-1.989H12.24z">
            </path>
          </svg>
          <span class="hidden mx-2 sm:inline">Sign in with Google</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "SignupPage",
  data() {
    return {
      email: "",
      password: "",
      role: ""
    };
  },
  methods: {
    signup() {
      const user = {
        email: this.email,
        password: this.password,
        role: this.role
      };
      axios.post("/signup", JSON.stringify(user), {
        headers: {
          "Content-Type": "application/json"
        },
      })
        .then(response => {
          router.replace("/login");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
</style>
