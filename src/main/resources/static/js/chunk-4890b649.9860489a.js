(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4890b649"],{"15ae":function(e,t,a){"use strict";a("c318")},"284b":function(e,t,a){"use strict";a("d3b7");var l=a("bc3a"),n=a.n(l),o=a("a18c"),i=a("5c96"),r=n.a.create({baseURL:"http://localhost:8080"});r.interceptors.request.use((function(e){var t=localStorage.getItem("token");return t?e.headers.token=t:o["a"].push("/login"),e}),(function(e){return Promise.reject(e)})),r.interceptors.response.use((function(e){if(e.data.code)switch(e.data.code){case 4004:window.localStorage.removeItem("token"),window.localStorage.removeItem("info"),o["a"].push("/login").catch((function(e){console.log(e)}));break;case 4003:Object(i["Message"])({showClose:!0,message:"权限不足",type:"warning"});break}return e}),(function(e){return Promise.reject(e.response.status)})),t["a"]=r},4035:function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"published"},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.publishedData,"row-class-name":e.tableRowClassName}},[a("el-table-column",{attrs:{prop:"taskName",label:"任务名称"}}),a("el-table-column",{attrs:{prop:"created",label:"任务创建时间"}}),a("el-table-column",{attrs:{prop:"untilTime",label:"任务截至时间"}}),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0===t.row.taskStatus?a("el-button",{attrs:{size:"mini",type:"warning"},on:{click:function(a){return e.handleEdit(t.row)}}},[e._v("查看任务进度 ")]):a("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(a){return e.handleEdit(t.row)}}},[e._v("查看任务结果 ")])]}}])})],1),a("el-drawer",{attrs:{title:e.title,visible:e.table,direction:"rtl",size:"90%"},on:{"update:visible":function(t){e.table=t},closed:function(t){e.drawerData=!1}}},[a("div",{staticStyle:{"margin-bottom":"10px",display:"flex","justify-content":"right","margin-right":"10px"}},[a("el-button",{on:{click:e.showItem}},[e._v("展示项")]),a("el-button",[e._v("导出表格")])],1),e.drawerData?a("div",[a("el-table",{attrs:{data:e.gridData}},[-1!=e.needItem.indexOf("realName")?a("el-table-column",{attrs:{prop:"info.realName",label:"姓名",width:"80px"}}):e._e(),-1!=e.needItem.indexOf("account")?a("el-table-column",{attrs:{prop:"info.account",label:"账号",width:"80px"}}):e._e(),-1!=e.needItem.indexOf("addr")?a("el-table-column",{attrs:{prop:"info.addr",label:"住址",width:"200px"}}):e._e(),-1!=e.needItem.indexOf("deptName")?a("el-table-column",{attrs:{prop:"info.deptName",label:"组织名称"}}):e._e(),-1!=e.needItem.indexOf("gender")?a("el-table-column",{attrs:{label:"性别",width:"40px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(1==t.row.info.gender?"男":"女"))])]}}],null,!1,1866781065)}):e._e(),-1!=e.needItem.indexOf("host")?a("el-table-column",{attrs:{label:"是否户主",width:"40px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.info.host?"是":"否"))])]}}],null,!1,3042631082)}):e._e(),-1!=e.needItem.indexOf("relation")?a("el-table-column",{attrs:{prop:"info.relation",label:"与户主关系",width:"50px"}}):e._e(),-1!=e.needItem.indexOf("idCardNum")?a("el-table-column",{attrs:{prop:"info.idCardNum",label:"身份证号码",width:"180px"}}):e._e(),-1!=e.needItem.indexOf("nation")?a("el-table-column",{attrs:{prop:"info.nation",label:"民族"}}):e._e(),-1!=e.needItem.indexOf("jvZhuQingKuang")?a("el-table-column",{attrs:{prop:"info.jvZhuQingKuang",label:"居住情况"}}):e._e(),-1!=e.needItem.indexOf("isDiBao")?a("el-table-column",{attrs:{label:"是否低保"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.diBao?"是":"否"))])]}}],null,!1,1823341042)}):e._e(),-1!=e.needItem.indexOf("diBaoMoney")?a("el-table-column",{attrs:{label:"低保金额"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.diBao?t.row.specialIdentity.diBaoMoney:"无"))])]}}],null,!1,575802867)}):e._e(),-1!=e.needItem.indexOf("isWuBao")?a("el-table-column",{attrs:{prop:"specialIdentity.wuBao",label:"是否五保"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.wuBao?"是":"否"))])]}}],null,!1,3847573661)}):e._e(),-1!=e.needItem.indexOf("wuBaoMoney")?a("el-table-column",{attrs:{label:"五保金额"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.diBao?t.row.specialIdentity.wuBaoMoney:"无"))])]}}],null,!1,3478825244)}):e._e(),-1!=e.needItem.indexOf("isCanJi")?a("el-table-column",{attrs:{prop:"specialIdentity.canJi",label:"是否残疾"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.canJi?"是":"否"))])]}}],null,!1,3655787228)}):e._e(),-1!=e.needItem.indexOf("canJiLevel")?a("el-table-column",{attrs:{label:"残疾等级"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.canJi?t.row.specialIdentity.canJiLevel:"无"))])]}}],null,!1,3762386805)}):e._e(),-1!=e.needItem.indexOf("canJiMoney")?a("el-table-column",{attrs:{label:"残疾补贴"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.canJi?t.row.specialIdentity.canJiMoney:"无"))])]}}],null,!1,942158387)}):e._e(),-1!=e.needItem.indexOf("isDangYuan")?a("el-table-column",{attrs:{prop:"specialIdentity.dangYuan",label:"是否党员"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.info.host?"是":"否"))])]}}],null,!1,3042631082)}):e._e(),-1!=e.needItem.indexOf("isTuiWuJunRen")?a("el-table-column",{attrs:{label:"是否退伍军人"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.specialIdentity.tuiWuJunRen?"是":"否"))])]}}],null,!1,3287920497)}):e._e(),-1!=e.needItem.indexOf("workType")?a("el-table-column",{attrs:{prop:"workCircumstances.workType",label:"工作类型"}}):e._e(),-1!=e.needItem.indexOf("workArea")?a("el-table-column",{attrs:{prop:"workCircumstances.workArea",label:"工作区域"}}):e._e(),-1!=e.needItem.indexOf("workAddr")?a("el-table-column",{attrs:{prop:"workCircumstances.workAddr",label:"工作地址"}}):e._e(),e._l(e.gridData[0].resultData,(function(t,l){return a("el-table-column",{key:l,attrs:{label:t.taskItem.taskItemName}},[a("el-table-column",{attrs:{label:"是否有"+t.taskItem.taskItemName},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.resultData[l].hasItem?"是":"否")+" ")]}}],null,!0)}),a("el-table-column",{attrs:{label:t.taskItem.taskItemName+"数量"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.resultData[l].itemNum)+" ")]}}],null,!0)})],1)})),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{round:"",size:"mini",type:"primary"},on:{click:function(a){return e.handleEditDetail(t.$index,t.row)}}},[e._v("编辑 ")])]}}],null,!1,2150795446)})],2)],1):e._e()]),a("el-dialog",{attrs:{title:"选择展示项",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[e._l(e.allItem,(function(t){return a("el-checkbox",{key:t.prop,staticStyle:{"margin-bottom":"10px"},attrs:{border:"",disabled:t.disabled,label:t.prop},model:{value:e.needItem,callback:function(t){e.needItem=t},expression:"needItem"}},[e._v(e._s(t.label)+" ")])})),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("确 定")])],1)],2)],1)},n=[],o=(a("e9c4"),a("284b")),i={data:function(){return{publishedData:[],drawerData:!1,dialogFormVisible:!1,table:!1,gridData:[],title:"",allItem:[{label:"姓名",prop:"realName",disabled:!0},{label:"账号",prop:"account",disabled:!1},{label:"住址",prop:"addr",disabled:!1},{label:"组织名称",prop:"deptName",disabled:!1},{label:"性别",prop:"gender",disabled:!1},{label:"是否户主",prop:"host",disabled:!1},{label:"与户主关系",prop:"relation",disabled:!1},{label:"身份证号码",prop:"idCardNum",disabled:!1},{label:"民族",prop:"nation",disabled:!1},{label:"居住情况",prop:"jvZhuQingKuang",disabled:!1},{label:"是否低保",prop:"isDiBao",disabled:!1},{label:"是否五保",prop:"isWuBao",disabled:!1},{label:"是否残疾",prop:"isCanJi",disabled:!1},{label:"是否党员",prop:"isDangYuan",disabled:!1},{label:"是否退伍军人",prop:"isTuiWuJunRen",disabled:!1},{label:"低保金额",prop:"diBaoMoney",disabled:!1},{label:"五保金额",prop:"wuBaoMoney",disabled:!1},{label:"残疾等级",prop:"canJiLevel",disabled:!1},{label:"残疾补贴",prop:"canJiMoney",disabled:!1},{label:"工作类型",prop:"workType",disabled:!1},{label:"工作区域",prop:"workArea",disabled:!1},{label:"工作地址",prop:"workAddr",disabled:!1}],needItem:["realName"]}},methods:{tableRowClassName:function(e){var t=e.row;return 0===t.taskStatus?"warning-row":1===t.taskStatus?"success-row":""},getPublished:function(){var e=this;o["a"].get("/task/published").then((function(t){200==t.data.code&&(e.publishedData=t.data.data)})).catch((function(e){console.log(e)}))},handleEdit:function(e){var t=this;this.title=e.taskName,this.table=!0,this.drawerData=!1,this.needItem=JSON.parse(window.localStorage.getItem("showItem"))?JSON.parse(window.localStorage.getItem("showItem")):["realName"],o["a"].get("/task/getResults/"+e.taskId).then((function(e){200==e.data.code?(t.gridData=e.data.data,t.drawerData=!0):t.$message.error(e.data.message)})).catch((function(e){console.log(e)}))},handleEditDetail:function(e,t){console.log(e,t)},showItem:function(){this.dialogFormVisible=!0}},created:function(){this.getPublished()},watch:{needItem:function(e){window.localStorage.setItem("showItem",JSON.stringify(e))}}},r=i,s=(a("15ae"),a("2877")),d=Object(s["a"])(r,l,n,!1,null,"14b53b79",null);t["default"]=d.exports},c318:function(e,t,a){},e9c4:function(e,t,a){var l=a("23e7"),n=a("da84"),o=a("d066"),i=a("2ba4"),r=a("e330"),s=a("d039"),d=n.Array,u=o("JSON","stringify"),c=r(/./.exec),p=r("".charAt),b=r("".charCodeAt),f=r("".replace),m=r(1..toString),w=/[\uD800-\uDFFF]/g,_=/^[\uD800-\uDBFF]$/,g=/^[\uDC00-\uDFFF]$/,h=function(e,t,a){var l=p(a,t-1),n=p(a,t+1);return c(_,e)&&!c(g,n)||c(g,e)&&!c(_,l)?"\\u"+m(b(e,0),16):e},I=s((function(){return'"\\udf06\\ud834"'!==u("\udf06\ud834")||'"\\udead"'!==u("\udead")}));u&&l({target:"JSON",stat:!0,forced:I},{stringify:function(e,t,a){for(var l=0,n=arguments.length,o=d(n);l<n;l++)o[l]=arguments[l];var r=i(u,null,o);return"string"==typeof r?f(r,w,h):r}})}}]);
//# sourceMappingURL=chunk-4890b649.9860489a.js.map