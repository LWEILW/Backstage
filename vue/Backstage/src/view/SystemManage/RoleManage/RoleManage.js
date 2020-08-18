import api from "@/api/role";

export default {
  name: "RoleManage",
  data() {
    return {
      // 角色台账
      roleTable: [],
      // 模糊查询字段
      roleName: "",
      roleDescribe: "",
      createPerson: "",
      updatePerson: "",

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
      // 模态框角色详情
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
    this.getPermissionAllListByRoleId();
  },
  methods: {

    // 权限展示所有数据
    getPermissionAllListByRoleId() {
      // 展示权限列表
      api.getPermissionAllListByRoleId().then(res => {
        this.permissionShowData = res.data.permissionAllList;
      });
    },

    // 角色台账
    getRoleList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize,
        'roleName': this.roleName,
        'roleDescribe': this.roleDescribe,
        'createPerson': this.createPerson,
        'updatePerson': this.updatePerson
      }
      api.getRoleList(params).then(res => {
        // 模糊查询，第一行插入空值
        res.data.roleList.unshift({});
        this.roleTable = res.data.roleList;
        this.currentPage = res.data.currentPage;
        this.totalCount = res.data.total;
      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.dialogTitle = '添加角色';
      this.roleDetails = {};
      // 已勾选权限列表
      // 清除element中el-tree已选中的选项，光是清除 default-checked-keys 值是没用的
      // element的el-tree 组件是采用赋值的方式改变是否勾选的
      this.$refs.permissionTree.setCheckedKeys([]);
      this.dialogStatus = true;
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.dialogTitle = '编辑角色';
      api.detailsRole(row.roleId).then(res => {
        this.roleDetails = res.data.role;
        // 已勾选权限列表
        this.permissionChangeData = res.data.permissionList;
      });
      this.dialogStatus = true;
    },

    // 角色保存
    handleSave(formName) {
      // 关闭模态框
      this.dialogStatus = false;
      const params = {
        'roleId': this.roleDetails.roleId,
        'roleDescribe': this.roleDetails.roleDescribe,
        'roleName': this.roleDetails.roleName,
        'permissionList': this.$refs.permissionTree.getCheckedKeys(),
      }
      api.saveRole(params).then(res => {
        // 刷新页面
        this.getRoleList();
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

        api.deleteRole(row.roleId).then(res => {
          // 刷新页面
          this.getRoleList();
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

    // 节点选中状态发生变化时的回调
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate + "节点选中状态发生变化时的回调");
    },
    // 字体 节点被点击时的回调
    handleNodeClick(data) {
      console.log(data + "节点被点击时的回调");
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


