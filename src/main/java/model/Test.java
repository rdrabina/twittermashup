package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Test extends Application {

    private AtomicInteger tick = new AtomicInteger(0);

    public int getValue()
    {
        Random generator = new Random();
        int i = generator.nextInt(100) + 1;
        return i;
    }

    @Override
    public void start(Stage stage) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setAnimated(false);
        //xAxis.setLabel("Tick");

        yAxis.setAnimated(false);
        //yAxis.setLabel("Value");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Values");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.getData().add(series);

        Scene scene = new Scene(chart, 1400, 900);
        stage.setScene(scene);
        stage.show();

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> series.getData().add(new XYChart.Data<>(tick.incrementAndGet(), getValue())));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
