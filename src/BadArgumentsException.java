
/**
 *
 * This class implements a custom exception that is used the user inputs
 * wrong arguments
 * @author Alexandru Nisulescu
 */
public class BadArgumentsException extends Exception{
    private String argsError;

    /**
     * The constructor that handles the error when it is thrown
     * @param errorMessage is the error message
     */
    public BadArgumentsException(String errorMessage) {
        super(errorMessage);
        this.argsError = errorMessage;
    }

    /**
     * The method that is used to write the error to the log file
     */
    public void throwExc() {
        System.out.println(this.argsError);
        //TO DO Implementeaza scrierea in log files cand e gata
    }
}
