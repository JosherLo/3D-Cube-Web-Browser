package application;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    private double X, Y;
    private double rotationAngleX = 0;
    private double rotationAngleY = 0;
    private DoubleProperty angleX = new SimpleDoubleProperty(80);
    private DoubleProperty angleY = new SimpleDoubleProperty(30);
    private Group group;
    public static int HEIGHT = 800;
    public static int WIDTH = 800;
    public static int DEPTH = 800;

    @Override
    public void start(Stage stage) throws Exception {
        Group group1 = new Group();
        this.group = new Group();
        Box box = new Box(WIDTH, HEIGHT , DEPTH);
        this.group.getChildren().add(box);

        // top left front back right bottom

        StackPane[] stackPanes = new StackPane[6];
        Rotate[] rotations = {new Rotate(-90, Rotate.X_AXIS), new Rotate(90, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Y_AXIS), new Rotate(180, Rotate.Y_AXIS),
                new Rotate(-90, Rotate.Y_AXIS), new Rotate(90, Rotate.X_AXIS)};
        double[] tz = {DEPTH/2.0, DEPTH/2.0, -DEPTH/2.0-1, DEPTH/2.0+1, -DEPTH/2.0, -DEPTH/2.0};
        double[] tx = {-WIDTH/2.0, -WIDTH/2.0-1, -WIDTH/2.0, WIDTH/2.0, WIDTH/2.0+1, -WIDTH/2.0};
        double[] ty = {-HEIGHT/2.0-1, -HEIGHT/2.0, -HEIGHT/2.0, -HEIGHT/2.0, -HEIGHT/2.0, HEIGHT/2.0+1};

        for (int i=0;i<6;i++) {
            StackPane stackPane = new StackPane();
            WebView webView = new WebView();
            webView.getEngine().load("https://www.google.com");
            this.group.getChildren().add(stackPane);
            stackPane.getTransforms().add(rotations[i]);
            stackPane.translateZProperty().set(tz[i]);
            stackPane.translateXProperty().set(tx[i]);
            stackPane.translateYProperty().set(ty[i]);
            stackPane.getChildren().add(webView);
            stackPane.setPrefSize(WIDTH, HEIGHT);
            stackPane.setMinSize(WIDTH, HEIGHT);
            stackPane.setMaxSize(WIDTH, HEIGHT);
            stackPanes[i] = stackPane;
        }

        group1.getChildren().add(this.group);
        group.translateYProperty().set(-150);
        Scene scene = new Scene(group1, 1000, 800, true);
        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);
        scene.setFill(Color.SILVER);
        stage.setScene(scene);
        stage.show();
        initEvents(scene, stage);
        group1.translateXProperty().bind(scene.widthProperty().divide(2));
        group1.translateYProperty().bind(scene.heightProperty().divide(2));
        this.group.translateYProperty().set(-10);
        group.translateZProperty().set(1000);

//        Scanner s = new Scanner(System.in);
//        while (true) {
//            System.out.print("Change URL of: ");
//            int fn = s.nextInt();
//            System.out.print("to: ");
//            String url = s.nextLine();
//        }
    }

    public void initEvents(Scene scene, Stage stage) {
        Rotate rotationX;
        Rotate rotationY;

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
        });


        stage.addEventHandler(ScrollEvent.SCROLL, se -> {
            double change = se.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() - change);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
