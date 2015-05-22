package pl.agh.sr.icebank.account;

import Bank.*;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;
import pl.agh.sr.icebank.loan.LoanProposal;
import pl.agh.sr.icebank.loan.LoanQuery;
import pl.agh.sr.icebank.loan.LoanService;
import pl.agh.sr.icebank.transfer.TransferManager;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class PremiumAccount extends _PremiumAccountDisp implements Bank.PremiumAccount, TransferableAccount {
    private final SilverAccount baseAccount;
    private LoanService loanService;

    /**
     * Package scope constructor. Only available for AccountRepository
     * @param transferManager required to do money transfers.
     */
    PremiumAccount(TransferManager transferManager, LoanService loanService) {
        this.loanService = loanService;
        this.baseAccount = new SilverAccount(transferManager);
    }

    @Override
    public void calculateLoan(int amount, Currency curr, int period, FloatHolder interestRate,
                              IntHolder totalCost, Current current) throws IncorrectData {
        LoanQuery loanQuery = new LoanQuery(Money.of(amount, curr), period);
        LoanProposal loanProposal = this.loanService.calculateLoan(loanQuery);

        interestRate.value = loanProposal.interestRate();
        totalCost.value = loanProposal.totalCost();
    }

    /*Methods delegation start*/
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
    public void updateBalance(Money newBalance) {
        this.baseAccount.updateBalance(newBalance);
    }

    @Override
    public void setTransferManager(TransferManager manager) {
        this.baseAccount.setTransferManager(manager);
    }
    /*Methods delegation end*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PremiumAccount that = (PremiumAccount) o;

        return baseAccount.equals(that.baseAccount);
    }

    @Override
    public int hashCode() {
        return baseAccount.hashCode();
    }

    @Override
    public String toString() {
        return "PremiumAccount{" +
                "baseAccount=" + baseAccount +
                '}';
    }
}
