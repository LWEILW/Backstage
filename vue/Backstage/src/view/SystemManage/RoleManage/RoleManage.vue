<template>
  <!-- 角色管理 -->
  <div class="role_container">
    <!-- 角色台账 -->
    <div class="role_table">
      <!-- 搜索框、按钮 -->
      <div class="role_operation">
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate()">添加角色</el-button>
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
      <div class="role_list">
        <el-table :data="roleTable" stripe border size="small" ref="RoleTable"
                  :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">

          <el-table-column prop="roleName" label="角色名称" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="roleName" @keyup.enter.native='getRoleList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.roleName }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="roleDescribe" label="角色描述" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="roleDescribe" @keyup.enter.native='getRoleList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.roleDescribe }}</div>
            </template>
          </el-table-column>

          <!--        <el-table-column prop="roleDescribe" label="权限字符"></el-table-column>-->
          <!--        <el-table-column prop="roleDescribe" label="角色状态"></el-table-column>-->

          <el-table-column prop="createPerson" label="创建者" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="createPerson" @keyup.enter.native='getRoleList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.createPerson }}</div>
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

          <el-table-column prop="updatePerson" label="更新者" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="updatePerson" @keyup.enter.native='getRoleList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.updatePerson }}</div>
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

          <el-table-column fixed="right" label="操作" width="130px">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>
                <el-button @click.stop="handleEdit(scope.row)" type="primary" icon="el-icon-edit" size="small"
                           plain></el-button>
                <el-button @click.stop="handleDelete(scope.row)" type="danger" icon="el-icon-delete" size="small"
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
      9.background:是否为分页按钮添加背景色-->
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :total="totalCount"
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :hide-on-single-page="false"
          layout="total, sizes, prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>


    <!-- 角色模态框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogStatus" width="900px" center>
      <!-- 角色详情 -->
      <el-form :model="roleDetails" ref="roleForm" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="roleDetails.roleName"></el-input>
            </el-form-item>
            <el-form-item label="角色描述" prop="roleDescribe">
              <el-input v-model="roleDetails.roleDescribe"></el-input>
            </el-form-item>
            <el-form-item label="角色状态" prop="roleDescribe">
              <el-input v-model="roleDetails.roleDescribe"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建人" prop="createPerson">
              <el-input v-model="roleDetails.createPerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createDate">
              <el-input v-model="roleDetails.createDate" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新者" prop="updatePerson">
              <el-input v-model="roleDetails.updatePerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updateDate">
              <el-input v-model="roleDetails.updateDate" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <!--权限列表
      show-checkbox:节点是否可被选择
      node-key:每个树节点用来作为唯一标识的属性，整棵树应该是唯一的
      default-expanded-keys:设置默认展开
      default-checked-keys:默认选中的节点
      check-change	节点选中状态发生变化时的回调
      node-click	节点被点击时的回调
       -->
      <el-tree :data="permissionShowData"
               show-checkbox
               node-key="permissionId"
               :default-expanded-keys="[]"
               :default-checked-keys="permissionChangeData"
               :props="permissionEditProps"
               @check-change="handleCheckChange"
               @node-click="handleNodeClick"
               accordion
               ref="permissionTree">
      </el-tree>

      <span slot="footer" class="dialog_footer">
           <el-button type="primary" size="small" @click="handleSave">确定</el-button>
           <el-button size="small" @click="dialogStatus = false">取消</el-button>
        </span>
    </el-dialog>

  </div>
</template>

<script>
  import RoleManage from './RoleManage'

  export default RoleManage
</script>

<style scoped>
  @import 'RoleManage.scss';
</style>
