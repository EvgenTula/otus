package ru.otus.hw16messageserver.frontend;

import org.eclipse.jetty.server.Server;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import java.util.logging.Logger;

public class FrontendMain {

    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());

    public static void main(String[] args) throws Exception {
        int port = Integer.valueOf(args[0]);
        logger.info("Frontend started on port " + port);
        FrontendServiceImpl frontendService = new FrontendServiceImpl(port);
        frontendService.start();

        Server server = ServerHelper.createServer(8090, frontendService);
        server.start();
        server.join();

    }
}
