<template>
  <b-container
    class="mt-3"
    v-if="userInfo"
    data-aos="zoom-in"
    data-aos-duration="200"
  >
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>나의 정보</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
    </b-row>
    <b-form @submit="onSubmit" @reset="onReset">
      <b-form-group
        id="input-group-username"
        label="이름 :"
        label-for="username"
      >
        <b-form-input
          id="username"
          ref="username"
          v-model="userName"
          placeholder="이름을 입력해주세요"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-userid" label="아이디 :" label-for="userid">
        <b-form-input
          id="userid"
          ref="userid"
          v-model="userId"
          readonly
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-userpwd"
        label="비밀번호 :"
        label-for="userpwd"
      >
        <b-form-input
          id="userpwd"
          ref="userpwd"
          type="password"
          v-model="userPwd"
          placeholder="비밀번호를 입력해주세요"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-pwdcheck"
        label="비밀번호 확인 :"
        label-for="pwdcheck"
        valid-feedback="비밀번호가 일치합니다"
        :invalid-feedback="pwdCheckMsg"
        :state="pwdCheckResult"
      >
        <b-form-input
          id="pwdcheck"
          ref="pwdcheck"
          type="password"
          v-model="pwdCheck"
          placeholder="비밀번호를 다시 입력해주세요"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-email" label="이메일 :" label-for="emailid">
        <div class="input-group">
          <b-form-input
            id="emailid"
            ref="emailid"
            v-model="emailId"
            placeholder="이메일 아이디"
            required
          ></b-form-input>
          <span style="font-size: 23px; font-weigt: bolder">&nbsp;@&nbsp;</span>
          <b-form-select
            id="emaildomain"
            ref="emaildomain"
            v-model="emailDomain"
            :options="emailDomains"
            required
          ></b-form-select>
        </div>
      </b-form-group>
      <div class="col-auto text-center">
        <b-button type="submit" variant="primary">수정</b-button>
        <b-button type="reset" variant="danger">취소</b-button>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import { modify } from "@/api/member";

export default {
  name: "UserModify",
  data() {
    return {
      userName: "",
      userId: "",
      userPwd: "",
      pwdCheck: "",
      emailId: "",
      emailDomain: "",
      emailDomains: [
        { text: "선택", value: "" },
        { text: "google.com", value: "google.com" },
        { text: "naver.com", value: "naver.com" },
        { text: "hanmail.net", value: "hanmail.net" },
        { text: "ssafy.com", value: "ssafy.com" },
      ],
      pwdCheckMsg: "",
    };
  },
  components: {},
  computed: {
    ...mapState("memberStore", ["userInfo"]),
    pwdCheckResult() {
      return this.pwdCheck != "" && this.userPwd == this.pwdCheck;
    },
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      this.checkValue();
    },
    onReset(event) {
      event.preventDefault();
      this.$router.push({ name: "mypage" });
    },
    checkValue() {
      let err = true;
      let msg = "";
      !this.userName &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.userId &&
        ((msg = "아이디를 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.userPwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());
      err &&
        !this.pwdCheckResult &&
        ((msg = "비밀번호를 확인해주세요"),
        (err = false),
        this.$refs.pwdcheck.focus());
      err &&
        !this.emailId &&
        ((msg = "이메일 아이디를 입력해주세요"),
        (err = false),
        this.$refs.emailid.focus());
      err &&
        !this.emailDomain &&
        ((msg = "이메일 도메인을 선택해주세요"),
        (err = false),
        this.$refs.emaildomain.focus());

      if (!err) alert(msg);
      else this.modifyMember();
    },
    modifyMember() {
      let user = {
        userName: this.userName,
        userId: this.userId,
        userPwd: this.userPwd,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
      };
      //   console.log("입력 받은 유저 정보: " + user);
      modify(
        user,
        ({ data }) => {
          //   console.log("회원정보 수정 결과 : " + data);
          if (data.message === "success") {
            this.$router.push({ name: "mypage" });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  created() {
    this.userName = this.userInfo.userName;
    this.userId = this.userInfo.userId;
    this.emailId = this.userInfo.emailId;
    this.emailDomain = this.userInfo.emailDomain;
  },
};
</script>

<style></style>
