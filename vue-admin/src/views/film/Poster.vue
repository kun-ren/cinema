<template>
  <div>
    <el-carousel v-if="posterList.length !== 0" height="500px" indicator-position="outside">
      <el-carousel-item v-for="(item, index) in posterList" :key="index">
        <img alt=""
             :src="item.url"
             class="image">
      </el-carousel-item>
    </el-carousel>

    <div class="content">
      <el-button
          @click="dialogFormVisible = true"
          type="primary"
          icon="el-icon-upload">
        Upload carousel poster
      </el-button>
      <el-button
          style="margin-left: 10px"
          type="danger"
          icon="el-icon-delete"
          @click="deleteAllPoster">
        Delete all posters
      </el-button>
      <el-table
          ref="filterTable"
          :data="posterList"
          style="width: 100%;margin-top: 15px;">
        <el-table-column
            prop="createAt"
            label="Uploaded at"
            width="220"
        >
        </el-table-column>
        <el-table-column
            prop="title"
            label="Title"
            width="180">
        </el-table-column>
        <el-table-column label="URL preview">
          <template slot-scope="scope">
            <a target="_blank" :href="scope.row.url">{{ scope.row.url }}</a>
          </template>
        </el-table-column>
        <el-table-column
            prop="status"
            label="Publication status"
            width="220">
          <template slot-scope="scope">
            <el-tag
                v-if="scope.row.status"
                type="success"
                disable-transitions>Published
            </el-tag>
            <el-tag
                v-else
                type="warning"
                disable-transitions>Unpublished
            </el-tag>
            <el-button v-if="!scope.row.status"
                       style="padding: 0 15px;color: #67C23A"
                       type="text"
                       @click="changePosterStatus(scope.$index, scope.row, true)"
                       icon="el-icon-upload2">Published
            </el-button>
            <el-button v-else
                       style="padding: 0 15px;color: #E6A23C"
                       type="text"
                       @click="changePosterStatus(scope.$index, scope.row, false)"
                       icon="el-icon-download">Unpublished
            </el-button>
            <template style="padding-left: 100px">
              <el-popconfirm @confirm="handleDeletePoster(scope.$index, scope.row.id)" title="Delete this item?">
                <el-button slot="reference"
                           style="color: #F56C6C" type="text"
                           icon="el-icon-delete">Delete
                </el-button>
              </el-popconfirm>
            </template>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="Carousel posters" :visible.sync="dialogFormVisible">
      <el-form :model="uploadPoster">
        <el-form-item label="Poster title" style="margin-bottom: 35px" label-width="150px">
          <el-input style="width: 360px" v-model="uploadPoster.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Publication status" style="margin-bottom: 35px" label-width="150px">
          <el-select style="width: 360px" v-model="uploadPoster.status" placeholder="Select publication status">
            <el-option label="Published" value="true"></el-option>
            <el-option label="Unpublished" value="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Select image" label-width="150px">
          <el-upload
              style="float: left"
              class="upload-demo"
              accept=".png,.jpg"
              :headers="header"
              :action="uploadAction"
              :on-success="handleUploadSuccess"
              multiple
              :limit="1">
            <el-button type="primary" icon="el-icon-upload">Upload carousel poster</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitUpload">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import config from "@/config";
import {AddPoster, DeleteAllPoster, DeletePosterById, ListAllPoster, UpdatePoster} from "@/api/film";

export default {

  mounted() {
    ListAllPoster().then(res => {
      this.posterList = res.data
    })
  },

  data() {
    return {
      header: {
        "Authorization": localStorage.getItem("token")
      },
      dialogFormVisible: false,
      uploadAction: config.API_URL + '/upload',
      uploadPoster: {
        url: '',
        title: '',
        status: '',
        createAt: '',
      },
      posterList: [],
    }
  },

  methods: {

    handleUploadSuccess(res) {
      this.uploadPoster.url = res
    },

    submitUpload() {
      AddPoster(this.uploadPoster).then(res => {
        console.log(res)
        this.dialogFormVisible = false;
      })
    },

    deleteAllPoster() {
      DeleteAllPoster().then(res => {
        this.posterList = null;
      })
    },

    changePosterStatus(index, poster, status) {
      poster.status = status
      UpdatePoster(poster).then(res => {
        this.posterList[index].status = status
        this.$message({
          message: 'Operation completed successfully',
          type: 'success'
        });
      })
    },

    handleDeletePoster(index, id) {
      DeletePosterById(id).then(res => {
        this.posterList.splice(index, 1);
        this.$message({
          message: 'Deleted successfully',
          type: 'success'
        });
      })
    },

  }
}
</script>

<style scoped>

.content {
  padding: 20px 40px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.image {
  width: 100%;
  height: 500px;
}

</style>