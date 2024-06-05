package BBOM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class LoggingAspect {
    public void logOperationStart(String operationName) {
        log("Executing " + operationName + " in LoggingAspect");
    }

    public void logOperationEnd(String operationName) {
        log("Completed " + operationName + " in LoggingAspect");
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }

    public String fetchHttpResponse(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine);
        }
        reader.close();
        connection.disconnect();
        return content.toString();
    }
}
