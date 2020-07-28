import api from "@/api/commodity";

export default {
  name: "commodityManage",
  data() {
    return {
      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,
      // 商品模糊查询
      commodityParams: {},
      // 商品台账列表
      commodityTable: [],
      // 商品详情数据
      commodityData: {},
      // 切换显示
      active: '1',
      isClear: false,
      detail: ""
    };
  },
  // 初始化加载
  created() {
    this.commodityList(this.currentPage, this.pageSize);
  },
  methods: {
    // 切换每页显示的数量
    handleSizeChange(size) {
      this.pageSize = size;
      // 每页下拉显示数据
      console.log(`每页 ${size} 条`);
    },
    // 切换页码
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      // 点击第几页
      console.log(`当前页: ${currentPage}`);
      this.commodityList(this.currentPage, this.pageSize)
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
      api.commodityDetails(row.commodityId).then(res => {
        this.commodityData = res.data;
      });
    },
    // 跳转至详情画面
    handleDetails(row) {
      this.active = "2";
      api.commodityDetails(row.commodityId).then(res => {
        this.commodityData = res.data;
        // 富文本赋值
        this.detail = res.data.commodityContent;
      });
    },
    // 返回至台账画面
    handleBack() {
      this.active = '1';
      this.resetForm("commodityFrom");
    },
    // 商品台账列表
    commodityList(currentPage, pageSize) {
      const params = {
        'currentPage': currentPage,
        'pageSize': pageSize
      }
      api.commodityList(params).then(res => {
        // 赋值台账数据
        this.commodityTable = res.data.commodityList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
      });
    },
    // 用户保存
    handleSave(formName) {
      const params = {
        'commodityName': this.commodityData.commodityName,
        'commodityTitle': this.commodityData.commodityTitle,
        'commodityContent': this.$refs.editorElem.value
      }
      api.commoditySave(params).then(res => {
        this.$message.success("保存成功");
        this.active = "1";
        // 刷新页面
        this.commodityList(this.commodityParams);
        this.resetForm("commodityFrom");
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
          api.commodityDelete(row.commodityId).then(res => {
            // 刷新页面
            this.commodityList();
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
