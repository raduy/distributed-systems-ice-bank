package pl.agh.sr.icebank.evictor;

import Bank.Account;
import Ice.*;
import Ice.Object;
import pl.agh.sr.icebank.evictor.baseevictor.EvictorBase;
import pl.agh.sr.icebank.repository.AccountRepository;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SilverAccountEvictor extends EvictorBase implements Ice.ServantLocator {

    private final AccountRepository accountRepository;

    public SilverAccountEvictor(int size, AccountRepository accountRepository) {
        super(size);
        this.accountRepository = accountRepository;
    }

    @Override
    public Ice.Object add(Current current, LocalObjectHolder cookie) {
        String accountId = current.id.name;
        return accountRepository.loadById(accountId);
    }

    @Override
    public void evict(Object servant, java.lang.Object cookie) {
        accountRepository.save((Account) servant);
    }
}
