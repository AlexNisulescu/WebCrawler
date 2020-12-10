public class FileNotFoundException extends Exception{
    private String fileError;

    public FileNotFoundException(String errorMessage) {
        super(errorMessage);
        this.fileError=errorMessage;
    }

    public void throwExc() {
        System.out.println(this.fileError);
        //TO DO Implementeaza scrierea in log files cand e gata
    }
}
