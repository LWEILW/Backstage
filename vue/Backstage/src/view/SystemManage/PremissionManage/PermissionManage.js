import api from "@/api/permission";

export default {
  name: "PermissionManage",
  data() {
    return {
      // 权限台账
      permissionTable: [],
      // 模糊查询字段
      permissionName: '',
      parentName: '',
      permissionPath: '',
      levelNo: '',
      createPerson: '',
      updatePerson: '',

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,

      // 模态框初始化隐藏
      permissionDialog: false,
      // 模态框标题名称
      updateTitle: '',
      // 权限详情数据
      permissionDetails: {},

      // 父类名称
      parentId: [],
      // 权限登记
      levelNoList: [
        {value: 1, label: 1},
        {value: 2, label: 2},
        {value: 3, label: 3},
        {value: 4, label: 4}]
    };
  },
  // 初始化加载
  created() {
    this.permissionList();
    this.getPermissionParent();
  },
  methods: {
    // 权限父类列表
    getPermissionParent() {
      api.getPermissionParent().then(res => {
        this.parentId = res.data.parentList;
      });
    },

    // 权限台账列表
    permissionList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize,
        'permissionName': this.permissionName,
        'parentName': this.parentName,
        'permissionPath': this.permissionPath,
        'levelNo': this.levelNo,
        'createPerson': this.createPerson,
        'updatePerson': this.updatePerson
      }
      api.permissionList(params).then(res => {
        // 返回结果判断
        if (res.data.status == 1) {
          this.permissionTable = res.data.permissionList;
          this.currentPage = res.data.currentPage;
          this.totalCount = res.data.total;
        } else {
          this.$message.warning(res.data.message)
        }

      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.updateTitle = '添加权限';
      this.permissionDetails = {};
      this.permissionDialog = true;
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.updateTitle = '编辑权限';
      api.detailsPermission(row.permissionId).then(res => {
        this.permissionDetails = res.data.permission;
      });
      this.permissionDialog = true;
    },

    // 权限保存
    handleSave(formName) {
      // 关闭模态框
      this.permissionDialog = false;
      api.createOrUpdatePermission(this.permissionDetails).then(res => {
        // 刷新页面
        this.permissionList();
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

        api.deletePermission(row.permissionId).then(res => {
          // 刷新页面
          this.permissionList();
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
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
    },
    // 重置方法
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
