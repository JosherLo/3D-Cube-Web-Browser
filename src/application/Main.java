package application;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    private double X, Y;
    private double rotationAngleX = 0;
    private double rotationAngleY = 0;
    private DoubleProperty angleX = new SimpleDoubleProperty(31);
    private DoubleProperty angleY = new SimpleDoubleProperty(32);
    private Group group;
    public static int HEIGHT = 800;
    public static int WIDTH = 800;
    public static int DEPTH = 800;
    private Rotate rotationX, rotationY;

    @Override
    public void start(Stage stage) throws Exception {
        Group group1 = new Group();
        this.group = new Group();
        Box box = new Box(WIDTH, HEIGHT , DEPTH);
        this.group.getChildren().add(box);

        String[] sides = {"Top", "Left", "Front", "Back", "Right", "Bottom"};
        Rotate[] rotations = {new Rotate(-90, Rotate.X_AXIS), new Rotate(90, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Y_AXIS), new Rotate(180, Rotate.Y_AXIS),
                new Rotate(-90, Rotate.Y_AXIS), new Rotate(90, Rotate.X_AXIS)};
        double[] tz = {DEPTH/2.0, DEPTH/2.0, -DEPTH/2.0-1, DEPTH/2.0+1, -DEPTH/2.0, -DEPTH/2.0};
        double[] tx = {-WIDTH/2.0, -WIDTH/2.0-1, -WIDTH/2.0, WIDTH/2.0, WIDTH/2.0+1, -WIDTH/2.0};
        double[] ty = {-HEIGHT/2.0-1, -HEIGHT/2.0, -HEIGHT/2.0, -HEIGHT/2.0, -HEIGHT/2.0, HEIGHT/2.0+1};

        for (int i=0;i<6;i++) {
            VBox vbox = new VBox();
            WebView webView = new WebView();
            webView.getEngine().load("https://www.google.com");
            vbox.getTransforms().add(rotations[i]);
            vbox.translateZProperty().set(tz[i]);
            vbox.translateXProperty().set(tx[i]);
            vbox.translateYProperty().set(ty[i]);

            webView.setPrefSize(WIDTH, HEIGHT);

            HBox hbox = new HBox();
            TextField tf = new TextField("https://www.google.com");
            tf.setPromptText("Enter URL...");
            tf.setPrefWidth(WIDTH*0.85);
            tf.setFont(new Font(25));
            Button button = new Button(sides[i]);
            button.setPrefWidth(WIDTH*0.15);
            button.setFont(new Font(25));

            tf.setOnMouseClicked(e -> {
                if (tf.getText().equals("")) { tf.setText("https://"); tf.positionCaret(8); }
            });
            tf.setOnKeyPressed(e -> { switch (e.getCode()) {
                case ENTER: button.requestFocus(); webView.getEngine().load(tf.getText());
            }});
            button.setOnMouseClicked(e -> { webView.getEngine().load(tf.getText()); });

            hbox.getChildren().addAll(tf, button);
            vbox.getChildren().addAll(hbox, webView);
            vbox.setPrefSize(WIDTH, HEIGHT);
            vbox.setMinSize(WIDTH, HEIGHT);
            vbox.setMaxSize(WIDTH, HEIGHT);
            this.group.getChildren().add(vbox);
        }

        group1.getChildren().add(this.group);
        group.translateYProperty().set(-150);
        Scene scene = new Scene(group1, 1000, 1000, true);
        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);
        scene.setFill(Color.SILVER);
        stage.setScene(scene);
        stage.show();

        this.group.getTransforms().addAll(
                rotationX = new Rotate(0, Rotate.X_AXIS),
                rotationY = new Rotate(0, Rotate.Y_AXIS)
        );

        rotationX.angleProperty().bind(this.angleX);
        rotationY.angleProperty().bind(this.angleY);

        scene.setOnMousePressed(pe -> {
            this.X = pe.getSceneX();
            this.Y = pe.getSceneY();
            this.rotationAngleX = this.angleX.get();
            this.rotationAngleY = this.angleY.get();
        });

        scene.setOnMouseDragged(de -> {
            this.angleX.set(this.rotationAngleX - (this.Y - de.getSceneY()));
            this.angleY.set(this.rotationAngleY + (this.X - de.getSceneX()));
            System.out.println(angleX);
            System.out.println(angleY);
        });


        stage.addEventHandler(ScrollEvent.SCROLL, se -> {
            double change = se.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() - change);
        });

        group1.translateXProperty().bind(scene.widthProperty().divide(2));
        group1.translateYProperty().bind(scene.heightProperty().divide(2));
        group.translateYProperty().set(10);
        group.translateZProperty().set(1000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
