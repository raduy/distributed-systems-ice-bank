package pl.agh.sr.icebank.transfer;

import Bank.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class CurrenciesTable {
    private final static float DEFAULT_CONVERSION_FACTOR = 1;
    private static final Logger LOG = LoggerFactory.getLogger(CurrenciesTable.class);
    private final ConcurrentMap<Key, Float> table = new ConcurrentHashMap<>();

    public float findMapping(Currency first, Currency second) {
        return table.getOrDefault(new Key(first, second), DEFAULT_CONVERSION_FACTOR);
    }

    public void update(Currency firstCurrency, Currency secondCurrency, float rate) {
        table.put(new Key(firstCurrency, secondCurrency), rate);
        LOG.trace("Table updated for rating {} --> {}", firstCurrency, secondCurrency);
    }

    public class Key {
        private final Currency from;
        private final Currency to;

        public Key(Currency from, Currency to) {
            this.from = from;
            this.to = to;
        }

        public Key of(Currency from, Currency to) {
            return new Key(from, to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (from != key.from) return false;
            if (to != key.to) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = from != null ? from.hashCode() : 0;
            result = 31 * result + (to != null ? to.hashCode() : 0);
            return result;
        }

    }
}
