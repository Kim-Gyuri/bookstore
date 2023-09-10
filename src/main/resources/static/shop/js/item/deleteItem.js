function deleteItemSubmit(){

     var id = $('#id').val();

    $.ajax({
                url : "/api/items/" + id,
                type : "delete",
                dataType : 'json',
                contentType : false,
                processData : false,
                success : function(data) {
                    alert("상품이 삭제되었습니다.");
                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}