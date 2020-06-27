 var webSocket = new WebSocket("ws://192.168.100.120:8002");

 function authU() {
    var auth = {
        login: 'root',
        password: '123',
        cmd: 'auth_req'
    };
    if (!webSocket.readyState) {
        setTimeout(n => (webSocket.send(JSON.stringify(auth))), 10);
    }
    else {
        webSocket.send(JSON.stringify(auth))
    }

}   


function saveDevice() {
    var form = document.getElementById("deviceForm");
    var myDevice = new device();
    myDevice.devEui = form.elements["devEui"].value;
    myDevice.devName = form.elements["devName"].value;
    if (!(form.elements["devAddress"].value === '' && form.elements["appsKey"].value === '' && form.elements["nwksKey"].value === '')) {
        myDevice.ABP =
        {
            devAddress: form.elements["devAddress"].value,
            appsKey: form.elements["appsKey"].value,
            nwksKey: form.elements["nwksKey"].value
        };
    }
    else{
        delete myDevice.ABP;
    }
    if (!(form.elements["appEui"].value === '' && form.elements["appKey"].value === '')) {
        myDevice.OTAA =
        {
            appEui: form.elements["appEui"].value,
            appKey: form.elements["appKey"].value
        };
    }
    else{
        delete myDevice.OTAA;
    }
    myDevice.position =
    {
        longitude: form.elements["longitude"].value,
        latitude: form.elements["latitude"].value,
        altitude: form.elements["altitude"].value
    };
    myDevice.rxWindow = parseInt(form.elements["rxWindow"].value,2);
    myDevice.delayRx1 = parseInt(form.elements["delayRx1"].value,2);
    myDevice.drRx2 = parseInt(form.elements["drRx2"].value,2);
    myDevice.preferDr = parseInt(form.elements["preferDr"].value,2);
    myDevice.preferPower = parseFloat(form.elements["preferPower"].value);
    myDevice.serverAdrEnable = form.elements["serverAdrEnable"].value;
    // myDevice.valid_data();
    var jsonMess =
    {
        cmd: "manage_devices_req",
        devices_list: [myDevice]
    };
    console.log(myDevice);

    console.log(JSON.stringify(jsonMess));
    webSocket.send(JSON.stringify(jsonMess));
}
  webSocket.onmessage = function (event) {
    console.log(event.data);
}   