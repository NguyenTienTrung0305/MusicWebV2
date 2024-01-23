function checkPasswordMatch(){

    var password = document.getElementById("password").value;
    var repeatpassword = document.getElementById("repeatpassword").value;
    var valid = document.getElementById("valid");

    if (password == repeatpassword) {
        valid.classList.remove("bi-lock");
        valid.classList.add("bi-lock-fill");
    }else{
        valid.classList.remove("bi-lock-fill");
        valid.classList.add("bi-lock");
    }
}