<template>
  <!-- 文章管理 -->
  <div class="article_container">
    <!-- 文章台账 -->
    <div class='article_table' v-if="active === '1'">
      <!-- 搜索框、按钮 -->
      <div class="article_operation">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate">新增</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="handleDelete">全删</el-button>
        <el-button type="primary" icon="el-icon-delete"><a :href='`http://localhost:9999/api/v1/article/wordExport`'
                                                           target="_blank">word导出</a></el-button>
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
      <div class="article_list">
        <el-table :data="articleTable" stripe border size="small"
                  :header-cell-style="{background:'#474b4c',color:'#f9f4dc'}">

          <el-table-column fixed label="序号" align="center" width="80">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>
                <span>{{scope.$index}} </span>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="articleName" label="文章名称" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="articleName" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.articleName }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="articleTitle" label="文章标题" :show-overflow-tooltip='true' align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="articleTitle" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.articleTitle }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="readingAmount" label="阅读数量" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>{{ scope.row.readingAmount }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="publisher" label="发布人" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="publisher" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.publisher }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="articleStatus" label="文章状态" align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index == 0'>
                <el-input v-model="articleStatus" @keyup.enter.native='getUserList' size="small"></el-input>
              </div>
              <div v-else>{{ scope.row.articleStatus }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="publisherTime" label="发布时间" sortable align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>{{ scope.row.publisherTime }}</div>
            </template>
          </el-table-column>

          <el-table-column prop="updateTime" label="更新时间" sortable align="center">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>{{ scope.row.updateTime }}</div>
            </template>
          </el-table-column>

          <el-table-column fixed="right" label="操作" align="center" width="200">
            <template slot-scope="scope">
              <div v-if='scope.$index != 0'>
                <el-button type="primary" icon="el-icon-edit" size="small" @click.stop="handleEdit( scope.row)"
                           plain></el-button>
                <el-button type="primary" icon="el-icon-edit" size="small" @click.stop="handleEdit( scope.row)"
                           plain></el-button>
                <el-button type="danger" icon="el-icon-edit" size="small" @click.stop="handleDelete(scope.row)"
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
          :page-sizes="[5, 10, 20, 100]"
          :hide-on-single-page="false"
          layout="total, sizes, prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>
    <!-- 文章新建/编辑 -->
    <div class='article_from' v-if="active === '2'">
      <el-form :model="articleData" ref="articleFrom" label-width="120px">

        <el-row>
          <el-form-item class="article_buttons">
            <el-button type="primary" @click="handleBack">返回列表</el-button>
            <el-button type="primary" @click="handleSave">保存</el-button>
          </el-form-item>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="文章名称" prop="articleName">
              <el-input v-model="articleData.articleName"></el-input>
            </el-form-item>
            <el-form-item label="文章标题" prop="articleTitle">
              <el-input v-model="articleData.articleTitle"></el-input>
            </el-form-item>
            <el-form-item label="文章状态" prop="articleStatus">
              <el-input v-model="articleData.articleStatus" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="阅读数量" prop="readingAmount">
              <el-input v-model="articleData.readingAmount" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="分类列表" required>
              <el-select v-model="categorySelectList" placeholder="角色列表" multiple>
                <el-option v-for="item in categoryList"
                           :key="item.categoryId"
                           :label="item.categoryName"
                           :value="item.categoryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="标签列表" required>
              <el-select v-model="labelSelectList" placeholder="角色列表" multiple>
                <el-option v-for="item in labelList"
                           :key="item.labelId"
                           :label="item.labelName"
                           :value="item.labelId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="发布人" prop="publisher">
              <el-input v-model="articleData.publisher" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="发布时间" prop="publisherTime">
              <el-input v-model="articleData.publisherTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="更新人" prop="updatePerson">
              <el-input v-model="articleData.updatePerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updateTime">
              <el-input v-model="articleData.updateTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 富文本 -->
        <el-row>
          <el-form-item>
            <editor-bar id="editorElem" ref="editorElem" v-model="editorDetail" :isClear="isClear"></editor-bar>
          </el-form-item>
        </el-row>

      </el-form>
    </div>
  </div>
</template>

<script>
  import ArticleManage from './ArticleManage'

  export default ArticleManage
</script>

<style scoped>
  @import "ArticleManage.scss";

</style>
