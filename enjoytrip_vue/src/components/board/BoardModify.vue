<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-4">
      <b-col>
        <b-navbar toggleable="lg" type="dark" variant="dark">
          <b-navbar-brand class="ms-3">
            <h3>게시글 수정</h3>
          </b-navbar-brand>
        </b-navbar>
      </b-col>
    </b-row>
    <board-input-item type="modify" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem.vue";

export default {
  name: "BoardModify",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
