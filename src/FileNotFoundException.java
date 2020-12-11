
/**
 *
 * This class implements a custom exception that is used when the file you are
 * looking for doesn't exists
 * @author Alexandru Nisulescu
 */
public class FileNotFoundException extends Exception{
    private String fileError;

    /**
     * The constructor that handles the error when it is thrown
     * @param errorMessage is the error message
     */
    public FileNotFoundException(String errorMessage) {
        super(errorMessage);
        this.fileError=errorMessage;
    }

    /**
     * The method that is used to write the error to the log file
     */
    public void throwExc() {
        System.out.println(this.fileError);
        //TO DO Implementeaza scrierea in log files cand e gata
    }
}
