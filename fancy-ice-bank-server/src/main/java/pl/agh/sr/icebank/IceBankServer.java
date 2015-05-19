package pl.agh.sr.icebank;

import Ice.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class IceBankServer {
    private static final Logger LOG = LoggerFactory.getLogger(IceBankServer.class);

    public void play(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);
        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("ICEBANK");

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Config.class);
        Bank.BankManager bankManager = appContext.getBean(BankManager.class);

        Identity bankMangerIdentity = communicator.stringToIdentity("main/bankManager");
        adapter.add(bankManager, bankMangerIdentity);


        adapter.activate();

        LOG.info("Ice Bank Server up!");
        communicator.waitForShutdown();
    }


    public static void main(String[] args) {
        IceBankServer bankServer = new IceBankServer();
        bankServer.play(args);
    }
}
