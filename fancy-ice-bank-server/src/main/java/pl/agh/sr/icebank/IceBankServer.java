package pl.agh.sr.icebank;

import Ice.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import pl.agh.sr.icebank.api.BankManager;
import pl.agh.sr.icebank.config.SpringConfig;
import pl.agh.sr.icebank.evictor.SilverAccountEvictor;
import pl.agh.sr.icebank.financialservice.FinancialNewsListener;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class IceBankServer {
    private static final Logger LOG = LoggerFactory.getLogger(IceBankServer.class);

    private final BankManager bankManager;
    private final SilverAccountEvictor accountEvictor;
    private final FinancialNewsListener listener;

    @Autowired
    public IceBankServer(BankManager bankManager, SilverAccountEvictor accountEvictor, FinancialNewsListener listener) {
        this.bankManager = bankManager;
        this.accountEvictor = accountEvictor;
        this.listener = listener;
    }

    public void play(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);
        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("ICEBANK");

        adapter.addServantLocator(accountEvictor, "silverAccounts");

        Identity bankMangerIdentity = communicator.stringToIdentity("main/bankManager");
        adapter.add(bankManager, bankMangerIdentity);

        listener.listen(communicator, adapter);
        adapter.activate();

        LOG.info("Ice Bank Server up!");
        communicator.waitForShutdown();
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IceBankServer bankServer = appContext.getBean(IceBankServer.class);
        bankServer.play(args);
    }
}
