import api from "@/api/user";

export default {
  name: "userManage",
  data() {
    return {
      // 用户台账
      userTable: [],
      // 模糊查询字段
      userAccount: "",
      userName: "",
      userSex: "",
      userPhone: "",
      userEmail: "",
      userStatus: "",
      // 多选用户数组
      multipleSelection: [],

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,

      // 模态框_隐藏/显示
      dialogStatus: false,
      // 模态框标题名称
      dialogTitle: '',
      // 模态框用户详情
      userData: {},
      // 模态框角色列表
      roleSelectList: [],
      roleList: []
    };
  },
  // 初始化加载
  created() {
    this.getUserList();
    this.getRoleList();
  },
  methods: {
    // 初始化台账,checkbox展示
    checkboxJudge(row, index) {
      if (index != 0) {
        return 1
      } else {
        return 0
      }
    },
    // 自动记录已选的数据，获取key值
    getRowKeys(row) {
      return row.userId;
    },

    // 角色初始化台账
    getRoleList() {
      api.getRoleList().then(res => {
        this.roleList = res.data.roleList;
      });
    },

    // 用户台账
    getUserList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize,
        'userAccount': this.userAccount,
        'userName': this.userName,
        'userSex': this.userSex,
        'userPhone': this.userPhone,
        'userEmail': this.userEmail,
        'userStatus': this.userStatus
      }
      api.getUserList(params).then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});

        } else if (res.data.status == 1) {
          // 模糊查询，第一行插入空值
          res.data.userList.unshift({});
          this.userTable = res.data.userList;
          this.currentPage = res.data.currentPage;
          this.totalCount = res.data.total;
        } else {
          this.$message.warning(res.data.message)
        }

      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.dialogTitle = '新建用户';
      this.userData = {};
      this.roleSelectList = [];
      this.dialogStatus = true;
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.dialogTitle = '修改用户信息';
      // 展示用户详情
      api.detailsUser(row.userId).then(res => {
        // 返回结果判断
        if (res.data.status == 1) {
          this.userData = res.data.user;
          this.roleSelectList = res.data.roleList;
        } else {
          this.$message.warning(res.data.message)
        }

      });
      this.dialogStatus = true;
    },

    // 用户保存
    handleSave(formName) {
      // 关闭模态框
      this.dialogStatus = false;
      const params = {
        'userId': this.userData.userId,
        'userAccount': this.userData.userAccount,
        'userEmail': this.userData.userEmail,
        'userName': this.userData.userName,
        'userPhone': this.userData.userPhone,
        'telephone': this.userData.telephone,
        'userSex': this.userData.userSex,
        'roleList': this.roleSelectList
      };
      api.saveUser(params).then(res => {
        // 刷新页面
        this.getUserList();
        // 返回消息
        if (res.data.status == 1) {
          this.$message.success("保存成功");
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

        api.deleteUserById(row.userId).then(res => {
          // 刷新页面
          this.getUserList();
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("删除成功!");
          } else {
            this.$message.error("删除失败!");
          }
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除"
        });
      });
    },

    // 当选择项发生变化时会触发该事件
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    // 取消全选方法
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 批量删除方法
    handleDeleteAll() {
      console.log(this.multipleSelection);
      if (this.multipleSelection.length != 0) {
        this.$confirm("此操作将删除该批数据, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          api.deleteUserList({"userList": this.multipleSelection}).then(res => {
            if (res.data.data) {
              this.$message({type: "success", message: "删除成功!"});
            } else {
              this.$message({type: "fail", message: "删除失败!"});
            }
            // 刷新页面
            this.getUserList();
          });
        }).catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
      } else {
        this.$message.warning("未选择数据，无法删除");
      }
    },

    // 用户状态更新
    changeSwitch: function ($event, row) {
      const params = {
        'userId': row.userId,
        'userStatus': $event
      };
      api.changeUserStatus(params).then(res => {
        // 返回结果判断
        if (res.data.status == 1) {
          this.$message.success("更新成功!");
        } else {
          this.$message.warning("更新失败!");
        }
        // 刷新页面
        this.getUserList();
      });
    },

    // 重置密码
    handleResetPassword(row) {
      this.$confirm("是否进行密码重置操作？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.resetPassword(row.userId).then(res => {
          // 刷新页面
          this.getUserList();
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("重置成功!");
          } else {
            this.$message.error("重置失败!");
          }
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消重置"
        });
      });
    },

    // 导出
    ExcelExport(rows) {
      api.downUser(rows).then(res => {

      });
    },


    // 导入用户

    // 导出用户


    // 切换每页显示的数量,每页下拉显示数据
    handleSizeChange(size) {
      this.pageSize = size;
      console.log(`每页 ${size} 条`);
      this.getUserList()
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
      this.getUserList()
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
