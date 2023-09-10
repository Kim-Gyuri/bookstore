function deleteBasket(orderItemId, event){

    $.ajax({
        url: "/api/cart/item/"+orderItemId,
        type: "delete",
        success: function(data){
             $(event.target).parents("tr").remove();
             changeTotalPrice();
        },
        error: function(error){
            alert(error.responseJSON.message);
        }
    });
}