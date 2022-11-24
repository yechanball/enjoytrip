<template>
  <b-container
    class="mt-4"
    v-if="userInfo"
    data-aos="zoom-in"
    data-aos-duration="200"
  >
    <b-row class="mb-4">
      <b-col></b-col>
      <b-col cols="8">
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>나의 정보</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
      <b-col></b-col>
    </b-row>
    <b-row></b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8" style="background-color: white">
        <table class="table">
          <tr>
            <td>아이디</td>
            <td id="userid">{{ userInfo.userId }}</td>
          </tr>

          <tr></tr>
          <tr>
            <td>이름</td>
            <td>{{ userInfo.userName }}</td>
          </tr>

          <tr></tr>
          <tr>
            <td>이메일</td>
            <td>{{ userInfo.emailId }}@{{ userInfo.emailDomain }}</td>
          </tr>

          <tr></tr>
          <tr>
            <td>가입일</td>
            <td>{{ userInfo.joinDate }}</td>
          </tr>

          <tr></tr>
        </table>
        <div class="col-auto text-center">
          <b-button variant="primary" @click.prevent="movePage" class="mr-1"
            >정보수정</b-button
          >
          <b-button variant="danger" @click.prevent="modalShow = true"
            >회원탈퇴</b-button
          >
        </div>
        <b-modal v-model="modalShow" @ok="passwordCheck">
          <p>
            정말 탈퇴하시겠습니까?
            <br />탈퇴하고 싶다면 비밀번호를 입력해주세요!
          </p>
          <b-form-input
            type="password"
            v-model="userPwd"
            placeholder="비밀번호를 입력해주세요"
            required
          ></b-form-input>
        </b-modal>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { passCheck, deleteMember } from "@/api/member";

export default {
  name: "UserMyPage",
  data() {
    return {
      userPwd: "",
      modalShow: false,
    };
  },
  components: {},
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  watch: {
    // 모달 감지
    modalShow: function () {
      if (!this.modalShow) {
        this.userPwd = "";
      }
    },
  },
  methods: {
    ...mapActions("memberStore", ["userLogout"]),
    movePage() {
      this.$router.push({ name: "modify" });
    },
    passwordCheck() {
      this.modalShow = true;
      let user = {
        userId: this.userInfo.userId,
        userPwd: this.userPwd,
      };
      passCheck(
        user,
        ({ data }) => {
          if (data.message === "success") {
            this.doDelete(user);
          } else {
            alert("비밀번호가 틀렸습니다.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    doDelete(user) {
      this.modalShow = true;
      deleteMember(
        user,
        ({ data }) => {
          if (data.message === "success") {
            // 탈퇴가 완료되면 로그아웃과 동시에 홈화면으로 이동
            this.userLogout(this.userInfo.userId);
            sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
            sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
            if (this.$route.path != "/") this.$router.push({ name: "home" });
            alert(
              "회원 탈퇴가 정상적으로 되었습니다\n그동안 이용해주셔서 감사합니다."
            );
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style>
.jumbotron {
  background-color: white !important;
}
</style>
