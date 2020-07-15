<template>
  <div class="mainMoudle">
    <el-calendar>
      <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
      <template
        slot="dateCell"
        slot-scope="{date, data}">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
        </p>
      </template>
    </el-calendar>
  </div>
</template>

<script>
  import MainMoudle from "./Home/components/mainMoudle";

  export default {
    name: "HomePage",
    components: {
      MainMoudle
    },
    data() {
      return {};
    }
  };
</script>

<style scoped>
  .is-selected {
    color: #1989FA;
  }
</style>
