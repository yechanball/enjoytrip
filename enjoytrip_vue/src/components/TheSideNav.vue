<template>
  <div>
    <div id="sidebar">
      <div class="sidebar-body">
        <router-link :to="{ name: 'home' }" style="text-decoration: none">
          <div class="sidebar-profile" v-show="memberActive">
            <img :src="require('@/assets/img/logo_trip.png')" alt="logo" />
            <div v-show="menuCompact.hidden" id="profile-name">
              <h1>여행가자</h1>
            </div>
          </div>
        </router-link>
        <div class="sidebar-links sidebar-user">
          <div class="links">
            <!-- after login -->
            <div v-if="userInfo">
              <div class="user-info">
                <b-avatar
                  variant="danger m-auto"
                  v-text="userInfo.userId.charAt(0).toUpperCase()"
                ></b-avatar>
                <div class="link-title" v-show="menuCompact.hidden">
                  {{ userInfo.userName }}님<br />환영합니다.
                </div>
              </div>
              <router-link :to="{ name: 'mypage' }">
                <b-icon icon="file-person"></b-icon>
                <div class="link-title" v-show="menuCompact.hidden">
                  마이페이지
                </div>
              </router-link>
              <a href="#">
                <b-icon icon="person-x-fill"></b-icon>
                <div
                  class="link-title"
                  v-show="menuCompact.hidden"
                  @click.prevent="onClickLogout"
                >
                  로그아웃
                </div>
              </a>
            </div>
            <!-- before login -->
            <div v-else>
              <router-link :to="{ name: 'login' }">
                <b-icon icon="key"></b-icon>
                <div class="link-title" v-show="menuCompact.hidden">로그인</div>
              </router-link>
              <router-link :to="{ name: 'join' }">
                <b-icon icon="person-plus"></b-icon>
                <div class="link-title" v-show="menuCompact.hidden">
                  회원가입
                </div>
              </router-link>
            </div>
          </div>
        </div>
        <div
          style="
            margin-block: 10px;
            border-bottom: 2px solid rgba(255, 255, 255, 0.75);
          "
        ></div>
        <div class="sidebar-links">
          <!-- after login -->
          <div class="links" v-if="userInfo">
            <router-link
              :to="link.path"
              v-for="link in menuLinksLogin"
              :key="link.title"
            >
              <b-icon :icon="link.icon"></b-icon>
              <div class="link-title" v-show="menuCompact.hidden">
                {{ link.title }}
              </div>
            </router-link>
          </div>
          <!-- before login -->
          <div class="links" v-else>
            <router-link
              :to="link.path"
              v-for="link in menuLinks"
              :key="link.title"
            >
              <b-icon :icon="link.icon"></b-icon>
              <div class="link-title" v-show="menuCompact.hidden">
                {{ link.title }}
              </div>
            </router-link>
          </div>
        </div>
      </div>
      <div class="sidebar-toggle">
        <div @click="changeToggleState()" id="btn-toggle">
          <i id="btn-toggle-icon" class="bi bi-chevron-double-left"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "TheSideNavTest",
  data: () => {
    return {
      drawer: true,
      menuCompact: {
        hidden: true,
      },
      memberActive: true,
      menuLinks: [
        { path: "/", title: "홈", icon: "house-fill" },
        { path: "/attraction", title: "여행지도", icon: "map-fill" },
        { path: "/travel", title: "여행코스", icon: "signpost-split" },
        { path: "/board", title: "자유게시판", icon: "card-text" },
      ],
      menuLinksLogin: [
        { path: "/", title: "홈", icon: "house-fill" },
        { path: "/attraction", title: "여행지도", icon: "map-fill" },
        { path: "/travel", title: "여행코스", icon: "signpost-split" },
        { path: "/plan", title: "여행코스 만들기", icon: "pin-map-fill" },
        { path: "/board", title: "자유게시판", icon: "card-text" },
      ],
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "home" });
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userId);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
    changeToggleState() {
      let btnToggleIcon = document.getElementById("btn-toggle-icon");
      this.menuCompact.hidden = !this.menuCompact.hidden;

      if (this.menuCompact.hidden) {
        return (btnToggleIcon.style.transform = "rotateY(0deg)");
      } else {
        return (btnToggleIcon.style.transform = "rotateY(180deg)");
      }
    },
  },
};
</script>

<style scoped>
.bi {
  font-size: 30px;
  font-weight: bold;
}

#sidebar {
  height: 100%;
  margin: 0;
  top: 0;
  left: 0;
  background-color: #bd5d38;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: center;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.6);
}

.sidebar-body {
  flex-grow: 1;
}

.sidebar-profile {
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.25);
  transition: 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  margin-bottom: 10px;
  cursor: pointer;
}

.sidebar-profile:hover {
  cursor: pointer;
}

#profile-name {
  min-width: 150px;
  align-self: center;
}

#profile-name > h1 {
  font-family: "TTCrownMychewR";
  font-weight: 600;
  flex-grow: 1;
  text-align: center;
  color: rgb(0, 0, 0, 0.8);
  margin: 0;
  padding-left: 5px;
}

.sidebar-profile img {
  max-width: 60px;
  border-radius: 100%;
  /* border: 4px inset rgba(255, 255, 255, 0.25); */
}

.sidebar-links {
  padding-inline: 10px;
}

.sidebar-links small {
  color: rgba(255, 255, 255, 0.4);
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 12px;
}

.sidebar-links .links {
  display: flex;
  flex-direction: column;
}

.sidebar-links a {
  text-decoration: none;
  background-color: rgba(255, 255, 255, 0.75);
  border-radius: 25px;
  padding-inline: 20px;
  padding-block: 10px;
  margin-block: 8px;
  font-weight: 900;
  border: 1px solid rgba(255, 255, 255, 0.85);
  transition: 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.sidebar-links a:hover {
  transform: scale(1.08);
  background-color: rgba(255, 255, 255, 0.8);
  box-shadow: 0px 0px 8px rgba(255, 255, 255, 0.5);
  border-color: rgba(255, 255, 255, 0.8);
}

/* .sidebar-links a:nth-last-of-type(1):hover {
  background-image: linear-gradient(
    to right,
    rgb(113, 57, 57),
    rgb(113, 57, 57)
  );
  box-shadow: 0px 0px 8px rgb(113, 57, 57);
  border-color: rgb(113, 57, 57);
} */

.sidebar-links .links a {
  display: flex;
  flex-direction: row;
  align-items: center;
  color: rgba(0, 0, 0, 0.6);
}

.sidebar-links .link-title {
  /* margin-left: 10px; */
  margin: auto;
}

.sidebar-links .icon {
  color: rgba(0, 0, 0, 0.9);
  margin-top: -1px;
  margin-left: 3px;
}

.sidebar-user {
  /* background-color: rgba(119, 170, 182, 0.9); */
  text-align: center;
}

.user-info {
  display: flex;
  flex-direction: row;
  background-color: rgba(255, 255, 255, 0.75);
  padding-inline: 15px;
  padding-block: 10px;
  font-weight: 900;
  border: 3px solid black;
}
.user-info span {
  margin: auto;
}
.user-info .link-title {
  padding-left: 10px;
}

.sidebar-toggle {
  top: 0px;
  right: 0px;
}

#btn-toggle {
  background-color: rgba(255, 255, 255, 0.3);
  transition: 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  padding: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
}

#btn-toggle:hover {
  background-color: rgba(255, 255, 255, 0.4);
  box-shadow: 0px 0px 6px rgba(255, 255, 255, 0.4);
}

.app-bar-content {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}

.app-bar-content h2 {
  color: rgba(255, 255, 255, 0.75);
}

.app-bar-content h2:hover {
  color: rgba(255, 255, 255, 1);
}

.transition-default {
  transition: 0.25s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  cursor: pointer;
}

@media screen and (max-width: 600px) {
  .app-bar-content h2 {
    font-size: 18px;
  }
}
</style>
