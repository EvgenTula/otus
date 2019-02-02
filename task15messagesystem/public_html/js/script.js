var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/dbwebsocket");
    ws.onopen = function (event) {

    }

    ws.onmessage = function (event) {
        var userList = JSON.parse(event.data);
        var tbl = document.getElementById("tbl");
        for (var i in userList) {
            var row = tbl.insertRow(tbl.rows.length);
            row.innerHTML = "<td>"+userList[i].id+"</td><td>"+userList[i].name+"</td><td>"+userList[i].age+"</td>";
        }
        document.getElementById("user_count").innerHTML = "user count = " + (tbl.rows.length - 1);
    }

    ws.onclose = function (event) {

    }
};

function sendMessage() {
/*
[{
	"name": "test1 hibernate",
	"age": 1,

	"address": {
		"street": "Street 1",
		"id": 1
	},
	"phoneList": [{
		"number": "1111",
		"id": 1
	}, {
		"number": "2222",
		"id": 2
	}, {
		"number": "3333",
		"id": 3
	}],

	"id": 1
}]
*/
/*
    <p>id: <input type="hidden" name="id"  value = ${user.id}></p>
    <p>Name: <input type="text" name="name"  value = "${user.name!}"></p>
    <p>Age: <input type="text" name="age"  value = ${user.age!}></p>
    <p>Address <input type="text" name="address"  value = "${user.getAddress()!}"></p>
    <p>Phone <input type="text" multiple="multiple" name="phone"  value = ${user.printPhoneList()!}></p>
*/

    var userId = document.getElementById("id");
    var userName = document.getElementById("name");
    var userAge = document.getElementById("age");
    var userAddress = document.getElementById("address");
    var userPhone = document.getElementById("phone");
    var message = userName.value + " _ " + userAge.value + " _ " + userAddress.value + " _ " + userPhone.value;
    alert(message)
    ws.send(message);
    history.back();
}