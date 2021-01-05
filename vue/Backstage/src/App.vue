<template>
  <div id="app">
    <div v-if="$route.meta.keepAlive" class="app-keepAlive">
      <el-container>
        <!--侧边栏-->
        <el-aside :width="this.store.state.isCollapse?'64px':'300px'">
          <!-- 导航栏 -->
          <Navigation></Navigation>
        </el-aside>
        <el-container>
          <!--顶栏-->
          <el-header>
            <!-- 页首 -->
            <Header></Header>
            <!-- 多重标签页组件 -->
            <Tags></Tags>
            <!-- 面包屑组件 -->
            <!-- <BreadBox></BreadBox> -->
          </el-header>
          <!--主要区域-->
          <el-main>
            <router-view/>
          </el-main>
        </el-container>
      </el-container>
    </div>

    <!--    <div v-if="$route.meta.keepAlive" class="app-keepAlive">-->
    <!--      <el-container>-->
    <!--        <el-header>-->
    <!--          &lt;!&ndash; 页首 &ndash;&gt;-->
    <!--          <Header></Header>-->
    <!--          &lt;!&ndash; 面包屑组件 &ndash;&gt;-->
    <!--          &lt;!&ndash; <BreadBox></BreadBox>&ndash;&gt;-->
    <!--        </el-header>-->

    <!--        <el-container>-->
    <!--          <el-aside :width="this.store.state.isCollapse?'64px':'300px'">-->
    <!--            &lt;!&ndash; 导航栏 &ndash;&gt;-->
    <!--            <Navigation></Navigation>-->
    <!--          </el-aside>-->
    <!--          <el-container>-->
    <!--            &lt;!&ndash; 多重标签页组件 &ndash;&gt;-->
    <!--            <Tags></Tags>-->
    <!--            &lt;!&ndash; 面包屑 &ndash;&gt;-->
    <!--            <BreadBox></BreadBox>-->
    <!--            <el-main>-->
    <!--              <router-view/>-->
    <!--              &lt;!&ndash;    <wavePlay></wavePlay>&ndash;&gt;-->
    <!--            </el-main>-->
    <!--            <el-footer>-->
    <!--              &lt;!&ndash; 页尾 &ndash;&gt;-->
    <!--              Footer-->
    <!--            </el-footer>-->
    <!--          </el-container>-->

    <!--        </el-container>-->
    <!--      </el-container>-->
    <!--    </div>-->

    <div class="app-login" v-if="!$route.meta.keepAlive">
      <router-view></router-view>
      <!-- 波浪 -->
      <wavePlay></wavePlay>

    </div>
  </div>

</template>

<script>
  export default {
    name: 'App',
    // created() {
    //   // live2d动画
    //   setTimeout(() => {
    //     window.L2Dwidget.init({
    //       pluginRootPath: 'backstage/static/live2dw/',
    //       pluginJsPath: 'lib/',
    //       pluginModelPath: 'live2d-widget-model-hijiki/assets/',
    //       tagMode: false,
    //       debug: false,
    //       model: {jsonPath: '../backstage/static/live2dw/live2d-widget-model-hijiki/assets/hijiki.model.json'},
    //       display: {position: 'right', width: 300, height: 500},
    //       mobile: {show: true},
    //       log: false
    //     })
    //   }, 1000)
    // }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    font-size: 18px;
    color: #000000;
  }


</style>
