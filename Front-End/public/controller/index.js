$("#form").on("submit", function (event) {
    event.preventDefault();
    const data = new FormData(event.target);
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/login?userName=" + data.get("userName").toLowerCase() + "&password=" + data.get("password"),
        success: function (data, textStatus, http) {
            console.log(http.status);
            http.status === 200 ? window.location.replace("public/views/homeForm.html") : alert("Invalid Credentials");
        }, error: function (error) {
            alert("Invalid Credentials");
        }
    });
});