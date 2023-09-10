function getCart(){

    $.ajax({
                url : "/api/cart",
                type : "get",
                dataType : 'json',
                success : function(data) {
                    window.location.replace('/cart');
                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}


