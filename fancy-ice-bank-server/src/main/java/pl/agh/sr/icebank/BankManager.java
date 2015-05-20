package pl.agh.sr.icebank;

import Bank.*;
import Ice.Current;
import Ice.Identity;
import Ice.ObjectAdapter;
import Ice.StringHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.PremiumAccount;
import pl.agh.sr.icebank.account.SilverAccount;
import pl.agh.sr.icebank.repository.AccountRepository;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class BankManager extends _BankManagerDisp implements Bank.BankManager {
    private static final Logger LOG = LoggerFactory.getLogger(BankManager.class);
    private final AccountRepository accountRepository;

    @Autowired
    public BankManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(PersonalData personalData, accountType type, StringHolder accountID, Current current) throws IncorrectData, RequestRejected {
        LOG.debug("Creating new account for {}", IceBankStrings.toString(personalData));
        validatePersonalData(personalData);

        switch (type) {
            case PREMIUM:
                PremiumAccount premiumAccount = new PremiumAccount();
                saveToAsmTable(current.adapter, premiumAccount);
                accountID.value = premiumAccount.getAccountNumber();
                break;
            case SILVER:
                SilverAccount silverAccount = new SilverAccount();
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

    }
}