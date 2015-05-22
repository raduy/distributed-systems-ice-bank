package pl.agh.sr.icebank.loan;

import Bank.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.Money;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class LoanService {

    private final InterestsTable interestsTable;

    @Autowired
    public LoanService(InterestsTable interestsTable) {
        this.interestsTable = interestsTable;
    }

    public LoanProposal calculateLoan(LoanQuery loanQuery) {
        Money queriedAmount = loanQuery.getQueriedAmount();

        Currency currency = queriedAmount.currency();
        int amount = queriedAmount.amount();

        float interest = interestsTable.findInterest(currency);

        int period = loanQuery.getPeriod();
        int totalCost = (int) ((period  * interest * amount) + amount);

        return new LoanProposal(totalCost, interest);
    }
}
