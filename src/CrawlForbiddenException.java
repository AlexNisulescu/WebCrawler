public class CrawlForbidden extends Exception{
    private String argsError;

    public CrawlForbidden(String errorMessage) {
        super(errorMessage);
        this.argsError = errorMessage;
    }

    public void throwExc() {
        System.out.println(this.argsError);
        //TO DO Implementeaza scrierea in log files cand e gata
    }
}