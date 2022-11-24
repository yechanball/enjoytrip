<template>
  <div class="row" id="plan-item-list">
    <div class="col-6">
      <h3>관광지 목록</h3>
      <draggable class="list-group" :list="attractionList" group="attraction">
        <div
          class="list-group-item"
          v-for="(atr, index) in attractionList"
          :key="index"
        >
          <b-card no-body class="attraction-item overflow-hidden">
            <b-row no-gutters>
              <b-col md="4" class="m-auto">
                <b-card-img
                  :src="atr.firstImage2"
                  :alt="atr.title"
                  class="rounded-0"
                ></b-card-img>
              </b-col>
              <b-col md="8" class="m-auto pl-3">
                <table>
                  <tr>
                    <td v-if="atr.title.length > 25" class="item-title">
                      {{ atr.title.substr(0, 25) }}...
                    </td>
                    <td v-if="atr.title.length <= 25" class="item-title">
                      {{ atr.title }}
                    </td>
                  </tr>
                </table>
              </b-col>
            </b-row>
          </b-card>
        </div>
      </draggable>
    </div>

    <div class="col-6">
      <h3>여행경로</h3>
      <draggable
        class="list-group"
        :list="planList"
        group="attraction"
        @change="setPlanMarker"
      >
        <div v-if="planList.length == 0">여기로 드래그해주세요</div>
        <div
          class="list-group-item"
          v-for="(atr, index) in planList"
          :key="index"
        >
          <b-card no-body class="attraction-item overflow-hidden">
            <b-row no-gutters>
              <b-col md="4" class="m-auto">
                <b-card-img
                  :src="atr.firstImage2"
                  :alt="atr.title"
                  class="rounded-0"
                ></b-card-img>
              </b-col>
              <b-col md="8" class="m-auto pl-3">
                <table>
                  <tr>
                    <td v-if="atr.title.length > 15" class="item-title">
                      <strong>{{ index + 1 }}번</strong><br />{{
                        atr.title.substr(0, 15)
                      }}...
                    </td>
                    <td v-if="atr.title.length <= 15" class="item-title">
                      <strong>{{ index + 1 }}번</strong><br />
                      {{ atr.title }}
                    </td>
                    <td>
                      <b-button
                        class="delete-btn"
                        variant="danger"
                        @click="deleteItem(atr.contentId)"
                      >
                        삭제
                      </b-button>
                    </td>
                  </tr>
                </table>
              </b-col>
            </b-row>
          </b-card>
        </div>
      </draggable>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import draggable from "vuedraggable";

export default {
  name: "PlanItemList",
  components: {
    draggable,
  },
  data() {
    return {
      attractionList: [],
      planList: [],
    };
  },
  computed: {
    ...mapState("attractionStore", ["attractions"]),
  },
  watch: {
    // 관광지 목록 변화에 따른 목록 변경
    attractions: function (attractions) {
      this.attractionList = attractions;
    },
    planList: function () {
      this.setPlanMarker();
    },
  },
  mounted() {
    this.CLEAR_PLAN_MARKERS();
  },
  methods: {
    ...mapMutations("attractionStore", [
      "SET_PLAN_MARKERS",
      "CLEAR_PLAN_MARKERS",
    ]),
    deleteItem(contentId) {
      for (let i = 0; i < this.planList.length; i++) {
        if (this.planList[i].contentId === contentId) {
          this.attractionList.push(this.planList[i]);
          this.planList.splice(i, 1);
          i--;
          break;
        }
      }
    },
    setPlanMarker() {
      this.CLEAR_PLAN_MARKERS();
      this.SET_PLAN_MARKERS(this.planList);
    },
  },
};
</script>

<style scoped>
#plan-item-list {
  width: 100%;
  height: 80vh;
  margin: 0;
}
.list-group {
  height: 73vh;
  overflow: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  background-color: white;
}
.list-group::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
.list-group-item {
  width: 100%;
  height: 100px;
  padding: 0;
  cursor: pointer;
}
.list-group-item img {
  height: 100px;
}
tr {
  height: 100px;
}
.item-title {
  width: 75%;
  font-size: 1.1rem;
  padding-right: 5px;
  align-items: center;
  text-overflow: ellipsis;
}
.delete-btn {
  width: 25%;
  width: 60px;
  margin-right: 5px;
  padding: 0;
  padding-block: 5px;
  align-items: center;
}
</style>
