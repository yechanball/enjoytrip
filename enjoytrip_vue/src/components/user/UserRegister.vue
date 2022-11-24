<template>
  <b-container class="mt-3" data-aos="zoom-in" data-aos-duration="200">
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>회원가입</h3>
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

      <b-form-group
        id="input-group-userid"
        label="아이디 :"
        label-for="userid"
        :valid-feedback="idCheckMsg"
        :invalid-feedback="idCheckMsg"
        :state="idCheckResult"
      >
        <b-form-input
          id="userid"
          ref="userid"
          v-model="userId"
          placeholder="아이디를 입력해주세요"
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
        <b-button type="submit" variant="primary">회원가입</b-button>
        <b-button type="reset" variant="danger">초기화</b-button>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import { checkId, join } from "@/api/member";

export default {
  name: "UserRegister",
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
        "google.com",
        "naver.com",
        "hanmail.net",
        "ssafy.com",
      ],
      idCheckBool: null,
      idCheckMsg: "",
      pwdCheckMsg: "",
    };
  },
  computed: {
    idCheckResult() {
      return this.idCheckBool;
    },
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
      this.userName = "";
      this.userId = "";
      this.userPwd = "";
      this.pwdCheck = "";
      this.emailId = "";
      this.emailDomain = "";
    },
    checkValue() {
      // 입력값 체크하기
      // 입력값이 없을 경우 각 항목에 맞는 메세지를 출력
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
      else this.joinMember();
    },
    joinMember() {
      let user = {
        userName: this.userName,
        userId: this.userId,
        userPwd: this.userPwd,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
      };
      join(
        user,
        ({ data }) => {
          if (data.message === "success") {
            this.$router.push({ name: "login" });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  watch: {
    // 아이디 입력 변화에 따른 검사
    userId: function (userId) {
      if (userId.length < 5 || userId.length > 16) {
        this.idCheckMsg = "아이디는 5자 이상 16자 이하 입니다.";
        this.idCheckBool = false;
      } else {
        checkId(userId, ({ data }) => {
          if (data == 0) {
            this.idCheckMsg = userId + "는 사용할 수 있습니다.";
            this.idCheckBool = true;
          } else {
            this.idCheckMsg = userId + "는 사용할 수 없습니다.";
            this.idCheckBool = false;
          }
        });
      }
    },
    // 비밀번호 확인 입력 변화에 따른 검사
    pwdCheck: function (pwdCheck) {
      if (pwdCheck != "") {
        this.pwdCheckMsg = "비밀번호가 일치하지 않습니다";
      } else {
        this.pwdCheckMsg = "";
      }
    },
  },
};
</script>

<style scoped>
form {
  padding: 20px;
  background-color: white;
}
</style>
