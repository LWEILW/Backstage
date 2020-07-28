<template>
  <div class="login_container">
    <!--    <h2>博客控制台管理</h2>-->
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../../static/image/cat.jpg">
      </div>

      <!-- model:表单数据对象
      rules:表单验证规则
      status-icon:是否在输入框中显示校验结果反馈图标
      label-position:表单域标签的位置
      label-width:表单域标签的宽度 -->
      <el-form class="login_form" :model="ruleForm" :rules="rules" status-icon ref="ruleForm"
               label-position="left" label-width="0">
        <el-form-item prop="account">
          <el-input v-model="ruleForm.account"></el-input>
        </el-form-item>

        <el-form-item prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>

        <!--        <el-form-item prop="checkPass">-->
        <!--          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>-->
        <!--        </el-form-item>-->

        <el-form-item class="login_button">
          <el-button type="primary" size="small" @click="submitForm('ruleForm')">提交</el-button>
          <el-button size="small" @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import api from "@/api/login";

  export default {
    name: "login",
    data() {
      // 验证邮箱
      var checkAccount = (rule, value, callback) => {
        if (!value) {
          return callback();
        }
        if (value) {
          setTimeout(() => {
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!reg.test(value)) {
              callback(new Error("请输入有效的电子邮箱！"));
            } else {
              callback();
            }
          }, 500);
        }
      };
      // 验证密码
      var validatePass = (rule, value, callback) => {
        if (value === "") {
          callback(new Error("请输入密码"));
        } else {
          if (this.ruleForm.checkPass !== "") {
            this.$refs.ruleForm.validateField("checkPass");
          }
          callback();
        }
      };
      // 验证二次密码
      var validatePass2 = (rule, value, callback) => {
        if (value === "") {
          callback(new Error("请再次输入密码"));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error("两次输入密码不一致!"));
        } else {
          callback();
        }
      };
      return {
        // 登录默认值
        ruleForm: {
          account: "欧阳锋",
          pass: "123",
          checkPass: "123"
        },
        // 验证规则
        rules: {
          // account: [{ validator: checkAccount, trigger: "blur" }],
          pass: [{validator: validatePass, trigger: "blur"}],
          checkPass: [{validator: validatePass2, trigger: "blur"}]
        }
      };
    },
    methods: {
      // 登录方法
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            api.ajaxLogin({
              userName: this.ruleForm.account,
              password: this.ruleForm.pass
            }).then(res => {
              console.log(res.data);
              if (res.data.status == 1) {
                //登录成功之后重定向到首页
                this.$router.push({path: "/HomePage"});
                this.$message.success(res.data.message);
              } else {
                this.$message.warning(res.data.message);
              }
            });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      // 重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  };
</script>

<style>
  .login_container {
    background-color: #000000;
    /*background-image: url("../assets/image/bj.jpg");*/
    background-repeat: no-repeat;
    height: 100%;
  }

  h2 {
    text-align: center;
    color: #3a8ee6;
    font-size: 32px;
    font-weight: 500;
    text-transform: uppercase;
    line-height: 100px;
  }

  .login_box {
    width: 450px;
    height: 300px;
    background-color: #d0e5f2;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 2;
  }

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    /*圆弧*/
    border-radius: 50%;
    padding: 10px;
    /*阴影*/
    box-shadow: 0 0 10px #000000;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;

  }

  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }

  .login_form {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }

  .login_button {
    display: flex;
    justify-content: flex-end;
  }

</style>
