import api from "@/api/quartz";


export default {
  name: "quartzManage",
  data() {
    return {
      // 定时器台账列表
      quartzTable: [],

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,

      // 弹出框状态：显示/隐藏
      dialogStatus: false,
      updateTitle: '',
      // 定时器详情数据
      quartzData: {}

    };
  },
  // 初始化加载
  created() {
    this.quartzList();
  },
  methods: {
    // 定时器台账列表
    quartzList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.quartzList(params).then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});

        } else if (res.data.status == 1) {
          // 赋值台账数据
          this.quartzTable = res.data.quartzList;
          this.currentPage = res.data.currentPage;
          this.totalCount = res.data.total;
        } else {
          this.$message.warning(res.data.message)
        }
      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.updateTitle = '新建定时器';
      this.quartzData = {};
      this.dialogStatus = true;
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.updateTitle = '编辑定时器';
      api.detailsQuartz(row.quartzId).then(res => {
        this.quartzData = res.data.quartz;
      });
      this.dialogStatus = true;
    },

    // 用户保存
    handleSave(formName) {
      const params = {
        'quartzId': this.quartzData.quartzId,
        'jobName': this.quartzData.jobName,
        'jobDesc': this.quartzData.jobDesc,
        'jobGroup': this.quartzData.jobGroup,
        'startTime': this.quartzData.startTime,
        'cronExpression': this.quartzData.cronExpression,
        'invokeParam': this.quartzData.invokeParam,
        'jobStatus': "启用"
      }
      api.createOrUpdateQuartz(params).then(res => {
        // 返回消息
        if (res.data.status == 1) {
          this.dialogStatus = false;
          this.$message.success("保存成功");
          // 刷新页面
          this.quartzList();
        } else {
          this.$message.error("保存失败");
        }
      });
    },

    // 删除方法
    handleDelete(row) {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.deleteQuartzList(row.quartzId).then(res => {
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("删除成功!");
          } else {
            this.$message.error("删除失败!");
          }
          // 刷新页面
          this.quartzList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除"
        });
      });
    },

    // 暂停所有
    handleSuspendAll() {
      this.$confirm("此操作将暂停所有任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.suspendAll().then(res => {
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("暂停成功!");
          } else {
            this.$message.error("暂停失败!");
          }
          // 刷新页面
          this.quartzList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消暂停所有"
        });
      });
    },

    // 恢复所有
    handleRecoveryAll() {
      this.$confirm("此操作将恢复所有任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.recoveryAll().then(res => {
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("暂停成功!");
          } else {
            this.$message.error("暂停失败!");
          }
          // 刷新页面
          this.quartzList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消恢复所有"
        });
      });
    },

    // 暂停job
    handleSuspendJob(row) {
      this.$confirm("此操作将暂停该任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.suspendJob(row.quartzId).then(res => {
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("暂停成功!");
          } else {
            this.$message.error("暂停失败!");
          }
          // 刷新页面
          this.quartzList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消暂停job"
        });
      });
    },

    // 恢复job
    handleRecoveryJob(row) {
      this.$confirm("此操作将恢复该任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.recoveryJob(row.quartzId).then(res => {
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("暂停成功!");
          } else {
            this.$message.error("暂停失败!");
          }
          // 刷新页面
          this.quartzList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消恢复job"
        });
      });
    },


    // 切换每页显示的数量,每页下拉显示数据
    handleSizeChange(size) {
      this.pageSize = size;
      console.log(`每页 ${size} 条`);
      this.quartzList();
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
      this.quartzList();
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }

  }
};
