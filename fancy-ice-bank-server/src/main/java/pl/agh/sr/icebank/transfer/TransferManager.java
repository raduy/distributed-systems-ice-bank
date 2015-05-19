package pl.agh.sr.icebank.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.icebank.account.Money;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TransferManager {
    private static final Logger LOG = LoggerFactory.getLogger(TransferManager.class);

    public static Money transfer(String source, String destination, Money money) {
        LOG.info("Transferring money from {} to {} with amount of {}", source, destination, money.amount());
//        todo implement
        return money;
    }
}
