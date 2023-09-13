function getCart(){

    var userId = $('#loginId').val();

    $.ajax({
                url : "/api/cart",
                type : "get",
                contentType: 'application/json',
                success : function(data) {
                    window.location.replace('/cart');
                },
                error : function(error){
                    console.log(error);
                }
    });
}


