public class ConnectionFailedException extends Exception {
    private String connError;

    public ConnectionFailedException(String errorMessage) {
        super(errorMessage);
        this.connError=errorMessage;
    }

    public void throwExc() {
        System.out.println(this.connError);
        //TO DO Implementeaza scrierea in log files cand e gata
    }
}
