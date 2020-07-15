import api from "@/api/user";

export default {
  name: "userManage",
  data() {
    return {
      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,
      // 搜索框实时同步数据
      input: "",
      // 多选用户数组
      multipleSelection: [],
      // 用户台账模糊查询
      userParams: {
        userAccount: '',
        userName: '',
        userSex: ''
      },
      // 用户台账列表
      userList: [],
      // 模态框_隐藏/显示
      UserDialog: false,
      // 模态框标题名称
      updateTitle: '',
      // 模态框用户详情信息
      userData: {},
      roleList: []
    };
  },
  // 初始化加载
  created() {
    this.getUserList(this.currentPage, this.pageSize);
  },
  methods: {
    // 切换每页显示的数量
    handleSizeChange(size) {
      this.pageSize = size;
      //每页下拉显示数据
      console.log(`每页 ${size} 条`);
      this.getUserList(this.currentPage, this.pageSize)
    },
    // 切换页码
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      //点击第几页
      console.log(`当前页: ${currentPage}`);
      this.getUserList(this.currentPage, this.pageSize)
    },
    // 当选择项发生变化时会触发该事件
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    // 取消选择方法
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 跳转至创建画面
    handleCreate() {
      this.UserDialog = true;
      this.updateTitle = '新建用户';
      this.userData = {};
      this.roleList = [];
      // this.resetForm("userform");
    },
    // 跳转至编辑画面
    handleEdit(row) {
      this.UserDialog = true;
      this.updateTitle = '修改用户信息';
      api.detailsUser(row.userId).then(res => {
        this.userData = res.data.user;
        this.roleList = res.data.roleList;
      });
    },
    // 用户台账列表
    getUserList(currentPage, pageSize) {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.getUserList(params).then(res => {
        this.userList = res.data.userList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
      });
    },
    // 用户保存
    handleSave(formName) {
      // 关闭模态框
      this.UserDialog = false;
      const params = {
        'userAccount': this.userData.userAccount,
        'userEmail': this.userData.userEmail,
        'userName': this.userData.userName,
        'userPhone': this.userData.userPhone,
        'userSex': this.userData.userSex,
        'roleList': this.roleList
      };
      api.saveUser(params).then(res => {
        this.$message.success("保存成功");
        // 刷新页面
        this.getUserList(this.userParams);
      });
    },
    // 删除方法
    handleDelete(row) {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {

          api.deleteUser(row.userId).then(res => {
            if (res.data.data) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
            } else {
              this.$message({
                type: "fail",
                message: "删除失败!"
              });
            }

            // 刷新页面
            this.getUserList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    // 批量删除方法
    handleDeleteAll() {
      this.multipleSelection;
      console.log(index, row);
    },
    // 用户状态更新
    changeSwitch: function ($event, userStatus) {
      console.log($event);
      console.log(userStatus);
    },
    // changeSwitch(user) {
    //   console.log(this.user)
    //
    //   console.log(this.user.userStatus);
    //   // shopUpdate(b), then(response => {
    //   //   if (response.code == 0) {
    //   //     // 成功了不做处理，因为switch状态已经修改
    //   //     this.message({
    //   //       message: "状态修改成功",
    //   //       type: "success"
    //   //     })
    //   //   } else {
    //   //     let newData = b;
    //   //     newData.state = newData.state == 1 ? "2" : "1";
    //   //     this.tableData(index) = newData;
    //   //   }
    //   // })
    // }
  }
};
