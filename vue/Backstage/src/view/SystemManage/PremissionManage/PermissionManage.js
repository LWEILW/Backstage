import api from "@/api/permission";

export default {
  name: "PermissionManage",
  data() {
    return {
      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,
      // 权限模糊查询
      permissionParams: {},
      // 权限台账列表
      permissionTable: [],
      // 模态框初始化隐藏
      permissionDialog: false,
      // 模态框标题名称
      updateTitle: '',
      // 权限详情数据
      permissionData: {},
      // 父类名称
      parentId: [
        {value: 1, label: '用户模块'},
        {value: 2, label: '角色模块'},
        {value: 3, label: '权限模块'},
        {value: 4, label: '文章模块'}],
      // 权限登记
      levelNo: [
        {value: 1, label: 1},
        {value: 2, label: 2},
        {value: 3, label: 3},
        {value: 4, label: 4}]
    };
  },
  // 初始化加载
  created() {
    this.getPermissionList();
  },
  methods: {
    // 切换每页显示的数量
    handleSizeChange(size) {
      this.pageSize = size;
      //每页下拉显示数据
      console.log(`每页 ${size} 条`);
    },
    // 切换页码
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      //点击第几页
      console.log(`当前页: ${currentPage}`);
    },
    // 重置方法
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 跳转至创建画面
    handleCreate() {
      this.permissionDialog = true;
      this.updateTitle = '添加权限';
      this.permissionData = {};
      // this.resetForm("permissionForm");
    },
    // 跳转至编辑画面
    handleEdit(row) {
      this.permissionDialog = true;
      this.updateTitle = '编辑权限';
      api.detailsPermission(row.permissionId).then(res => {
        this.permissionData = res.data;
      });
    },
    // 权限台账列表
    getPermissionList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.getPermissionList(params).then(res => {
        console.log(res.data.permissionList)
        this.permissionTable = res.data.permissionList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
      });
    },
    // 权限保存
    handleSave(formName) {
      // 关闭模态框
      this.permissionDialog = false;
      api.savePermission(this.permissionData).then(res => {
        this.$message.success("保存成功");
        // 刷新页面
        this.getPermissionList();
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
          api.deletePermission(row.permissionId).then(res => {
            // 刷新页面
            this.getPermissionList();
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
