<template>
  <b-tr>
    <b-td>{{ articleNo }}</b-td>
    <b-th class="text-left">
      <router-link
        :to="{ name: 'boardDetail', params: { articleno: articleNo } }"
        >{{ subject }}</router-link
      >
    </b-th>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ userId }}</b-td>
    <b-td>{{ registerTime | dateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";

export default {
  name: "BoardListItem",
  props: {
    articleNo: Number,
    userId: String,
    subject: String,
    hit: Number,
    registerTime: String,
  },
  filters: {
    dateFormat(registerTime) {
      return moment(new Date(this.registerTime)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
