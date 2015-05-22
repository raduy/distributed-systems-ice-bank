package pl.agh.sr.icebank.evictor;

import Bank.Account;
import Ice.Current;
import Ice.LocalObjectHolder;
import Ice.Object;
import Ice.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.repository.AccountRepository;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class SilverAccountEvictor implements Ice.ServantLocator {
    private final AccountRepository accountRepository;

    @Autowired
    public SilverAccountEvictor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Object locate(Current current, LocalObjectHolder localObjectHolder) throws UserException {
        return accountRepository.loadById(current.id.name);
    }

    @Override
    public void finished(Current current, Object servant, java.lang.Object o) throws UserException {
        Account updatedAccount = (Account) servant;
        accountRepository.save(updatedAccount);
    }

    @Override
    public void deactivate(String s) {
        accountRepository.flush();
    }
}
