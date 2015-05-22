package pl.agh.sr.icebank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.transfer.TransferManager;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class AccountFactory {

    private final TransferManager transferManager;

    @Autowired
    public AccountFactory(TransferManager transferManager) {
        this.transferManager = transferManager;
    }

    public SilverAccount newSilverAccount() {
        return new SilverAccount(transferManager);
    }

    public PremiumAccount newPremiumAccount() {
        return new PremiumAccount(transferManager);
    }
}
