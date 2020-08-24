import api from "@/api/article";
import EditorBar from '../../components/wangEnduit/index'

export default {
  name: "articleManage",
  components: {EditorBar},
  data() {
    return {
      // 分类列表
      categoryList: [],
      // 标签列表
      labelList: [],
      // 文章台账列表
      articleTable: [],
      // 模糊查询参数
      articleName: "",
      articleTitle: "",
      publisher: "",
      articleStatus: "",
      categorySelectList: "",
      labelSelectList: "",

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 5,
      // 显示总共有多少数据
      totalCount: 0,

      // 切换显示
      active: '1',
      // 文章详情数据
      articleData: {},

      // 富文本内容
      editorDetail: "",
      isClear: true
    };
  },
  // 初始化加载
  created() {
    this.articleList();
    this.getCategoryList();
    this.getLabelList();
  },
  methods: {

    // 分类列表
    getCategoryList() {
      api.getCategoryList().then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});
        } else if (res.data.status == 1) {
          this.categoryList = res.data.categoryList;
        } else {
          this.$message.warning(res.data.message)
        }
      });
    },
    // 标签列表
    getLabelList() {
      api.getLabelList().then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});
        } else if (res.data.status == 1) {
          this.labelList = res.data.labelList;
        } else {
          this.$message.warning(res.data.message)
        }
      });
    },

    // 文章台账列表
    articleList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.articleList(params).then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});

        } else if (res.data.status == 1) {
          // 模糊查询，第一行插入空值
          res.data.articleList.unshift({});
          // 赋值台账数据
          this.articleTable = res.data.articleList;
          this.currentPage = res.data.currentPage;
          this.totalCount = res.data.total;
        } else {
          this.$message.warning(res.data.message)
        }

      });
    },

    // 跳转至创建画面
    handleCreate() {
      this.active = "2";
      // 初始化富文本为空值
      this.editorDetail = "";
    },

    // 返回至台账画面
    handleBack() {
      this.active = '1';
      this.resetForm("articleFrom");
    },

    // 跳转至编辑画面
    handleEdit(row) {
      this.active = "2";
      // 初始化富文本为空值
      this.editorDetail = "";

      api.articleDetails(row.articleId).then(res => {
        this.articleData = res.data.article;
        // 富文本赋值
        this.editorDetail = res.data.article.articleContent;
      });
    },


    // 文章保存
    handleSave(formName) {
      const params = {
        'articleName': this.articleData.articleName,
        'articleTitle': this.articleData.articleTitle,
        'articleContent': this.$refs.editorElem.value
      }
      api.articleSave(params).then(res => {
        // 返回结果判断
        if (res.data.status == 1) {
          this.active = "1";
          this.$message.success("保存成功");
          this.resetForm("articleFrom");
          // 刷新页面
          this.articleList(this.articleParams);
        } else {
          this.$message.warning(res.data.message)
        }

      });
    },

    // 删除方法
    handleDelete(row) {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.articleDelete(row.articleId).then(res => {
          // 刷新页面
          this.articleList();
          // 返回消息
          if (res.data.status == 1) {
            this.$message.success("删除成功!");
          } else {
            this.$message.error("删除失败!");
          }
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除"
        });
      });
    },

    // 切换每页显示的数量,每页下拉显示数据
    handleSizeChange(size) {
      this.pageSize = size;
      console.log(`每页 ${size} 条`);
      this.articleList(this.currentPage, this.pageSize)
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
      this.articleList(this.currentPage, this.pageSize)
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
