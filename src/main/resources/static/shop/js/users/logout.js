function logoutSubmit(){

    $.ajax({
                url : "/api/users/logout",
                type : "post",
                contentType : false,
                processData : false,
                success : function(data) {
                    window.location.replace('/users/login'); // 로그인 창으로 이동
                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}
