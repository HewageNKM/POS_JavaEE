$("#form").on("submit", function (event) {
   let b = confirm("Are you sure you want to submit?");
    if (b === false) {
        return
    }
    event.preventDefault();
    let data = new FormData(event.target);
    data = {
        userName: data.get("userName").toLowerCase(),
        password: data.get("password")
    };
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/api/login",
        data: JSON.stringify(data),
        success: function (data, textStatus, http) {
            console.log(http.status);
            http.status === 200 ? alert("Successfully Created") : alert("Invalid");
        }, error: function (error) {
            alert("Error");
        }
    });
});