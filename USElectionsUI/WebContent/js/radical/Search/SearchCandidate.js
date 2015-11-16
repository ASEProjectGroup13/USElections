/**
 * Created by Venu on 11/15/15.
 */

var alertStatus = false;
$(document).ready(function(){

    /* "Encountered a problem fetching data from Twitter.\n"
     + "Check the twitter handle you entered to ensure that it is spelled correctly.\n\n"
     + "Additionally, some handles are set to private and can't be accessed without permission."; */
    var querystring = window.location.search;
    if(querystring.indexOf('errorcode-1') > -1) {
        setAlert("Invalid input: Make sure the twitter handle you're searching for exists and is not set to private.");
    }
    else if(querystring.indexOf('errorcode215') > -1) {
        setAlert("Autorization rejected: Make sure your twitter and klout API keys are correct.");
    }
    else if(querystring.indexOf('errorcode99') > -1) {
        setAlert("Encountered a problem fetching data from Twitter.")
    }
});
// Displays an alert underneath the form.
function setAlert1 (innerText) {
    if (alertStatus == false) {
        var iDiv = document.createElement('div');
        iDiv.id = 'alert';
        iDiv.className = 'alert alert-danger alert-dismissable';
        iDiv.innerHTML = innerText;
        document.getElementById('search').appendChild(iDiv);
        alertStatus = true;
    }
}

// Checks input for special characters and disables submit if invalid input is detected.
function checkHandle1() {
    var submit = document.getElementById('analyze');
    var handle = document.getElementById('textHandle');
    if (handle.value.match(/[^A-Z0-9_]+/i)) {
        submit.disabled=true;
        $('form :input').on("keyup keypress", function(e) {
            if (e.keyCode  == 13)
                return false;
        });
        setAlert("Invalid Character.")
    }
    else {
        submit.disabled=false;
        document.getElementById('alert').remove();
        alertStatus = false;
    }
}
