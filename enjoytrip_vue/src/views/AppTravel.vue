<template>
  <div class="main row" data-aos="fade-up" data-aos-duration="200">
    <div class="col-7">
      <travel-map></travel-map>
    </div>
    <div class="col-5">
      <router-view></router-view>
      <!-- <travel-list></travel-list> -->
    </div>
  </div>
</template>

<script>
import TravelMap from "@/components/travel/TravelMap";

export default {
  name: "AppTravel",
  components: {
    TravelMap,
    // TravelList,
  },
  data() {
    return {};
  },
  computed: {},
  mounted() {},
  methods: {},
};
</script>

<style scoped>
.main {
  height: 100vh;
  margin: 0;
}
h2 {
  text-align: center;
}
</style>
