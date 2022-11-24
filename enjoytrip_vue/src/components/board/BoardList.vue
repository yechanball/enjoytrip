<template>
  <b-container class="bv-example-row mt-5">
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>자유게시판</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <vue-good-table
          v-bind="articles"
          :columns="columns"
          :rows="articles.articles"
          @on-row-click="onRowClick"
          :sort-options="{
            enabled: true,
            initialSortBy: { field: 'articleNo', type: 'desc' },
          }"
          :search-options="{
            enabled: true,
            trigger: 'enter',
            skipDiacritics: true,
            placeholder: '검색어를 입력해주세요',
          }"
          :pagination-options="{
            enabled: true,
            mode: 'records',
            perPage: 10,
            perPageDropdownEnabled: false,
          }"
          :select-options="{
            enabled: false,
            selectOnCheckboxOnly: true, // only select when checkbox is clicked instead of the row
            selectionInfoClass: 'custom-class',
            selectionText: 'rows selected',
            clearSelectionText: 'clear',
            disableSelectInfo: true, // disable the select info panel on top
            selectAllByGroup: true, // when used in combination with a grouped table, add a checkbox in the header row to check/uncheck the entire group
          }"
        >
        </vue-good-table>
      </b-col>
    </b-row>
    <b-row class="mt-2 mb-1">
      <b-col class="text-right">
        <b-button variant="dark" @click="moveWrite()">글 작성</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import "vue-good-table/dist/vue-good-table.css";
import { VueGoodTable } from "vue-good-table";
// import PrCommentList from "../memo/PrCommentList.vue";

export default {
  name: "BoardList",
  components: {
    VueGoodTable,
    // PrCommentList,
    // BoardListItem,
  },
  data() {
    return {
      articles: [],
      columns: [
        {
          label: "글번호",
          field: "articleNo",
          type: "number",
        },
        {
          label: "제목",
          field: "subject",
          type: "text",
        },
        {
          label: "작성자",
          field: "userId",
          type: "text",
        },
        {
          label: "조회수",
          field: "hit",
          type: "number",
        },
        {
          label: "작성 시간",
          field: "registerTime",
          type: "text",
        },
        // dateInputFormat: "yyyy-MM-dd",
        // dateOutputFormat: "MMM do yy",
      ],
      rows: [],
    };
  },
  created() {
    http
      .get(`/board/list?pgno=1&orderby=none&orderbydir=&key=none&word=none`)
      .then(({ data }) => {
        this.articles = data;
      });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "boardwrite" });
    },
    // viewArticle(article) {
    //   this.$router.push({
    //     name: "boardView",
    //     params: { articleno: article.articleno },
    //   });
    // },
    onRowClick(article) {
      // console.log("onRowClick");
      // console.log(article);
      // console.log(article.row.articleNo);
      // console.log("===========");
      this.$router.push({
        name: "boardDetail",
        params: { articleno: article.row.articleNo },
      });
    },
  },
};
</script>

<style>
h3 {
  margin: 0;
}

.vgt-wrap {
  min-width: 650px;
}

th,
td {
  text-align: center !important;
}

.vgt-global-search {
  width: 21% !important;
  min-width: 200px;
  float: left;
}
</style>
