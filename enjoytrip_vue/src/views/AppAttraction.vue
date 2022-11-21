<template>
  <div class="main">
    <attraction-header-select></attraction-header-select>
    <attraction-map></attraction-map>
  </div>
</template>

<script>
import AttractionMap from "@/components/attraction/AttractionMap";
import AttractionHeaderSelect from "@/components/attraction/AttractionHeaderSelect";

export default {
  name: "AppAttraction",
  components: {
    AttractionMap,
    AttractionHeaderSelect,
  },
};
</script>

<style scoped>
.main {
  height: 100vh;
}
</style>
