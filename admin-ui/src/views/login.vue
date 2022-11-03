<template>
  <div class="login_form">
    <el-row :gutter="15" class="login_size">
      <el-form ref="elForm" :model="loginForm" :rules="rules" size="medium" label-width="100px">
        <el-col :span="6">
          <el-form-item label="用户名" prop="use_name" class="login_input">
            <el-input v-model="loginForm.use_name" placeholder="请输入用户名" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="密码" prop="pass_word" class="login_input">
            <el-input v-model="loginForm.password" placeholder="请输入密码" clearable show-password
                      :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import Cookies from "js-cookie";
import {encrypt} from '@/utils/jsencrypt'
import store from "@/store";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'login',
  components: {},
  props: [],
  data() {
    return {
      loginForm: {
        use_name: undefined,
        password: undefined,
        button_login: undefined,
        rememberMe: false,
        code: "",
        uuid: ""
      },
      rules: {
        use_name: [{
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
      },
      loading: false,
    }
  },
  computed: {},
  watch: {
    // route: {
    //   handler: function(route) {
    //     this.redirect = route.query && route.query.redirect;
    //   },
    //   immediate: true
    // }
  },
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        this.loading = true;
        if (this.loginForm.rememberMe) {
          Cookies.set("use_name", this.loginForm.use_name, { expires: 30 });
          Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
          Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
        } else {
          Cookies.remove("use_name");
          Cookies.remove("password");
          Cookies.remove('rememberMe');
        }

        store.dispatch("Login", this.loginForm).then(() => {
          router.push({ path: this.redirect || "/" }).catch(()=>{});
        }).catch((err) => {
          console.log("Login error!!!!")
          console.log(err)
          this.loading = false;
        });
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
.login_form{
  margin-left: 35%;
  margin-top: 20%;
}

.login_size{
  width: 500px;
}

.login_input{
  width: 300px;
}

</style>
