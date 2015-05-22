package pl.agh.sr.icebank.account;

import Bank.*;
import Ice.Current;
import com.google.common.base.Preconditions;
import pl.agh.sr.icebank.transfer.MoneyTransfer;
import pl.agh.sr.icebank.transfer.TransferManager;

import java.util.UUID;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SilverAccount extends _AccountDisp implements Account, TransferableAccount {
    private Money balance = Money.of(100, Currency.PLN);
    private final UUID accountNumber = UUID.randomUUID();
    private transient TransferManager transferManager;

    /**
     * Package scope constructor. Only available for AccountRepository
     *
     * @param transferManager required to do money transfers.
     */
    SilverAccount(TransferManager transferManager) {
        this.transferManager = transferManager;
    }

    public int getBalance(Current current) {
        return this.balance.amount();
    }

    @Override
    public String getAccountNumber(Current current) {
        return this.accountNumber.toString();
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current current)
            throws IncorrectAccountNumber, IncorrectAmount {

        MoneyTransfer moneyTransfer = new MoneyTransfer(this.getAccountNumber(), accountNumber, Money.of(amount, Currency.PLN));
        transferManager.atomicTransfer(moneyTransfer);
    }

    @Override
    public void updateBalance(Money newBalance) {
        this.balance = newBalance;
    }

    @Override
    public void setTransferManager(TransferManager manager) {
        Preconditions.checkState(this.transferManager == null, "Cannot change Transfer Manager when already set");
        this.transferManager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SilverAccount that = (SilverAccount) o;

        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = balance != null ? balance.hashCode() : 0;
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SilverAccount{" +
                "balance=" + balance +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
