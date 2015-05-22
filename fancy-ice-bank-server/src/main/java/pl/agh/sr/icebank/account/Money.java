package pl.agh.sr.icebank.account;

import Bank.Currency;

import java.io.Serializable;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Money implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amount != money.amount) return false;
        if (currency != money.currency) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
