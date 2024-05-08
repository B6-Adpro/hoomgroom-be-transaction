package hoomgroom.transaction.Wallet.service;

import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;

public interface TopUpStrategy {
    public boolean validateTopUpDetails();
    public void topUp(String walletId, double amount);
}
