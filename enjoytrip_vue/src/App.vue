<template>
  <div id="app">
    <the-side-nav></the-side-nav>
    <router-view></router-view>
  </div>
</template>

<script>
import TheSideNav from "@/components/TheSideNav";

export default {
  name: "App",
  components: {
    TheSideNav,
  },
};
</script>

<style>
/* 화면 크기 설정 */
body {
  padding: 0;
  margin: 0;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
body::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
#app {
  display: inline-flex;
  width: 100vw;
  padding: 0;
  margin: 0;
}
.main {
  width: 100%;
  height: 100vh;
  /* 임시 */
  background-color: rgba(153, 199, 205, 0.499);
}
</style>
