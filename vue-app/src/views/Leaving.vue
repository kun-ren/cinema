<template>
  <div class="main">

    <el-container>

      <el-aside width="400px">
        <h1 style="color: #FFFFFF;padding-bottom: 40px;letter-spacing: 1px">Support staff</h1>
        <div class="item" v-for="(item, index) in workerList" :key="index">
          <img class="av" alt="" :src="item.avatar">
          <div>
            <div class="d1">Name: {{ item.nickname }}</div>
            <div class="d1">Gender: {{ item.gender }}</div>
            <div class="d1">ID: {{ item.id }}</div>
            <div>
              <el-button
                  @click="handleListEvaluate(item)"
                  type="text">My reviews
              </el-button>
              <el-button
                  style="padding-left: 40px"
                  :disabled="!isLogin"
                  @click="handleEvaluate(item)"
                  type="text">Review support worker
              </el-button>
            </div>
          </div>
        </div>
      </el-aside>

      <el-main>
        <h1 style="color: #FFFFFF;padding-bottom: 40px;letter-spacing: 1px">Cinema messages</h1>
        <div class="item2" v-for="(item, index) in leavingMessageList" :key="index">
          <div style="display: flex">
            <img class="av2" :src="item.user.avatar" alt="null">
            <div>
              <div class="d2">{{ item.user.nickname }}</div>
              <div v-if="!item.user.nickname" class="d2">Anonymous user</div>
              <div class="d3">{{ item.leavingMessage.content }}</div>
              <div class="d2">Time: {{ item.leavingMessage.createAt }}</div>
            </div>
          </div>
          <div v-if="!item.leavingMessage.reply" class="reply">
            <div class="d2">Support reply:</div>
            <div class="d3">{{ item.leavingMessage.reply }}</div>
          </div>
        </div>
        <el-button :disabled="!isLogin"
                   @click="handleLeaving"
                   class="btn"
                   type="success"
                   icon="el-icon-share">
          Add message
        </el-button>
      </el-main>
    </el-container>

    <el-dialog title="Support reviews" :visible.sync="dialogFormVisible1">
      <el-form :model="evaluate">
        <el-form-item label="Satisfaction" label-width="150px">
          <el-select v-model="evaluate.type" placeholder="Select a satisfaction level">
            <el-option label="Satisfied" value="Satisfied"></el-option>
            <el-option label="Dissatisfied" value="Dissatisfied"></el-option>
            <el-option label="Very satisfied" value="Very satisfied"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 100%" label="Review text" label-width="150px">
          <el-input type="textarea" :rows="8" v-model="evaluate.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">Cancel</el-button>
        <el-button type="primary" @click="submitEvaluate">Submit review</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Add message" :visible.sync="dialogFormVisible2">
      <el-input type="textarea" :rows="8" v-model="leavingMessage.content" autocomplete="off"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">Cancel</el-button>
        <el-button type="primary" @click="submitLeaving">Send message</el-button>
      </div>
    </el-dialog>

    <el-dialog title="My reviews" :visible.sync="dialogFormVisible3">
      <el-table :data="evaluateList">
        <el-table-column property="createAt" label="Date" width="250"></el-table-column>
        <el-table-column property="content" label="Review"></el-table-column>
        <el-table-column property="type" width="150">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type==='Very satisfied'" type="success">Very satisfied</el-tag>
            <el-tag v-if="scope.row.type==='Satisfied'" type="info">Satisfied</el-tag>
            <el-tag v-if="scope.row.type==='Dissatisfied'" type="danger">Dissatisfied</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import {findById} from "@/api/user";
import {
  CreateEvaluate,
  CreateLeavingMessage,
  ListAllLeavingMessage,
  ListAllWorker,
  ListWorkerEvaluate
} from "@/api/leaving";

export default {

  data() {
    return {
      isLogin: false,
      evaluate: {
        wid: '',
        uid: localStorage.getItem("uid"),
        type: 'Very satisfied',
        content: '',
      },
      evaluateList: [],
      workerList: [],
      leavingMessageList: [],
      leavingMessage: {
        uid: localStorage.getItem("uid"),
        reply: 'null',
        content: '',
        createAt: '',
      },
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
    }
  },

  mounted() {
    if (localStorage.getItem("uid")) {
      this.isLogin = true
    }
    ListAllLeavingMessage().then(res => {
      this.leavingMessageList = res.data
    })
    ListAllWorker().then(res => {
      this.workerList = res.data
    })
  },

  methods: {

    handleListEvaluate(item) {
      this.dialogFormVisible3 = true
      ListWorkerEvaluate(item.id).then(res => {
        this.evaluateList = res.data
      })
    },

    handleEvaluate(item) {
      this.dialogFormVisible1 = true
      this.evaluate.wid = item.id
    },

    handleLeaving() {
      this.dialogFormVisible2 = true
    },

    submitEvaluate() {
      CreateEvaluate(this.evaluate).then(res => {
        if (res.success) {
          this.dialogFormVisible1 = false
          this.$message({
            message: 'Thank you for reviewing our staff',
            type: 'success'
          });
        }
      })
    },

    submitLeaving() {
      CreateLeavingMessage(this.leavingMessage).then(res => {
        if (res.success) {
          this.dialogFormVisible2 = false
          this.$message({
            message: 'Thank you for your feedback on Little Cinema',
            type: 'success'
          });
        }
      })
    },

  },

}
</script>

<style scoped>

.main {
  color: #FFFFFF;
  padding: 40px;
}

.el-aside {
  background-color: #242930;
  color: #333;
  padding: 10px 50px 40px;
  min-height: 500px;
}

.el-main {
  background-color: #353b43;
  color: #333;
  padding: 10px 70px 50px;
  min-height: 500px;
}

.item {
  margin-bottom: 40px;
  padding-bottom: 10px;
  border-bottom: 1px dashed rgba(255, 255, 255, .3);
}

.item2 {
  padding-bottom: 60px;
}

.av {
  width: 76px;
  height: 76px;
  border-radius: 38px;
  margin-right: 20px;
  float: left;
}

.av2 {
  width: 60px;
  height: 60px;
  border-radius: 30px;
  margin-right: 20px;
  margin-top: 10px;
}

.d1 {
  letter-spacing: 2px;
  line-height: 30px;
  color: #afbac4;
  font-size: 14px;
}

.d2 {
  color: #afbac4;
  letter-spacing: 2px;
  font-size: 14px;
  line-height: 30px;
}

.d3 {
  letter-spacing: 2px;
  font-size: 15px;
  font-weight: 400;
  line-height: 30px;
  color: #FFFFFF;
}

.el-button--text {
  color: #57cc8a;
}

.btn {
  float: right;
  background: #57cc8a;
  letter-spacing: 1px;
}

.reply {
  padding-left: 75px;
  padding-top: 15px;
}

</style>