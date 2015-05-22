package pl.agh.sr.icebank.api;

import Bank.*;
import Ice.Current;
import Ice.Identity;
import Ice.ObjectAdapter;
import Ice.StringHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.AccountFactory;
import pl.agh.sr.icebank.account.PremiumAccount;
import pl.agh.sr.icebank.account.SilverAccount;
import pl.agh.sr.icebank.repository.AccountRepository;
import pl.agh.sr.icebank.util.IceBankStrings;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class BankManager extends _BankManagerDisp implements Bank.BankManager {
    private static final Logger LOG = LoggerFactory.getLogger(BankManager.class);
    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    @Autowired
    public BankManager(AccountRepository accountRepository, AccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
    }

    @Override
    public void createAccount(PersonalData personalData, accountType type, StringHolder accountID, Current current) throws IncorrectData, RequestRejected {
        LOG.debug("Creating new account for {}", IceBankStrings.toString(personalData));
        validatePersonalData(personalData);

        switch (type) {
            case PREMIUM:
                PremiumAccount premiumAccount = accountFactory.newPremiumAccount();
                saveToAsmTable(current.adapter, premiumAccount);
                accountID.value = premiumAccount.getAccountNumber();
                break;
            case SILVER:
                SilverAccount silverAccount = accountFactory.newSilverAccount();
                saveToInMemoryCache(silverAccount);
                accountID.value = silverAccount.getAccountNumber();
        }
    }

    private void validatePersonalData(PersonalData personalData) throws IncorrectData {
        if (!isPersonalDataValid(personalData)) {
            throw new IncorrectData("Personal Data incorrect");
        }
    }

    private void saveToInMemoryCache(SilverAccount account) {
        accountRepository.save(account);
    }

    private void saveToAsmTable(ObjectAdapter adapter, PremiumAccount account) {
        Identity identity = new Identity(account.getAccountNumber(), "premiumAccounts");
        adapter.add(account, identity);
    }

    private boolean isPersonalDataValid(PersonalData personalData) {
        if (isBlank(personalData.firstName)) {
            return false;
        }
        if (isBlank(personalData.lastName)) {
            return false;
        }
        if (isBlank(personalData.nationalIDNumber)) {
            return false;
        }

        if (isBlank(personalData.nationality)) {
            return false;
        }
        return true;
    }

    @Override
    public void removeAccount(String accountID, Current current) throws IncorrectData, NoSuchAccount {
        LOG.info("Removing account with id: {}", accountID);
        accountRepository.removeById(accountID);
    }
}
