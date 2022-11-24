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
/* 폰트 설정 */
/* KOFIH이종욱체 */
@font-face {
  font-family: "KOFIHDrLEEJWTTF-B";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2210-2@1.0/KOFIHDrLEEJWTTF-B.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}

/* 마이쮸체 */
@font-face {
  font-family: "TTCrownMychewR";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/TTCrownMychewR.woff2")
    format("woff2");
  font-weight: normal;
  font-style: normal;
}

/* 화면 크기 설정 */
body {
  font-family: "KOFIHDrLEEJWTTF-B" !important;
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
}
</style>
