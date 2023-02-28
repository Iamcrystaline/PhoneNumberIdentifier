function makeAPICall() {
    let phoneNumber = document.getElementById("phoneNumber").value;
    if (phoneNumber.charAt(0) === '+') {
        phoneNumber = phoneNumber.substring(1);
    }
    jQuery(document).ready(function () {
        jQuery.ajax({
            url: 'http://localhost:8088/api/v1/countryPhoneCode?phoneNumber=' + phoneNumber,
            type: "GET",
            success: function (result) {
                document.getElementById("response").innerHTML = result;
            },
            error: function (xhr, status, error) {
                console.log(error);
                let errorInfo = JSON.parse(xhr.responseText)
                document.getElementById("response").innerHTML = errorInfo.message;
            }
        })
    })
}