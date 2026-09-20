<template>
  <div class="film-list">

    <el-dialog
        title="Edit film details"
        :visible.sync="dialog1"
        width="60%">

      <el-form ref="form" style="padding: 20px" :model="form" label-width="140px">

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
            <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="Select date" v-model="form.releaseTime" style="width: 100%;"></el-date-picker>
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

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog1 = false">Cancel</el-button>
        <el-button type="primary" @click="submitUpdate()">Confirm</el-button>
      </span>
    </el-dialog>

    <el-dialog
        title="Add screening"
        :visible.sync="dialog2"
        width="50%">

      <el-form ref="form" :model="arrangement" label-width="140px">

        <el-form-item label="Film title">
          <el-input :disabled="true" v-model="arrangement.name"></el-input>
        </el-form-item>

        <el-form-item label="Available seats">
          <el-input-number v-model="arrangement.seatNumber" :min="10" :max="100"></el-input-number>
        </el-form-item>

        <el-form-item label="Screening format">
          <el-select v-model="arrangement.type" placeholder="Select a screening format">
            <el-option label="2D" value="2D"></el-option>
            <el-option label="3D" value="3D"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item style="width: 500px" label="Screening date">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="Select date" v-model="arrangement.date"
                            value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item style="width: 500px" label="Screening time">
          <el-col :span="11">
            <el-time-picker placeholder="Start time" value-format="HH:mm:ss" v-model="arrangement.startTime" style="width: 100%;"></el-time-picker>
          </el-col>
          <el-col style="text-align: center" class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="End time" value-format="HH:mm:ss" v-model="arrangement.endTime" style="width: 100%;"></el-time-picker>
          </el-col>
        </el-form-item>

        <el-form-item label="Ticket price">
          <el-input-number v-model="arrangement.price" :precision="2" :step="0.1" :max="999.99"></el-input-number>
        </el-form-item>

        <el-form-item label="Scheduled by">
          <el-input type="textarea" v-model="arrangement.founder"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog2 = false">Cancel</el-button>
        <el-button type="primary" @click="submitArrange()">Confirm</el-button>
      </span>
    </el-dialog>

    <el-table
        v-loading="tableLoading"
        :data="filmList.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">

      <el-table-column label="Film title">
        <template slot-scope="scope">
          <el-image
              style="width: 100px; height: 145px"
              :src="scope.row.cover">
          </el-image>
          <div style="font-size: 10px;padding-top: 5px;padding-left: 3px">{{scope.row.name}}</div>
        </template>
      </el-table-column>

      <el-table-column
          label="Release date"
          prop="releaseTime">
      </el-table-column>

      <el-table-column
          label="Type"
          prop="type">
      </el-table-column>

      <el-table-column label="Publication status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === true" type="success" disable-transitions>Published</el-tag>
          <el-tag v-if="scope.row.status === false" type="warning" disable-transitions>Unpublished</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          label="Region"
          prop="region">
      </el-table-column>

      <el-table-column label="Duration">
        <template slot-scope="scope">
          <el-tag size="medium" effect="plain">{{ scope.row.duration }}  minutes</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="260" align="right">
        <template slot="header" slot-scope="scope">
          <el-input
              v-model="search"
              size="mini"
              placeholder="Search by keyword"/>
        </template>
        <template style="white-space: nowrap" slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-edit"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)">Edit
          </el-button>
          <el-button
              size="mini"
              icon="el-icon-circle-plus"
              type="success"
              @click="handleArrange(scope.$index, scope.row)">Screenings
          </el-button>
          <el-popconfirm
              confirm-button-text='Confirm'
              cancel-button-text='No thanks'
              icon="el-icon-info"
              icon-color="red"
              @confirm=handleDelete(scope.$index,scope.row)
              title=" Delete this film? "
          >
            <el-button
                style="margin-left: 8px"
                size="mini"
                icon="el-icon-delete"
                type="danger"
                slot="reference">Delete
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

  </div>
</template>

<script>
import {ListAllFilm, DeleteById, UpdateFilm, AddArrangement} from "@/api/film";
import config from "@/config";

export default {
  data() {
    return {
      header:{
        "Authorization": localStorage.getItem("token")
      },
      uploadAction: config.API_URL + '/upload',
      url: '',
      tableLoading: false,
      form: {
        cover: 'null',
        name: '',
        region: '',
        releaseTime: '',
        duration: 120,
        introduction: '',
        type: '',
        status: true,
      },
      arrangement: {
        name: '',
        fid: '',
        seatNumber: 40,
        price: 30.50,
        date: '',
        startTime: '',
        endTime: '',
        founder: '',
        type: '2D',
      },
      filmList: [],
      search: '',
      dialog1: false,
      dialog2: false,
    }
  },

  mounted() {
    this.tableLoading = true;
    ListAllFilm().then(res=>{
      setTimeout(()=>{
        this.filmList = res.data;
        this.tableLoading = false;
      },700)
    })
  },

  methods: {

    submitUpdate() {
      this.form.cover = this.url;
      UpdateFilm(this.form).then(res=>{
        this.dialog1 = false;
      })
    },

    submitArrange() {
      AddArrangement(this.arrangement).then(res=>{
        this.dialog2 = false;
      })
    },

    handleEdit(index, row) {
      this.dialog1 = true;
      this.form = row;
    },

    handleArrange(index, row) {
      this.dialog2 = true;
      this.arrangement.name = row.name
      this.arrangement.fid = row.id
    },

    handleDelete(index, row) {
      DeleteById(row.id).then(res=>{
        this.filmList.splice(index, 1)
        this.$message({
          message: 'Film ' + row.name + ' deleted successfully!',
          type: 'success'
        });
      })
    },

    handleUploadSuccess(res){
      this.url = res;
    },

  },
}
</script>

<style scoped>
.film-list {
  padding: 20px;
}
</style>
