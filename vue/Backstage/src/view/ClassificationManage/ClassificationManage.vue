<template>
  <!-- 分类管理 -->
  <div class="classification_container">
    <!-- 分类台账 -->
    <div class='classification_table'>
      <!-- 搜索框、按钮 -->
      <div class="classification_operation">
        <div class="classification_button">
          <el-button type="primary" size="small" icon="el-icon-circle-plus-outline" @click="handleCreate">新增</el-button>
          <el-button type="primary" size="small" icon="el-icon-delete" @click="handleDelete">全删</el-button>
        </div>
        <div class="classification_search">
          <div class="classification_input">
            <el-input size="small" placeholder="请输入内容">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </div>
          <el-button type="primary" size="small" icon="el-icon-search">搜索</el-button>
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
      <div class="classification_list">
        <el-table :data="articleTable" stripe border size="small" @row-click="handleDetails"
                  :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">
          <el-table-column fixed label="序号" align="center" min-width="5%">
            <template slot-scope="scope"><span>{{scope.$index + 1}} </span></template>
          </el-table-column>
          <el-table-column prop="articleName" label="文章名称" align="center" min-width="20%"></el-table-column>
          <el-table-column prop="articleTitle" label="文章标题" align="center" min-width="20%"></el-table-column>
          <el-table-column prop="articleStatus" label="文章状态" align="center" min-width="5%"></el-table-column>
          <el-table-column prop="readingAmount" label="阅读数量" align="center" min-width="5%"></el-table-column>
          <el-table-column prop="publisher" label="发布人" align="center" min-width="10%"></el-table-column>
          <el-table-column prop="publisherTime" label="发布时间" sortable align="center" min-width="10%"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" sortable align="center" min-width="10%"></el-table-column>
          <el-table-column fixed="right" label="操作" min-width="15%">
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
    <el-dialog :title="updateTitle" :visible.sync="dialogStatus" class="classification_dialog" width="800px">
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
           <el-button type="primary" size="small" @click="handleSave">确定</el-button>
           <el-button size="small" @click="UserDialog = false">取消</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
  import ClassificationManage from './ClassificationManage'

  export default ClassificationManage
</script>

<style scoped>
  @import "ClassificationManage.scss";

</style>
