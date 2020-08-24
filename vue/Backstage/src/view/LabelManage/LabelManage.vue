<template>
  <!-- 标签管理 -->
  <div class="label_container">
    <!-- 标签台账 -->
    <div class='label_table'>
      <!-- 搜索框、按钮 -->
      <div class="label_operation">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate">新增</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="handleDelete">全删</el-button>
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
      <div class="label_list">
        <el-table :data="labelTable" stripe border size="small"
                  :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">

          <el-table-column fixed label="序号" align="center">
            <template slot-scope="scope"><span>{{scope.$index + 1}} </span></template>
          </el-table-column>

          <el-table-column prop="labelName" label="标签名称" align="center"></el-table-column>

          <el-table-column prop="publisher" label="发布人" align="center"></el-table-column>

          <el-table-column prop="publisherDate" label="发布时间" sortable align="center"></el-table-column>

          <el-table-column prop="updateDate" label="更新时间" sortable align="center"></el-table-column>

          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" size="small" @click.stop="handleEdit( scope.row)"
                         plain></el-button>
              <el-button type="primary" icon="el-icon-edit" size="small" @click.stop="handleEdit( scope.row)"
                         plain></el-button>
              <el-button type="danger" icon="el-icon-edit" size="small" @click.stop="handleDelete(scope.row)"
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
          :page-sizes="[5, 10, 20, 100]"
          :hide-on-single-page="false"
          layout="total, sizes, prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>

    <!-- 弹出模态框 -->
    <el-dialog :title="updateTitle" :visible.sync="dialogStatus" class="label_dialog" width="800px">
      <el-form :model="labelData" ref="userform" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="标签名称" prop="userAccount">
              <el-input v-model="labelData.labelName"></el-input>
            </el-form-item>
            <el-form-item label="创建人" prop="createPerson">
              <el-input v-model="labelData.publisher" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新人" prop="updatePerson">
              <el-input v-model="labelData.updatePerson" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="创建时间" prop="createDate">
              <el-input v-model="labelData.publisherDate" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updatePerson">
              <el-input v-model="labelData.updateDate" :disabled="true"></el-input>
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
  import LabelManage from './LabelManage'

  export default LabelManage
</script>

<style scoped>
  @import "LabelManage.scss";

</style>
