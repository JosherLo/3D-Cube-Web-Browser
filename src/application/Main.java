package application;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
    private double X, Y;
    private double rotationAngleX = 0;
    private double rotationAngleY = 0;
    private DoubleProperty angleX = new SimpleDoubleProperty(80);
    private DoubleProperty angleY = new SimpleDoubleProperty(30);
    private Group group;
    public static int HEIGHT = 700;
    public static int WIDTH = 500;
    public static int DEPTH = 400;

    @Override
    public void start(Stage stage) throws Exception {
        Group group1 = new Group();
        this.group = new Group();
        Box box = new Box(WIDTH, HEIGHT , DEPTH);
        this.group.getChildren().add(box);
        StackPane stackPaneFront = new StackPane();
        WebView webViewFront = new WebView();
        webViewFront.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneFront);
        stackPaneFront.translateZProperty().set(-DEPTH/2.0 - 1);
        stackPaneFront.translateXProperty().set(-WIDTH/2.0);
        stackPaneFront.translateYProperty().set(-HEIGHT/2.0);
        stackPaneFront.getChildren().add(webViewFront);
        stackPaneFront.setPrefSize(WIDTH, HEIGHT);
        stackPaneFront.setMinSize(WIDTH, HEIGHT);
        stackPaneFront.setMaxSize(WIDTH, HEIGHT);
        StackPane stackPaneBack = new StackPane();
        WebView webViewBack = new WebView();
        webViewBack.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneBack);
        stackPaneBack.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
        stackPaneBack.translateZProperty().set(DEPTH/2.0 + 1.0);
        stackPaneBack.translateXProperty().set(WIDTH/2.0);
        stackPaneBack.translateYProperty().set(-HEIGHT/2.0);
        stackPaneBack.getChildren().add(webViewBack);
        stackPaneBack.setPrefSize(WIDTH, HEIGHT);
        stackPaneBack.setMinSize(WIDTH, HEIGHT);
        stackPaneBack.setMaxSize(WIDTH, HEIGHT);
        StackPane stackPaneLeft = new StackPane();
        WebView webViewLeft = new WebView();
        webViewLeft.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneLeft);
        stackPaneLeft.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        stackPaneLeft.translateZProperty().set(DEPTH/2.0);
        stackPaneLeft.translateXProperty().set(-WIDTH/2.0 - 1);
        stackPaneLeft.translateYProperty().set(-HEIGHT/2.0);
        stackPaneLeft.getChildren().add(webViewLeft);
        stackPaneLeft.setPrefSize(DEPTH, HEIGHT);
        stackPaneLeft.setMinSize(DEPTH, HEIGHT);
        stackPaneLeft.setMaxSize(DEPTH, HEIGHT);
        StackPane stackPaneRight = new StackPane();
        WebView webViewRight = new WebView();
        webViewRight.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneRight);
        stackPaneRight.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));
        stackPaneRight.translateZProperty().set(-DEPTH/2.0);
        stackPaneRight.translateXProperty().set(WIDTH/2.0 + 1);
        stackPaneRight.translateYProperty().set(-HEIGHT/2.0);
        stackPaneRight.getChildren().add(webViewRight);
        stackPaneRight.setPrefSize(DEPTH, HEIGHT);
        stackPaneRight.setMinSize(DEPTH, HEIGHT);
        stackPaneRight.setMaxSize(DEPTH, HEIGHT);
        StackPane stackPaneBottom = new StackPane();
        WebView webViewBottom = new WebView();
        webViewBottom.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneBottom);
        stackPaneBottom.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        stackPaneBottom.translateZProperty().set(-DEPTH/2.0);
        stackPaneBottom.translateXProperty().set(-WIDTH/2.0);
        stackPaneBottom.translateYProperty().set(HEIGHT/2.0 + 1);
        stackPaneBottom.getChildren().add(webViewBottom);
        stackPaneBottom.setPrefSize(WIDTH, DEPTH);
        stackPaneBottom.setMinSize(WIDTH, DEPTH);
        stackPaneBottom.setMaxSize(WIDTH, DEPTH);
        StackPane stackPaneTop = new StackPane();
        WebView webViewTop = new WebView();
        webViewTop.getEngine().load("https://www.google.com");
        this.group.getChildren().add(stackPaneTop);
        stackPaneTop.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));
        stackPaneTop.translateZProperty().set(DEPTH/2.0);
        stackPaneTop.translateXProperty().set(-WIDTH/2.0);
        stackPaneTop.translateYProperty().set(-HEIGHT/2.0 - 1);
        stackPaneTop.getChildren().add(webViewTop);
        stackPaneTop.setPrefSize(WIDTH, DEPTH);
        stackPaneTop.setMinSize(WIDTH, DEPTH);
        stackPaneTop.setMaxSize(WIDTH, DEPTH);
        group1.getChildren().add(this.group);
        group.translateYProperty().set(-150);
        Scene scene = new Scene(group1, 600, 600, true);
        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);
        scene.setFill(Color.GRAY);
        stage.setScene(scene);
        stage.show();
        initEvents(scene, stage);
        group1.translateXProperty().bind(scene.widthProperty().divide(2));
        group1.translateYProperty().bind(scene.heightProperty().divide(2));
        this.group.translateYProperty().set(-10);
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
