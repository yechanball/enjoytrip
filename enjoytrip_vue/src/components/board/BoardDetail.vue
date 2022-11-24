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
          :header-html="`<h3 class='mb-3'>${article.subject}</h3><div><h6>작성자 : ${article.userId}</div><div>작성일 : ${article.registerTime}</h6></div>`"
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
      <b-col
        class="text-right"
        v-if="userInfo && article.userId == userInfo.userId"
      >
        <b-button variant="outline-dark" @click="moveModifyArticle" class="mr-2"
          >수정</b-button
        >
        <b-button variant="outline-dark" @click="deleteArticle">삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mt-3">
      <b-col>
        <div>
          <b-form-group
            id="input-memo"
            label="댓글"
            label-for="memo"
            description=""
          >
            <b-card
              v-if="memoList.length == 0"
              :header-html="`<h6>등록된 댓글이 없습니다</h6>`"
              class="mb-2"
              no-body
            ></b-card>
            <div v-else id="memo-list">
              <div v-for="(memoItem, index) in memoList" :key="index">
                <!--border-variant="dark"-->
                <b-row class="mb-2">
                  <b-col cols="1" class="text-left">
                    <h6>[{{ memoItem.userId }}]</h6>
                  </b-col>
                  <b-col cols="10" class="text-left">
                    <h6>
                      {{ memoItem.comment }} &nbsp;({{ memoItem.memoTime }})
                    </h6>
                  </b-col>
                  <b-col cols="1" class="text-right">
                    <b-button
                      v-if="userInfo && memoItem.userId == userInfo.userId"
                      type="button"
                      variant="dark"
                      @click="deleteMemo(memoItem.memoNo)"
                      >삭제</b-button
                    >
                  </b-col>
                </b-row>
              </div>
            </div>
            <b-row class="mb-1" v-if="userInfo">
              <b-col cols="11" class="text-left">
                <b-form-input
                  id="memo"
                  v-model="inputMemo.comment"
                  type="text"
                  placeholder="댓글을 입력해주세요"
                  required
                ></b-form-input>
              </b-col>
              <b-col cols="1" class="text-right">
                <b-button type="button" variant="dark" @click="writeMemo"
                  >입력</b-button
                >
              </b-col>
            </b-row>
          </b-form-group>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

export default {
  name: "BoardDetail",
  data() {
    return {
      article: {},
      memoList: [],
      inputMemo: {
        articleNo: 0,
        comment: "",
        userId: "",
      },
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    if (this.userInfo) {
      this.inputMemo.userId = this.userInfo.userId;
    }

    http.get(`/board/view/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      this.inputMemo.articleNo = this.article.articleNo;
    });

    http
      .get(`/memo/list/${this.$route.params.articleno}`)
      .then(({ data }) => {
        if (data.message === "success") {
          data.list.forEach((memo) => {
            this.memoList.push(memo);
          });
        }
      })
      .catch((error) => {
        console.log(error);
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
    writeMemo() {
      http
        .post(`/memo/write`, this.inputMemo)
        .then(({ data }) => {
          if (data.message === "success") {
            this.inputMemo.comment = "";
            http
              .get(`/memo/list/${this.$route.params.articleno}`)
              .then(({ data }) => {
                if (data.message === "success") {
                  this.memoList = [];
                  data.list.forEach((memo) => {
                    this.memoList.push(memo);
                  });
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteMemo(memoNo) {
      let memo;
      this.memoList.forEach((item) => {
        if (item.memoNo == memoNo) {
          memo = item;
        }
      });
      http
        .post(`/memo/delete`, memo)
        .then(({ data }) => {
          if (data.message === "success") {
            alert("댓글이 삭제되었습니다!");
            http
              .get(`/memo/list/${this.$route.params.articleno}`)
              .then(({ data }) => {
                if (data.message === "success") {
                  this.memoList = [];
                  data.list.forEach((memo) => {
                    this.memoList.push(memo);
                  });
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        })
        .catch((error) => {
          console.log(error);
        });
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
  height: 40vh;
}
#memo-list {
  height: 25vh;
  overflow: auto;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
#memo-list::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
