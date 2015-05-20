package pl.agh.sr.icebank.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.SilverAccount;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class FileSystemPersister {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemPersister.class);

    public void persist(SilverAccount account) {
        LOG.debug("Persisting account {}", account);

        byte[] serialized = SerializationUtils.serialize(account);

        Path path = buildPath(account);
        File file = path.toFile();
        try {
            FileUtils.writeByteArrayToFile(file, serialized);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SilverAccount read(String accountId) {
        LOG.debug("Reading account of id: {}", accountId);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(buildPath(accountId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (SilverAccount) SerializationUtils.deserialize(bytes);
    }

    public void removeById(String accountId) {
        LOG.debug("Account of id: {} removed", accountId);
        try {
            Files.delete(buildPath(accountId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path buildPath(SilverAccount account) {
        return buildPath(account.getAccountNumber());
    }

    private Path buildPath(String accountId) {
        return Paths.get(System.getProperty("user.home"), "silver-accounts", accountId);
    }

    public static void main(String[] args) {
        FileSystemPersister fileSystemPersister = new FileSystemPersister();
        SilverAccount silverAccount = new SilverAccount();
        fileSystemPersister.persist(silverAccount);

        SilverAccount read = fileSystemPersister.read(silverAccount.getAccountNumber());
        System.out.println(read);
    }
}
