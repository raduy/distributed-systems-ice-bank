package pl.agh.sr.icebank.financialservice;

import FinancialNews.FinancialNewsServerPrx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class FinancialNewsPinger implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(FinancialNewsPinger.class);
    private static final int TIMEOUT = 120;
    private final FinancialNewsServerPrx serverPrx;

    public FinancialNewsPinger(FinancialNewsServerPrx serverPrx) {
        this.serverPrx = serverPrx;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                serverPrx.ice_ping();
                LOG.trace("Pinging FinancialNews service");
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (Exception e) {
                LOG.error("Error pinging FinancialNews service", e);
            }
        }
    }
}
