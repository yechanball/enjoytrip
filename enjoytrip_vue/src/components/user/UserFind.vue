<template>
  <b-container class="mt-3" data-aos="zoom-in" data-aos-duration="200">
    <b-row>
      <b-col>
        <b-alert variant="danger" show><h3>비밀번호 찾기</h3></b-alert>
      </b-col>
    </b-row>
    <b-form @submit="onSubmit" @reset="onCancel">
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
          placeholder="아이디를 입력해주세요"
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
        <b-button type="submit" variant="primary">비밀번호 찾기</b-button>
        <b-button type="reset" variant="danger">취소</b-button>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import { findPass } from "@/api/member";

export default {
  name: "UserFind",
  data() {
    return {
      userName: "",
      userId: "",
      emailId: "",
      emailDomain: "",
      emailDomains: [
        { text: "선택", value: "" },
        "google.com",
        "naver.com",
        "hanmail.net",
        "ssafy.com",
      ],
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      this.checkValue();
    },
    onCancel(event) {
      event.preventDefault();
      this.$router.push({ name: "login" });
    },
    onReset() {
      this.userName = "";
      this.userId = "";
      this.emailId = "";
      this.emailDomain = "";
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
      else this.doFindPass();
    },
    doFindPass() {
      let user = {
        userName: this.userName,
        userId: this.userId,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
      };
      findPass(
        user,
        ({ data }) => {
          console.log("비밀번호 찾기 결과 : " + data);
          if (data.message === "success") {
            alert(
              "비밀번호가 임시 비밀번호 0000으로 변경되었습니다.\n로그인 후 비밀번호를 변경해주세요."
            );
            this.$router.push({ name: "login" });
          } else {
            alert(
              "일치하는 계정 정보가 없습니다!\n입력해주신 정보를 다시 확인해주세요."
            );
            this.onReset();
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

<style scoped>
form {
  padding: 20px;
  background-color: white;
}
</style>
