<template>
  <div class="main row" data-aos="fade-up" data-aos-duration="200">
    <div class="col-7">
      <plan-map></plan-map>
    </div>
    <div class="col-5">
      <plan-header-select></plan-header-select>
      <plan-item-list></plan-item-list>
      <div class="col-auto text-center">
        <b-button type="button" variant="primary" class="m-1" @click="openModal"
          >여행코스 작성하러 가기</b-button
        >
      </div>
    </div>
    <b-modal v-model="modalShow" title="여행코스 작성하기" hide-footer>
      <b-form>
        <b-form-group id="input-group-title" label="제목 :" label-for="title">
          <b-form-input
            id="title"
            ref="title"
            v-model="title"
            placeholder=""
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-content"
          label="내용 :"
          label-for="content"
        >
          <b-form-textarea
            id="content"
            v-model="content"
            placeholder=""
            rows="6"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <div class="col-auto text-center">
          <b-button type="button" @click="onSubmit" variant="primary"
            >작성하기</b-button
          >
        </div>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
import http from "@/api/http";

import { mapState, mapActions } from "vuex";
import PlanMap from "@/components/plan/PlanMap";
import PlanHeaderSelect from "@/components/plan/PlanHeaderSelect";
import PlanItemList from "@/components/plan/PlanItemList";

export default {
  name: "AppPlan",
  components: {
    PlanMap,
    PlanHeaderSelect,
    PlanItemList,
  },
  data() {
    return {
      modalShow: false,
      title: "",
      content: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
    ...mapState("attractionStore", ["planMarkers"]),
  },
  methods: {
    ...mapActions("attractionStore", ["postPlan"]),
    openModal() {
      console.log(this.planMarkers);
      console.log(this.planMarkers.length);
      if (this.planMarkers.length == 0) {
        alert("작성하기전 여행 경로를 선택해주세요!");
      } else {
        this.modalShow = !this.modalShow;
      }
    },
    onSubmit() {
      this.checkValue();
    },
    checkValue() {
      let err = true;
      let msg = "";
      !this.title &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = "내용을 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else this.doWritePlan();
    },
    doWritePlan() {
      http.defaults.headers["refresh-token"] =
        sessionStorage.getItem("refresh-token");

      let planDetailList = [];
      this.planMarkers.forEach((item, index) => {
        planDetailList.push({
          planOrder: index + 1,
          contentId: item[2],
        });
      });

      let plan = {
        title: this.title,
        content: this.content,
        userId: this.userInfo.userId,
        planDetailList: planDetailList,
      };

      http
        .post(`/plan/write`, plan)
        .then(({ data }) => {
          if (data.message === "success") {
            alert("여행경로가 등록되었습니다!");
            this.$router.push({ name: "travel" });
          } else {
            alert("여행경로 등록에 실패하였습니다.");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.main {
  height: 100vh;
  margin: 0;
}
</style>
