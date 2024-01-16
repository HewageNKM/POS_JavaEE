$('#CbtnSaveCustomer').on('click', function (event) {
    if (validateCId('#CcustomerId') & validateName('#CcustomerName') & validateAddress('#CcustomerAddress') & validateSalary('#CcustomerSalary')) {
        const customerID = $('#CcustomerId').val().toLowerCase();
        const customerName = $('#CcustomerName').val();
        const customerAddress = $('#CcustomerAddress').val();
        const customerSalary = parseInt($('#CcustomerSalary').val());

        const customer = {
            id: customerID,
            name: customerName,
            address: customerAddress,
            salary: customerSalary
        };
        console.log(customer);
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/customer',
            headers: {
                Origin: 'http://localhost:5000'
            },
            data: JSON.stringify(customer),
            contentType: 'application/json',
            success: function (response) {
                loadCustomerTable();
                $('#CcustomerId').val("");
                $('#CcustomerName').val("");
                $('#CcustomerAddress').val("");
                $('#CcustomerSalary').val("");

                $('#CcustomerId').css("border", "#dee2e6");
                $('#CcustomerName').css("border", "#dee2e6");
                $('#CcustomerAddress').css("border", "#dee2e6");
                $('#CcustomerSalary').css("border", "#dee2e6");
            },
            error: function (err) {
                console.error(err);
                switch (err.responseText) {
                    case '11000':
                        alert("Customer ID already exists");
                        break;
                    default:
                        alert("Something went wrong");
                        break;
                }
            }
        });
    } else {
        alert("Invalid Details");

    }
});
$("#CbtnSearchButton").on('click', function (event) {
    if (validateCId('#CinputCusSearch')) {
        const id = $('#CinputCusSearch').val().toLowerCase();
        let val = $('#CcusCombo').val();
        switch (val) {
            case 'id':
                $.ajax({
                    url: "http://localhost:8080/api/customer?id="+id+"&volume=single",
                    headers: {
                        Origin: 'http://localhost:5000'
                    },
                    type: "GET",
                    success: function (response) {
                        let parse = JSON.parse(response);
                        console.log(parse);
                        $('#CId').val(parse.id.toUpperCase());
                        $('#Cname').val(parse.name);
                        $('#Caddress').val(parse.address);
                        $('#Csalary').val(parse.salary);

                        $('#Cid').prop("disabled", false);
                        $('#Cname').prop("disabled", false);
                        $('#Caddress').prop("disabled", false);
                        $('#Csalary').prop("disabled", false);
                    },
                    error: function (error) {
                        if (error.status === 404) {
                            alert("Customer Not Found");
                            return;
                        }else {
                            alert("Error While Getting Customer Details");
                        }
                    }
                });
                break;
            default:
                alert("Error While Getting Customer Details");
        }
    } else {
        alert("Invalid Customer ID")
    }
});
$('#clearBtn').on('click', function (event) {
    $('#CId').val("");
    $('#Cname').val("");
    $('#Caddress').val("");
    $('#Csalary').val("");

    $('#CId').prop("disabled", true);
    $('#Cname').prop("disabled", true);
    $('#Caddress').prop("disabled", true);
    $('#Csalary').prop("disabled", true);
});

function loadCustomerTable() {
    $("#CtblCustomer").empty();
    $.ajax({
        url: "http://localhost:8080/api/customer?volume=all",
        type: "GET",
        success: function (response) {
            response = JSON.parse(response);
            response.forEach(function (customer) {
                let row = `<tr class="table_row" data-id="${customer.id.toUpperCase()}" data-name="${customer.name}" data-address="${customer.address}" data-salary="${customer.salary}">
                                <td>${customer.id.toUpperCase()}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.salary}</td>
                            </tr>`;
                $('#CtblCustomer').append(row);
                $("#CtblCustomer tr").on("click", function () {
                    const row = $(this);
                    $('#CId').val(row.data("id"));
                    $('#Cname').val(row.data("name"));
                    $('#Caddress').val(row.data("address"));
                    $('#Csalary').val(row.data("salary"));

                    $('#Cid').prop("disabled", false);
                    $('#Cname').prop("disabled", false);
                    $('#Caddress').prop("disabled", false);
                    $('#Csalary').prop("disabled", false);
                });
            });
        },
        error: function (error) {
            console.error(error);
            alert(error.responseText)
        }
    });
}

$('#deleteBtn').on('click', function (event) {
    let response = confirm("Are you sure you want to delete this customer?").valueOf();
    if (!response) {
        return;
    }
    if (cid($('#CId').val())) {
        $.ajax({
            type: 'DELETE',
            headers: {
                Origin: 'http://localhost:5000'
            },
            url: 'http://localhost:8080/api/customer?id=' + $('#CId').val().toLowerCase(),
            success: function (response) {
                loadCustomerTable();
                $('#CId').val("");
                $('#Cname').val("");
                $('#Caddress').val("");
                $('#Csalary').val("");

                $('#CId').prop("disabled", true);
                $('#Cname').prop("disabled", true);
                $('#Caddress').prop("disabled", true);
                $('#Csalary').prop("disabled", true);

                $('#Cname').css("border", "none");
                $('#Caddress').css("border", "none");
                $('#Csalary').css("border", "none");
            },
            error: function (err) {
                if (err.status === 404) {
                    alert("Customer Not Found");
                    return;
                }else {
                    alert("Error While Deleting Customer");
                }
            }
        });
    } else {

    }
});
$('#updateBtn').on('click', function (event) {
    let response = confirm("Are you sure you want to update this customer?").valueOf();
    if (!response) {
        return;
    }
    if (validateName('#Cname') & validateAddress('#Caddress') & validateSalary('#Csalary')) {
        const customerID = $('#CId').val().toLowerCase();
        const customerName = $('#Cname').val();
        const customerAddress = $('#Caddress').val();
        const customerSalary = parseInt($('#Csalary').val());

        $('#CId').val("");
        $('#Cname').val("");
        $('#Caddress').val("");
        $('#Csalary').val("");
        const customer = {
            id: customerID,
            name: customerName,
            address: customerAddress,
            salary: customerSalary
        };
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/api/customer',
            headers: {
                Origin: 'http://localhost:5000'
            },
            data: JSON.stringify(customer),
            contentType: 'application/json',
            success: function (response) {
                loadCustomerTable();
                $('#CId').prop("disabled", true);
                $('#Cname').prop("disabled", true);
                $('#Caddress').prop("disabled", true);
                $('#Csalary').prop("disabled", true);

                $('#Cname').css("border", "1px solid #dee2e6");
                $('#Caddress').css("border", "1px solid #dee2e6");
                $('#Csalary').css("border", "1px solid #dee2e6");
            },
            error: function (err) {
                console.error(err);
                alert(err.responseText);
            }
        });
    } else {
        alert("Invalid Details");

    }
});
$("#CinputCusSearch").on('keyup', function (event) {
    validateCId('#CinputCusSearch');
});
$('#CcustomerId').on('keyup', function (event) {
    validateCId('#CcustomerId');
});
$('#CcustomerName').on('keyup', function (event) {
    validateName('#CcustomerName');
});
$('#CcustomerAddress').on('keyup', function (event) {
    validateAddress('#CcustomerAddress');
});
$('#CcustomerSalary').on('keyup', function (event) {
    validateSalary('#CcustomerSalary');
});
$('#Cname').on('keyup', function (event) {
    validateName('#Cname');
});
$('#Caddress').on('keyup', function (event) {
    validateAddress('#Caddress');
});
$('#Csalary').on('keyup', function (event) {
    validateSalary('#Csalary');
});

function validateCId(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css("border", "#dee2e6");
        return false;
    }

    if (cid($(fld).val())) {
        $(fld).css("border", "3px solid green");
        return true;
    } else {
        $(fld).css("border", "3px solid red");
        return false;
    }
}

function validateName(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css("border", "#dee2e6");
        return false;
    }

    if (text($(fld).val())) {
        $(fld).css("border", "3px solid green");
        return true;
    } else {
        $(fld).css("border", "3px solid red");
        return false;
    }
}

function validateAddress(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css("border", "#dee2e6");
        return false;
    }

    if (address($(fld).val())) {
        $(fld).css("border", "3px solid green");
        return true;
    } else {
        $(fld).css("border", "3px solid red");
        return false;
    }
}

function validateSalary(fld) {
    if ($(fld).val().trim().length === 0) {
        $(fld).css("border", "#dee2e6");
        return false;
    }

    if (value($(fld).val())) {
        $(fld).css("border", "3px solid green");
        return true;
    } else {
        $(fld).css("border", "3px solid red");
        return false;
    }
}
loadCustomerTable();
