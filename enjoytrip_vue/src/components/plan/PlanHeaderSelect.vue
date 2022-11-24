<template>
  <div id="plan-header" class="text-center">
    <div id="plan-header-select" class="container row">
      <b-col class="col-2 p-1">
        <b-form-select
          v-model="doIdx"
          :options="dos"
          @change="sigunguList"
        ></b-form-select>
      </b-col>
      <b-col class="col-2 p-1">
        <b-form-select v-model="sigunguIdx" :options="sigungus"></b-form-select>
      </b-col>
      <b-col class="col-2 p-1">
        <b-form-select
          v-model="contentTypeId"
          :options="tourtypes"
        ></b-form-select>
      </b-col>
      <b-col class="col-4 p-1">
        <b-form-input
          id="searchword"
          v-model="word"
          placeholder="검색어"
          @keyup.enter="searchAttraction"
        ></b-form-input>
      </b-col>
      <b-col class="col-2 p-1">
        <b-button type="button" variant="primary" @click="searchAttraction"
          >검색</b-button
        >
      </b-col>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "PlanHeaderSelect",
  data() {
    return {
      doIdx: 0,
      sigunguIdx: 0,
      contentTypeId: 0,
      word: "",
    };
  },
  computed: {
    ...mapState("attractionStore", ["dos", "sigungus", "tourtypes"]),
  },
  methods: {
    ...mapActions("attractionStore", [
      "getDo",
      "getSigungu",
      "getSearchAttractionList",
    ]),
    ...mapMutations("attractionStore", [
      "CLEAR_DO_LIST",
      "CLEAR_SIGUNGU_LIST",
      "CLEAR_ATTRACTION_LIST",
      "CLEAR_MARKER_POSITIONS",
    ]),
    sigunguList() {
      this.CLEAR_SIGUNGU_LIST();
      this.sigunguIdx = null;
      if (this.doIdx) this.getSigungu(this.doIdx);
    },
    searchAttraction() {
      this.CLEAR_ATTRACTION_LIST();
      this.CLEAR_MARKER_POSITIONS();
      this.getSearchAttractionList({
        doIdx: this.doIdx,
        sigunguIdx: this.sigunguIdx,
        contentTypeId: this.contentTypeId,
        word: this.word,
      });
    },
  },
  created() {
    this.CLEAR_DO_LIST();
    this.doIdx = null;
    this.CLEAR_SIGUNGU_LIST();
    this.sigunguIdx = null;
    this.CLEAR_ATTRACTION_LIST();
    this.getDo();
    this.getSearchAttractionList({
      doIdx: this.doIdx,
      sigunguIdx: this.sigunguIdx,
      contentTypeId: this.contentTypeId,
      word: this.word,
    });
  },
};
</script>

<style>
#plan-header {
  width: 100%;
  height: calc(12% - 20px);
  min-height: 80px;
  padding: 0;
  padding-top: 30px;
}

#attraction-header-select {
  /* TODO: 배경색 수정하기 */
  /* background-color: rgb(158, 124, 188); */
  margin: auto;
}
</style>
