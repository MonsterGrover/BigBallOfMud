package BBOM;

import java.util.Scanner;

public class Client {
    private static LoggingAspect loggingAspect;

    public static void main(String[] args) {
        loggingAspect = new LoggingAspect();
        ErrorHandlingAspect errorHandlingAspect = new ErrorHandlingAspect(loggingAspect);
        NOAAWeatherAPI noaaWeatherAPI = new NOAAWeatherAPI(loggingAspect, errorHandlingAspect);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Enter the longitude: ");
        double longitude = scanner.nextDouble();

        logMessage("Fetched weather data for coordinates: " + latitude + ", " + longitude);

        try {
            String gridEndpoint = noaaWeatherAPI.retrieveGridEndpoint(latitude, longitude);
            logMessage("Retrieved grid endpoint: " + gridEndpoint);
            String forecast = noaaWeatherAPI.retrieveWeatherForecast(gridEndpoint);
            logMessage("Weather forecast data retrieved");
            System.out.println("Weather Forecast: \n" + forecast);
        } catch (Exception e) {
            errorHandlingAspect.processError(e);
        }
    }

    private static void logMessage(String message) {
        loggingAspect.log("Client: " + message);
    }
}
