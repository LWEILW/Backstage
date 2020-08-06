<template>
  <div class="header-container">
    <div class="header_box">

      <!--  logo样式 -->
      <div class="header-logo">
        <div class="header-font">
          <i class="iconfont  iconliebiao" @click="changeStatus"></i>
          <span @click="drawer = true">Bókè guǎnlǐ xìtǒng</span>
        </div>
      </div>

      <!-- 用户名下拉菜单
       command:点击菜单项触发的事件回调
       trigger:触发下拉的行为
       divided:显示分割线
       -->
      <div class="header-user">
        <div>
          <el-dropdown @command="handleCommand">
            <!-- 用户头像 -->
            <img src="../../../static/image/cat.jpg"/>
            <!--          <img :src="url"/>-->
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="info">个人资料</el-dropdown-item>
              <el-dropdown-item command="loginOut" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div>
          <span>账户:{{account}}</span>
          <span>角色:{{password}}</span>
        </div>

      </div>

      <!-- 右侧抽屉 -->
      <el-drawer title="我是标题" :visible.sync="drawer" direction="rtl" :with-header="false" size="50%">

        <span>我来啦!</span>

        <el-table :data="gridData">
          <el-table-column property="date" label="日期" width="150"></el-table-column>
          <el-table-column property="name" label="姓名" width="200"></el-table-column>
          <el-table-column property="address" label="地址"></el-table-column>
        </el-table>
      </el-drawer>

    </div>
  </div>
</template>

<script>

  export default {
    name: "Header",
    // 存储数据
    data() {
      return {
        account: "",
        password: "",
        url: '../static/image/tx.jpg',
        // 右侧抽屉
        drawer: false,
        direction: 'rtl',
        gridData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
      };
    },
    // 初始化加载
    created() {
      let user = JSON.parse(sessionStorage.getItem('user'));
      this.account = user.userName
      this.password = user.roleList[0]
    },
    // 方法
    methods: {
      // 下拉菜单事件回调
      handleCommand(command) {
        if ("loginOut" == command) {
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});
        }
      },
      changeStatus() {
        if (this.store.state.isCollapse) {
          this.store.commit('changeStatus', false);
        } else {
          this.store.commit('changeStatus', true);
        }
      }
    }
  }
</script>


<style scoped>
  .header-container {
    height: 40px;
    line-height: 40px;
    /* 下边框 */
    border-bottom: 1px solid #d6ecf0;
    /* 阴影 */
    box-shadow: 0 5px 10px #ddd;
    padding: 0 20px;
  }

  .header_box {
    display: flex;
    justify-content: space-between;
    height: 100%;
  }

  .header-logo {
    display: flex;
    justify-content: space-between;
  }

  .header-font {
    display: flex;
    justify-content: space-between;
  }

  .header-font i {
    font-size: 20px;

  }

  .header-font span {
    padding-left: 10px;
    font-weight: bold;
  }

  .header-user {
    /*!*阴影*!*/
    /*box-shadow: 0 0 10px #cdd1d3;*/
    line-height: 40px;
    height: 40px;
    display: flex;
    justify-content: space-between;
  }

  .header-user span {

    font-size: 16px;
  }

  .header-user img {
    width: 35px;
    height: 35px;
    /*圆弧*/
    border-radius: 20%;
    /*阴影*/
    box-shadow: 0 0 10px #cdd1d3;
    /*边框*/
    border: 1px solid #fffef8;
  }

</style>
