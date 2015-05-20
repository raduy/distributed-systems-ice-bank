package pl.agh.sr.icebank.repository;

import Bank.Account;
import Bank.PremiumAccount;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.SilverAccount;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class CachedFileSystemAccountRepository implements AccountRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CachedFileSystemAccountRepository.class);
    private final static int CACHE_SIZE = 2;

    private final ConcurrentMap<String, PremiumAccount> premiumAccountsStorage = new ConcurrentHashMap<>();
    private final FileSystemPersister fileSystemPersister;
    private final LoadingCache<String, SilverAccount> loadingCache;

    @Autowired
    public CachedFileSystemAccountRepository(FileSystemPersister fileSystemPersister, AccountLoader accountLoader) {
        this.fileSystemPersister = fileSystemPersister;
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .build(accountLoader);
    }


    @Override
    public void save(Account account) {
        LOG.debug("Saving account {}", account);

        if (account instanceof SilverAccount) {
            fileSystemPersister.persist((SilverAccount) account);
        } else {
            premiumAccountsStorage.put(account.getAccountNumber(), (PremiumAccount) account);
        }
    }

    @Override
    public Account loadById(String accountId) {
        LOG.debug("Loading account of id: {}", accountId);
        if (premiumAccountsStorage.containsKey(accountId)) {
            return premiumAccountsStorage.get(accountId);
        }

        try {
            return loadingCache.get(accountId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeById(String accountId) {
        premiumAccountsStorage.remove(accountId);

        loadingCache.invalidate(accountId);
        fileSystemPersister.removeById(accountId);
    }
}
