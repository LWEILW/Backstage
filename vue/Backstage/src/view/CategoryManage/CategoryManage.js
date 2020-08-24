import api from "@/api/category";

export default {
  name: "categoryManage",
  data() {
    return {
      // 文章台账列表
      categoryTable: [],

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 5,
      // 显示总共有多少数据
      totalCount: 0,

      // 弹出框状态：显示/隐藏
      dialogStatus: false,
      updateTitle: '',
      // 文章详情数据
      categoryData: {}
    };
  },
  // 初始化加载
  created() {
    this.categoryList();
  },
  methods: {

    // 文章台账列表
    categoryList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.categoryList(params).then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});

        } else if (res.data.status == 1) {
          // 赋值台账数据
          this.categoryTable = res.data.categoryList;
          this.currentPage = res.data.currentPage;
          this.totalCount = res.data.total;
        } else {
          this.$message.warning(res.data.message)
        }
      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.updateTitle = '新建分类';
      this.categoryData = {};
      this.dialogStatus = true;
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.updateTitle = '分类编辑';
      api.categoryDetails(row.categoryId).then(res => {
        this.categoryData = res.data.category;
      });
      this.dialogStatus = true;
    },

    // 用户保存
    handleSave(formName) {
      const params = {
        'categoryId': this.categoryData.categoryId,
        'categoryName': this.categoryData.categoryName,
      }
      api.categorySave(params).then(res => {
        // 返回结果判断
        if (res.data.status == 1) {
          this.dialogStatus = false;
          this.$message.success("保存成功");
          // 刷新页面
          this.categoryList();
        } else {
          this.$message.warning(res.data.message)
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
        api.deleteCategoryAll({"idList": [row.categoryId]}).then(res => {
          // 刷新页面
          this.categoryList();
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

    // 切换每页显示的数量,每页下拉显示数据
    handleSizeChange(size) {
      this.pageSize = size;
      console.log(`每页 ${size} 条`);
      this.categoryList()
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
      this.categoryList()
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
