function orderSubmit(){

    var count = $.trim($("#count").val());
    var id = $.trim($("#id").val());

    $.ajax({
                url : "/api/cart/item/" + id + "/" + count,
                type : "post",
                dataType : 'json',
                success : function(data) {
                    alert("장바구니에 담겼습니다.")
                    window.location.replace('/items');
                },
                error : function(error){
                    alert(error.responseJSON.message);
                }
    });
}