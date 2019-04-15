var APIRoot = "http://www.micro-learn.xyz/php";
var webRoot = "http://www.micro-learn.xyz";//file:///C:/Users/ariel/School/poosd/microlearning/website
var fileExtension = ".php";
var userInfo = new Object();
var userId = 0;
var username = '';
var password = '';
var corgiGlobalUrl = '';
var individualContact = null;
var data = null;
var maxNumOfArticles = 5;

// Ensures that user cannot get to home before login
$(document).ready(function() {
    var currentUrl = window.location.href;
    if (currentUrl.indexOf("home") >= 0 && window.name === '')
        window.location = webRoot + "/index.html";
} );

// Creates hide attribute for alerts
$(function(){
    $("[data-hide]").on("click", function(){
        $(this).closest("." + $(this).attr("data-hide")).hide();
    });
});

// Sets user display information in navbar
function setUserDisplay()
{
    var userInfo = window.name.split(",");
    username = userInfo[0];
    userId = userInfo[1];
    document.getElementById("userDisplay").innerHTML = username;
}

function identifyUserInterests(event)
{
    event.preventDefault();
	
	var usersInterests = [];
	var count = 0;
	var checkedValue = null;
	var interest = null;
	
	var inputElements = document.getElementsByClassName('form-check-input');
	for ( var i=0; inputElements[i]; i++ ) {
		if (inputElements[i].checked) {
			checkedValue = inputElements[i].value;
			interest.number = i;
			interest.name = checkedValue;
			usersInterests.push(interest);
			count++;
		}
	}
	
	if (count === 0) {
		return false;
	}
	
	var index = Math.floor(Math.random() * count);
	var interestCategory = usersInterests[index];
	
	var jsonSendObject = new Object();
	jsonSendObj.interest_id = interestCategory.number;
	jsonSendObj.interest_name = interestCategory.name;
	
	fetchNewArticle(jsonSendObj);
}

function fetchNewArticle(jsonSendObj)
{
	var url = APIRoot + '/fetchArticle' + fileExtension;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
    var jsonPayload = JSON.stringify(jsonSendObj);
    try
    {
        xhr.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200)
            {
                $('.alert').hide();
				var jsonArray = JSON.parse(xhr.responseText);
				document.getElementById("article-title").innerHTML = jsonArray.title;
				document.getElementById("article-summary").innerHTML = jsonArray.summary;
				document.getElementById("article-link").href = jsonArray.links;
            }
            else if (this.status != 200)
            {
                $('.alert').hide();
                $("#addContact").modal("hide");
                document.getElementById("genericErrorMessage").innerHTML = "Unable to find article.";
                $("#genericErrorAlert").show();
            }
        };
        xhr.send(jsonPayload);
    }
    catch(err)
    {
        alert(err.message);
        return 'err';
    }
}

function fetchArticle(event)
{
	event.preventDefault();
	var day = new Date();
	var today = day.getDate();
	
	var currentUser = localStorage.getItem('currentUsername');
	
	var index = checkForDuplicates(currentUser);
	
	if ( data[index].lastArticleDate !== today ) {
		data[index].numOfArticles = 0;
		data[index].lastArticleDate = today;
	}
	
	if ( data[index].numOfArticles >= maxNumOfArticles ) {
        $('.alert').hide();
        $("#articleLimitAlert").show();
        return;
	}
	
	if (receiveAlgorithm()) {
		data[index].numOfArticles++;
	}
	
	localStorage.setItem("allUsersInfo", JSON.stringify(data));
}
	
function fetchExistingUser()
{
	var index = checkForDuplicates(document.getElementById("exampleInputEmail1").value);
	if (index === -1){
		return false;
	}
	
	if(data[index].passwords === document.getElementById("exampleInputPassword1").value) {
		localStorage.setItem("currentUsername", document.getElementById("exampleInputEmail1").value);
		return true;
	}
	
	return false;
}

function storeNewUser()
{
	var index = checkForDuplicates(document.getElementById("exampleInputEmail2").value);
	if (index >= 0) {
		return false;
	}
	
	var userData = {
		usernames: document.getElementById("exampleInputEmail2").value,
		passwords: document.getElementById("exampleInputPassword2").value,
		lastArticleDate: -1,
		numOfArticles: 0
	};
	data.push(userData);
	localStorage.setItem("allUsersInfo", JSON.stringify(data));
	return true;
}

function checkForDuplicates(userName)
{
	var allUsersInfo = localStorage.getItem('allUsersInfo');
	
	// If no existing data, use the value by itself
	// Otherwise, add the new value to it
	data = allUsersInfo ? JSON.parse(allUsersInfo) : [];
	
	for (var i=0; i < data.length; i++) {
        if (data[i].usernames === userName) {
            return i;
        }
    }
	return -1;
}

function receiveAlgorithm()
{
    var usersInterests = [];
	var count = 0;
	var checkedValue = null;
	
	var inputElements = document.getElementsByClassName('form-check-input');
	for ( var i=0; inputElements[i]; i++ ) {
		if (inputElements[i].checked) {
			checkedValue = inputElements[i].value;
			usersInterests.push(checkedValue);
			count++;
		}
	}
	
	if (count === 0) {
		return false;
	}
	
	var index = Math.floor(Math.random() * count);
	var interestCategory = usersInterests[index];
	
	var url = webRoot + '/json/' + interestCategory + '.json';
	var script = document.createElement("script"); //Make a script DOM node
    	script.src = url; //Set it's src to the provided URL
   	document.head.appendChild(script);
	
	return true;
}


// Gets salt(s) for a username from the website API
function fetchUserSalts(userName, callback)
{
    var url = APIRoot + '/userGetSalt' + fileExtension;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");

    var jsonSendObject = new Object();
    jsonSendObject.username = userName;
    var jsonPayload = JSON.stringify(jsonSendObject);
    try
    {
        xhr.onreadystatechange = function()
        {
            if (this.readyState == 4 && this.status == 200)
            {
                var jsonArray = JSON.parse(xhr.responseText);
                callback(jsonArray);
            }
        };
        xhr.send(jsonPayload);
    }
    catch(err)
    {
        alert(err.message);
        return 'err';
    }
}

// Validates a new user and sends new user information to website API.
function createUser(userSalts)
{
    var url = APIRoot + '/userAdd' + fileExtension;
    var newUserName = document.getElementById("newUserName").value.trim();
    var newPassword = document.getElementById("newPassword").value.trim();

    if (newUserName.length === 0 || newPassword.length === 0)
    {
        $('.alert').hide();
        $("#invalidCreateUserAlert").show();
        return;
    }

    var salt = Math.random().toString(36).substring(0, 5) + Math.random().toString(36).substring(5, 10);
    newPassword += salt;
    var passwordHash = md5(newPassword);

    if (userSalts.length > 0)
    {
        $('.alert').hide();
        $("#duplicateUserNameAlert").show();
        return;
    }

    var jsonSendObj = new Object();
    jsonSendObj.username = newUserName;
    jsonSendObj.password_hash = passwordHash;
    jsonSendObj.password_salt = salt;
    var jsonPayload = JSON.stringify(jsonSendObj);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
    try
    {
        xhr.onreadystatechange = function()
        {
            if (this.readyState == 4 && this.status == 200)
            {
                $('.alert').hide();
                $("#registerNewUser").modal("hide");
                $("#createUserSuccessAlert").show();
            }
            else if (this.status != 200)
            {
                $('.alert').hide();
                $("#genericErrorRegisterAlert").show();
            }
          };
          xhr.send(jsonPayload);
    }
    catch(err)
    {
        alert(err.message);
        return 'err';
    }
}

// Validates login information and sends login information to website API.
// Returns valid user ID along with original info if successful.
function loginUser(userSalts)
{
    var url = APIRoot + '/login' + fileExtension;
    var userName = document.getElementById("username").value.trim();
    var passWord = document.getElementById("password").value.trim();

    if (userSalts.length === 0)
    {
        $('.alert').hide();
        $("#invalidLoginAlert").show();
        return;
    }
    salt = userSalts[0];
    passWord += salt;
    var passwordHash = md5(passWord);

    var jsonSendObj = new Object();
    jsonSendObj.username = userName;
    jsonSendObj.password_hash = passwordHash;
    var jsonPayload = JSON.stringify(jsonSendObj);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
    try
    {
        xhr.onreadystatechange = function()
        {
            if (this.readyState == 4 && this.status == 200)
            {
                var jsonObject = JSON.parse(xhr.responseText);
                if (jsonObject.username === '')
                {
                    $('.alert').hide();
                    $("#invalidLoginAlert").show();
                }
                else if (jsonObject.username !== '')
                {
                    username = jsonObject.username;
                    userId = jsonObject.user_id;
                    password = jsonObject.password_hash;
                    window.name = username + "," + userId;
                    window.location.href = webRoot + '/main.html';
                }
            }
            else if (this.status != 200)
            {
                $('.alert').hide();
                $("#genericErrorLoginAlert").show();
            }
          };
          xhr.send(jsonPayload);
    }
    catch(err)
    {
        alert(err.message);
        return 'err';
    }
}

// Begins process of creating a new user by first getting any user salts for the new username
function submitCreateUser(event)
{
    event.preventDefault();

    if (document.getElementById("exampleInputEmail2").value.length === 0 || document.getElementById("exampleInputPassword2").value.length === 0	)
    {
        $('.alert').hide();
        $("#emptyRegisterAlert").show();
        return;
    }
	
	if (!(exampleInputEmail2.checkValidity()))
    {
        $('.alert').hide();
        $("#invalidCreateUserAlert").show();
        return;
    }
	
	if (!storeNewUser())
    {
        $('.alert').hide();
        $("#duplicateUserNameAlert").show();
        return;
    }
	
	$('.alert').hide();
	document.getElementById("myForm").reset();
	$("#exampleModalCenter").modal("hide");
	$('#createUserSuccessAlert').show();

    fetchUserSalts(document.getElementById("newUserName").value, createUser);
}

// Begins process of logging in by first getting any user salts for username
function submitLoginUser(event)
{
    event.preventDefault();
	
    if (document.getElementById("exampleInputEmail1").value.length === 0 || document.getElementById("exampleInputPassword1").value.length === 0
		|| !(exampleInputEmail1.checkValidity()) || !fetchExistingUser())
    {
        $('.alert').hide();
        $("#invalidLoginAlert").show();
        return;
    }
	
	
	window.location.href = webRoot + '/main.html';//'file:///C:/Users/ariel/School/poosd/microlearning/website/main.html';
	document.getElementById("myForm").reset();
	
    fetchUserSalts(document.getElementById("username").value, loginUser);
}

// Signs out user by deleting resetting local user information and redirects to login page.
function signOut()
{
    username = '';
    userId = 0;
	localStorage.setItem("currentUsername", null);
    window.name = '';
    window.location.href = webRoot + '/index.html';//'file:///C:/Users/ariel/School/poosd/microlearning/website/index.html';
}
