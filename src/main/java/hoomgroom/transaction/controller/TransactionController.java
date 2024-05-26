package hoomgroom.transaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TransactionController {
    @GetMapping("/")
    public String transaction() {
        return "transaction";
    }

    @GetMapping("/wallet")
    public String getWalletPage() {
        return "walletPage"; // This corresponds to wallet.html in src/main/resources/static
    }
}
