<template>
  <div id="attraction-header" class="text-center">
    <div id="attraction-header-select" class="container row">
      <b-col class="sm-3">
        <b-form-select
          v-model="doIdx"
          :options="dos"
          @change="sigunguList"
        ></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select
          v-model="sigunguIdx"
          :options="sigungus"
          @change="searchAttraction"
        ></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select
          v-model="contentTypeId"
          :options="tourtypes"
          @change="searchAttraction"
        ></b-form-select>
      </b-col>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "AttractionMap",
  data() {
    return {
      doIdx: 0,
      sigunguIdx: 0,
      contentTypeId: 0,
    };
  },
  computed: {
    ...mapState("attractionStore", ["dos", "sigungus", "tourtypes"]),
  },
  methods: {
    ...mapActions("attractionStore", [
      "getDo",
      "getSigungu",
      "getAttractionList",
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
      if (this.doIdx && this.sigunguIdx) {
        this.getAttractionList({
          doIdx: this.doIdx,
          sigunguIdx: this.sigunguIdx,
          contentTypeId: this.contentTypeId,
        });
      }
    },
  },
  created() {
    this.CLEAR_DO_LIST();
    this.doIdx = null;
    this.CLEAR_SIGUNGU_LIST();
    this.sigunguIdx = null;
    this.CLEAR_ATTRACTION_LIST();
    this.getDo();
  },
};
</script>

<style>
#attraction-header {
  height: calc(10% - 20px);
  min-height: 80px;
  padding-top: 20px;
}

#attraction-header-select {
  /* TODO: 배경색 수정하기 */
  /* background-color: rgb(158, 124, 188); */
  margin: auto;
}
</style>
