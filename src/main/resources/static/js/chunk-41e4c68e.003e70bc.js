(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-41e4c68e"],{"284b":function(e,r,t){"use strict";t("d3b7");var o=t("bc3a"),s=t.n(o),n=t("a18c"),a=t("5c96"),i=s.a.create({baseURL:"http://e7s6ux.natappfree.cc"});i.interceptors.request.use((function(e){var r=localStorage.getItem("token");return r?e.headers.token=r:n["a"].push("/login"),e}),(function(e){return Promise.reject(e)})),i.interceptors.response.use((function(e){if(e.data.code)switch(e.data.code){case 4004:window.localStorage.removeItem("token"),window.localStorage.removeItem("info"),n["a"].push("/login").catch((function(e){console.log(e)}));break;case 4003:Object(a["Message"])({showClose:!0,message:"权限不足",type:"warning"});break}return e}),(function(e){return Promise.reject(e.response.status)})),r["a"]=i},"69cf":function(e,r,t){"use strict";t("74ba")},"74ba":function(e,r,t){},a55b:function(e,r,t){"use strict";t.r(r);var o=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"login"},[t("div",{staticClass:"login-box"},[t("h2",[e._v("云峰村信息采集程序")]),t("div",{staticClass:"login-form"},[t("el-form",{ref:"loginForm",staticClass:"demo-ruleForm",attrs:{model:e.loginForm,rules:e.rules,"label-width":"80px"}},[t("el-form-item",{attrs:{label:"用户名:",prop:"username"}},[t("el-input",{model:{value:e.loginForm.username,callback:function(r){e.$set(e.loginForm,"username",r)},expression:"loginForm.username"}})],1),t("el-form-item",{attrs:{label:"密码:",prop:"password"}},[t("el-input",{attrs:{"show-password":""},model:{value:e.loginForm.password,callback:function(r){e.$set(e.loginForm,"password",r)},expression:"loginForm.password"}})],1),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:function(r){return e.submitForm("loginForm")}}},[e._v("登陆")]),t("el-button",{on:{click:function(r){return e.resetForm("loginForm")}}},[e._v("重置")])],1)],1)],1)])])},s=[],n=t("284b"),a={data:function(){return{loginForm:{username:"",password:""},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"},{min:2,max:10,message:"长度在 2 到 10 个字符",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"},{min:6,max:20,message:"长度在 6 到 20 个字符",trigger:"blur"}]}}},methods:{submitForm:function(e){var r=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;n["a"].post("/login",r.$qs.stringify(r.loginForm)).then((function(e){var t=e.headers.token;if(t&&200==e.data.code){r.$message({message:"登陆成功",type:"success"}),window.localStorage.setItem("token",t);var o=localStorage.getItem("toPath");o?r.$router.push(o):r.$router.push("/main")}else r.$message.error(e.data.message)})).catch((function(e){console.log(e)}))}))},resetForm:function(e){this.$refs[e].resetFields()}}},i=a,l=(t("69cf"),t("2877")),c=Object(l["a"])(i,o,s,!1,null,"e64e91ee",null);r["default"]=c.exports}}]);
//# sourceMappingURL=chunk-41e4c68e.003e70bc.js.map