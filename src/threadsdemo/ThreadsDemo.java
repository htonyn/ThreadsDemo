package threadsdemo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadsDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(8);
        
        TextArea text = new TextArea();
        text.setEditable(false);
        root.getChildren().add(text);
        VBox.setVgrow(text, Priority.ALWAYS);
        
        Button startButton = new Button("Start");
        startButton.setOnAction(
            (ActionEvent event) -> {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            // pass, fuck it
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                text.appendText("Run completed!\n");
                            }
                        });
                        
                        Platform.runLater(
                            () -> text.appendText("Lambda yo\n")
                        );
                    }
                };
                th.start();
            }            
        );
        
        root.getChildren().add(startButton);
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setScene(scene);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
        StringBuffer sb = new StringBuffer();
        
        ContesterThread c1 = new ContesterThread(sb);
        ContesterThread c2 = new ContesterThread(sb);
        
        c1.start();
        c2.start();
        
        try {
            c1.join();
            c2.join();
        } catch (InterruptedException e) {
            System.err.println("Managed exceptions are unforgivably stupid: "+e);
        }
        
        System.out.println(sb);
    }
    
}
