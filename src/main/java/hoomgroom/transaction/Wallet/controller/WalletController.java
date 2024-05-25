package hoomgroom.transaction.Wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @GetMapping("")
    public String transactionHome(Model model) {return "walletPage";}
}
