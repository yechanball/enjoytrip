import Vue from "vue";
import VueRouter from "vue-router";
import AppHome from "@/views/AppHome";
import AppAttraction from "@/views/AppAttraction";

import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크 시작");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다.");
    router.push({ name: "login" });
  } else {
    console.log("로그인되어 있는 상태");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: AppHome,
  },
  {
    path: "/attraction",
    name: "attraction",
    component: AppAttraction,
  },
  {
    path: "/plan",
    name: "plan",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/AppPlan"),
  },
  {
    path: "/travel",
    name: "travel",
    component: () => import("@/views/AppTravel"),
    redirect: "/travel/list",
    children: [
      {
        path: "list",
        name: "travellist",
        component: () => import("@/components/travel/TravelList"),
      },
      {
        path: "view",
        name: "travelview",
        component: () => import("@/components/travel/TravelView"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/AppBoard"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/board/BoardList"),
      },
      {
        path: "write",
        name: "boardwrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardWrite"),
      },
      {
        path: "detail/:articleno",
        name: "boardDetail",
        component: () => import("@/components/board/BoardDetail"),
      },
      {
        path: "modify/:articleno",
        name: "boardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify"),
      },
      {
        path: "delete/:articleno",
        name: "boardDelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDelete"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/AppUser"),
    children: [
      {
        path: "join",
        name: "join",
        component: () => import("@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
      {
        path: "find",
        name: "find",
        component: () => import("@/components/user/UserFind"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/UserMyPage"),
      },
      {
        path: "modify",
        name: "modify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/UserModify"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
