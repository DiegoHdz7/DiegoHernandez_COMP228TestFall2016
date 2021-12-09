import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.*;

import javax.swing.*;
import java.util.ArrayList;

public class Gui extends Application {

    BorderPane borderPane;
    ComboBox cbxCity;
    TextArea txtArea;
    Button btnDisplay;
    Label lblDescription;
    GridPane gridPane;
    HBox hBox;
    VBox vBox;
    DbConnection dbConnection = new DbConnection();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Initializing Components
        borderPane = new BorderPane();
        cbxCity = new ComboBox();
        txtArea = new TextArea();
        btnDisplay = new Button("Display");
        lblDescription = new Label("Select students by city:");
        gridPane = new GridPane();
        hBox = new HBox();
        vBox = new VBox();

        //Sizes, Style & padding
        cbxCity.setPrefSize(200,30);
        btnDisplay.setPrefSize(200,30);
        lblDescription.setPadding(new Insets(10,10,10,10));
        lblDescription.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        hBox.setSpacing(150);
        hBox.setPadding(new Insets(0,0,0,0));

        //Getting Children
        vBox.getChildren().addAll(cbxCity,btnDisplay);
        hBox.getChildren().addAll(lblDescription,vBox);

        //Components settings
        txtArea.setEditable(false);
        txtArea.setPrefSize(borderPane.getWidth(),100);


        //Positioning components
        borderPane.setTop(hBox);
        borderPane.setBottom(txtArea);


        //Filling Combobox
        ObservableList items = FXCollections.observableArrayList();
        ArrayList<String> cities = dbConnection.getCities();
        for(String city : cities){
            items.add(city);
        }

        cbxCity.getItems().addAll(items);
        cbxCity.getSelectionModel().selectFirst();




        //Creating Scene
        Scene scene = new Scene(borderPane,500,200);
        primaryStage.setTitle("Final Test - Diego Hernandez");
        primaryStage.setScene(scene);
        primaryStage.show();

        btnDisplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> displayList=dbConnection.getRows(cbxCity.getValue().toString());
                txtArea.clear();
                for(String row: displayList){
                    txtArea.appendText(row+"\n");
                }

            }
        });




    }
}
