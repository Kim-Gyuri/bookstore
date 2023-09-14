function addItemSubmit(){

    // CreateItemRequest 정보 저장
    var form = $("#addItemForm")[0];
    var formData = new FormData(form);

    //data json 으로 저장
    var data = {
        "name" : $.trim($("#name").val()),
        "price" : $.trim($("#price").val()),
        "stockQuantity" : $.trim($("#stockQuantity").val()),
        "itemType" : $.trim($("#itemType option:selected").val()),
        "categoryType" : $.trim($("#category option:selected").val())
    }
    formData.append("createItemRequest", new Blob([JSON.stringify(data)] , {type: "application/json"}));     //createItemRequest 이름으로 게시글 내용 저장


    // 이미지 파일을 담을 배열 (버튼을 눌렀을 때 서버에 전송할 데이터)
    var inputFileList = new Array();

    // 파일 선택 이벤트
    $('input[name=images]').on('change', function(e) {
    　　var files = e.target.files;
    　　var filesArr = Array.prototype.slice.call(files);

    　　// 업로드 된 파일 유효성 체크
    　　if (filesArr.length  < 1) {
    　　　　alert("이미지는 최소 1개가 필요합니다.");　　　
    　　　　return;
    　　}

        // 이미지 파일을 배열에 담는다.
        filesArr.forEach(function(f){
            inputFileList.push(f);
        });

        // 이미지 파일을 배열에 담는다.
    　　filesArr.forEach(function(f) {
    　　　　inputFileList.push(f);
    　　});
    });

    // 배열에서 이미지들을 꺼내 폼 객체에 담는다.
    for (let i = 0; i < inputFileList.length; i++) {
        formData.append("images", inputFileList[i]);
    }


    $.ajax({
                url : "/api/items",
                type : "post",
                enctype:"multipart/form-data", // 이미지 업로드 필수 파라미터
                data : formData,
                dataType : 'json',
                contentType : false, // 이미지 업로드 필수 파라미터
                processData : false, // 이미지 업로드 필수 파라미터
                success : function(data) {
                    alert("상품이 등록되었습니다.");
                    window.location.replace('/items'); // 메인 홈 창으로 이동

                },
                error : function(error){
                    alert(JSON.stringify(error));
                }
    });
}


