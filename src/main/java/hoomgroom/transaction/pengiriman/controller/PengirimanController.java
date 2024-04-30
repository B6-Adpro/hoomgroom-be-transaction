package hoomgroom.transaction.pengiriman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.PengirimanService;

@Controller
@RequestMapping("/pengiriman")
public class PengirimanController {

    @Autowired
    private PengirimanService pengirimanService;

    @GetMapping("/listpengiriman")
    public String pengirimanListPage(Model model){
        List<Pengiriman> allPengiriman = pengirimanService.findAll();
        model.addAttribute("pengiriman", allPengiriman);
        return "pengirimanList";
    }
}