import api from "@/api/role";

export default {
  name: "RoleManage",
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
      // 角色模糊查询
      roleParams: {},
      // 角色台账列表
      roleTable: [],
      // 角色模块模态框隐藏
      roleDialog: false,
      // 模态框标题名称
      updateTitle: '',
      // 角色详情数据
      roleDetails: {},
      // 权限展示所有数据
      permissionShowData: [],
      // 权限编辑已选数据
      permissionChangeData: [],
      // 权限编辑树状图规则
      permissionEditProps: {
        id: 'permissionId',
        label: 'permissionName',
        children: 'children'
      }
    };
  },
  // 初始化加载
  created() {
    this.getRoleList();
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
    // 节点选中状态发生变化时的回调
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate + "节点选中状态发生变化时的回调");
    },
    // 字体 节点被点击时的回调
    handleNodeClick(data) {
      console.log(data + "节点被点击时的回调");
    },
    // 重置方法
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 跳转至创建画面
    handleCreate() {
      this.roleDialog = true;
      this.updateTitle = '添加角色';
      this.roleDetails = {};
      // this.resetForm("roleForm");
      // 展示权限列表
      api.getPermissionAllListByRoleId().then(res => {
        this.permissionShowData = res.data.permissionAllList;
      });
    },
    // 跳转至编辑画面
    handleEdit(row) {
      api.detailsRole(row.roleId).then(res => {
        // if (res.data.retCode === 200) {
        this.roleDialog = true;
        this.updateTitle = '编辑角色';
        this.roleDetails = res.data.role;
        // 全部权限列表
        this.permissionShowData = res.data.permissionAllList;
        // 已勾选权限列表
        this.permissionChangeData = res.data.permissionList;
        // } else {
        //   console.log(res.data.message)
        // }
      });
    },
    // 角色台账列表
    getRoleList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.getRoleList(params).then(res => {
        // if (res.data.retCode === 200) {
        // 角色台账列表
        this.roleTable = res.data.roleList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
        // } else if (res.data.retCode === 400) {
        //   console.log(400)
        // }

      });
    },
    // 角色保存
    handleSave(formName) {
      // 关闭模态框
      this.roleDialog = false;
      const params = {
        'roleId': this.roleDetails.roleId,
        'roleDescribe': this.roleDetails.roleDescribe,
        'roleName': this.roleDetails.roleName,
        'permissionList': this.$refs.permissionTree.getCheckedKeys(),
      }
      api.saveRole(params).then(res => {
        this.$message.success("保存成功");
        // 刷新页面
        this.getRoleList();
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
          api.deleteRole(row.roleId).then(res => {
            // 刷新页面
            this.getRoleList();
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


