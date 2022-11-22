<template>
  <el-table
      ref="multipleTableRef"
      v-model:data="Servers"
  >
    <el-table-column type="selection" min-width="10%"/>
    <el-table-column property="id" label="游戏服id" min-width="10%" show-overflow-tooltip/>
    <el-table-column property="serverName" label="游戏服名" min-width="10%"/>
    <el-table-column property="zone" label="大区" min-width="10%" show-overflow-tooltip/>
    <el-table-column property="serverType" label="服务器类型" min-width="10%" show-overflow-tooltip/>
    <el-table-column property="openServerTime" label="开服时间" min-width="10%" show-overflow-tooltip/>
    <el-table-column property="openEntranceTime" label="开入口时间" min-width="10%" show-overflow-tooltip/>
  </el-table>
  <div style="margin-top: 20px">
    <el-button @click="openWarning()">delete</el-button>
    <el-button @click="showAddEditPanel(1)">edit</el-button>
    <el-button @click="showAddItemPanel(2)">add item</el-button>
  </div>

  <el-dialog v-model="dialog_visible" title="Tips" width="30%">
    <el-form ref="elForm" :model="form" label-width="120px">
      <el-form-item label="server name">
        <el-input v-model="form.serverName" />
      </el-form-item>
      <el-form-item label="server id">
        <el-input v-model="form.id" />
      </el-form-item>
      <el-form-item label="server zone">
        <el-input v-model="form.zone" />
      </el-form-item>
      <el-form-item label="serverType">
        <el-input v-model="form.serverType" />
      </el-form-item>
      <el-form-item label="开服时间">
        <el-col :span="11">
          <el-date-picker
              v-model="form.openServerTime"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="Pick a date"
              style="width: 100%"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="入口时间">
        <el-col :span="11">
          <el-date-picker
              v-model="form.openEntranceTime"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="Pick a date"
              style="width: 100%"
          />
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>


</template>

<script>

// eslint-disable-next-line no-unused-vars
import {ElTable} from 'element-plus'
import {addServer, deleteServer, getServers, updateServer} from "@/api/server";
import { markRaw } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'

export default {
  name: "GameServer",
  components: {

  },
  methods: {
    showAddEditPanel(operation) {
      this.operation = operation
      let selectArr = this.$refs.multipleTableRef.getSelectionRows()
      this.dialog_visible = this.dialog_visible === false
      this.$nextTick(() =>{
        selectArr.forEach((f) => {
          this.$refs['elForm'].validate(valid => {
            if (!valid) return
            this.form.serverName = f.serverName
            this.form.port = f.port
            this.form.zone = f.zone
            this.form.id = f.id
            this.form.serverType = f.serverType
            this.form.openServerTime = f.openServerTime
            this.form.openEntranceTime = f.openEntranceTime
          })
        })
      })
    },
    showAddItemPanel(operation) {
      this.operation = operation
      this.dialog_visible = this.dialog_visible === false
    },
    onSubmit() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.form.port = Number(this.form.port)
        this.form.zone = Number(this.form.zone)
        this.form.id = Number(this.form.id)
        this.form.serverType = Number(this.form.serverType)
        if (this.operation === 1){
          updateServer(this.form).then(res => {
            ElMessage({
              message: res.err_msg,
              type: 'success',
            })
            this.getServerList()
            this.showAddItemPanel()
          }).catch((err) => {
            console.log(err)
          })
        } else {
          addServer(this.form).then(response => {
            ElMessage({
              message: response.err_msg,
              type: 'success',
            })
            this.getServerList()
            this.showAddItemPanel()
          }).catch((err) => {
            console.log(err)
          })
        }

      })
    },
    getServerList(){
      getServers(0).then(res => {
        this.Servers.length = 0
        res.serverList.forEach(f => {
          this.Servers.push(f)
        })
      }).catch((err) => {
        console.log(err)
      })
    },
    deleteServer(){
      let selectArr = this.$refs.multipleTableRef.getSelectionRows()
      let arr = []
      if (selectArr.length > 0){
        selectArr.forEach(f => arr.push(f.id))
      }
      deleteServer(arr).then((res) =>{
        ElMessage({
          message: res.err_msg,
          type: 'success',
        })
        this.getServerList()
      })
    },
    openWarning(){
      ElMessageBox.confirm(
          '将要删除这些服。继续？',
          'Warning',
          {
            type:"warning",
            icon: markRaw(Delete),
          },
      ).then(() => this.deleteServer())

    }
  },
  created() {
    this.getServerList()
  },
  data() {
    return {
      dialog_visible: false,
      operation: 2,
      form:{
        serverName: '',
        id: 0,
        zone: 0,
        serverType: 0,
        openServerTime: '',
        openEntranceTime: ''
      },
      Servers: []
    }
  }
}
</script>

<style scoped>

</style>