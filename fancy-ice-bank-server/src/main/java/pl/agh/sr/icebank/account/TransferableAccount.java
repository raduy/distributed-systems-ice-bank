package pl.agh.sr.icebank.account;

import pl.agh.sr.icebank.transfer.TransferManager;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface TransferableAccount {

    void updateBalance(Money newBalance);
    void setTransferManager(TransferManager manager);
}
