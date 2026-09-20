<template>
    <div class="login">
        <div class="background">
            <img :src="imgSrc" width="100%" height="100%" alt="" />
        </div>
        <div class="login-form">
            <div class="login-form-header">
                <img style="width: 75px; height: 75px;float: left;padding-right: 30px;" src="../assets/img/logo.png"
                     alt=""/>
                <div class="login-form-text">Little Cinema - Administration</div>
            </div>
            <div style="color: #000000;font-weight: bolder">

                <p style="font-size: 20px">Username</p>
                <el-input class="login-form-input" v-model="username" placeholder="Username"></el-input>
                <el-alert
                        v-if="usernameError"
                        title="Enter a username with at least 6 characters"
                        type="error"
                        show-icon>
                </el-alert>

                <p style="font-size: 20px">Password</p>
                <el-input class="login-form-input" placeholder="Password" v-model="password" show-password></el-input>
                <el-alert
                        v-if="passwordError"
                        title="Enter a password with at least 6 characters"
                        type="error"
                        show-icon>
                </el-alert>

                <p style="font-size: 20px">Role</p>
                <el-select style="width: 100%" class="login-form-input" v-model="role" placeholder="Select your role">
                    <el-option label="Administrator" value="admin"></el-option>
                    <el-option label="Staff" value="worker"></el-option>
                </el-select>
                <el-alert
                        v-if="roleError"
                        title="Select your login role"
                        type="error"
                        show-icon>
                </el-alert>

                <div style="padding-top: 10px">
                    <el-checkbox v-model="remember" style="color: #000000">Remember me</el-checkbox>
                    <div style="float: right">
                        <el-tooltip class="item" effect="dark" content="Contact an administrator to reset your password" placement="bottom">
                            <el-link style="font-weight: bolder;font-size: 14px;color: #000000;"
                                     :underline="false">
                                Forgot password?
                            </el-link>
                        </el-tooltip>
                    </div>
                </div>
                <el-button :loading="loading" @click="postLogin" class="login-form-button" type="primary">Log in
                </el-button>
            </div>
            <div class="login-form-footer">
                <div style="font-weight: bolder;font-size: 16px;color: #000000;"
                     :underline="false">
                    Little Cinema - Administration
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import {Login} from "@/api/user"

    export default {
        data() {
            return {
                loading: false,
                role: '',
                username: '',
                password: '',
                remember: false,
                usernameError: false,
                passwordError: false,
                roleError: false,
                imgSrc:require('../assets/bg2.png')
            }
        },
        methods: {
            postLogin() {
                this.loading = true;
                const LoginData = {
                    username: this.username,
                    password: this.password,
                    remember: this.remember,
                };

                if (LoginData.username.length <= 5) {
                    setTimeout(() => {
                        this.loading = false;
                        this.usernameError = true;
                    }, 700)
                    return
                } else {
                    this.usernameError = false;
                }

                if (LoginData.password.length <= 5) {
                    setTimeout(() => {
                        this.loading = false;
                        this.passwordError = true;
                    }, 700)
                    return
                } else {
                    this.passwordError = false;
                }

                if (!this.role) {
                    setTimeout(() => {
                        this.loading = false;
                        this.roleError = true;
                    }, 700)
                    return
                } else {
                    this.roleError = false;
                }

                Login(this.role, LoginData).then(res => {
                    if (!res.success) {
                        setTimeout(() => {
                            this.loading = false;
                        }, 700)
                    } else {
                        setTimeout(() => {
                            //Login successful
                            console.log(res.data)
                            localStorage.setItem("token", res.data.token)
                            if (this.role === 'worker') {
                                localStorage.setItem("wid", res.data.worker.id)
                            }
                            localStorage.setItem("role", this.role)
                            this.$router.push("/")
                            this.loading = false;
                        }, 700)
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
    /*.login {*/
    /*    width: 100%;*/
    /*    height: 100%;*/
    /*    background: #FFFFFF;*/
    /*}*/

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
        padding-left: 40px;
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
