package pl.agh.sr.icebank.account;

import Bank.*;
import Ice.Current;
import pl.agh.sr.icebank.transfer.TransferManager;

import java.util.UUID;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SilverAccount extends _AccountDisp implements Account {
    private Money balance = Money.of(0, Currency.PLN);
    private final UUID accountNumber = UUID.randomUUID();

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

        balance = TransferManager.transfer(this.getAccountNumber(), accountNumber, Money.of(amount, Currency.PLN));
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
