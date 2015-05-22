package pl.agh.sr.icebank.financialservice;

import FinancialNews.FinancialNewsReceiverPrx;
import FinancialNews.FinancialNewsServerPrx;
import FinancialNews.FinancialNewsServerPrxHelper;
import Ice.Communicator;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static FinancialNews.FinancialNewsReceiverPrxHelper.checkedCast;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class FinancialNewsListener {
    private static final String FINANCIAL_SERVICE_PROXY = "ice-bank.financial-service.proxy";
    private static final Logger LOG = LoggerFactory.getLogger(FinancialNewsListener.class);

    private final IceBankFinancialNewsReceiver newsReceiver;

    @Autowired
    public FinancialNewsListener(IceBankFinancialNewsReceiver newsReceiver) {
        this.newsReceiver = newsReceiver;
    }

    public void listen(Communicator communicator, ObjectAdapter adapter) {
        ObjectPrx objectPrx = communicator.propertyToProxy(FINANCIAL_SERVICE_PROXY);
        FinancialNewsServerPrx serverPrx = FinancialNewsServerPrxHelper.checkedCast(objectPrx);

        FinancialNewsReceiverPrx receiverPrx = checkedCast(adapter.addWithUUID(newsReceiver));
        serverPrx.registerForNews(receiverPrx);
        serverPrx.ice_getConnection().setAdapter(adapter);
        LOG.debug("Listening on Financial News");
    }
}
