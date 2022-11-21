<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userId }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.userName
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start"
                >{{ userInfo.emailId }}@{{ userInfo.emailDomain }}</b-col
              >
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.joinDate
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="primary" @click.prevent="movePage" class="mr-1"
            >정보수정</b-button
          >
          <b-button variant="danger" @click.prevent="modalShow = true"
            >회원탈퇴</b-button
          >
          <b-modal v-model="modalShow" @ok="doDelete">
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
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import { deleteMember } from "@/api/member";

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
    movePage() {
      this.$router.push({ name: "modify" });
    },
    doDelete() {
      this.modalShow = true;
      let user = {
        userId: this.userInfo.userId,
        userPwd: this.userPwd,
      };
      console.log(user);
      deleteMember(
        user,
        ({ data }) => {
          console.log(data);
          if (data.message === "success") {
            this.$router.push({ name: "home" });
          } else {
            alert("비밀번호가 틀렸습니다.");
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

<style></style>
