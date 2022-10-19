//用户名、密码
let userId=null;
let password=null;
//验证码
// let verifyCode = null;
//
// $(function(){
//     verifyCode = new GVerify({
//         width:100,
//         height:30,
//         id:"verify_img",    //容器的ID
//         type:"blend"    //图形验证码的类型：blend-数字字母混合类型（默认）、number-纯数字、letter-纯字母
//     });
//     //刷新验证码
//     verifyCode.refresh();
// });

function login() {
     userId=document.getElementById("userName");
     password=document.getElementById("psw");
    //  let code=document.getElementById("verifyCode").value;
    // //校验验证码
    // if (!verifyCode.validate(code)){         //如果校验正确返回ture，校验错误返回false
    //     alert("验证码错误！");
    //     return;
    // }
     $.ajax({
         type:"POST",
         data:{
             userName:userId.value,
             password:password.value,
         },

         url: '/admin/login',
         async:false,
         success:function (data) {
             if (data.code!=="0"){
                 sessionStorage.setItem("token" , data.data.token)
                 sessionStorage.setItem("userName" , data.data.userName)
                 window.location.href="/admin/index";
             }else{
                 alert("用户名或密码错误！");
             }
         }
     });
}
