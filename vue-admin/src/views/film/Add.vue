<template>
  <div class="film-list">

    <el-form ref="form" :model="form" label-width="140px">

      <el-form-item label="Film cover">
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

      <el-form-item label="Film title">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label="Release region">
        <el-select v-model="form.region" placeholder="Select the release region">
          <el-option label="Mainland China" value="Mainland China"></el-option>
          <el-option label="Hong Kong" value="Hong Kong"></el-option>
          <el-option label="Taiwan" value="Taiwan"></el-option>
          <el-option label="Japan" value="Japan"></el-option>
          <el-option label="USA" value="USA"></el-option>
          <el-option label="South Korea" value="South Korea"></el-option>
          <el-option label="UK" value="UK"></el-option>
          <el-option label="UK" value="UK"></el-option>
          <el-option label="France" value="France"></el-option>
          <el-option label="India" value="India"></el-option>
          <el-option label="Other" value="Other"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="Duration">
        <el-input-number v-model="form.duration" :min="10" :max="550"></el-input-number>
      </el-form-item>

      <el-form-item label="Release date">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="Select date" value-format="yyyy-MM-dd" v-model="form.releaseTime"
                          style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>

      <el-form-item label="Publish now">
        <el-switch v-model="form.status"></el-switch>
      </el-form-item>

      <el-form-item label="Genre">
        <el-radio-group v-model="form.type">
          <el-radio style="padding-bottom: 20px" label="Romance"></el-radio>
          <el-radio label="Comedy"></el-radio>
          <el-radio label="Science fiction"></el-radio>
          <el-radio label="Animation"></el-radio>
          <el-radio style="padding-bottom: 20px" label="Horror"></el-radio>
          <el-radio label="Mystery"></el-radio>
          <el-radio label="Adventure"></el-radio>
          <el-radio label="Action"></el-radio>
          <el-radio style="padding-bottom: 20px" label="Crime"></el-radio>
          <el-radio label="History"></el-radio>
          <el-radio label="Period drama"></el-radio>
          <el-radio label="War"></el-radio>
          <el-radio label="Documentary"></el-radio>
          <el-radio style="padding-bottom: 20px" label="Family"></el-radio>
          <el-radio label="Biography"></el-radio>
          <el-radio label="Martial arts"></el-radio>
          <el-radio label="Children"></el-radio>
          <el-radio label="Short film"></el-radio>
          <el-radio label="Other"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="Synopsis">
        <el-input rows="8" type="textarea" v-model="form.introduction"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">Submit</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import {AddFilm} from "@/api/film";
import config from "@/config";

export default {
  data() {
    return {
      header: {
        "Authorization": localStorage.getItem("token")
      },
      uploadAction: config.API_URL + '/upload',
      url: '',
      form: {
        cover: 'null',
        name: '',
        region: '',
        releaseTime: '',
        duration: 120,
        introduction: '',
        type: '',
        status: true,
      }
    }
  },
  methods: {

    onSubmit() {
      this.form.cover = this.url;
      const film = this.form;
      AddFilm(film).then(res => {
        this.$message({
          type: 'success',
          message: 'Film added successfully!'
        });
        this.$router.push("/film/list")
      })
    },

    handleUploadSuccess(res) {
      this.url = res;
    },

  }
}
</script>

<style scoped>
.film-list {
  padding: 50px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
