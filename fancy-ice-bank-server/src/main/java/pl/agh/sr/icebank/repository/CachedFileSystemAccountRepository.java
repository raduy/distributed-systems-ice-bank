package pl.agh.sr.icebank.repository;

import Bank.Account;
import Bank.PremiumAccount;
import Bank._AccountDisp;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.SilverAccount;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

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
                .expireAfterAccess(100, TimeUnit.MINUTES)
                .maximumSize(CACHE_SIZE)
                .removalListener(removal -> {
                    LOG.trace("Removing from cache: " + removal.getKey() + "/" + removal.getValue());
                    LOG.trace("Now in cache" + cacheView());
                })
                .build(accountLoader);
    }

    public String cacheView() {
        return String.valueOf(loadingCache.asMap().values().stream().map(_AccountDisp::getAccountNumber).collect(toList()));
    }

    @Override
    public void save(Account account) {
        LOG.debug("Saving account {}", account);
        if (account instanceof SilverAccount) {
            if (accountExistsAndNotDirty(account)) {
                LOG.debug("Account {} not dirty. Skipping flush to file", account.getAccountNumber());
                return;
            }

            loadingCache.put(account.getAccountNumber(), (SilverAccount) account);
            fileSystemPersister.persist((SilverAccount) account);
        } else {
            premiumAccountsStorage.put(account.getAccountNumber(), (PremiumAccount) account);
        }
    }

    private boolean accountExistsAndNotDirty(Account account) {
        Account existingAccount = loadById(account.getAccountNumber());
        return Objects.equals(account, existingAccount);
    }

    @Override
    public Account loadById(String accountId) {
        LOG.debug("Loading account of id: {}", accountId);
        if (premiumAccountsStorage.containsKey(accountId)) {
            return premiumAccountsStorage.get(accountId);
        }

        try {
            return loadingCache.get(accountId);
        } catch (Exception e) {
            LOG.debug("No account of id: {}", accountId);
        }
        return null;
    }

    @Override
    public void removeById(String accountId) {
        premiumAccountsStorage.remove(accountId);

        loadingCache.invalidate(accountId);
        fileSystemPersister.removeById(accountId);
    }

    @Override
    public void flush() {
        LOG.debug("Flushing Account Repository");
        loadingCache.asMap().values().stream().forEach(fileSystemPersister::persist);
    }
}
