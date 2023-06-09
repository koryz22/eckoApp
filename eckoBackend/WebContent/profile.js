
/**
 * Handle the data returned by profile
 * @param resultDataString jsonObject, consists of session info
 */
function handleSessionData(resultDataString) {
    let resultDataJson = JSON.parse(resultDataString);

    console.log("profilejs");

    console.log(resultDataString);
    // show the session information
    $("#user_records").text("Name: " + resultDataJson["first_name"] +" "+ resultDataJson["last_name"] +"\n" +
        "");


}


$.ajax("api/profile", {
    method: "GET",
    success: handleSessionData
});


