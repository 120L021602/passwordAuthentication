window.onload = function(){
    var changeButton = document.getElementById("change")
    changeButton.onclick = function(){
        var userName = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        var password2 = document.getElementById("password2").value;

        //判断两次输入的密码值是否相等
        if(password != password2){
            console.log("The two passwords are different.");
        }
        //相等的情况
        else{
            axios({
                url: "http://localhost:12345/api/changePassword",
                method: 'post',
                data: {
                  userName: userName,
                  password: password
                },
                transformRequest: [function (data) {
                
                  let ret = ''
                  for (let it in data) {
                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                  }
                  return ret
                }],
                headers: {
                  'Content-Type': 'application/x-www-form-urlencoded'
                }
              }).then((res) => {
                console.log(res);
                if(res.data){
                    console.log("Successfully changed password!");
                  }
                else{
                    console.log("Change failed.");
                  }
              }).catch((err) => {
                console.log(err);
              });

        }
    }
}