package pl.agh.sr.icebank.account;

import Bank.*;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class PremiumAccount extends _PremiumAccountDisp implements Bank.PremiumAccount {
    private final Account baseAccount = new SilverAccount();

    @Override
    public void calculateLoan(int amount, Currency curr, int period, FloatHolder interestRate,
                              IntHolder totalCost, Current current) throws IncorrectData {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int getBalance(Current current) {
        return baseAccount.getBalance(current);
    }

    @Override
    public String getAccountNumber(Current current) {
        return baseAccount.getAccountNumber(current);
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current current)
            throws IncorrectAccountNumber, IncorrectAmount {
        baseAccount.transferMoney(accountNumber, amount, current);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PremiumAccount that = (PremiumAccount) o;

        if (baseAccount != null ? !baseAccount.equals(that.baseAccount) : that.baseAccount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return baseAccount != null ? baseAccount.hashCode() : 0;
    }
}
