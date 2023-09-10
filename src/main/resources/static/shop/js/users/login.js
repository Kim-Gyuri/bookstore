function loginSubmit(){

    var form = $("#loginForm")[0];
    var formData = new FormData(form);

    //data json으로 저장
    var data = {
        "loginId" : $.trim($("#loginId").val()),
        "password" : $.trim($("#password").val())
    }
    formData.append("loginRequest", new Blob([JSON.stringify(data)] , {type: "application/json"}));     //loginRequest 이름으로 로그인 정보 저장

    $.ajax({
                url : "/api/users/login",
                type : "post",
                data : formData,
                dataType : 'json',
                contentType : false,
                processData : false,
                success : function(data) {
                    window.location.replace('/items'); // 메인 홈화면으로 이동
                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}
