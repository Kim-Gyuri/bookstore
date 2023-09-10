function getSalesSubmit(){

    $.ajax({
                url : "/api/users/sales",
                type : "get",
                dataType : 'json',
                success : function(data) {
                    window.location.replace('/sales');

                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}


