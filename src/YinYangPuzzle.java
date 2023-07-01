// Import Package and Libraries

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

//structure button run new game that main game in it;
class RunNewGame extends Application {
    //Variables of apps
    int count = 0;
    int Max_Button_Color;

     int Row_Flex;
     int Col_Flex;
    private GridPane gridPane;
    private Text statusTextOfPuzzle;
    private Alert alert;
    private TextInputDialog input;
    private MediaPlayer mediaPlayer;
    private static final String MUSIC_FILE = "E:/Programming/YinYangPuzzle/src/Nirvana - Something In The Way.mp3";


    private void getRowAndCol(){
        input = new TextInputDialog();
        input.setTitle("input");
        input.setContentText("Enter Row : ");
        input.showAndWait().ifPresent(result ->{
            Row_Flex = Integer.parseInt(result);
        });
        input.setTitle("input");
        input.setContentText("Enter Col : ");
        input.showAndWait().ifPresent(result ->{
            Col_Flex = Integer.parseInt(result);
        });

    }

    private void getMaxButtonColor(){
        input = new TextInputDialog();
        input.setTitle("input");
        input.setContentText("Enter Max_Button_Color : ");
        input.showAndWait().ifPresent(result ->{
            Max_Button_Color = Integer.parseInt(result);
        });
    }

    //create primitive puzzle with gray background
    private void createPrimitivePuzzle() {
        for (int row = 0; row < Row_Flex; row++) {
            for (int col = 0; col < Col_Flex; col++) {
                Button button = new Button();
                button.setStyle("-fx-background-color: gray");
                button.setPrefSize(70, 70);
                gridPane.add(button, row, col);
            }
        }
    }
    //print 10 button puzzle with random Color
    private void randomPainting() {
        int count = 0;
        while (true) {
            int rowRandom = (int) (Math.random() * Row_Flex);
            int colRandom = (int) (Math.random() * Col_Flex);
            int choiceRandomColor = (int) (Math.random() * 2);
            Button button = (Button) gridPane.getChildren().get(rowRandom * Row_Flex + colRandom);
            if(button.getStyle().contains("gray")){
                if (choiceRandomColor == 1) {
                    button.setStyle("-fx-background-color: black");
                    count++;
                } else if(choiceRandomColor == 0) {
                    button.setStyle("-fx-background-color: white");
                    count++;
                }
            }
            if (count == Max_Button_Color) {
                break;
            }
        }

    }
    //method that can handle event listener  of buttons
    private void eventListenButton() {
        for (int row = 0; row < Row_Flex; row++) {
            for (int col = 0; col < Col_Flex; col++) {
                ArrayList<String> colorsOfButton = new ArrayList<>();
                Button button = (Button) gridPane.getChildren().get(row * Col_Flex + col);

                button.setOnMouseClicked((MouseEvent event) -> {
                    colorsOfButton.add(button.getStyle());
                    statusTextOfPuzzle.setText("");
                    // event handler of left click
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (button.getStyle().contains("gray")) {
                            button.setStyle("-fx-background-color: white");
                            colorsOfButton.add(button.getStyle());
                        } else if (button.getStyle().contains("white")) {
                            button.setStyle("-fx-background-color: black");
                            colorsOfButton.add(button.getStyle());
                        } else if (button.getStyle().contains("black")) {
                            button.setStyle("-fx-background-color: white");
                            colorsOfButton.add(button.getStyle());
                        }
                        //if condition 1 is false...
                        if (condition2v2()) {
                            alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Defect in the first condition");
                            alert.setContentText("You select wrong button!");
                            alert.showAndWait();
                            button.setStyle(colorsOfButton.get(colorsOfButton.size() - 2));
                        }
                        //if the game finished without any error;
                        if (winPuzzle()) {
                            statusTextOfPuzzle.setStroke(Color.GREEN);
                            for (int rowX = 0; rowX < Row_Flex; rowX++) {
                                for (int colX = 0; colX < Col_Flex; colX++) {
                                    Button buttonX = (Button) gridPane.getChildren().get(rowX * Col_Flex + colX);
                                    buttonX.setDisable(true);
                                    count++;
                                    if(count==1){
                                        alert = new Alert(AlertType.INFORMATION);
                                        alert.setTitle("Success!");
                                        alert.setContentText("congratulations! You won");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                    }
                    // event handler of left click
                    if (event.getButton() == MouseButton.SECONDARY) {
                        if (button.getStyle().contains("white") || button.getStyle().contains("black")) {
                            button.setStyle("-fx-background-color: gray");
                        }
                        //if condition 1 is false...
                        if (condition2v2()) {
                            alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Defect in the first condition");
                            alert.setContentText("You select wrong button!");
                            alert.showAndWait();
                            button.setStyle(colorsOfButton.get(colorsOfButton.size() - 2));
                        }
                        //if the game finished without any error;
                        if (winPuzzle()) {
                            statusTextOfPuzzle.setStroke(Color.GREEN);
                            for (int rowX = 0; rowX < Row_Flex; rowX++) {
                                for (int colX = 0; colX < Col_Flex; colX++) {
                                    Button buttonX = (Button) gridPane.getChildren().get(rowX * Col_Flex + colX);
                                    buttonX.setDisable(true);
                                    count++;
                                    if(count==1){
                                        alert = new Alert(AlertType.INFORMATION);
                                        alert.setTitle("Success!");
                                        alert.setContentText("congratulations! You won");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                    }
                });
                //handle event listener when entered to mouse
                button.setOnMouseEntered(event -> {
                    button.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(5))));
                });
                //handle event listener when exited to mouse
                button.setOnMouseExited(event -> {
                    button.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(0))));

                });
            }
        }
    }
   // check condition2v2 that be true in any conditions;
    private boolean condition2v2() {
        for (int row = 0; row < Row_Flex - 1; row++) {
            for (int col = 0; col < Col_Flex - 1; col++) {
                Button button1 = (Button) gridPane.getChildren().get(row * Row_Flex + col);
                Button button2 = (Button) gridPane.getChildren().get(row * Row_Flex + (col + 1));
                Button button3 = (Button) gridPane.getChildren().get((row + 1) * Row_Flex + col);
                Button button4 = (Button) gridPane.getChildren().get((row + 1) * Row_Flex + (col + 1));

                if (button1.getStyle().contains("white") || button1.getStyle().contains("black")) {
                    if ((button1.getStyle().equals(button2.getStyle()) && button2.getStyle().equals(button3.getStyle()) && button3.getStyle().equals(button4.getStyle()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // check result of puzzle that check when is run without any error
    private boolean winPuzzle() {
        int count = 0;
        for (int row = 0; row < Row_Flex; row++) {
            for (int col = 0; col < Col_Flex; col++) {
                Button button = (Button) gridPane.getChildren().get(row * Col_Flex + col);
                if (button.getStyle().contains("white") || button.getStyle().contains("black")) {
                    count++;
                }
            }
        }
        if (count == (Row_Flex * Col_Flex)) {
            return true;
        } else {
            return false;
        }
    }

//    private void music(){
//        Media sound = new Media(new File(MUSIC_FILE).toURI().toString());
//        mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
//
//    }

   // overriding start method that implements from application and there we are hera graphics and mainly of game
    @Override
    public void start(Stage stage) throws Exception {

        Stage GameCanvas = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #ff0000, #0000ff)");
        gridPane.setPadding(new Insets(10));
        //create an object from border to use in app
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,                        // Border color
                BorderStrokeStyle.SOLID,            // Border style
                null,                               // CornerRadii
                new BorderWidths(5, 5, 5, 5)        // Border widths (top, right, bottom, left)
        );

        statusTextOfPuzzle = new Text(" ");
        statusTextOfPuzzle.setTextAlignment(TextAlignment.CENTER);
        statusTextOfPuzzle.setY(30);
        statusTextOfPuzzle.setX(180);
        Font font = Font.font("Verdana", FontWeight.BOLD, 18);
        statusTextOfPuzzle.setFont(font);
        Border border = new Border(borderStroke);
        gridPane.setBorder(border);
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        root.getChildren().add(gridPane);
        ///call method that we have need to
        getRowAndCol();
        getMaxButtonColor();
        createPrimitivePuzzle();
        //in here wh he has a delay for randomizing color 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(2), event -> {
            randomPainting();
            eventListenButton();
            //music();

        }));
        timeline.play();
        ///
        root.getChildren().add(statusTextOfPuzzle);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        Image icon = new Image("yin-yang.png");
        GameCanvas.getIcons().add(icon);
        GameCanvas.setTitle("YinYang Puzzle");
        GameCanvas.setWidth((Col_Flex*70)+75);//60
        GameCanvas.setHeight((Row_Flex*70)+133);//130
        GameCanvas.setFullScreen(false);
        GameCanvas.setResizable(false);
        GameCanvas.setScene(scene);
        GameCanvas.show();
    }
}
// class that show information of user
class getInformation extends Application {

    String Name = "Farzin"; // name

    String FamilyName = "Hamzehi"; //family name

    String Project = "Java"; // this STAFF

    long Id = 40117023173L; // user`s id

    // state of get information
    @Override
    public void start(Stage stage) throws Exception {
        Stage GameCanvas = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root,Color.rgb(5,25,30));
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,                        // Border color
                BorderStrokeStyle.SOLID,            // Border style
                null,                               // CornerRadii
                new BorderWidths(5, 5, 5, 5)        // Border widths (top, right, bottom, left)
        );
        // set text in object text with the information that can get in class;
        Text text = new Text();;
        text.setText("Name :"+Name+"\n\n\n"
                +
                "Family Name :"+FamilyName+"\n\n\n"
                +
                 "ID :"+Id);
        text.setX(100);
        text.setY(100);
        Font font = Font.font("Verdana", FontWeight.BOLD, 25);
        text.setFont(font);
        root.getChildren().add(text);
        Image icon = new Image("yin-yang.png");
        GameCanvas.getIcons().add(icon);
        GameCanvas.setTitle("YinYang Puzzle");
        GameCanvas.setWidth(490);
        GameCanvas.setHeight(550);
        GameCanvas.setFullScreen(false);
        GameCanvas.setResizable(false);
        GameCanvas.setScene(scene);
        GameCanvas.show();
    }
}
//Main class of game that have menus
public class YinYangPuzzle extends Application {

    public static void main(String[] args) {
        launch(args);
    }    // YinYang puzzle main

    public void start(Stage stage) throws Exception {    // YinYang puzzle start
        Group root = new Group();  // group of stage that linked app on it
        Scene scene = new Scene(root, Color.DARKGRAY); //scene of stage that linked app on it
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm()); //set background for scene
        Image icon = new Image("yin-yang.png");  //add icon for stage
        stage.getIcons().add(icon);
        stage.setTitle("YinYang Puzzle");  // set title for stage
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("./yin-yang.png"))); // set image on menu on game
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        // set width and height for image
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setX(140);
        imageView.setY(10);
        // text and set property for text
        Text text = new Text("Yin Yang Puzzle");
        Font font = Font.font("Verdana", FontWeight.BOLD, 20); // set Font tor texts elements
        text.setFont(font);
        text.setY(240);
        text.setX(150);
        root.getChildren().add(text);
        Button runNewGameBtn = new Button("New Game");
        runNewGameBtn.getStyleClass().add("dark-blue"); // set classStyle for elements
        root.getChildren().add(runNewGameBtn);
        stage.setHeight(500);
        stage.setWidth(500);
        runNewGameBtn.setLayoutX(200);
        runNewGameBtn.setLayoutY(265);
        Button loadOldGame = new Button("Load Game");
        loadOldGame.getStyleClass().add("dark-blue");// set classStyle for elements
        root.getChildren().add(loadOldGame);
        loadOldGame.setLayoutY(310);
        loadOldGame.setLayoutX(200);
        Button info = new Button("Information");
        info.getStyleClass().add("dark-blue");// set classStyle for elements
        info.setLayoutY(355);
        info.setLayoutX(200);
        root.getChildren().add(info);
        stage.setResizable(false);
        // if click runNewGame this event listener happens...
        runNewGameBtn.setOnAction(actionEvent -> {
            RunNewGame newGame = new RunNewGame();
            try {
                newGame.start(stage);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // if click get information this event listener happens...
        getInformation getInformation = new getInformation();
        info.setOnAction(actionEvent -> {
            try {
                getInformation.start(stage);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        stage.setScene(scene);   // add scene to stage
        stage.show();
    }
}