<template>
  <div class="login">
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>
    <div class="login-form">
      <div class="login-form-header">
        <img style="width: 75px; height: 75px;float: left;padding-right: 40px;" src="../assets/img/logo.png"
             alt=""/>
        <div class="login-form-text">Little Cinema - Register</div>
      </div>
      <div style="color: #000000;font-weight: bolder">
        <p style="font-size: 20px">Username</p>
        <el-input class="login-form-input" v-model="username" placeholder="Username"></el-input>
        <p style="font-size: 20px">Password</p>
        <el-input class="login-form-input" placeholder="Password" v-model="password" show-password></el-input>
        <p style="font-size: 20px">Enter your password again</p>
        <el-input class="login-form-input" placeholder="Confirm password" v-model="checkPassword"
                  show-password></el-input>
        <div style="padding-top: 10px ;color: black">
          <el-checkbox v-model="agree">I agree to the Little Cinema user agreement.</el-checkbox>
        </div>
        <el-button @click="postLogin" class="login-form-button" type="primary">SIGN UP</el-button>
      </div>
      <div class="login-form-footer">
        <el-link href="/login" style="font-weight: bolder;font-size: 16px;color: #91949c;"
                 :underline="false">
          Already have an account? Log in
          <i style="font-weight: bolder;font-size: 15px" class="el-icon-right"></i>
        </el-link>
      </div>
    </div>
  </div>
</template>

<script>

import {Register} from "@/api/user"

export default {
  data() {
    return {
      username: '',
      password: '',
      checkPassword: '',
      agree: false,
      imgSrc:require('../assets/img/bg.png')
    }
  },
  methods: {
    postLogin() {
      if (this.username.length < 6) {
        this.$message({
          message: 'Enter a username with at least 6 characters',
          type: 'warning'
        });
        return
      }
      if (this.agree === false) {
        this.$message({
          message: 'Please accept the Little Cinema user agreement!',
          type: 'warning'
        });
        return
      }
      if (this.password !== this.checkPassword) {
        this.$message({
          message: 'The passwords do not match!',
          type: 'warning'
        });
        return
      }
      const LoginData = {
        username: this.username,
        password: this.password,
        remember: this.remember
      };
      console.log(LoginData);
      Register(LoginData).then(res => {
        if (res.success) {
          this.$router.push("/login")
        }
      })
    }
  }
}
</script>

<style scoped>
  .background{
    width:100%;
    height:100%;  /**Use full width and height to fill the screen */
    z-index:-1;
    position: absolute;
  }
.login {
  width: 100%;
  height: 100%;
}

.login-form {
  width: 500px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  letter-spacing: 2px;
}

.login-form-header {
  height: 20px;
  padding-left: 45px;
  padding-bottom: 100px;
}

.login-form-text {
  color: #000000;
  font-weight: bold;
  font-size: 30px;
  padding-top: 15px;
}

.login-form-input {
  margin-bottom: 10px;
}

.login-form-button {
  border-radius: 3px;
  width: 100%;
  font-weight: 600;
  font-size: 15px;
  letter-spacing: 2px;
  height: 60px;
  background: #5a84fd;
  box-shadow: 0 5px 30px rgb(0 66 8.5%);
  margin-top: 35px;
}

.login-form-footer {
  font-weight: bolder;
  color: #91949c;
  padding-top: 40px;
  text-align: center;
}

>>> .el-input__inner {
  height: 48px;
}

.el-checkbox {
  color: #91949c;
  font-weight: bolder;
  font-size: 15px;
}

</style>
