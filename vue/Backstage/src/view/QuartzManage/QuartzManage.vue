<template>
  <!-- 定时器管理 -->
  <div class="quartz_container">
    <!-- 定时器台账 -->
    <div class='quartz_table'>
      <!-- 搜索框、按钮 -->
      <div class="quartz_operation">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate">新增</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="handleSuspendAll">暂停所有</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="handleRecoveryAll">恢复所有</el-button>
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
      <div class="quartz-list">
        <el-table :data="quartzTable" stripe border size="small"
                  :header-cell-style="{background:'#8799a3',color:'#FFF'}">
          <el-table-column fixed label="序号" align="center" width="100">
            <template slot-scope="scope"><span>{{scope.$index + 1}} </span></template>
          </el-table-column>

          <el-table-column prop="jobName" label="任务名" align="center"></el-table-column>
          <el-table-column prop="jobDesc" label="任务描述" sortable align="center"></el-table-column>
          <el-table-column prop="jobGroup" label="任务组" align="center"></el-table-column>
          <el-table-column prop="startTime" label="开始时间" align="center"></el-table-column>
          <el-table-column prop="nextFireTime" label="下次执行时间" align="center"></el-table-column>
          <el-table-column prop="cronExpression" label="CRON表达式" align="center"></el-table-column>
          <el-table-column prop="jobStatus" label="状态" sortable align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" align="center" width="300">
            <template slot-scope="scope">
              <el-button @click.stop="handleEdit( scope.row)" type="primary" size="small">编辑</el-button>
              <el-button @click.stop="handleSuspendJob(scope.row)" type="danger" size="small">暂停</el-button>
              <el-button @click.stop="handleRecoveryJob(scope.row)" type="danger" size="small">恢复</el-button>
              <el-button @click.stop="handleDelete( scope.row)" type="primary" size="small">删除</el-button>
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
    <el-dialog :title="updateTitle" :visible.sync="dialogStatus" class="quartz_dialog" width="900px">
      <el-form :model="quartzData" ref="quartzFrom" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名" prop="jobName">
              <el-input v-model="quartzData.jobName"></el-input>
            </el-form-item>
            <el-form-item label="任务描述" prop="jobDesc">
              <el-input v-model="quartzData.jobDesc"></el-input>
            </el-form-item>
            <el-form-item label="任务组" prop="jobGroup">
              <el-input v-model="quartzData.jobGroup"></el-input>
            </el-form-item>
            <el-form-item label="上传参数" prop="publisherDate">
              <el-input type="textarea" :rows="3" v-model="quartzData.invokeParam"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="CRON表达式" prop="publisher">
              <el-input v-model="quartzData.cronExpression"></el-input>
            </el-form-item>
            <el-form-item label="开始时间" prop="quartzPrice">
              <el-date-picker
                v-model="quartzData.startTime"
                type="datetime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="状态" prop="jobStatus">
              <el-input v-model="quartzData.jobStatus" :disabled="true"></el-input>
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
  import QuartzManage from './QuartzManage'

  export default QuartzManage
</script>

<style scoped>
  @import "QuartzManage.scss";

</style>
