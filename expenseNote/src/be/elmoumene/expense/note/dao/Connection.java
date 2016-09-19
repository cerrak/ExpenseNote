package be.elmoumene.expense.note.dao;

public interface Connection {

    void op();

    void close();

    boolean isConnected();
}