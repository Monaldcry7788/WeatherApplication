package christian.scuola.weatherapplication;

import christian.scuola.weatherapplication.parser.Item;
import christian.scuola.weatherapplication.parser.Response;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML private TextField cityInput;
    @FXML private Label cityName, currentTemp, weatherDescription, humidity, windSpeed, pressure, feelsLikeTemp, statusMsg;
    @FXML private ImageView weatherIcon;
    @FXML private HBox forecastContainer;
    @FXML private Label populationLabel, altitudeLabel, sunriseLabel, sunsetLabel;
    @FXML private LineChart<String, Number> tempChart;
    @FXML private javafx.scene.chart.CategoryAxis xAxis;

    private final String API_KEY = "d4b5f3632a012ef5ed2f2ba550d7ff05";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric&lang=it";

    @FXML
    protected void onButtonClick() {
        String city = cityInput.getText().trim();
        if (city.isEmpty()) {
            statusMsg.setText("Inserisci una città!");
            return;
        }

        new Thread(() -> {
            try {
                String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
                String fullUrl = String.format(BASE_URL, encodedCity, API_KEY);

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Status code: " + response.statusCode());

                if (response.statusCode() == 200) {
                    System.out.println("Response body: " + response.body());
                    parseAndDisplayData(response.body());
                } else {
                    Platform.runLater(() -> statusMsg.setText("Città non trovata o errore interno"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> statusMsg.setText("Errore durante la richiesta"));
            }
        }).start();
    }

    private void parseAndDisplayData(String jsonBody) {
        Gson gson = new Gson();
        Response data = gson.fromJson(jsonBody, Response.class);

        Platform.runLater(() -> {
            Item current = data.getList().get(0);

            cityName.setText(data.getCity().getName() + ", " + data.getCity().getCountry());
            currentTemp.setText(Math.round(current.getMain().getTemp()) + "°");
            weatherDescription.setText(current.getWeather().get(0).getDescription());

            String mainIconCode = current.getWeather().get(0).getIcon();
            weatherIcon.setImage(new Image("https://openweathermap.org/img/wn/" + mainIconCode + "@2x.png"));

            populationLabel.setText("Pop: " + String.format("%, d", data.getCity().getPopulation()));
            altitudeLabel.setText("Alt: " + current.getMain().getGrndLevel() + " m");

            humidity.setText(current.getMain().getHumidity() + "%");
            windSpeed.setText(current.getWind().getSpeed() + " m/s");
            pressure.setText(current.getMain().getPressure() + " hPa");
            feelsLikeTemp.setText(Math.round(current.getMain().getFeelsLike()) + "°");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            sunriseLabel.setText(Instant.ofEpochSecond(data.getCity().getSunrise()).atZone(ZoneId.systemDefault()).format(dtf));
            sunsetLabel.setText(Instant.ofEpochSecond(data.getCity().getSunset()).atZone(ZoneId.systemDefault()).format(dtf));

            xAxis.getCategories().clear();
            tempChart.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < 8; i++) {
                Item item = data.getList().get(i);
                String time = item.getDt_txt().substring(11, 16);
                series.getData().add(new XYChart.Data<>(time, item.getMain().getTemp()));
            }
            tempChart.getData().add(series);

            forecastContainer.getChildren().clear();
            for (int i = 0; i < 8; i++) {
                forecastContainer.getChildren().add(createHourCard(data.getList().get(i)));
            }

            statusMsg.setText("Ultimo aggiornamento effettuato con successo");
        });
    }

    private VBox createHourCard(Item item) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25); -fx-background-radius: 15; -fx-padding: 10;");
        card.setMinWidth(110);

        String hour = item.getDt_txt().substring(11, 16);
        Label lblHour = new Label(hour);
        lblHour.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

        String iconCode = item.getWeather().get(0).getIcon();
        ImageView icon = new ImageView(new Image("https://openweathermap.org/img/wn/" + iconCode + ".png", true));
        icon.setFitWidth(45);
        icon.setFitHeight(45);

        Label lblTemp = new Label("Reale: " + Math.round(item.getMain().getTemp()) + "°");
        lblTemp.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label lblFeels = new Label("Perc: " + Math.round(item.getMain().getFeelsLike()) + "°");
        lblFeels.setStyle("-fx-text-fill: moccasin; -fx-font-size: 12px; -fx-font-style: italic;");

        card.getChildren().addAll(lblHour, icon, lblTemp, lblFeels);

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: rgba(0, 0, 0, 0.45); -fx-background-radius: 15; -fx-padding: 10; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25); -fx-background-radius: 15; -fx-padding: 10;"));

        return card;
    }
}