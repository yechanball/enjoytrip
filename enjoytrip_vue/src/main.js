import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "@/api/vueBootstrap.js";
import "@/api/aos.js";

import VueCookies from "vue-cookies";
//쿠키를 사용한다.
Vue.use(VueCookies);
//쿠키의 만료일은 7일이다. (글로벌 세팅)
Vue.$cookies.config("7d");

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
