package pl.agh.sr.icebank.repository;

import Bank.Account;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface AccountRepository {
    void save(Account account);

    Account loadById(String accountId);

    void removeById(String accountId);
}
