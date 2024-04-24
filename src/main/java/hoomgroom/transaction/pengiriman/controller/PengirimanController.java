package hoomgroom.transaction.pengiriman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class PengirimanController {

    @GetMapping("/")
    @ResponseBody
    public String createUserPage(Model model) {
        return "<h1>Test</h1>";
    }
}