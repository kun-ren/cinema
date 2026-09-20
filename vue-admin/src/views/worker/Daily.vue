<template>
  <div style="padding: 40px">
    <el-button @click="dialogFormVisible = true" plain>Add work update</el-button>
    <el-table
        v-loading="loading"
        :data="list"
        style="width: 100%;margin-top: 20px">
      <el-table-column
          prop="createAt"
          label="Added at"
          width="220">
      </el-table-column>
      <el-table-column label="Priority" width="150">
        <template slot-scope="props">
          <el-tag type="info" v-if="props.row.type === 1" effect="dark">
            Normal
          </el-tag>
          <el-tag type="success" v-if="props.row.type === 2" effect="dark">
            Important
          </el-tag>
          <el-tag type="danger" v-if="props.row.type === 3" effect="dark">
            Urgent
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="content"
          label="Content">
      </el-table-column>
      <el-table-column width="150" label="Actions">
        <template slot-scope="props">
          <el-button @click="handleDelete(props.$index)" size="small" type="danger" plain>Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Add daily work" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="Priority" label-width="150px">
          <el-select v-model="form.type" placeholder="Select a priority">
            <el-option label="Normal" value="1"></el-option>
            <el-option label="Important" value="2"></el-option>
            <el-option label="Urgent" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="padding-right: 100px" label="Content" label-width="150px">
          <el-input :rows="8" type="textarea" v-model="form.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveDailyWork">Save</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {AddDailyWork, DeleteDailyWork, ListDailyWork} from "@/api/worker";

export default {

  data() {
    return {
      loading: false,
      list: [],
      dialogFormVisible: false,
      form: {
        type: '',
        content: '',
      },
    }
  },

  mounted() {
    this.loadList();
  },

  methods: {

    loadList() {
      this.loading = true;
      ListDailyWork().then(res => {
        setTimeout(() => {
          this.list = res.data
          this.loading = false
        }, 700)
      })
    },

    saveDailyWork() {
      AddDailyWork(this.form).then(res => {
        this.dialogFormVisible = false
        this.loadList()
        this.$message({
          type: 'success',
          message: 'Saved successfully!'
        });
      })
    },

    handleDelete(index) {
      DeleteDailyWork(this.list[index].id).then(res => {
        this.list.slice(index, 1)
        this.loadList()
        this.$message({
          type: 'success',
          message: 'Deleted successfully!'
        });
      })
    },

  },

}
</script>

<style scoped>

</style>
