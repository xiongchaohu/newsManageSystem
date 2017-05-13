//用户首页的js文件
/*首页js文件*/

    //登录的前端校验
    function fcheck(){
    	var uid=document.getElementById("userID").value.trim();
    	var pwd=document.getElementById("password").value.trim();
    	
    	if(uid==""){
    		alert("用户名不能为空");
    		
    	}
    	if(pwd==""){
    		alert("密码不能为空");
    		return;
    	}
    	if(pwd.length<6){
    		alert("密码长度不得小于6!!!");
    		return;
    	}
    	document.login_form.submit();
    }
    
    
    