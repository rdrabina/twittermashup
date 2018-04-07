package model;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
public class BarChartSample extends Application {
    final List values = new ArrayList();
    //for testing
    public void random(int i)
    {
        values.add(i,i);
    }

    public void addTweet(int amountOfTweets)
    {
        values.add(amountOfTweets);
    }
    //For initialising JavaFX Front End
    private void init(Stage primaryStage) {
        //Horizontal Box
        HBox root = new HBox();
        primaryStage.setScene(new Scene(root));
        //Text Area for display of Statistics Data
        final TextArea text = new TextArea ("");
        text.setMaxHeight(100);
        text.setMaxWidth(100);
        root.getChildren().addAll(createChart(text),text);
    }
    //Method for creation of chart.
    protected BarChart<String,Number> createChart(final TextArea text) {
        //final List values = new ArrayList();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);


        // setup chart
        bc.setTitle("Twitter statistics");
        yAxis.setLabel("Number of individual statistics");
        // add starting data
        final XYChart.Series<String,Number> serie = new XYChart.Series<>();
        // Initialisation and change of data at regular intervals of time - 1 seconds.
        Timeline Updater = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serie.getData().clear();
                text.clear();
                //class with implemented method which gets us amount of particular tweets
                //addTweet(class.method());
                //addTweet();

                //for testing
                    random(0);
                    random(1);
                    random(2);
                int i;
                for (i = 0; i < values.size(); i++) {
                        serie.getData().add(new XYChart.Data<String, Number>(String.valueOf(i+1), (Number) values.get(i)));
                        }
                text.appendText("Last value: "+ String.valueOf(values.get(i-1))+"\n");
            }
        }));
        Updater.setCycleCount(Timeline.INDEFINITE);
        Updater.play();
        bc.getData().add(serie);
        return bc;
    }
    //To start initialisation of Front End.
    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}