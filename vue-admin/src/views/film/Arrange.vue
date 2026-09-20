<template>
  <div class="main">

    <el-dialog
        title="Add screening"
        :visible.sync="dialog"
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
            <el-time-picker placeholder="Start time" value-format="HH:mm:ss" v-model="arrangement.startTime"
                            style="width: 100%;"></el-time-picker>
          </el-col>
          <el-col style="text-align: center" class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="End time" value-format="HH:mm:ss" v-model="arrangement.endTime"
                            style="width: 100%;"></el-time-picker>
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
        <el-button @click="dialog = false">Cancel</el-button>
        <el-button type="primary" @click="submitEditArrange">Confirm</el-button>
      </span>
    </el-dialog>

    <el-table
        v-loading="loading"
        :data="List.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">

      <el-table-column
          label="Film title"
          prop="name">
      </el-table-column>

      <el-table-column
          label="Screening date"
          prop="date">
      </el-table-column>

      <el-table-column
          label="Available seats"
          prop="seatNumber">
      </el-table-column>

      <el-table-column label="Screening format">
        <template slot-scope="scope">
          <el-tag effect="dark" v-if="scope.row.type === '2D'" type="success" disable-transitions>2D</el-tag>
          <el-tag effect="dark" v-if="scope.row.type === '3D'" type="primary" disable-transitions>3D</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          label="Box office"
          prop="boxOffice">
      </el-table-column>

      <el-table-column
          label="End time"
          prop="endTime">
      </el-table-column>

      <el-table-column label="Ticket price">
        <template slot-scope="scope">
          <el-tag size="medium" effect="plain">{{ scope.row.price }}  CNY</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="300" align="right">
        <template slot="header" slot-scope="scope">
          <el-input
              v-model="search"
              size="mini"
              placeholder="Search by keyword"/>
        </template>
        <template style="white-space: nowrap" slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-s-ticket"
              type="success"
              @click="handleBoxOffice(scope.$index, scope.row)">Update box office
          </el-button>
          <el-button
              size="mini"
              icon="el-icon-edit"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)">Edit
          </el-button>

          <el-popconfirm
              confirm-button-text='Confirm'
              cancel-button-text='No thanks'
              icon="el-icon-info"
              icon-color="red"
              @confirm="handleDelete(scope.$index, scope.row)"
              title=" Delete this screening? "
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
import {FindAllArrangement, DeleteArrangement, UpdateArrangement} from "@/api/film";
import {UpdateUser} from "@/api/user";

export default {
  name: "Arrange",
  data() {
    return {
      dialog: false,
      search: '',
      List: [],
      loading: false,
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
    }
  },

  mounted() {
    this.load()
  },

  methods: {

    load() {
      this.loading = true;
      FindAllArrangement().then(res => {
        setTimeout(() => {
          this.loading = false;
          this.List = res.data
        }, 700)
      })
    },


    handleEdit(index, row) {
      this.dialog = true;
      this.arrangement = row;
    },

    submitEditArrange() {
      UpdateArrangement(this.arrangement).then(res => {
        if (res.success) {
          this.dialog = false;
          this.$message({
            message: 'Screening added successfully!',
            type: 'success'
          });
        }
      })
    },

    handleDelete(index, row) {
      DeleteArrangement(row.id).then(res => {
        this.List.splice(index, 1)
        this.$message({
          message: 'Screening deleted successfully!',
          type: 'success'
        });
      })
    },

    handleBoxOffice(index, row) {
      this.$prompt('Enter the box office total for this screening', 'Notice', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
      }).then(({value}) => {
        if (!value) {
          this.$message({
            type: 'warning',
            message: 'A value is required'
          });
          return
        }
        if (value < 0) {
          this.$message({
            type: 'warning',
            message: 'Enter a number greater than or equal to zero'
          });
          return
        }
        this.List[index].boxOffice = value
        UpdateArrangement(this.List[index]).then(res => {
          this.$message({
            message: 'Box office updated successfully',
            type: 'success',
          });
        })
      }).catch(err => {
        this.$message({
          type: 'warning',
          message: err
        });
      });
    },

  }

}
</script>

<style scoped>
.main {
  padding: 20px;
}
</style>
