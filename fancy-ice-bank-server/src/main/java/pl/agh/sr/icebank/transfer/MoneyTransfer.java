package pl.agh.sr.icebank.transfer;

import pl.agh.sr.icebank.account.Money;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class MoneyTransfer {
    private final String sourceAccount;
    private final String destinationAccount;
    private final Money amount;

    public MoneyTransfer(String sourceAccount, String destinationAccount, Money amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public String sourceAccount() {
        return sourceAccount;
    }

    public String destinationAccount() {
        return destinationAccount;
    }

    public Money amount() {
        return amount;
    }
}
