
/**
 * Handle the data returned by IndexServlet
 * @param resultDataString jsonObject, consists of session info
 */
function handleSessionData(resultDataString) {
    let resultDataJson = JSON.parse(resultDataString);

    console.log("mainPagejs");
    console.log("resultDataString: " + resultDataString);
    //parsing results
    let html = "";

    for(let i = 0; i < resultDataJson.length;i++){
        html +=" <ol>" + resultDataJson[i]["date"] +"</ol>";
        html +=" <ol>" + resultDataJson[i]["ls_score"] +"</ol>";
        html +=" <ol>" + resultDataJson[i]["food_score"] +"</ol>";
        html +=" <ol>" + resultDataJson[i]["exercise_score"] +"</ol>";
        html +=" <ol>" + resultDataJson[i]["sleep_score"] +"</ol>";
        html += "<br>";
    }
    // show passed values
    $("#passedInfo").append(html);

}


$.ajax("api/mainPage?UserId=100000008", {
    method: "GET",
    success: handleSessionData
});


