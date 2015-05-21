package pl.agh.sr.icebank.financialservice;

import FinancialNews.Currency;
import FinancialNews._FinancialNewsReceiverDisp;
import Ice.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class IceBankFinancialNewsReceiver extends _FinancialNewsReceiverDisp {
    private static final Logger LOG = LoggerFactory.getLogger(IceBankFinancialNewsReceiver.class);

    @Override
    public void interestRate(float rate, Currency currency, Current current) {
        LOG.info("Interest rate change for: {} currency to {} rate", currency, rate);
    }

    @Override
    public void exchangeRate(float rate, Currency firstCurrency, Currency secondCurrency, Current current) {
        LOG.info("Exchange rate change for transfer {} --> {} to rate: {}", firstCurrency, secondCurrency, rate);
    }
}
