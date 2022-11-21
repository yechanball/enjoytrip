<template>
  <div id="attraction-detail" v-if="atr != null">
    <b-card no-body bg-variant="light" :header="atr.title">
      <b-row no-gutters>
        <b-col md="6" class="img-container">
          <b-card-img
            class="img align-middle"
            v-if="atr.firstImage != ''"
            :src="atr.firstImage"
            :alt="atr.title"
          />
          <b-card-img
            class="img align-middle"
            v-if="atr.firstImage == ''"
            :src="require('@/assets/img/noimage.png')"
            alt="no image"
          />
        </b-col>
        <b-col md="6">
          <b-card-body>
            <table class="table table-bordered w-auto">
              <tbody>
                <tr>
                  <th scope="row">주소</th>
                  <td>{{ atr.addr1 }} {{ atr.addr2 }}</td>
                </tr>
                <tr>
                  <th scope="row">번호</th>
                  <td>{{ atr.tel }}</td>
                </tr>
                <tr>
                  <th scope="row">설명</th>
                  <td>{{ atr.overview }}</td>
                </tr>
              </tbody>
            </table>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "AttractionDetail",
  data() {
    return {
      atr: null,
    };
  },
  computed: {
    ...mapState("attractionStore", ["attraction"]),
  },
  methods: {
    init() {
      this.atr = this.attraction;
      console.log("모달 생성: " + this.atr.title);
    },
  },
  watch: {
    attraction: function () {
      //   alert("관광지 데이터 변화: " + this.attraction.title);
      this.atr = this.attraction;
    },
  },
  created() {},
  mounted() {},
};
</script>

<style scoped>
#attraction-detail {
  text-align: center;
}
.img-container {
  margin: auto;
}
.card-header {
  font-size: 25px;
  font-weight: bold;
}
th {
  width: 15%;
  min-width: 60px;
  vertical-align: center;
}
td {
  width: 85%;
}
</style>
