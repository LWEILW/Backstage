<template>
  <!-- 用户管理 -->
  <div class="user_container">
    <!-- 用户台账 -->
    <div class="user_table">
      <!-- 搜索框、按钮 -->
      <div class="user_operation">
        <el-button type="primary" icon="el-icon-edit" @click="toggleSelection()">取消全选</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate()">模板下载</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate()">导入用户</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate()">导出用户</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleDeleteAll()">批量删除</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate()">新建用户</el-button>
      </div>

      <!--台账
      1.data:显示的数据
      2.stripe:是否为斑马纹
      3.border:是否带有纵向边框,
      4.row-click:表格添加行点击事件
      5.header-cell-style:表头背景色
      6.selection-change:当选择项发生变化时会触发该事件
      7.row-click:行点击事件
      8.ref:显示元素身份
      9.row-key:行数据的Key,用来优化 Table 的渲染;
      10.reserve-selection: 自动记录已选的数据,与row-key联用 -->
      <div class="user_list">
        <el-table :data="userTable" stripe border @selection-change="handleSelectionChange" :row-key="getRowKeys"
                  size="small" ref="multipleTable" :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">
          <el-table-column type="selection" :selectable='checkboxJudge' disabled='true' :reserve-selection="true"
                           width="55"></el-table-column>

          <el-table-column prop="userAccount" label="账号" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="userAccount" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.userAccount }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="userName" label="姓名" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="userName" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.userName }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="userSex" label="性别" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="userSex" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.userSex }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="userPhone" label="电话" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="userPhone" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.userPhone }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="telephone" label="邮箱" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="userEmail" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.userEmail }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="userStatus" label="用户状态" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-select v-model="userStatus" @keyup.enter.native='getUserList' filterable clearable size="small"
                           placeholder="请选择">
                  <el-option label="未启用" value="0"></el-option>
                  <el-option label="启用" value="1"></el-option>
                </el-select>
              </div>
              <div v-else>
                <el-switch
                  v-model="scope.row.userStatus"
                  active-color="#13ce66"
                  inactive-color="#b2bbbe"
                  :active-value="1"
                  :inactive-value="0"
                  @change=changeSwitch($event,scope.row)>
                </el-switch>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="createDate" label="创建时间" align="center" sortable>
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input size="small" disabled></el-input>
              </div>
              <div v-else>{{ scope.row.createDate }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="updateDate" label="更新时间" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input size="small" disabled></el-input>
              </div>
              <div v-else>{{ scope.row.updateDate }}</div>
            </template>
          </el-table-column>

          <el-table-column fixed="right" label="操作" width="185">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>
                <el-button @click.stop="handleEdit(scope.row)" type="primary" icon="el-icon-edit" size="small"
                           plain></el-button>
                <el-button @click.stop="handleDelete(scope.row)" type="danger" icon="el-icon-delete" size="small"
                           plain></el-button>
                <el-button @click.stop="handleResetPassword(scope.row)" type="warning" icon="el-icon-delete"
                           size="small"
                           plain></el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!--分页
      1.size-change:页码大小变动时候触发的事件
      2.current-change:当前页变动时候触发的事件
      3.total:总条目数
      4.current-page:当前页数
      5.page-size:每页显示条目个数
      6.page-sizes:每页显示个数选择器的选项设置
      7.hide-on-single-page:只有一页时是否隐藏
      8.layout:组件布局，子组件名用逗号分隔
      9.background:是否为分页按钮添加背景色
      -->
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :total="totalCount"
          :current-page="currentPage"
          :page-623188="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :hide-on-single-page="false"
          layout="total, sizes, prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>

    <!--     弹出模态框-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogStatus" width="900px">
      <el-form :model="userData" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="工号" prop="userAccount">
              <el-input v-model="userData.userAccount"></el-input>
            </el-form-item>

            <el-form-item label="姓名" prop="userName">
              <el-input v-model="userData.userName"></el-input>
            </el-form-item>

            <el-form-item label="性别" prop="userSex">
              <el-radio-group v-model="userData.userSex">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="创建人" prop="createPerson">
              <el-input v-model="userData.createPerson" :disabled="true"></el-input>
            </el-form-item>

            <el-form-item label="更新人" prop="updatePerson">
              <el-input v-model="userData.updateDate" :disabled="true"></el-input>
            </el-form-item>

            <el-form-item label="角色列表" required>
              <el-select v-model="roleSelectList" style="width:770px;" placeholder="角色列表" multiple>
                <el-option v-for="item in roleList"
                           :key="item.roleId"
                           :label="item.roleName"
                           :value="item.roleId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="手机号" prop="userPhone">
              <el-input v-model="userData.userPhone"></el-input>
            </el-form-item>

            <el-form-item label="电话" prop="userPhone">
              <el-input v-model="userData.telephone"></el-input>
            </el-form-item>

            <el-form-item label="邮箱" prop="userEmail">
              <el-input v-model="userData.userEmail"></el-input>
            </el-form-item>

            <el-form-item label="创建时间" prop="createDate">
              <el-input v-model="userData.createDate" :disabled="true"></el-input>
            </el-form-item>

            <el-form-item label="更新时间" prop="updatePerson">
              <el-input v-model="userData.updateDate" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog_footer">
           <el-button type="primary" size="small" @click="handleSave">确定</el-button>
           <el-button size="small" @click="dialogStatus = false">取消</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
  import UserManage from './UserManage'

  export default UserManage
</script>

<style scoped>
  @import 'UserManage.scss';

</style>
