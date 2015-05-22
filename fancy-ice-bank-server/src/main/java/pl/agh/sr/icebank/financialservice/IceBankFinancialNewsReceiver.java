package pl.agh.sr.icebank.financialservice;

import FinancialNews.Currency;
import FinancialNews._FinancialNewsReceiverDisp;
import Ice.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.transfer.CurrenciesTable;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class IceBankFinancialNewsReceiver extends _FinancialNewsReceiverDisp {
    private static final Logger LOG = LoggerFactory.getLogger(IceBankFinancialNewsReceiver.class);
    private final CurrenciesTable currenciesTable;

    @Autowired
    public IceBankFinancialNewsReceiver(CurrenciesTable currenciesTable) {
        this.currenciesTable = currenciesTable;
    }

    @Override
    public void interestRate(float rate, Currency currency, Current current) {
        LOG.info("Interest rate changed for: {} currency to {} rate", currency, rate);
    }

    @Override
    public void exchangeRate(float rate, Currency firstCurrency, Currency secondCurrency, Current current) {
        currenciesTable.update(firstCurrency, secondCurrency, rate);

        LOG.info("Exchange rate changed for transfer {} --> {} to rate: {}", firstCurrency, secondCurrency, rate);
    }
}
