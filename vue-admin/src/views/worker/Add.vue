<template>
  <div v-loading="loading" style="padding: 50px">
    <el-form ref="form" :model="form" label-width="140px">
      <el-form-item label="*Username">
        <el-input style="width: 300px" v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="*Password">
        <el-input style="width: 300px" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="*Nickname">
        <el-input style="width: 300px" v-model="form.nickname"></el-input>
      </el-form-item>
      <el-form-item label="*Gender">
        <el-select style="width: 200px" v-model="form.gender" placeholder="Select gender">
          <el-option label="Male" value="Male"></el-option>
          <el-option label="Female" value="Female"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="width: 300px" label="*Contact phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="*Department">
        <el-radio-group v-model="form.department">
          <el-radio label="Customer Support"></el-radio>
          <el-radio label="Operations"></el-radio>
          <el-radio label="Marketing"></el-radio>
          <el-radio label="Planning"></el-radio>
          <el-radio label="Human Resources"></el-radio>
          <el-radio label="Supervision"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Worker avatar">
        <el-upload
            class="upload-demo"
            drag
            accept=".png,.jpg"
            :headers="header"
            :action="uploadAction"
            :on-success="handleUploadSuccess"
            multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drop a file here or <em>Click to upload</em></div>
          <div class="el-upload__tip" slot="tip">Only upload jpg/png files, up to 50mb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {CreateWorker} from "@/api/worker";
import config from "@/config";

export default {
  data() {
    return {
      header: {
        "Authorization": localStorage.getItem("token")
      },
      uploadAction: config.API_URL + '/upload',
      url: '',
      loading: false,
      form: {
        username: '',
        nickname: '',
        password: '',
        gender: '',
        avatar: '',
        phone: '',
        department: '',
      }
    }
  },
  methods: {

    onSubmit() {
      this.loading = true;
      CreateWorker(this.form).then(res => {
        setTimeout(() => {
          this.$message({
            message: 'Worker added successfully',
            type: 'success'
          });
          this.loading = false
        }, 700)
      })
    },

    handleUploadSuccess(res) {
      this.form.avatar = res;
    },

  }
}
</script>

<style scoped>

</style>