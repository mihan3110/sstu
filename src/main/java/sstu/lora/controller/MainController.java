package sstu.lora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sstu.lora.dao.DbConnection;
import sstu.lora.domain.DevInfo;
import sstu.lora.domain.Role;
import sstu.lora.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/spaces")
    public String main(@AuthenticationPrincipal User user, Map<String, Object> model) throws SQLException {
        Map<Long, String> spaces = DbConnection.getSpaces(user.getId());
        model.put("spaces", spaces);
        return "spaces";
    }


    @PostMapping("/spaces")
    public String addSpace(@AuthenticationPrincipal User user,
                           @RequestParam String name, Map<String, Object> model) throws SQLException {
        if (DbConnection.checkpace(name, user.getId())) {
            DbConnection.addSpace(user.getId(), name);
        }
        Map<Long, String> spaces = DbConnection.getSpaces(user.getId());
        model.put("spaces", spaces);
        return "spaces";
    }

    @GetMapping("/spaces/{idspace}")
    public String getDev(@AuthenticationPrincipal User user, @RequestParam Long idspace, Model model) throws SQLException {
        ArrayList<DevInfo> devices = DbConnection.getDevices(user.getId(), idspace);
        model.addAttribute("devices", devices);
        return "/devices";
    }


    @PostMapping("/spaces/{idspace}")
    public String addDev(@AuthenticationPrincipal User user, @RequestParam String name, @RequestParam String address, @RequestParam Long idspace, Model model) throws SQLException {
        if (DbConnection.checkdevice(user.getId(), name)) {
            DbConnection.addDevice(user.getId(), idspace, name, address);

        }
        ArrayList<DevInfo> devices = DbConnection.getDevices(user.getId(), idspace);
        model.addAttribute("devices", devices);
        return "/devices";
    }

    @GetMapping("/spaces/delete")
    public String deleteSpace(@RequestParam Long idspace) {
        DbConnection.deleteSpace(idspace);
        return "redirect:/spaces";
    }

    @GetMapping("/devices/delete")
    public String deleteDevice(@AuthenticationPrincipal User user, @RequestParam String deveui) {
        System.out.println(111);

        DbConnection.deleteDevice(deveui, user.getId());
        return "redirect:/spaces";
    }

    @GetMapping("/devices/{deveui}")
    public String getData(@RequestParam String deveui, Model model) throws SQLException {
        ArrayList<String> dataList = DbConnection.getData(deveui);
        String devname = DbConnection.getDevName(deveui);
        model.addAttribute("devname", devname);
        model.addAttribute("deveui", deveui);
        model.addAttribute("dataList", dataList);
        return "/data";
    }

    @GetMapping("/senddata")
    public String prepareToSend(@AuthenticationPrincipal User user, @RequestParam String deveui, @RequestParam String data, @RequestParam String devname, Model model) {
        model.addAttribute("deveui", deveui);
        model.addAttribute("data", data);
        model.addAttribute("devname", devname);

        return "senddata";
    }


    @PostMapping("/senddata")
    public String sendData(@AuthenticationPrincipal User user, @ModelAttribute(name = "address1") String address1, @ModelAttribute(name = "address2") String address2, Model model) {
        String url = address1 + address2;
        System.out.println(url);
        return "redirect:"+url;
    }

    @GetMapping("/alldevices")
    public String allDevices(@AuthenticationPrincipal User user,  Map<String, Object> model) throws SQLException {
        Map<String, String> devices = DbConnection.getAllDevices();
        model.put("devices", devices);
        return "alldevices";
    }

    @GetMapping("/index")
    public String redir() throws SQLException {

        return "/index";
    }



}
