<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <!-- <b-form-group
          id="userid-group"
          label="UserID"
          label-for="userid"
          description=""
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.userId"
            type="text"
            required
            placeholder=""
          ></b-form-input>
        </b-form-group> -->

        <b-form-group
          id="subject-group"
          label="Title"
          label-for="subject"
          description=""
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder=""
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="Contents" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder=""
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button
          type="submit"
          variant="dark"
          class="m-1"
          v-if="this.type === 'register'"
          >작성</b-button
        >
        <b-button type="submit" variant="dark" class="m-1" v-else
          >수정</b-button
        >
        <b-button type="reset" variant="dark" class="m-1">취소</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

export default {
  name: "BoardInputItem",
  data() {
    return {
      article: {
        articleNo: 0,
        userId: "",
        subject: "",
        content: "",
      },
      isUserid: false,
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  props: {
    type: { type: String },
  },
  created() {
    this.article.userId = this.userInfo.userId;
    if (this.type === "modify") {
      http
        .get(`/board/view/${this.$route.params.articleno}`)
        .then(({ data }) => {
          // this.article.articleNo = data.article.articleNo;
          // this.article.userId = data.article.userId;
          // this.article.subject = data.article.subject;
          // this.article.content = data.article.content;
          this.article = data;
        });
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userId &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleNo = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "boardList" });
    },
    registArticle() {
      console.log(this.article.userId);
      http
        .post(`/board/write`, {
          userId: this.article.userId,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          // let msg = "게시글 작성 오류";
          // if (data.message === "success") {
          //   msg = "게시글 작성 완료";
          // }
          // alert(msg);
          console.log(data);
          this.moveList();
        });
    },
    modifyArticle() {
      console.log(this.article.articleNo);
      http
        .put(`/board/${this.article.articleNo}`, {
          articleno: this.article.articleNo,
          userid: this.article.userId,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          // let msg = "게시글 수정 오류";
          // if (data.message === "success") {
          //   msg = "게시글 수정 완료";
          // }
          // alert(msg);
          console.log(data);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        });
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
};
</script>

<style></style>
