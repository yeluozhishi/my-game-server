<template>
  <div style="width: 100%">
    <label>home page</label>
  </div>

  <div style="display: inline-flex; width: 100%" >
    <div style="float: left">
      <homeCollapse></homeCollapse>
    </div>
    <div style="width: 100%">
      <GameServer></GameServer>
    </div>
  </div>


</template>

<script>
import homeCollapse from "@/views/HomeCollapse";
import GameServer from "@/views/GameServer";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "home",
  components:{
    homeCollapse,
    GameServer,
  }
}
</script>

<style>

</style>