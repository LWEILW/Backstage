<template>
  <!-- 用户管理 -->
  <div class="user-container">
    <!-- 用户台账 -->
    <div class="user-table">
      <!-- 搜索框、按钮 -->
      <div class="user-operation">
        <div class="user-button">
          <el-button size="primary" icon="el-icon-edit" @click="handleCreate()">模板下载</el-button>
          <el-button size="primary" icon="el-icon-edit" @click="handleCreate()">导入</el-button>
          <el-button size="primary" icon="el-icon-edit" @click="handleCreate()">导出</el-button>
          <el-button size="primary" icon="el-icon-edit" @click="handleDeleteAll()">批量删除</el-button>
          <el-button size="primary" icon="el-icon-edit" @click="handleCreate()">新建用户</el-button>
        </div>
        <div class="user-search">
          <div class="user-input">
            <el-input placeholder="请输入检测单号" v-model="input"></el-input>
          </div>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch()">搜索</el-button>
          <el-button type="primary" icon="el-icon-edit" @click="toggleSelection()">取消选择</el-button>
        </div>
      </div>

      <!--台账
      1.data:显示的数据
      2.stripe:是否为斑马纹
      3.border:是否带有纵向边框,
      4.row-click:表格添加行点击事件
      5.header-cell-style:表头背景色
      6.selection-change:当选择项发生变化时会触发该事件
      7.row-click:行点击事件
      8.ref:显示元素身份 -->
      <div class="user-list">
        <el-table :data="userList" stripe border @selection-change="handleSelectionChange"
                  ref="multipleTable" :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="userAccount" label="账号" align="center"></el-table-column>
          <el-table-column prop="userName" label="姓名" align="center"></el-table-column>
          <el-table-column prop="userSex" label="性别" align="center"></el-table-column>
          <el-table-column prop="userPhone" label="电话" align="center"></el-table-column>
          <el-table-column prop="userEmail" label="邮箱" align="center"></el-table-column>
          <el-table-column prop="userStatus" label="用户状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.userStatus"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
                @change=changeSwitch($event,scope.row.userStatus)>
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="createPerson" label="创建者" align="center"></el-table-column>
          <el-table-column prop="createDate" label="创建时间" align="center" sortable></el-table-column>
          <el-table-column prop="updateDate" label="更新时间" align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" width="185">
            <template slot-scope="scope">
              <el-button @click.stop="handleEdit(scope.row)" type="primary" icon="el-icon-edit" size="small"
                         plain></el-button>
              <el-button @click.stop="handleDelete(scope.row)" type="danger" icon="el-icon-delete" size="small"
                         plain></el-button>
              <el-button @click.stop="handleDelete(scope.row)" type="warning" icon="el-icon-delete" size="small"
                         plain></el-button>
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
    <el-dialog :title="updateTitle" :visible.sync="UserDialog" class="user-dialog" width="800px">
      <el-form :model="userData" ref="userform" label-width="110px">
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
              <el-select v-model="roleList" style="width:650px;" placeholder="角色列表" multiple>
                <el-option label="多选1" :value=1></el-option>
                <el-option label="多选2" :value=2></el-option>
                <el-option label="多选3" :value=3></el-option>
                <el-option label="多选4" :value=4></el-option>
                <el-option label="多选5" :value=5></el-option>
                <el-option label="多选6" :value=6></el-option>
                <el-option label="多选7" :value=7></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="手机号" prop="userPhone">
              <el-input v-model="userData.userPhone"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="userPhone">
              <el-input v-model="userData.userPhone"></el-input>
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
      <span slot="footer" class="dialog-footer">
           <el-button @click="handleSave">确定</el-button>
           <el-button type="primary" @click="UserDialog = false">取消</el-button>
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
