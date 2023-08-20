import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import ProductsView from "@/views/ProductsView.vue";
import SignupPage from "@/components/SignupPage.vue";
import LoginPage from "@/components/LoginPage.vue";
import ItemRegisterPage from "@/components/ItemRegisterPage.vue";
import { useAuthStore } from "@/stores/user-store";
import ItemPage from "@/components/ItemPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: HomeView
    },
    {
      path: "/products",
      component: ProductsView
    },
    {
      path: "/signup",
      component: SignupPage
    },
    {
      path: "/login",
      component: LoginPage
    },
    {
      path: '/products/:id',
      component: ItemPage,
      props: true,
    },
    {
      path: "/products/write",
      component: ItemRegisterPage,
      beforeEnter: (to, from, next) => {
        const authStore = useAuthStore();
        if (authStore.isLoggedIn) {
          next();
        } else {
          alert("로그인이 필요한 기능입니다!");
          next("/login");
        }
      }
    },
  ]
});

export default router;
