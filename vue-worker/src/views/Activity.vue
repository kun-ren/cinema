<template>
  <div style="padding: 40px">
    <el-button @click="dialogFormVisible = true" plain>Add cinema activity</el-button>
    <el-table
        v-loading="loading"
        :data="list"
        style="width: 100%;margin-top: 20px">
      <el-table-column
          prop="startTime"
          label="Start time"
          width="220">
      </el-table-column>
      <el-table-column
          prop="endTime"
          label="End time"
          width="220">
      </el-table-column>
      <el-table-column
          prop="content"
          label="Activity details">
      </el-table-column>
      <el-table-column
          prop="number"
          label="Participants">
      </el-table-column>
      <el-table-column width="150" label="Actions">
        <template slot-scope="props">
          <el-button @click="handleDelete(props.$index)" size="small" type="danger" plain>Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Add activity" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item style="padding-right: 100px" label="Start date" label-width="150px">
          <el-date-picker
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="Start date">
          </el-date-picker>
        </el-form-item>
        <el-form-item style="padding-right: 100px" label="End date" label-width="150px">
          <el-date-picker
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="End date">
          </el-date-picker>
        </el-form-item>
        <el-form-item style="padding-right: 100px" label="Activity details" label-width="150px">
          <el-input :rows="8" type="textarea" v-model="form.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitActivity">Save</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {CreateActivity, DeleteActivityById, ListAllActivity} from "@/api/activity";

export default {

  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: 'Today',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: 'Yesterday',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: 'One week ago',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      loading: false,
      list: [],
      dialogFormVisible: false,
      form: {
        content: '',
        startTime: '',
        endTime: '',
      },
    }
  },

  mounted() {
    this.loadList();
  },

  methods: {

    loadList() {
      this.loading = true;
      ListAllActivity().then(res => {
        setTimeout(() => {
          this.list = res.data
          this.loading = false
        }, 700)
      })
    },

    submitActivity() {
      CreateActivity(this.form).then(res => {
        if (res.success) {
          this.dialogFormVisible = false
          this.$message({
            type: 'success',
            message: 'Saved successfully!'
          });
          this.loadList()
        }
      })
    },

    handleDelete(index) {
      DeleteActivityById(this.list[index].id).then(res => {
        if (res.success) {
          this.list.slice(index, 1)
          this.$message({
            type: 'success',
            message: 'Deleted successfully!'
          });
          this.loadList()
        }
      })
    },

  },

}
</script>

<style scoped>

</style>