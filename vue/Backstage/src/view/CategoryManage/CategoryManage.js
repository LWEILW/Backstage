import api from "@/api/category";

export default {
  name: "categoryManage",
  data() {
    return {
      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 5,
      // 显示总共有多少数据
      totalCount: 0,
      // 文章模糊查询
      categoryParams: {},
      // 文章台账列表
      categoryTable: [],
      // 文章详情数据
      categoryData: {},
      // 切换显示
      active: '1',
      isClear: false,
      detail: "",
      // 弹出框状态：显示/隐藏
      dialogStatus: false,
      updateTitle:''
    };
  },
  // 初始化加载
  created() {
    this.categoryList(this.currentPage, this.pageSize);
  },
  methods: {
    // 切换每页显示的数量
    handleSizeChange(size) {
      this.pageSize = size;
      // 每页下拉显示数据
      console.log(`每页 ${size} 条`);
      this.categoryList(this.currentPage, this.pageSize)
    },
    // 切换页码
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      // 点击第几页
      console.log(`当前页: ${currentPage}`);
      this.categoryList(this.currentPage, this.pageSize)
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 跳转至创建画面
    handleCreate() {
      this.active = "2";
    },
    // 跳转至编辑画面
    handleEdit(row) {
      this.active = "2";
      api.categoryDetails(row.categoryId).then(res => {
        this.categoryData = res.data;
      });
    },
    // 跳转至详情画面
    handleDetails(row) {
      this.active = "2";
      api.categoryDetails(row.categoryId).then(res => {
        this.categoryData = res.data;
        // 富文本赋值
        this.detail = res.data.categoryContent;
      });
    },
    // 返回至台账画面
    handleBack() {
      this.active = '1';
      this.resetForm("categoryFrom");
    },
    // 文章台账列表
    categoryList(currentPage, pageSize) {
      const params = {
        'currentPage': currentPage,
        'pageSize': pageSize
      }
      api.categoryList(params).then(res => {
        // 赋值台账数据
        this.categoryTable = res.data.categoryList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
      });
    },
    // 用户保存
    handleSave(formName) {
      const params = {
        'categoryName': this.categoryData.categoryName,
        'categoryTitle': this.categoryData.categoryTitle,
        'categoryContent': this.$refs.editorElem.value
      }
      api.categorySave(params).then(res => {
        this.$message.success("保存成功");
        this.active = "1";
        // 刷新页面
        this.categoryList(this.categoryParams);
        this.resetForm("categoryFrom");
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
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          api.categoryDelete(row.categoryId).then(res => {
            // 刷新页面
            this.categoryList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
