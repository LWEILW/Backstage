<template>
  <div class="breadBox">
    <!--  面包屑组件  -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item
        v-for="(item,index) in breadList"
        :key="index"
        :to="{ path: item.path }"
      >{{item.meta.title}}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
  export default {
    name: "breadBox",
    data() {
      return {
        // 路由集合
        breadList: []
      };
    },
    watch: {
      $route() {
        this.getBreadcrumb();
      }
    },
    methods: {
      isHome(route) {
        return route.name === "HomePage";
      },
      getBreadcrumb() {
        let matched = this.$route.matched;
        //如果不是首页
        if (!this.isHome(matched[0])) {
          matched = [{path: "/HomePage", meta: {title: "首页"}}].concat(matched);
        }
        this.breadList = matched;
        console.log(this.breadList)
      }
    },
    created() {
      this.getBreadcrumb();
    }
  }
</script>

<style scoped>
  .breadBox {
    height: 15px;
    padding: 10px;
    color: #FFFFFF;
    /*background:#f8f8f8;*/
  }
</style>
