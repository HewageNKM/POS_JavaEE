$('#itemSaveButton').on('click', function () {
    if (validateId("#item-code") & validateItemName("#item-name") & validateQty("#item-qty") & validatePrice("#item-Price")) {
        let itemCode = $("#item-code").val().toLowerCase();
        let itemName = $("#item-name").val();
        let itemQty = $("#item-qty").val();
        let itemPrice = $("#item-Price").val();
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/item",
            contentType: "application/json",
            headers: {
                Origin: 'http://localhost:5000/itemForm.html'
            },
            data: JSON.stringify({
                id: itemCode,
                name: itemName,
                qty: itemQty,
                price: itemPrice
            }),
            success: function (data) {
                loadItemTable();
                resetFld();
            },
            error: function (error) {
                if (error.status === 500) {
                    alert("Item ID already exists");
                } else {
                    alert("Something went wrong");
                }
            }
        });
    } else {
        alert("Please fill the required fields");
    }
});
$('#item-code').on('keyup', function () {
    validateId("#item-code");
});
$('#item-name').on('keyup', function () {
    validateItemName("#item-name");
});
$('#item-qty').on('keyup', function () {
    validateQty("#item-qty");
});
$('#item-Price').on('keyup', function () {
    validatePrice("#item-Price");
});
$('#itemId').on('keyup', function () {
    validateId("#itemId");
});
$('#itemName').on('keyup', function () {
    validateItemName("#itemName");
});
$('#qtyOnHand').on('keyup', function () {
    validateQty("#qtyOnHand");
});
$('#price').on('keyup', function () {
    validatePrice("#price");
});

function setFld(response) {
    $('#itemId').val(response.id.toUpperCase());
    $('#itemName').val(response.name);
    $('#qtyOnHand').val(response.qty);
    $('#price').val(response.price);

    $('#itemName').prop("disabled", false);
    $('#qtyOnHand').prop("disabled", false);
    $('#price').prop("disabled", false);
}

$('#btnItemSearchButton').on('click', function () {
    if (validateId("#inputItemSearch")) {
        let val = $('#itemCombo').val();
        $.ajax({
            url: "http://localhost:8080/api/item?id=" + $('#inputItemSearch').val().toLowerCase()+"&volume=single",
            type: "GET",
            headers: {
                Origin: 'http://localhost:5000/itemForm.html'
            },
            success: function (response) {
                response = JSON.parse(response);
                setFld(response);
            },
            error: function (error) {
                if (error.status === 404) {
                    alert("Not Found");
                } else {
                    alert("Something went wrong");
                }
            }
        });
    } else {
        alert("Invalid Item ID")
    }
});
$('#updateBtn').on('click', function () {
    const response = confirm("Are you sure you want to update this item?");
    if (!response) {
        return;
    }
    if (validateItemName("#itemName") & validateQty("#qtyOnHand") & validatePrice("#price")) {
        let itemId = $("#itemId").val().toLowerCase();
        let itemName = $("#itemName").val();
        let itemQty = $("#qtyOnHand").val();
        let itemPrice = $("#price").val();
        $.ajax({
            method: "PUT",
            headers: {
                Origin: 'http://localhost:5000/itemForm.html'
            },
            url: "http://localhost:8080/api/item",
            contentType: "application/json",
            data: JSON.stringify({
                id: itemId,
                name: itemName,
                qty: itemQty,
                price: itemPrice
            }),
            success: function (data) {
                loadItemTable();
                resetFld();
            },
            error: function (error) {
                console.error(error);
                if (error.status === 404) {
                    alert("Item Not Found");
                } else {
                    alert("Something went wrong");
                }
            }
        });
    } else {
        alert("Please fill the required fields");
    }
});

function resetFld() {
    $("#itemId").val("");
    $("#itemName").val("");
    $("#qtyOnHand").val("");
    $("#price").val("");

    $("#item-code").val("");
    $("#item-name").val("");
    $("#item-qty").val("");
    $("#item-Price").val("");

    $("#itemName").css("border", "1px solid #dee2e6");
    $("#qtyOnHand").css("border", "1px solid #dee2e6");
    $("#price").css("border", "1px solid #dee2e6")

    $("#item-code").css("border", "1px solid #dee2e6");
    $("#item-name").css("border", "1px solid #dee2e6");
    $("#item-qty").css("border", "1px solid #dee2e6");
    $("#item-Price").css("border", "1px solid #dee2e6");

    $('#itemName').prop("disabled", true);
    $('#qtyOnHand').prop("disabled", true);
    $('#price').prop("disabled", true);
}

$('#deleteBtn').on('click', function () {
    const response = confirm("Are you sure you want to delete this item?");
    if (!response) {
        return;
    }
    let itemId = $("#itemId").val().toLowerCase();
    $.ajax({
        method: "DELETE",
        headers: {
            Origin: 'http://localhost:5000/itemForm.html'
        },
        url: "http://localhost:8080/api/item?id=" + itemId,
        contentType: "application/json",
        success: function (response) {
            loadItemTable();
            resetFld();
        },
        error: function (error) {
            console.error(error);
            switch (error.responseText) {
                case '11000':
                    alert("Item ID already exists");
                    break;
                default:
                    alert("Something went wrong");
                    break;
            }
        }
    });
});
$('#clearBtn').on('click', function () {
    resetFld();
});

function validateQty(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css('border', '1px solid #dee2e6');
        return false;
    }
    if (qty($(fld).val())) {
        $(fld).css('border', '2px solid green');
        return true;
    } else {
        $(fld).css('border', '2px solid red');
        return false;
    }
}

function validateItemName(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css('border', '1px solid #dee2e6');
        return false;
    }
    if (text($(fld).val())) {
        $(fld).css('border', '2px solid green');
        return true;
    } else {
        $(fld).css('border', '2px solid red');
        return false;
    }
}

function validatePrice(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css('border', '1px solid #dee2e6');
        return false;
    }
    if (value($(fld).val())) {
        $(fld).css('border', '2px solid green');
        return true;
    } else {
        $(fld).css('border', '2px solid red');
        return false;
    }
}

function validateId(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css('border', '1px solid #dee2e6');
        return false;
    }
    if (iid($(fld).val())) {
        $(fld).css('border', '2px solid green');
        return true;
    } else {
        $(fld).css('border', '2px solid red');
        return false;
    }
}

function loadItemTable() {
    $('#tblItem').empty();
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/item?volume=all",
        async: true,
        success: function (response) {
            response = JSON.parse(response);
            response.forEach(function (item) {
                let row = `<tr class="table_row" data-id="${item.id.toUpperCase()}" data-name="${item.name}" data-price="${item.price}" data-qty="${item.qty}">
                                <td>${item.id.toUpperCase()}</td>
                                <td>${item.name}</td>
                                <td>${item.qty}</td>
                                <td>${item.price}</td>
                            </tr>`;
                $('#tblItem').append(row);
                $("#tblItem tr").on("click", function () {
                    const row = $(this);
                    const response = {
                        id: row.data("id"),
                        name: row.data("name"),
                        qty: row.data("qty"),
                        price: row.data("price")
                    }
                    setFld(response)
                });
            });
        },
        error: function (error) {
            console.error(error);
        }
    });
}

$('#inputItemSearch').on('keyup', function () {
    validateId("#inputItemSearch");
});
loadItemTable()