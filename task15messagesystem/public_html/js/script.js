var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/dbwebsocket");
    ws.onopen = function (event) {
        //ws.send("onopen");
    }

    ws.onmessage = function (event) {
        //alert(event.data);

        var $textarea = document.getElementById("messages");
        var result;
        var myList = JSON.parse(event.data);
        for (var i = 0; i < myList.length; i++) {
            var row = "<tr>"

            //for (var colIndex = 0; colIndex < columns.length; colIndex++) {
                //var cellValue = myList[i][columns[colIndex]];
                //if (cellValue == null)
                  //  cellValue = "";
                //row = row + cellValue + "<td/>";
                //row$.append($('<td/>').html(cellValue));
            //}
            result = result + "\n" + myList[i].name;
        }
        alert(result);
        $textarea.value = $textarea.value + JSON.parse(event.data) + "\n";
    }

    ws.onclose = function (event) {

    }
};

function sendMessage() {
/*
    <p>id: <input type="hidden" name="id"  value = ${user.id}></p>
    <p>Name: <input type="text" name="name"  value = "${user.name!}"></p>
    <p>Age: <input type="text" name="age"  value = ${user.age!}></p>
    <p>Address <input type="text" name="address"  value = "${user.getAddress()!}"></p>
    <p>Phone <input type="text" multiple="multiple" name="phone"  value = ${user.printPhoneList()!}></p>
*/


    var userName = document.getElementById("name");
    var userAge = document.getElementById("age");
    var userAddress = document.getElementById("age");
    var userPhone = document.getElementById("age");
    var message = userName.value + " _ " + userAge.value + " _ " + userAddress.value + " _ " + userPhone.value;
    alert(message)
    ws.send(message);
    history.back();
}