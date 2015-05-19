package pl.agh.sr.icebank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class IceBankServer {
    private static final Logger LOG = LoggerFactory.getLogger(IceBankServer.class);

    public void play(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);
        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("ICEBANK");

        adapter.activate();

        LOG.info("Ice Bank Server up!");
        communicator.waitForShutdown();
    }


    public static void main(String[] args) {
        IceBankServer bankServer = new IceBankServer();
        bankServer.play(args);
    }
}
