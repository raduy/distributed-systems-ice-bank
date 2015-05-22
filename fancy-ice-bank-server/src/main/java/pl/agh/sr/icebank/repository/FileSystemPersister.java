package pl.agh.sr.icebank.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.SilverAccount;
import pl.agh.sr.icebank.transfer.TransferManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class FileSystemPersister {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemPersister.class);

    @Autowired
    private ApplicationContext appContext;

    public void persist(SilverAccount account) {
        LOG.debug("Persisting account {}", account);

        byte[] serialized = SerializationUtils.serialize(account);

        Path path = buildPath(account);
        File file = path.toFile();
        try {
            FileUtils.writeByteArrayToFile(file, serialized);
        } catch (IOException e) {
            LOG.error(format("Cannot persist account %s to file", account), e);
        }
    }

    public SilverAccount read(String accountId) {
        LOG.debug("Reading account of id: {}", accountId);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(buildPath(accountId));
        } catch (IOException e) {
            LOG.info("No account of id: %s in files", accountId);
        }
        SilverAccount silverAccount = (SilverAccount) SerializationUtils.deserialize(bytes);
        silverAccount.setTransferManager(appContext.getBean(TransferManager.class));
        return silverAccount;
    }

    public void removeById(String accountId) {
        LOG.debug("Account of id: {} removed", accountId);
        try {
            Files.delete(buildPath(accountId));
        } catch (IOException e) {
            LOG.error(format("Cannot remove account of id: %s from file", accountId), e);
        }
    }

    private Path buildPath(SilverAccount account) {
        return buildPath(account.getAccountNumber());
    }

    private Path buildPath(String accountId) {
        return Paths.get("data", "silver-accounts", accountId);
    }
}
