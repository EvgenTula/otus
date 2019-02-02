var ws;
var COMMAND_GET_USER = "get_user";
var userList;

init = function () {
    ws = new WebSocket("ws://localhost:8090/dbwebsocket");
    ws.onopen = function (event) {

    }

    ws.onmessage = function (event) {
        userList = JSON.parse(event.data);
        var tbl = document.getElementById("tbl");
        while (tbl.rows.length > 1) {
            tbl.deleteRow(1);
        }
        for (var i in userList) {
            var row = tbl.insertRow(tbl.rows.length);
            row.innerHTML = "<td><a onClick='editRow("+ userList[i].id +");' href='#'>"+userList[i].id+"</a></td><td>"+userList[i].name+"</td><td>"+userList[i].age+"</td>";
        }
        document.getElementById("user_count").innerHTML = "user count = " + (tbl.rows.length - 1);
    }

    ws.onclose = function (event) {

    }
};

function clearField() {
    document.getElementById("id").value = 0;
    document.getElementById("name").value = "";
    document.getElementById("age").value = 0;
    document.getElementById("address").value = "";
    document.getElementById("phoneList").value = "";
};

function editRow(userId) {
     for (var i in userList) {
         if (userList[i].id == userId)
         {
             document.getElementById("id").value = userList[i].id;
             document.getElementById("name").value = userList[i].name;
             document.getElementById("age").value = userList[i].age;
             document.getElementById("address").value = userList[i].address.street;
             var phoneList = "";
             for(var phone in userList[i].phoneList)
             {
                 if (phoneList != "")
                     phoneList += ",";
                 phoneList += userList[i].phoneList[phone].number;
             }

             document.getElementById("phoneList").value = phoneList;
         }
     }
 };





function sendMessage() {
    var userId = document.getElementById("id");
    var userName = document.getElementById("name");
    var userAge = document.getElementById("age");
    var userAddress = document.getElementById("address");
    var userPhone = document.getElementById("phoneList");
    var message = "{ \"" + userId.id + "\" : \"" + userId.value + "\", \""
                        + userName.id + "\" : \"" + userName.value +  "\" , \""
                        + userAge.id + "\" : " + userAge.value;
    message += " ,  \"" + userAddress.id + "\": { \"street\" : \"" + userAddress.value + "\", \"id\" : 1 } , "
                        + "\""+userPhone.id+"\" : [";

    var phones = userPhone.value.replace(/\s+/g,"").split(",");
    var phoneListJson = "";
    for(var item in phones)
    {
        if (phoneListJson != "")
            phoneListJson += ",";
        phoneListJson += "{ \"number\" : " + phones[item] + " , \"id\" : " + item + "}";
    }
    message += phoneListJson + "] }";
    ws.send(message);
};