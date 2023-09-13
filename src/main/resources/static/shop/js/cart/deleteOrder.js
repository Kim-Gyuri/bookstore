function deleteOrderItem(){

     var id = $('#orderItemId').val();

    $.ajax({
                url : "/api/cart/item/" + id,
                type : "delete",
                dataType : 'json',
                contentType : false,
                processData : false,
                success : function(data) {
                    alert("상품이 삭제되었습니다.");
                    window.location.replace('/cart');
                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}