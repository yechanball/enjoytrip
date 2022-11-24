<template>
  <b-container
    class="bv-example-row mt-3"
    data-aos="zoom-in"
    data-aos-duration="200"
  >
    <b-row class="mb-4">
      <b-col></b-col>
      <b-col cols="8">
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>로그인</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
      <b-col></b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <div
              class="form-check row pr-3"
              style="display: inline-flex; float: right"
            >
              <b-form-checkbox
                id="checkbox-saveid"
                v-model="saveIdCheck"
                value="true"
                unchecked-value="false"
              >
              </b-form-checkbox>
              <label class="form-check-label" for="saveid"> 아이디저장 </label>
            </div>

            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userId"
                required
                placeholder="아이디 입력"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userPwd"
                required
                placeholder="비밀번호 입력"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <div class="col-auto text-center">
              <b-button
                type="button"
                variant="primary"
                class="m-1"
                @click="confirm"
                >로그인</b-button
              >
              <b-button
                type="button"
                variant="success"
                class="m-1"
                @click="movePage"
                >회원가입</b-button
              >
              <br /><router-link :to="{ name: 'find' }">
                비밀번호 찾기
              </router-link>
            </div>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "UserLogin",
  data() {
    return {
      saveIdCheck: "false",
      // isLoginError: false,
      user: {
        userId: null,
        userPwd: null,
      },
    };
  },
  created() {
    if (this.$cookies.isKey("saveid")) {
      this.saveIdCheck = "true";
      this.user.userId = this.$cookies.get("saveid");
    }
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        // 로그인시 쿠키 추가/삭제
        if (this.saveIdCheck == "true") {
          this.$cookies.set("saveid", this.user.userId);
        } else {
          this.$cookies.remove("saveid");
        }

        this.$router.push({ name: "home" });
      }
    },
    movePage() {
      this.$router.push({ name: "join" });
    },
  },
};
</script>

<style></style>
