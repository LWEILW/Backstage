<template>
  <!-- 模块管理 -->
  <div class="menus-container">
    <!-- 模块台账 -->
    <div class="menus-table">
      <!-- 搜索框、按钮 -->
      <div class="menus-operation">
        <el-button type="text" @click="handleCreate()">新增</el-button>
        <el-button type="text" @click="handleEdit()">修改</el-button>
        <!--        <el-button type="text" @click="handleSwitch()">展开/折叠</el-button>-->
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
      <div class="menus-list">
        <el-table :data="menusTable" stripe border max-height="800px"
                  ref="MenusTable" :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">
          <el-table-column fixed label="序号" align="center" width="100">
            <template slot-scope="scope"><span>{{scope.$index + 1}} </span></template>
          </el-table-column>
          <el-table-column prop="permissionId" label="菜单名称" align="center"></el-table-column>
          <el-table-column prop="modName" label="排序" align="center" sortable></el-table-column>
          <el-table-column prop="parentId" label="请求地址" align="center"></el-table-column>
          <el-table-column prop="funName" label="类型" align="center"></el-table-column>
          <el-table-column prop="path" label="可见" align="center"></el-table-column>
          <el-table-column prop="createPerson" label="权限标识" align="center"></el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button @click.stop="handleEdit(scope.row)" type="primary" size="small">编辑</el-button>
              <el-button @click.stop="handleDelete(scope.row)" type="primary" size="small">删除</el-button>
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
      <div class="menus-page">
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
    <!-- 模块模态框 -->
    <el-dialog :title="updateTitle" :visible.sync="menusDialog" width="700px" center>
      <el-form :model="menusData" ref="menusForm" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="模块目录" prop="modName">
              <el-input v-model="menusData.modName"></el-input>
            </el-form-item>
            <el-form-item label="父类ID">
              <el-select v-model="menusData.parentId" filterable placeholder="请选择" :default-first-option="true"
                         value-key="VALUE">
                <el-option
                  v-for="item in parentId"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="权限等级" prop="levelNo">
              <el-select v-model="menusData.levelNo" filterable placeholder="请选择" :default-first-option="true"
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
            <el-form-item label="功能名称" prop="funName">
              <el-input v-model="menusData.funName"></el-input>
            </el-form-item>
            <el-form-item label="功能英文名称" prop="modName">
              <el-input v-model="menusData.path"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createDate">
              <el-input v-model="menusData.createDate"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="submitForm">立即创建</el-button>
          <el-button @click="permissionDialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import MenusManage from './MenusManage'

  export default MenusManage
</script>

<style scoped>
  .menus-container {
    height: 100%;
    background: #FFFFFF;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .menus-operation {
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;
  }

  .menus-search {
    display: flex;
    flex-direction: row;
  }

  .menus-input {
    width: 150px;
    margin-right: 10px;
  }

  /* 分页 */
  .menus-page {
    text-align: center;
    margin-top: 30px;
  }

  .dialog-footer {
    display: flex;
    justify-content: center;
    font-size: 18px;
    color: #398ee3;
  }
</style>
