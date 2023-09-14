function getCategory(){

    var form = $("#form")[0];
    var formData = new FormData(form);

    //data json 으로 저장 ->
    var data = {
        "itemName" : $.trim($("#itemName").val())
    }
    formData.append("condition", new Blob([JSON.stringify(data)] , {type: "application/json"}));     //condition 이름으로 게시글 내용 저장

    var code = $('#categoryType').val();
    console.log(code);

    $.ajax({
                url : "/api/items/category/"+ code,
                type : "get",
                dataType : 'json',
                success : function(data) {
                    window.location.replace('/items/category/'+ code);
                },
                error : function(error){
                    console.log(error);
                }
    });
}

