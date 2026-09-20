<template>
  <div>
    <h3 style="letter-spacing: 1px;font-weight: 400;padding-bottom: 20px">Profile settings</h3>

    <div>
      <el-form style="width: 350px;float: left" label-position="top" ref="form" :model="user" label-width="140px">
        <el-form-item style="padding: 0" label="Nickname">
          <el-input v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item style="padding: 0" label="Password">
          <el-input type="password" v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="Gender">
          <el-radio-group v-model="user.gender">
            <el-radio label="Male"></el-radio>
            <el-radio label="Female"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Date of birth">
          <el-col>
            <el-date-picker
                type="date" placeholder="Select date"
                v-model="user.birthday"
                value-format="yyyy-MM-dd"
                style="width: 100%;"/>
          </el-col>
        </el-form-item>
        <el-form-item style="padding: 0" label="Email">
          <el-input type="email" v-model="user.email"></el-input>
        </el-form-item>
        <el-form-item label="Biography">
          <el-input :rows="5" type="textarea" v-model="user.info"></el-input>
        </el-form-item>
        <el-form-item style="padding-top: 20px">
          <el-button type="primary" @click="onSubmit">Update profile</el-button>
        </el-form-item>
      </el-form>
      <div>
        <img style="padding-bottom: 10px;padding-left: 150px;width: 150px; height: 150px;" alt=""
             :src="user.avatar">
        <el-upload
            style="padding-left: 500px;letter-spacing: 1px"
            class="upload-demo"
            accept=".png,.jpg"
            :headers="header"
            :action="uploadAction"
            :on-success="handleUploadSuccess"
            multiple
            :limit="1">
          <el-button style="width: 150px" size="small" type="primary">
            <i class="el-icon-upload2"></i> Click to upload
          </el-button>
          <div slot="tip" class="el-upload__tip">Only upload jpg/png files</div>
        </el-upload>
      </div>
    </div>
  </div>
</template>

<script>
import {findById, updateUser} from "@/api/user";
import config from "@/config";

export default {

  data() {
    return {
      header: {
        "Authorization": localStorage.getItem("token")
      },
      uploadAction: config.API_URL + '/upload',
      user: {
        nickname: '',
        password: '',
        email: '',
        birthday: '',
        gender: '',
        info: '',
        avatar: ''
      }
    }
  },

  mounted() {
    if (localStorage.getItem("uid") !== null) {
      findById(localStorage.getItem("uid")).then(res => {
        this.user = res.data;
      })
    }
  },

  methods: {

    onSubmit() {
      updateUser(this.user).then(res => {
        if (res.success) {
          this.user = res.data;
          this.$message({
            type: 'success',
            message: 'Profile updated successfully!'
          });
        }
      })
    },

    handleUploadSuccess(res) {
      this.user.avatar = res;
      updateUser(this.user).then(res => {
        if (res.success) {
          this.user = res.data;
          this.$message({
            type: 'success',
            message: 'Avatar uploaded successfully!'
          });
        }
      })
    }

  },

}

</script>

<style scoped>
>>> .el-form--label-top .el-form-item__label {
  padding: 0;
}

.el-form-item {
  margin-bottom: 5px;
}
</style>