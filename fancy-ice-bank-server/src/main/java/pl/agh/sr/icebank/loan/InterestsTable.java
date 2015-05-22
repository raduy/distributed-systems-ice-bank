package pl.agh.sr.icebank.loan;

import Bank.Currency;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class InterestsTable {
    private final ConcurrentMap<Currency, Float> table = new ConcurrentHashMap<>();
    private static final float DEFAULT_INTEREST_RATE = 0.05f;


    public void updateInterest(Currency currency, float newInterest) {
        table.put(currency, newInterest);
    }

    public float findInterest(Currency currency) {
        return table.getOrDefault(currency, DEFAULT_INTEREST_RATE);
    }
}
