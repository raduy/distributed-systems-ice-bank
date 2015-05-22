package pl.agh.sr.icebank.transfer;

import Bank.Account;
import Bank.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.account.Money;
import pl.agh.sr.icebank.account.TransferableAccount;
import pl.agh.sr.icebank.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class TransferManager {
    private static final Logger LOG = LoggerFactory.getLogger(TransferManager.class);
    private final Lock guard = new ReentrantLock();
    private final AccountRepository accountRepository;
    private final CurrenciesTable currenciesTable;

    @Autowired
    public TransferManager(AccountRepository accountRepository, CurrenciesTable currenciesTable) {
        this.accountRepository = accountRepository;
        this.currenciesTable = currenciesTable;
    }

    public void atomicTransfer(MoneyTransfer moneyTransfer) {
        guard.lock();
        try {
            transfer(moneyTransfer);
        } catch (Exception e) {
            LOG.error("Error transferring money! From: {} to: {}", moneyTransfer.sourceAccount(), moneyTransfer.destinationAccount());
        } finally {
            guard.unlock();
        }
    }

    private void transfer(MoneyTransfer moneyTransfer) {
        Account source = accountRepository.loadById(moneyTransfer.sourceAccount());
        Account destination = accountRepository.loadById(moneyTransfer.destinationAccount());

        checkState(source instanceof TransferableAccount);
        checkState(destination instanceof TransferableAccount);

        TransferableAccount sourceAccount = (TransferableAccount) source;
        TransferableAccount destinationAccount = (TransferableAccount) destination;

        final Money afterWithdrawal = calculateBalanceAfterWithdrawal(Money.of(source.getBalance(), Currency.PLN), moneyTransfer.amount());
        sourceAccount.updateBalance(afterWithdrawal);
        final Money afterDeposit = calculateAfterDeposit(Money.of(destination.getBalance(), Currency.PLN), moneyTransfer.amount());
        destinationAccount.updateBalance(afterDeposit);
    }

    private Money calculateAfterDeposit(Money accountBalance, Money deposit) {
        float factor = this.currenciesTable.findMapping(accountBalance.currency(), deposit.currency());
        BigDecimal bigDecimal = new BigDecimal(accountBalance.amount() + factor * deposit.amount());

        return Money.of(bigDecimal.intValue(), accountBalance.currency());
    }

    private Money calculateBalanceAfterWithdrawal(Money accountBalance, Money withdrawalAmount) {
        float factor = this.currenciesTable.findMapping(accountBalance.currency(), withdrawalAmount.currency());
        BigDecimal bigDecimal = new BigDecimal(accountBalance.amount() - factor * withdrawalAmount.amount());

        return Money.of(bigDecimal.intValue(), accountBalance.currency());
    }
}
