package BBOM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class NOAAWeatherAPI {
    private LoggingAspect loggingAspect;
    private ErrorHandlingAspect errorHandlingAspect;

    public NOAAWeatherAPI(LoggingAspect loggingAspect, ErrorHandlingAspect errorHandlingAspect) {
        this.loggingAspect = loggingAspect;
        this.errorHandlingAspect = errorHandlingAspect;
    }

    public String retrieveGridEndpoint(double latitude, double longitude) throws Exception {
        String url = "https://api.weather.gov/points/" + latitude + "," + longitude;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        String response = loggingAspect.fetchHttpResponse(connection);
        loggingAspect.log("NOAAWeatherAPI: Grid endpoint response: " + response);
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONObject("properties").getString("forecast");
    }

    public String retrieveWeatherForecast(String gridEndpoint) throws Exception {
        String url = gridEndpoint;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        String response = loggingAspect.fetchHttpResponse(connection);
        loggingAspect.log("NOAAWeatherAPI: Weather forecast response: " + response);
        return formatWeatherForecast(response);
    }

    private String formatWeatherForecast(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        StringBuilder formattedForecast = new StringBuilder();
        jsonResponse.getJSONObject("properties").getJSONArray("periods").forEach(period -> {
            JSONObject periodObj = (JSONObject) period;
            formattedForecast.append(periodObj.getString("name"))
                    .append(": ")
                    .append(periodObj.getString("detailedForecast"))
                    .append("\n");
        });
        return formattedForecast.toString();
    }
}
