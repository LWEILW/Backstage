<template>
  <!-- 权限管理 -->
  <div class="permission-container">
    <!-- 权限台账 -->
    <div class="permission-table">
      <!-- 搜索框、按钮 -->
      <div class="permission-operation">
        <div class="permission-search">
          <div class="permission-input">
            <el-input size="small" placeholder="请输入检测单号"></el-input>
          </div>
          <el-button type="primary" size="small" icon="el-icon-search" @click="handleSearch()">搜索</el-button>
          <el-button type="primary" size="small" icon="el-icon-edit" @click="toggleSelection()">取消选择</el-button>
        </div>
        <div class="permission-button">
          <el-button type="primary" size="small" icon="el-icon-edit" @click="handleCreate">添加权限</el-button>
          <!--        <el-button size="medium" round @click="handleSwtich">展开/折叠</el-button>-->
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
      8.ref:显示元素身份
      9.row-key:行数据的 Key，用来优化 Table 的渲染
      10.default-expand-all:是否默认展开所有行
      11.tree-props:渲染嵌套数据的配置选项-->
      <div class="permission-list">
        <el-table :data="permissionTable" stripe border size="small" ref="PermissionTable"
                  :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}" row-key="permissionId"
                  :default-expand-all="false" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
          <el-table-column prop="permissionName" label="权限名称"></el-table-column>
          <el-table-column prop="permissionPath" label="权限路径" align="center"></el-table-column>
          <el-table-column prop="parentName" label="父类名称" align="center"></el-table-column>
          <!--        <el-table-column prop="functionName" label="功能名称"></el-table-column>-->
          <el-table-column prop="levelNo" label="权限等级" align="center"></el-table-column>
          <el-table-column prop="createPerson" label="创建人" align="center"></el-table-column>
          <el-table-column prop="createDate" label="创建时间" sortable align="center"></el-table-column>
          <el-table-column prop="updatePerson" label="更新人" align="center"></el-table-column>
          <el-table-column prop="updateDate" label="更新时间" align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" width="130px">
            <template slot-scope="scope">
              <el-button @click.stop="handleEdit(scope.row)" type="primary" icon="el-icon-edit" size="small"
                         plain></el-button>
              <el-button @click.stop="handleDelete(scope.row)" type="danger" icon="el-icon-delete" size="small"
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

    <!-- 权限模态框 -->
    <el-dialog :title="updateTitle" :visible.sync="permissionDialog" width="900px" center>
      <el-form :model="permissionData" ref="permissionForm" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="权限名称" prop="permissionName">
              <el-input v-model="permissionData.permissionName"></el-input>
            </el-form-item>
            <el-form-item label="权限路径" prop="permissionPath">
              <el-input v-model="permissionData.permissionPath"></el-input>
            </el-form-item>
            <el-form-item label="父类名称" prop="ParentId">
              <!--              <el-input v-model="permissionData.ParentId"></el-input>-->
              <el-select v-model="permissionData.parentId" clearable filterable placeholder="请选择"
                         :default-first-option="true" value-key="VALUE">
                <el-option
                  v-for="item in parentId"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="权限等级" prop="levelNo">
              <el-select v-model="permissionData.levelNo" filterable placeholder="请选择" :default-first-option="true"
                         value-key="VALUE">
                <el-option
                  v-for="item in levelNo"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="创建者" prop="createPerson">
              <el-input v-model="permissionData.createPerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createDate">
              <el-input v-model="permissionData.createDate" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新者" prop="updatePerson">
              <el-input v-model="permissionData.updatePerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updateDate">
              <el-input v-model="permissionData.updateDate" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
           <el-button type="primary" size="small" @click="handleSave">确定</el-button>
           <el-button size="small" @click="UserDialog = false">取消</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
  import PermissionManage from './PermissionManage'

  export default PermissionManage
</script>

<style scoped>
  @import "PermissionManage.scss";

</style>
