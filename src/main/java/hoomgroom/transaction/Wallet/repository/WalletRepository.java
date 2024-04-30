package hoomgroom.transaction.Wallet.repository;

import hoomgroom.transaction.Wallet.model.Wallet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WalletRepository {
    private final List<Wallet> walletList = new ArrayList<>();

    public void addWallet(Wallet wallet) {
        walletList.add(wallet);
    }

    public Wallet findById (String id) {
        for (Wallet w: walletList) {
            if (w.getWalletId().equals(id)) {
                return w;
            }
        }
        return null;
    }

}
