<template>
  <div class="main">
    <h3 style="letter-spacing: 1px;font-weight: 400;padding-bottom: 20px">Profile settings</h3>

    <div>
      <el-form style="width: 350px;float: left" label-position="top" ref="form" :model="worker" label-width="140px">
        <el-form-item style="padding: 0" label="Username">
          <el-input v-model="worker.username"></el-input>
        </el-form-item>
        <el-form-item style="padding: 0" label="Nickname">
          <el-input v-model="worker.nickname"></el-input>
        </el-form-item>
        <el-form-item style="padding: 0" label="Password">
          <el-input type="password" v-model="worker.password"></el-input>
        </el-form-item>
        <el-form-item label="Gender">
          <el-radio-group v-model="worker.gender">
            <el-radio label="Male"></el-radio>
            <el-radio label="Female"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="padding: 0" label="Phone number">
          <el-input v-model="worker.phone"></el-input>
        </el-form-item>
        <el-form-item label="*Department">
          <el-radio-group style="width: 600px" v-model="worker.department">
            <el-radio label="Customer Support"></el-radio>
            <el-radio label="Operations"></el-radio>
            <el-radio label="Marketing"></el-radio>
            <el-radio label="Planning"></el-radio>
            <el-radio label="Human Resources"></el-radio>
            <el-radio label="Supervision"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="padding-top: 20px">
          <el-button type="primary" @click="onSubmit">Update profile</el-button>
        </el-form-item>
      </el-form>
      <div>
        <img style="padding-bottom: 10px;padding-left: 150px;width: 150px; height: 150px;" alt=""
             :src="worker.avatar">
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
import config from "@/config";
import {FindWorkerById, UpdateWorker} from "@/api/worker";

export default {

  data() {
    return {
      header: {
        "Authorization": localStorage.getItem("token")
      },
      uploadAction: config.API_URL + '/upload',
      worker: {
        nickname: '',
        password: '',
        phone: '',
        gender: '',
        avatar: '',
        department: '',
      }
    }
  },

  mounted() {
    FindWorkerById(localStorage.getItem("wid")).then(res => {
      this.worker = res.data;
    })
  },

  methods: {

    onSubmit() {
      UpdateWorker(this.worker).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: 'Worker profile updated successfully!'
          });
        }
      })
    },

    handleUploadSuccess(res) {
      console.log(res)
      this.worker.avatar = res;
      UpdateWorker(this.worker).then(res => {
        if (res.success) {
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

.main {
  padding: 50px;
  height: 800px;
}

>>> .el-form--label-top .el-form-item__label {
  padding: 0;
}

.el-form-item {
  margin-bottom: 5px;
}
</style>