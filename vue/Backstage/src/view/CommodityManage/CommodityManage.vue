<template>
  <!-- 文章管理 -->
  <div class="commodity_container">
    <!-- 文章台账 -->
    <div class='commodity_table' v-if="active === '1'">
      <!-- 搜索框、按钮 -->
      <div class="commodity_operation">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate">新增</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="handleDelete">全删</el-button>
        <el-button type="primary" icon="el-icon-delete"><a :href='`http://localhost:9999/api/v1/commodity/wordExport`'
                                                           target="_blank">word导出 </a></el-button>
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
      <div class="commodity-list">
        <el-table :data="commodityTable" stripe border size="small" @row-click="handleDetails"
                  :header-cell-style="{background:'#8799a3',color:'#FFF'}">
          <el-table-column fixed label="序号" align="center" width="100">
            <template slot-scope="scope"><span>{{scope.$index + 1}} </span></template>
          </el-table-column>

          <el-table-column prop="commodityName" label="商品图片" align="center" width="500">
            <template slot-scope="scope">
              <div style="display: flex; justify-content: flex-start">
                <div v-for="item in scope.row.commodityFileList" style="padding-left: 5px;">
                  <el-image style="width: 100px; height: 100px" v-bind:src="item.url"></el-image>
                </div>
              </div>

            </template>
          </el-table-column>

          <el-table-column prop="commodityName" label="商品名称" align="center"></el-table-column>
          <el-table-column prop="commodityDesc" label="商品描述" align="center"></el-table-column>
          <el-table-column prop="commodityPrice" label="商品价格" align="center"></el-table-column>
          <el-table-column prop="commodityStatus" label="商品状态" align="center"></el-table-column>
          <el-table-column prop="browseCount" label="浏览次数" align="center"></el-table-column>
          <el-table-column prop="publisherTime" label="发布时间" sortable align="center"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" sortable align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" width="250">
            <template slot-scope="scope">
              <el-button @click.stop="handleEdit( scope.row)" type="primary" size="small">查看</el-button>
              <el-button @click.stop="handleEdit( scope.row)" type="primary" size="small">发布</el-button>
              <el-button @click.stop="handleDelete(scope.row)" type="danger" size="small">编辑</el-button>
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
    <div class='commodity-from' v-if="active === '2'">
      <el-form :model="commodityData" ref="commodityFrom" label-width="120px">

        <el-row>
          <el-form-item class="commodity-buttons">
            <el-button size="small" @click="handleBack">返回列表</el-button>
            <el-button type="primary" size="small" @click="handleSave">保存</el-button>
          </el-form-item>
        </el-row>

        <el-row>
          <p class="commodity-p">商品编辑</p>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="商品名称" prop="commodityName">
              <el-input v-model="commodityData.commodityName"></el-input>
            </el-form-item>
            <el-form-item label="商品描述" prop="commodityDesc">
              <el-input v-model="commodityData.commodityDesc"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="商品状态" prop="commodityStatus">
              <el-input v-model="commodityData.commodityStatus" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="商品价格" prop="commodityPrice">
              <el-input v-model="commodityData.commodityPrice" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="发布人" prop="publisher">
              <el-input v-model="commodityData.publisher" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="发布时间" prop="publisherDate">
              <el-input v-model="commodityData.publisherDate" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="更新人" prop="updatePerson">
              <el-input v-model="commodityData.updatePerson" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updateDate">
              <el-input v-model="commodityData.updateDate" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-form-item>
            <!--            <editor-bar id="editorElem" ref="editorElem" v-model="detail" :isClear="isClear"></editor-bar>-->

            <!--
            action 必选参数，上传的地址
            list-type	文件列表的类型
            accept	接受上传的文件类型
            limit	最大允许上传个数
            file-list	上传的文件列表
            multiple 是否支持多选文件
            before-upload	上传文件之前的钩子，拦截限制检测
            on-preview	点击文件列表中已上传的文件时的钩子
            on-remove	文件列表移除文件时的钩子
            on-success	文件上传成功时的钩子
            on-error	文件上传失败时的钩子
            on-exceed	文件超出个数限制时的钩子-->
            <el-upload
              action="http://localhost:9998/api/v1/commodity/imgUpload"
              list-type="picture-card"
              accept="image/jpeg,image/gif,image/png"
              :limit="imgLimit"
              :file-list="productImages"
              :multiple="isMultiple"
              :before-upload="beforeAvatarUpload"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-success="handleAvatarSuccess"
              :on-error="imgUploadError"
              :on-exceed="handleExceed"
              ref="uploadA">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </el-form-item>
        </el-row>

      </el-form>
    </div>
  </div>
</template>

<script>
  import CommodityManage from './CommodityManage'

  export default CommodityManage
</script>

<style scoped>
  @import "CommodityManage.scss";

</style>
