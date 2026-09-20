<template>
  <div class="main">
    <el-timeline>
      <el-timeline-item
          style="letter-spacing: 2px"
          v-for="(item, index) in list"
          :key="index"
          :timestamp="item.startTime"
          placement="top">
        <el-card>
          <h4 class="h1">{{ item.content }}</h4>
          <h4 class="p1">Activity end time: {{ item.endTime }}</h4>
          <el-button @click="handleRegister(item)" type="text">Register</el-button>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import {ListAllActivity, SubmitActive} from "@/api/active";

export default {

  data() {
    return {
      list: [],
      form: {
        aid: '',
        uid: '',
      },
    }
  },

  mounted() {
    this.load()
  },

  methods: {

    load() {
      ListAllActivity().then(res => {
        if (res.success) {
          this.list = res.data
        }
      })
    },

    handleRegister(item) {
      if (!localStorage.getItem("uid")) {
        this.$confirm('You are not logged in. Go to login?', 'Notice', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login")
        })
      } else {
        this.form.aid = item.id
        this.form.uid = localStorage.getItem('uid')
        SubmitActive(this.form).then(res => {
          if (res.success) {
            this.$message({
              message: 'Registered for the activity successfully!',
              type: 'success'
            });
          }
        })
      }
    },

  },

}

</script>

<style scoped>
.main {
  padding: 100px;
}

.h1 {
  font-size: 18px;
}

.p1 {
  color: #666666;
  font-weight: 300;
}
</style>