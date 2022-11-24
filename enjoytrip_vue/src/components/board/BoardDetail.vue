<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>게시글</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3 class='mb-3'>${article.articleNo})
          ${article.subject}</h3><div><h6>작성자 : ${article.userId}</div><div>작성일 : ${article.registerTime}</h6></div>`"
          class="mb-2 article"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-dark" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button variant="outline-dark" @click="moveModifyArticle" class="mr-2"
          >수정</b-button
        >
        <b-button variant="outline-dark" @click="deleteArticle">삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mt-3">
      <b-col>
        <div>
          <!-- 댓글 데이터를 불러와 반복문으로 돌려 컴포넌트에 각각 들어가도록 한다.
          <div v-for="item in commentObj" :key="item.comment_id">
            <PrCommentListItem :commentItem="item" :reload="reload" />
          </div> -->
          <!-- 댓글 쓰기
          <div>
            <PrCommentCreate :contentId="contentId" :reload="reload" />
          </div> -->
          <b-card
            :header-html="`<div><h6>${article.userId} : ${article.content}</div><div>Regist_Time : ${article.registerTime}</h6></div>`"
            class="mb-2"
            border-variant="dark"
            no-body
          ></b-card>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/http";
// import data from "@/data";
// import PrCommentListItem from "@/components/PrCommentListItem";
// import PrCommentCreate from "@/components/PrCommentCreate";

export default {
  name: "BoardDetail",
  data() {
    return {
      article: {},
      memo: {},
    };
  },
  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/view/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
    http
      .get(`/memo/list/${this.$route.params.articleno}`)
      .then(({ memoData }) => {
        this.memo = memoData;
      });
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      console.log(this.article.articleNo);
      this.$router.replace({
        name: "boardModify",
        params: { articleno: this.article.articleNo },
      });
    },
    deleteArticle() {
      console.log(this.article.articleNo);
      if (confirm("삭제하시겠습니까?")) {
        this.$router.replace({
          name: "boardDelete",
          params: { articleno: this.article.articleNo },
        });
      }
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style>
.article {
  height: 50vh;
}
</style>
