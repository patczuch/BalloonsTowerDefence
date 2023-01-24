package tower_defence;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Canvas primaryCanvas = new Canvas(800,600);
        Scene primaryScene = new Scene(new Group(primaryCanvas), 800, 600, Color.BLACK);

        primaryStage.setTitle("Balloons Tower Defence");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        primaryCanvas.widthProperty().bind(primaryScene.widthProperty());
        primaryCanvas.heightProperty().bind(primaryScene.heightProperty());
        new GameLoop(new Renderer(primaryCanvas)).start();

    }
}
