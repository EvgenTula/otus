package ru.otus.hw16messageserver.messageserver.messagesystem;

public class MessageSystemContext {

    public final static String DEFAULT_MESSAGE_SYSTEM_HOST = "localhost";
    public final static Integer DEFAULT_MESSAGE_SYSTEM_PORT = 8091;
    public final static String DEFAULT_FRONTEND_ADDRESS_VALUE = "frontend";
    public final static String DEFAULT_DB_SERVER_ADDRESS_VALUE = "dbServer";

    private final Address frontendAddress;
    private final Address dbServerAddress;
    private final SocketWorker worker;

    public MessageSystemContext(String frontendAddress, String dbServerAddress, SocketWorker worker) {
        this.frontendAddress = new Address(frontendAddress);
        this.dbServerAddress = new Address(dbServerAddress);
        this.worker = worker;
    }

    public Address getDbServerAddress() {
        return dbServerAddress;
    }

    public Address getFrontendAddress() {
        return frontendAddress;
    }

    public SocketWorker getWorker() {
        return worker;
    }
}
