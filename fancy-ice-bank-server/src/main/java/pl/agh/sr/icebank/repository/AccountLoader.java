package pl.agh.sr.icebank.repository;

import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.SilverAccount;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class AccountLoader extends CacheLoader<String, SilverAccount> {
    private static final Logger LOG = LoggerFactory.getLogger(AccountLoader.class);
    private final FileSystemPersister fileSystemPersister;

    @Autowired
    public AccountLoader(FileSystemPersister fileSystemPersister) {
        this.fileSystemPersister = fileSystemPersister;
    }

    @Override
    public SilverAccount load(String accountId) throws Exception {
        LOG.info("Loading account for {} id", accountId);
        return fileSystemPersister.read(accountId);
    }
}
