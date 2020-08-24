import api from "@/api/commodity";


export default {
  name: "commodityManage",
  data() {
    return {
      // 商品台账列表
      commodityTable: [],

      // 初始选中页码
      currentPage: 1,
      // 显示每页的数据
      pageSize: 10,
      // 显示总共有多少数据
      totalCount: 0,

      // 切换显示
      active: '1',
      // 商品详情数据
      commodityData: {},


      uploadDetails: "",
      // 图片预览参数
      dialogVisible: false,
      dialogImageUrl: '',
      // 上传的文件列表
      productImages: [],
      // {url: "http://120.26.174.45:8089/backstage/static/image/tx.jpg"},
      // {url: "http://120.26.174.45:8089/backstage/static/image/tx.jpg"}
      // 是否支持多选文件
      isMultiple: true,
      // 最大允许上传个数
      imgLimit: 6
    };
  },
  // 初始化加载
  created() {
    this.commodityList();
  },
  methods: {
    // 商品台账列表
    commodityList() {
      const params = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      api.commodityList(params).then(res => {
        // 返回结果判断
        if (res.data.code == "notLogin" || res.data.code == "notRole") {
          this.$message.warning(res.data.message)
          //登录成功之后重定向到首页
          this.$router.push({path: "/Login"});

        } else if (res.data.status == 1) {
          // 赋值台账数据
          this.commodityTable = res.data.commodityList;
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
    },
    // 跳转至编辑画面
    handleEdit(row) {
      this.active = "2";
      api.commodityDetails(row.commodityId).then(res => {
        this.commodityData = res.data.commodity;
        this.productImages = res.data.commodityFileList

      });
    },
    // 跳转至详情画面
    handleDetails(row) {
      this.active = "2";
      api.commodityDetails(row.commodityId).then(res => {
        this.commodityData = res.data.commodity;
        this.productImages = res.data.commodity.commodityFileList
      });
    },
    // 返回至台账画面
    handleBack() {
      this.active = '1';
      this.resetForm("commodityFrom");
    },

    // 用户保存
    handleSave(formName) {
      const params = {
        'commodityId': this.commodityData.commodityId,
        'commodityName': this.commodityData.commodityName,
        'commodityDesc': this.commodityData.commodityDesc,
        'productImages': this.$refs.uploadA.uploadFiles
      }
      api.commoditySave(params).then(res => {
        // 返回消息
        if (res.data.status == 1) {
          this.$message.success("保存成功");
          this.active = "1";
          // 刷新页面
          this.commodityList();
          this.resetForm("commodityFrom");
        } else {
          this.$message.error("保存失败");
        }
      });
    },

    // 删除方法
    handleDelete(row) {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          api.commodityDelete(row.commodityId).then(res => {
            // 返回消息
            if (res.data.status == 1) {
              this.$message.success("删除成功!");
            } else {
              this.$message.error("删除失败!");
            }
            // 刷新页面
            this.commodityList();
          });
        })
        .catch(() => {
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
      this.commodityList()
    },
    // 切换页码,点击第几页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(`当前页: ${currentPage}`);
      this.commodityList()
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },


    // 文件上传之前调用做一些拦截限制
    beforeAvatarUpload(file) {
      console.log(file);
      const isJPG = true;
      // const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!');
      // }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    // 预览图片时调用
    handlePictureCardPreview(file) {
      console.log(file);
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 移除图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 图片上传成功
    handleAvatarSuccess(res, file) {
      // if (res.status != 1) {
      //   this.$message.error(res.message);
      // }
      console.log(res);
      console.log(file);
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    // 图片上传失败调用
    imgUploadError(err, file, fileList) {
      console.log(err)
      this.$message.error('上传图片失败!');
    },
    // 图片上传超过数量限制
    handleExceed(files, fileList) {
      this.$message.error('上传图片不能超过6张!');
      console.log(file, fileList);
    }

  }
};
