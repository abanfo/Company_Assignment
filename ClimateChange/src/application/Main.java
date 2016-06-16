package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			/*
			CalculationClass calculation = new CalculationClass();
			
			
			calculation.calculate(20, 35, 2);
			 ArrayList<Double> horses= calculation.getPopulationHorsesWholePeriod();
			 ArrayList<Double> cattle= calculation.getPopulationCattleWholePeriod();
			 int i=0;
			for (double listItem : cattle){
				System.out.println("population cattle in month"+i+": "+listItem);
				i++;
			}
			i=0;
			for (double listItem : horses){
				System.out.println("population cattle in month"+i+": "+listItem);
				i++;
			}
			*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
