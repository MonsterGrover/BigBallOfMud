package BBOM;

public class ErrorHandlingAspect {
    private LoggingAspect loggingAspect;

    public ErrorHandlingAspect(LoggingAspect loggingAspect) {
        this.loggingAspect = loggingAspect;
    }

    public void logAndHandleSampleError() {
        loggingAspect.log("Executing logAndHandleSampleError in ErrorHandlingAspect");
        processError(new Exception("Sample error"));
    }

    public void logAndHandleSampleErrorAgain() {
        loggingAspect.log("Executing logAndHandleSampleErrorAgain in ErrorHandlingAspect");
        processError(new Exception("Sample error"));
    }

    public void processError(Exception e) {
        System.out.println("ERROR: " + e.getMessage());
    }
}
