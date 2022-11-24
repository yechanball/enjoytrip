<template>
  <div id="travel-info" v-if="travelInfo">
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>여행코스 상세보기</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
    </b-row>
    <div id="travel-info-item">
      <table class="table table-hover">
        <tr>
          <td>제목</td>
          <td>{{ travelInfo.title }}</td>
        </tr>
        <tr>
          <td>작성자</td>
          <td>{{ travelInfo.userId }}</td>
        </tr>
        <tr>
          <td>작성일</td>
          <td>{{ travelInfo.planTime }}</td>
        </tr>
        <tr>
          <td>내용</td>
          <td>{{ travelInfo.content }}</td>
        </tr>
      </table>
      <b-row class="mb-4">
        <b-col>
          <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand class="ms-3">
              <h3>여행 경로</h3>
            </b-navbar-brand>
          </b-navbar>
        </b-col>
      </b-row>
      <table class="table table-hover">
        <tr
          v-for="(trm, index) in travelMarkers"
          :key="index"
          @click="movePan(trm[0], trm[1])"
        >
          <td>
            <strong>{{ index + 1 }}번</strong>
          </td>
          <td v-if="trm[4].length > 25">
            <strong>{{ trm[4].substr(0, 25) }}</strong
            >...
          </td>
          <td v-if="trm[4].length <= 25">
            <strong>{{ trm[4] }}</strong>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "TravelView",
  data() {
    return {
      travelInfo: null,
    };
  },
  computed: {
    ...mapState("attractionStore", [
      "travelList",
      "travelMarkers",
      "travelPlanId",
    ]),
  },
  watch: {
    travelPlanId: function (travelPlanId) {
      this.travelList.forEach((item) => {
        if (item.planId == travelPlanId) {
          this.travelInfo = item;
        }
      });
    },
  },
  methods: {
    ...mapActions("attractionStore", ["getPosition"]),
    movePan(lat, lon) {
      this.getPosition({
        latitude: lat,
        longitude: lon,
      });
    },
  },
  mounted() {
    this.travelList.forEach((item) => {
      if (item.planId == this.travelPlanId) {
        this.travelInfo = item;
      }
    });
  },
};
</script>

<style>
h3 {
  margin: 0;
}

#travel-info {
  width: 100%;
  height: 95vh;
  padding-top: 5vh;
  margin: 0;
  text-align: center;
}

#travel-info-item {
  width: 100%;
  height: 90%;
  overflow: auto;
}

tr {
  background-color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
}
</style>
