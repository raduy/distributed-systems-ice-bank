package pl.agh.sr.icebank.loan;

import pl.agh.sr.icebank.account.Money;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class LoanQuery {
    private final Money queriedAmount;
    private final int period;

    public LoanQuery(Money queriedAmount, int period) {
        this.queriedAmount = queriedAmount;
        this.period = period;
    }

    public Money getQueriedAmount() {
        return queriedAmount;
    }

    public int getPeriod() {
        return period;
    }
}
