
class device {
    constructor() {
        this.devEui;
        this.devName;
        this.ABP = {
            devAddress: undefined,
            appsKey: undefined,
            nwksKey: undefined
        };
        this.OTAA = {
            appEui: undefined,
            appKey: undefined
        };
        this.frequencyPlan = {
            freq4: 864100000,
            freq5: 864300000,
            freq6: 864500000,
            freq7: 864700000,
            freq8: 864900000
        };
        this.channelMask = {
            channel1En: true,
            channel2En: true,
            channel3En: true,
            channel4En: true,
            channel5En: true,
            channel6En: true,
            channel7En: true,
            channel8En: true,
            channel9En: false,
            channel10En: false,
            channel11En: false,
            channel12En: false,
            channel13En: false,
            channel14En: false,
            channel15En: false,
            channel16En: false,
        };
        this.position = {
            longitude: 0,
            latitude: 0,
            altitude: 0
        };
        this.class = 'CLASS_A';
        this.rxWindow=1;
        this.delayRx1=1;
        this.delayJoin1=5;
        this.drRx2=0;
        this.freqRx2 = 869100000;
        this.adr;
        this.preferDr=5;
        this.preferPower=14;
        this.fcnt_up;
        this.fcnt_down;
        this.last_data_ts;
        this.lastRssi;
        this.lastSnr;
        this.totalNum;
        this.reactionTime;
        this.useDownlinkQueueClassC = false;
        this.serverAdrEnable = true;
    }
    valid_data() {

        if (this.valid_reg_data() && ((this.valid_clean_ABP() || this.valid_clean_OTAA()) || (!this.valid_clean_OTAA() && !this.valid_clean_ABP()))) {
            if ((valid.valid(this.devName) || this.devName == undefined || this.devName == "") && valid.devEui(this.devEui) && valid.device_class(this.class)) {
                if ((valid.isNumber(this.position.longitude) || this.position.longitude == 0 || this.position.longitude == "") && (valid.isNumber(this.position.latitude) || this.position.latitude == 0 || this.position.latitude == "") && (valid.isNumber(this.position.altitude) || this.position.altitude == 0 || this.position.altitude == "")) {
                    var ok = true;
                    for (var key in this.frequencyPlan) {
                        if (!valid.isFrequency(this.frequencyPlan[key])) {
                            ok = false;
                            break;
                        }
                    }
                    if (!ok) {
                        console.log('Error  frequencyPlan');
                        return false;
                    }
                    else {
                        if (valid.isBool(this.useDownlinkQueueClassC) && valid.isBool(this.serverAdrEnable) && valid.oneortwo(this.rxWindow) && valid.num1_15(this.delayRx1) && valid.num1_15(this.delayJoin1) && valid.num0_5(this.drRx2) && valid.preferPower(this.preferPower)) {

                            return true;

                        }
                        else {
                            console.log('Error информации для добавления');
                            return false;
                        }
                    }
                }
                else {
                    console.log('Error  position');
                    return false;
                }
            }
            else {
                console.log('Error main setting');
                return false;
            }

        }
        else {
            console.log('Error в регистрационной информации');
            return false;
        }
    }
    frequency_change(num) {
        var frequencyPlan = this.frequencyPlan;
        if (num = 'pattern') {
            for (var key in frequencyPlan) {
                var num = key.replace('freq', '');
                var freq = frequencyPlan[key];
                if (freq == 0) {
                    this.channelMask['channel' + num + 'En'] = false;
                }
                else {
                    this.channelMask['channel' + num + 'En'] = true;
                }
            }
        }
        else {
            var freq = frequencyPlan['freq' + num];
            if (freq == 0) {
                this.channelMask['channel' + num + 'En'] = false;
            }
        }
    }

    valid_clean_ABP() {
        if ((this.ABP.devAddress == "" || this.ABP.devAddress == undefined) && (this.ABP.appsKey == "" || this.ABP.appsKey == undefined) && (this.ABP.nwksKey == "" || this.ABP.nwksKey == undefined)) {
            return true;
        }
        else {
            return false;
        }
    }
    valid_clean_OTAA() {
        if ((this.OTAA.appEui == "" || this.OTAA.appEui == undefined) && (this.OTAA.appKey == "" || this.OTAA.appKey == undefined)) {
            return true;
        }
        else {
            return false;
        }
    }
    valid_reg_data() {
        return (this.valid_ABP() || this.valid_OTAA()) ? true : false;
    }
    valid_ABP() {
        if (valid.devAddress(this.ABP.devAddress) && this.ABP.devAddress != '' && this.ABP.devAddress != undefined && this.ABP.appsKey != '' && this.ABP.appsKey != undefined && valid.byte16(this.ABP.appsKey) && valid.byte16(this.ABP.nwksKey) && this.ABP.nwksKey != '' && this.ABP.nwksKey != undefined) {
            return true;
        }
        else {
            return false;
        }
    }
    valid_OTAA() {
        if (valid.byte16(this.OTAA.appKey) && this.OTAA.appKey != undefined && this.OTAA.appKey != '') {
            if (valid.byte8(this.OTAA.appEui) || this.OTAA.appEui == "" || this.OTAA.appEui == undefined) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
class valid_data {
    constructor() {

    }
    device_access(str) {
        if (this.valid(str)) {
            if (str == 'FULL' || str == 'SELECTED') {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    direction(str) {
        if (this.valid(str)) {
            if (str == 'UPLINK' || str == 'DOWNLINK' || str == 'ALL') {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    oneortwo(num) {
        return (num == 1 || num == 2) ? true : false;
    }
    num1_15(num) {
        return (this.isInt(num) && (num >= 1 && num <= 15)) ? true : false;
    }
    num0_5(num) {
        return (this.isInt(num) && (num >= 0 && num <= 5)) ? true : false;
    }
    preferPower(num) {
        return (this.isInt(num)) ? true : false;
    }
    bit32(num) {
        if (num < 33554431 && num >= 0) {
            return true;
        }
        else {
            return false;
        }
    }
    isParity(num) {
        if (this.isInt(num) && num % 2 === 0) {
            return true;
        }
        else {
            return false;
        }
    }
    isData(data) {
        if (this.simbol16(data) && data.length <= 444 && this.isParity(data.length)) {
            return true;
        }
        else {
            return false;
        }
    }
    isPort(num) {
        if (this.isInt(num) && num >= 0 && num <= 255) {
            return true
        }
        else {
            return false;
        }
    }
    device_class(str) {
        if (str == "CLASS_A" || str == "CLASS_C") {
            return true;
        }
        else {
            return false;
        }
    }
    simbol16(str) {
        if (str != undefined) {
            for (var i = 0; i < str.length; i++) {
                if (!((str[i].charCodeAt() >= 48 && str[i].charCodeAt() <= 57) || (str[i].charCodeAt() >= 65 && str[i].charCodeAt() <= 70) || (str[i].charCodeAt() >= 97 && str[i].charCodeAt() <= 102))) {
                    return false;
                }

            }
            return true;
        }
        else {
            return false;
        }
    }
    devAddress(num) {
        if (this.simbol16(num)) {
            return true;
        }
        else {
            return false;
        }
    }
    byte16(str) {
        if (str != undefined && str.length == 32) {
            return this.simbol16(str);
        }
        else {
            return false;
        }
    }
    byte8(str) {
        if (str != undefined && str.length == 16) {
            return this.simbol16(str);
        }
        else {
            return false;
        }
    }
    valid(str) {
        if (str != undefined && str.length > 0) {
            return true;
        }
        else {
            return false;
        }

    }
    name(str) {
        if (this.valid(str) && str.length <= 25) {
            return true;
        }
        else {
            return false;
        }
    }
    comment(str) {
        if (this.valid(str) && str.length <= 200) {
            return true;
        }
        else {
            return false;
        }
    }
    address(str) {
        if (this.valid(str) && str.length <= 200) {
            return true;
        }
        else {
            return false;
        }
    }
    isInt(str) {
        if (this.isNumber(str) && str % 1 == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    devEui(str) {
        return (this.valid(str) && str.length == 16);
    }
    isFrequency(num) {
        if ((this.isNumber(num) && num <= 1020000000 && num >= 862000000) || num == 0 || num == '0') {
            return true;
        }
        else {
            return false;
        }
    }
    isNumber(str) {
        if (str != undefined && !isNaN(parseFloat(str)) && str !== '') {
            return true;
        }
        else {
            return false;
        }
    }
    isBool(str) {
        if (typeof (str) == "boolean") {
            return true;
        }
        else return false;
    }
    isDate(str) {
        if (str != undefined) {
            try {
                var date = Math.abs(new Date(str).getTime());
                if (date > 0 && this.isInt(date)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (err) {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
var valid = new valid_data();