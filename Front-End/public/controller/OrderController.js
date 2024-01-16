let customers = [];
let items = [];
let qtyOnHand = 0;
let orderItems = [];
let subTotal = 0;
let id = '';

function loadOrderId() {
    const randomID = Math.random().toString(36).substring(2, 10);
    orderID = `OD${randomID}`;
    $('#orderId').val(orderID.toUpperCase());
    id = orderID.toLowerCase();
}

function loadIds() {
    const today = new Date().toISOString().split('T')[0];
    $('#OrderDate').val(today)
    $.ajax({
        url: 'http://localhost:8080/api/item?volume=all',
        headers: {
            Origin: 'http://localhost:5000/orderForm.html'
        },
        type: 'GET',
        success: function (data) {
            console.log(data);
            data = JSON.parse(data);
            items = data;
            data.forEach(function (item) {
                $('#itemId').append('<option value="' + item.id + '">' + item.id.toUpperCase() + '</option>');
            });
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    })
    $.ajax({
        url: 'http://localhost:8080/api/customer?volume=all',
        type: 'GET',
        headers: {
            Origin: 'http://localhost:5000/orderForm.html'
        },
        success: function (data) {
            console.log(data);
            data = JSON.parse(data);
            customers = data;
            data.forEach(function (customer) {
                $('#customerId').append('<option value="' + customer.id + '">' + customer.id.toUpperCase() + '</option>');
            });
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    })
}

$('#customerId').on('change', function () {
    const customerId = $('#customerId').val();
    customers.forEach(function (customer) {
        if (customerId === customer.id) {
            $("#customerName").val(customer.name);
            $('#address').val(customer.address);
            $('#salary').val(customer.salary);
        } else if (customerId === 'CXXXX') {
            $("#customerName").val('');
            $('#address').val('');
            $('#salary').val('');
        }
    });
});
$('#itemId').on('change', function () {
    const itemId = $('#itemId').val();
    items.forEach(function (item) {
        if (itemId === item.id) {
            $("#item").val(item.name);
            $('#price').val(item.price);
            $('#qtyOnHand').val(item.qty);
            qtyOnHand = item.qty;
        } else if (itemId === 'TXXXX') {
            $("#item").val('');
            $('#price').val('');
            $('#qtyOnHand').val('');
        }
    });
});
$('#orderQty').on('keyup', function () {
    if ($('#itemId').val() === 'TXXXX') {
        alert('Please Select an Item');
        $('#orderQty').val('');
        $('#orderQty').focus();
    } else {
        const orderQty = $('#orderQty').val();
        if (orderQty.trim().length === 0) {
            $('#qtyOnHand').val(qtyOnHand);
        }
        if (parseInt(qtyOnHand) < parseInt(orderQty)) {
            alert('Order Qty is greater than Qty On Hand');
            $('#orderQty').focus();
            $('#orderQty').val('');
            $('#qtyOnHand').val(qtyOnHand);
        } else {
            $('#qtyOnHand').val(qtyOnHand - orderQty);
        }
    }
})
$('#addBtn').on('click', function () {
    if ($('#customerId').val() === 'CXXXX') {
        alert('Please Select a Customer');
        return;
    }
    if (($('#itemId').val() === 'TXXXX') || ($('#orderQty').val() === '') || ($('#itemId').val() === 'TXXXX')) {
        alert('Invalid Order Details');
    } else {
        const itemId = $('#itemId').val();
        const item = $('#item').val();
        const orderQty = parseInt($('#orderQty').val());
        const price = parseFloat($('#price').val());
        const total = orderQty * price;
        const row = '<tr><td>' + itemId.toUpperCase() + '</td><td>' + item + '</td><td>' + price + '</td><td>' + orderQty + '</td><td>' + total + '</td></tr>';
        orderItems.push({
            item_id: itemId.toLowerCase(),
            item_name: item,
            item_price: price,
            item_qty: orderQty,
            item_total: total
        });
        $('#cartTable').append(row);
        $('#itemId').val('TXXXX');
        $('#item').val('');
        $('#orderQty').val('');
        $('#price').val('');
        $('#qtyOnHand').val('');
        $('#itemId').focus();
        items.forEach(function (item) {
            if (item.id === itemId.toLowerCase()) {
                item.qty = item.qty - orderQty;
            }
        });
        if ($('#customerId').val() !== 'CXXXX') {
            $('#customerId').attr('disabled', true);
        }
        subTotal = subTotal + total;
        $('#total').val(subTotal);

        if ($('#discount').val().trim().length === 0) {
            $('#subTotal').val(subTotal);
        } else {
            const discount = $('#discount').val();
            const netTotal = subTotal - (subTotal * discount / 100);
            $('#subTotal').val(netTotal);
        }
    }
});
$('#discount').on('keyup', function () {
    if (($('#discount').val() < 0) || ($('#discount').val() > 100)) {
        alert('Invalid Discount');
        $('#discount').val('');
        $('#discount').focus();
        $('#subTotal').val(subTotal);
        return;
    }
    if ($('#discount').val().trim().length === 0) {
        $('#subTotal').val(subTotal);
        const balance = $('#cash').val() - $('#subTotal').val();
        $('#balance').val(balance);
    } else {
        const discount = $('#discount').val();
        const netTotal = subTotal - (subTotal * discount / 100);
        $('#subTotal').val(netTotal);
        const balance = $('#cash').val() - $('#subTotal').val();
        $('#balance').val(balance);
    }
});
$('#cash').on('keyup', function () {
    if ($('#subTotal').val() < 0) {
        alert('Invalid Cash');
        return;
    }
    const balance = $('#cash').val() - $('#subTotal').val();
    $('#balance').val(balance);
});
$('#purchaseBtn').on('click', function () {
    if ($('#balance').val() < 0) {
        alert('Invalid Cash');
        return;
    }
    const res = confirm('Do you want to purchase this order?');
    if (res) {
        const order = {
            id: id.toLowerCase(),
            customer_id: $('#customerId').val().toLowerCase(),
            items: orderItems,
            order_total: parseFloat($('#subTotal').val()),
            discount: parseFloat($('#discount').val()),
        }
        console.log(JSON.stringify(order));
        $.ajax({
            url: 'http://localhost:8080/api/order',
            headers: {
                Origin: 'http://localhost:5000/orderForm.html'
            },
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(order),
            success: function (data) {
                alert('Order Placed Successfully');
                $('#customerId').val('CXXXX');
                $('#customerName').val('');
                $('#address').val('');
                $('#salary').val('');
                $('#itemId').val('TXXXX');
                $('#item').val('');
                $('#orderQty').val('');
                $('#price').val('');
                $('#qtyOnHand').val('');
                $('#cartTable').empty();
                $('#total').val('');
                $('#discount').val('');
                $('#subTotal').val('');
                $('#cash').val('');
                $('#balance').val('');
                $('#customerId').attr('disabled', false);
                $('cartTable').empty();
                subTotal = 0;
                orderItems = [];

                loadOrderId();
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        })
    }
});
loadIds();
loadOrderId();