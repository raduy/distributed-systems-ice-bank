package pl.agh.sr.icebank.account;

import Bank.Currency;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Money {
    private final int amount;
    private final Currency currency;

    private Money(int amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(int amount, Currency currency) {
        return new Money(amount, currency);
    }

    public int amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }
}