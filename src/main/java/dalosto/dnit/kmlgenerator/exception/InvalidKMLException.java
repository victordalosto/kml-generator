package dalosto.dnit.kmlgenerator.exception;


public class InvalidKMLException extends Exception {

    private static final long serialVersionUID = 1L;


    public InvalidKMLException(Exception e) {
        super(e);
    }


    public InvalidKMLException(String message) {
        super(message);
    }


    public InvalidKMLException() {
        super();
    }

}
