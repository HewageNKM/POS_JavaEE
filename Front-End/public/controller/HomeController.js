function loadOrderTable() {
    $.ajax({
        url: 'http://localhost:8080/api/order?volume=all',
        headers:{
          Origin: 'http://localhost:5000/homeForm.html'
        },
        method: 'GET',
        success: function (data) {
            data = JSON.parse(data);
            $('#orderTable').empty();
            data.forEach(function (order) {
                let date = new Date(order.date.year, order.date.month - 1, order.date.day).toLocaleDateString();
                console.log(order.date);
                let discount = order.discount == null ? 0 : order.discount;
                let row = `<tr class="table_row" data-id="${order.id.toUpperCase()}" data-discount="${discount}" data-date="${date}" data-total="${order.order_total} " data-name="${order.customer_id}">
                                <td>${order.id.toUpperCase()}</td>
                                <td>${date}</td>
                                <td>${order.customer_id}</td>
                                <td>${discount}</td>
                                <td>${order.order_total}</td>
                            </tr>`;
                $('#orderTable').append(row);
                $("#orderTable tr").on("click", function () {
                    const row = $(this);
                    $('#orderId').val(row.data("id"));
                    $('#OrderDate').val(row.data("date"));
                    $('#customerName').val(row.data("name"));
                    $('#discount').val(row.data("discount"));
                    $('#cost').val(row.data("total"));
                });
            });
        }
    });

}

$('#clearBtn').on('click', function (event) {
    $('#orderId').val("");
    $('#OrderDate').val("");
    $('#customerName').val("");
    $('#discount').val("");
    $('#cost').val("");
});
$('#deleteBtn').on('click', function (event) {
    const value = confirm("Are you sure you want to delete this order?");
    if (!value) {
        return;
    }
    if (($('#orderId').val().trim().length > 0)) {
        $.ajax({
            type: 'DELETE',
            headers: {
                Origin: 'http://localhost:5000/homeForm.html'
            },
            url: 'http://localhost:8080/api/order?id=' + $('#orderId').val().toLowerCase(),
            success: function (response) {
                loadOrderTable();
                alert("Order Deleted");
            },
            error: function (error) {
                console.error(error);
                alert(error.responseText)
            }
        });
    } else {
        alert("Please select an order to delete");
    }
});

$('#searchBtn').on('click', function (event) {
    if (($('#searchFld').val().trim().length >= 5)) {
        $.ajax({
            url: 'http://localhost:8080/api/order?volume=single&id='+$('#searchFld').val().trim().toLowerCase(),
            headers:{
                Origin: 'http://localhost:5000/homeForm.html'
            },
            method: 'GET',
            success: function (data) {
                data = JSON.parse(data);
                $('#orderTable').empty();
                data.forEach(function (order) {
                    let date = new Date(order.date.year, order.date.month - 1, order.date.day).toLocaleDateString();
                    console.log(order.date);
                    let discount = order.discount == null ? 0 : order.discount;
                    let row = `<tr class="table_row" data-id="${order.id.toUpperCase()}" data-discount="${discount}" data-date="${date}" data-total="${order.order_total} " data-name="${order.customer_id}">
                                <td>${order.id.toUpperCase()}</td>
                                <td>${date}</td>
                                <td>${order.customer_id}</td>
                                <td>${discount}</td>
                                <td>${order.order_total}</td>
                            </tr>`;
                    $('#orderTable').append(row);
                    $("#orderTable tr").on("click", function () {
                        const row = $(this);
                        $('#orderId').val(row.data("id"));
                        $('#OrderDate').val(row.data("date"));
                        $('#customerName').val(row.data("name"));
                        $('#discount').val(row.data("discount"));
                        $('#cost').val(row.data("total"));
                    });
                });
            },error: function (error) {
                if (error.status === 404) {
                    alert("Order not found");
                } else {
                    alert("Error Occurred");
                }
            }
        });
    } else {
        alert("Invalid Order Id")
    }
});
$("#searchFld").on("keyup", function () {
    if($('#searchFld').val().trim() === ''){
        loadOrderTable();
    }
});
loadOrderTable();