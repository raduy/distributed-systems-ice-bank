package pl.agh.sr.icebank.evictor;

import Bank.Account;
import Ice.*;
import Ice.Object;
import org.slf4j.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.evictor.baseevictor.EvictorBase;
import pl.agh.sr.icebank.repository.AccountRepository;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class SilverAccountEvictor extends EvictorBase implements Ice.ServantLocator {
    private static final Logger LOG = LoggerFactory.getLogger(SilverAccountEvictor.class);

    private final AccountRepository accountRepository;

    @Autowired
    public SilverAccountEvictor(AccountRepository accountRepository) {
        super(2);
        this.accountRepository = accountRepository;
    }

    @Override
    public Ice.Object add(Current current, LocalObjectHolder cookie) {
        String accountId = current.id.name;
        LOG.debug("Loading state for account of id {}", accountId);

        return accountRepository.loadById(accountId);
    }

    @Override
    public void evict(Object servant, java.lang.Object cookie) {
        LOG.debug("Moving account of id: {} out of cache", cookie);
        accountRepository.save((Account) servant);
    }
}
