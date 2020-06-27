package sstu.lora.domain;

public class DevInfo {
   private String name;
    private  String deveui;
    private  String address;

    public DevInfo() {
    }

    public DevInfo(String name, String deveui, String address) {
        this.name = name;
        this.deveui = deveui;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveui() {
        return deveui;
    }

    public void setDeveui(String deveui) {
        this.deveui = deveui;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
