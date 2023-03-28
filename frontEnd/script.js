// import instance from "index.js";

function AESDecrypt(textBase64, AESKey, AESIv) {
  // console.log(textBase64, CryptoJS.enc.Utf8.parse(AESKey), {
  //   iv: CryptoJS.enc.Utf8.parse(AESIv), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7
  // })
  const plainTextObj = CryptoJS.AES.decrypt(textBase64, CryptoJS.enc.Utf8.parse(AESKey), {
    iv: CryptoJS.enc.Utf8.parse(AESIv), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7
  });
  return plainTextObj.toString(CryptoJS.enc.Utf8);
}

window.onload = function () {
  var button = document.getElementById("log-in");
  button.onclick = function () {
    // 客户端输入用户名，口令
    var userName = document.getElementById("username").value;
    var passWord = document.getElementById("password").value;
    // alert(userName);
    // alert(passWord);
    // 随机产生认证码，使用散列函数计算用户名与口令的散列值1，使用散列值1与认证码计算散列值2，
    // 将用户名，散列值2，认证码明文传送到服务器端。

    // 随机产生认证码
    var authCode = '';
    for (i = 0; i < 8; i++) {
      authCode += Math.round(Math.random() * 10);
    }
    var hashedValue1 = '';
    var hashedValue2 = '';

    // 使用散列函数(SHA-256)计算用户名与口令的散列值1
    var hashedValue1 = CryptoJS.enc.Base64.stringify(CryptoJS.SHA256(userName + passWord));

    // 使用散列值1与认证码计算散列值2
    var hashedValue2 = CryptoJS.enc.Base64.stringify(CryptoJS.SHA256(hashedValue1 + authCode));

    //将用户名，散列值2，认证码明文传送到服务器端

    axios({
      url: "http://localhost:12345/api/logIn",
      method: 'post',
      data: {
        userName: userName,
        hashedValue2: hashedValue2,
        authCode: authCode
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
      //解析后端发来的数据

      var success = res.data.success;
      if (success) {
        var AESEncryptedMessage = res.data.aesencryptedAuthCode;
        //解密后端发来的数据
        const decryptedAuthCode = AESDecrypt(AESEncryptedMessage, hashedValue1.substring(0, 16), hashedValue1.substring(16, 32));
        console.log(decryptedAuthCode);
        
        const stringUrl = URL.createObjectURL(
          new Blob([decryptedAuthCode])
        );
    
        const anchor = document.createElement("a");
        anchor.href = stringUrl;
        anchor.download = "authcode.txt";
    
        anchor.click();
    
        URL.revokeObjectURL(stringUrl);
      }
      else {
        console.log("Error occurred.");
      }
      
      

    }).catch((err) => {
      console.log(err);
    });

    


    
  }
}




