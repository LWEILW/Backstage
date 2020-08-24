<template>
  <div class="navigation-container">
    <div class="navigation-box">

      <!-- 导航栏logo -->
      <div class="navigation-logo">
        <div class="navigation-image">
          <img src="../../../static/image/cat.jpg"/>
        </div>
        <div class="navigation-font">
          <h3>BlOgGeR_WW</h3>
        </div>
      </div>

      <!-- el-menu导航栏
       1.default-active:高亮显示 刷新页面后，对应menu高亮
       2.collapse:侧边栏是否收展
       3.@select:选中事件
       4.background-color:菜单的背景色
       5.text-color:菜单的文字颜色
       6.active-text-color:当前激活菜单的文字颜色
       7.unique-opened:是否只保持一个子菜单的展开
       8.collapse-transition:是否开启折叠动画
       rgb(191, 203, 217)
       -->
      <el-menu class="navigation-el-menu" :default-active="activeIndex" :collapse="isCollapse" @select="handleSelect"
               background-color="#474b4c" text-color="#f9f4dc" active-text-color="#1890ff"
               :unique-opened="true" :collapse-transition="false">

        <!-- 首页 -->
        <el-menu-item index="HomePage">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>

        <!-- 用户管理 -->
        <el-submenu index="UserManage">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>用户管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="UserManage">
              <span>用户一览</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 角色管理 -->
        <el-submenu index="RoleManage">
          <template slot="title">
            <i class="el-icon-s-custom"></i>
            <span>角色管理</span>
          </template>
          <el-menu-item-group class="itemGroup">
            <el-menu-item class="twoGroup" index="RoleManage">
              <span>角色一览</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 权限管理 -->
        <el-submenu index="PermissionManage">
          <template slot="title">
            <i class="el-icon-s-grid"></i>
            <span>权限管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="PermissionManage">
              <span>权限一览</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 模块管理 -->
        <el-submenu index="MenusManage">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span>模块管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="MenusManage">
              <span>模块一览</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 文章管理 -->
        <el-submenu index="ArticleManage">
          <template slot="title">
            <i class="el-icon-notebook-2"></i>
            <span>文章管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="ArticleManage">
              <span>文章一览</span>
            </el-menu-item>
            <el-menu-item class="twoGroup" index="BloggerCreatePage">
              <span>文章创建</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 分类管理  -->
        <el-submenu index="CategoryManage">
          <template slot="title">
            <i class="el-icon-s-order"></i>
            <span>分类管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="CategoryManage">
              <span>分类一览</span>
            </el-menu-item>
            <el-menu-item class="twoGroup" index="BloggerCreatePage">
              <span>分类创建</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>


        <!-- 标签管理  -->
        <el-submenu index="LabelManage">
          <template slot="title">
            <i class="el-icon-price-tag"></i>
            <span>标签管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="LabelManage">
              <span>标签一览</span>
            </el-menu-item>
            <el-menu-item class="twoGroup" index="LabelManage">
              <span>标签创建</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>


        <!-- 商品管理 -->
        <el-submenu index="CommodityManage">
          <template slot="title">
            <i class="el-icon-picture"></i>
            <span>商品管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="CommodityManage">
              <span>商品一览</span>
            </el-menu-item>
            <el-menu-item class="twoGroup" index="CommodityManage">
              <span>商品创建</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- 定时器展示 -->
        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-alarm-clock"></i>
            <span>定时器展示</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="twoGroup" index="quartzManage">
              <span>定时器一览</span>
            </el-menu-item>
            <el-menu-item class="twoGroup" index="quartzManage">
              <span>定时器创建</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

      </el-menu>

    </div>
  </div>
</template>

<script>
  export default {
    name: "navigation",
    // 存储数据
    data() {
      return {
        // 刷新页面,初始化导航名称
        activeIndex: this.$route.name,
        // 是否水平折叠收起菜单
        isCollapse: this.store.state.isCollapse
      };
    },
    // 方法
    methods: {
      // 选择路由方法
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
        switch (key) {
          case "HomePage":
            this.$router.push("/HomePage");
            break;
          case "UserManage":
            this.$router.push("/UserManage");
            break;
          case "RoleManage":
            this.$router.push("/RoleManage");
            break;
          case "PermissionManage":
            this.$router.push("/PermissionManage");
            break;
          case "MenusManage":
            this.$router.push("/MenusManage");
            break;
          case "ArticleManage":
            this.$router.push("/ArticleManage");
            break;
          case "CategoryManage":
            this.$router.push("/CategoryManage");
            break;
          case "LabelManage":
            this.$router.push("/LabelManage");
            break;
          case "CommodityManage":
            this.$router.push("/CommodityManage");
            break;
          case "quartzManage":
            this.$router.push("/quartzManage");
            break;
        }
      },
    },
    // 监听isCollapse变化,控制导航栏展开关闭
    computed: {
      changeIsCollapse() {
        return this.store.state.isCollapse
      }
    },
    watch: {
      changeIsCollapse: function (nl, ol) {
        this.isCollapse = this.store.state.isCollapse
      }
    }

  };
</script>


<style scoped>
  .navigation-container {
    height: 100%;
  }

  .navigation-box {
    height: 100%;
  }

  .navigation-logo {
    display: flex;
    justify-content: space-around;
    height: 80px;
    background-color: #5B5B5B;
  }

  .navigation-image {
    /*圆弧*/
    border-radius: 50%;
    padding: 2px;
    /*边框*/
    border: 1px solid #617172;
    /*阴影*/
    box-shadow: 0 0 10px #cdd1d3;
    height: 70px;
    width: 70px;
    background-color: #737c7b;
  }

  .navigation-image img {
    height: 100%;
    width: 100%;
    border-radius: 50%;
  }

  .navigation-font {
    font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif;
    font-size: 1em;
    color: #cdd1d3;
  }

  /* 导航栏 侧边边框颜色 */
  .navigation-el-menu {
    height: 100%;
    border-right: solid 1px #474b4c;
  }

  /* 导航栏收缩样式 */
  .navigation-el-menu:not(.el-menu--collapse) {
    min-width: 200px;
  }

  /* 二级目录背景样式 */
  .navigation-el-menu .twoGroup {
    background-color: #363433 !important;
  }

</style>
