package pl.agh.sr.icebank.repository;

import Bank.Account;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class InMemoryAccountRepository implements AccountRepository {
    private final ConcurrentMap<String, Account> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        storage.put(account.getAccountNumber(), account);
    }

    @Override
    public Account loadById(String accountId) {
        return storage.get(accountId);
    }

    @Override
    public void removeById(String accountId) {
        storage.remove(accountId);
    }
}
