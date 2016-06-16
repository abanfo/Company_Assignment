package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;

import javafx.geometry.Insets;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.TextField;

public class view extends Application {
	Button button;
	Stage windows;
	Scene p_scene, l_scene;
	String content="";
	TextArea textArea= new TextArea(content);

	 final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
	XYChart.Series series = new XYChart.Series();
	XYChart.Series series2 = new XYChart.Series();
	 GridPane grid = new GridPane();
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	    public void start(Stage primaryStage) throws Exception {
		 windows = primaryStage;
		 windows.setTitle("climate change");

	        //linechart=layout
	       
	       
	        Scene p_scene = new Scene(grid, 800, 600);
	     //   layout1.getChildren().addAll(goback);
	        
	        //GridPane with 10px padding around edge
	        
	        grid.setPadding(new Insets(10, 10, 10, 10));
	        grid.setVgap(8);
	        grid.setHgap(10);
	        grid.getColumnConstraints().setAll(
	                ColumnConstraintsBuilder.create().percentWidth(100/3.0).build(),
	                ColumnConstraintsBuilder.create().percentWidth(100/3.0).build(),
	                ColumnConstraintsBuilder.create().percentWidth(100/3.0).build()
	        );
	        //grid.setGridLinesVisible(true);
	        Label temLabel = new Label("temperature chaneg over 10 years:");
	        GridPane.setConstraints(temLabel, 0, 0);

	        TextField tempChangeInput = new TextField("2");
	        GridPane.setConstraints(tempChangeInput, 1, 0);
	        
	        Label cattleLabel = new Label("initial Cattle Population:");
	        GridPane.setConstraints(cattleLabel, 0, 1);

	        TextField cattleInput = new TextField("30");
	        GridPane.setConstraints(cattleInput, 1, 1);
	        
	        Label horseLabel = new Label("initial Horse Population:");
	        GridPane.setConstraints(horseLabel, 0, 2);

	        TextField horseInput = new TextField("40");
	        GridPane.setConstraints(horseInput, 1, 2);
	       
	        Button predictButton = new Button("predict");
	        GridPane.setConstraints(predictButton, 0, 3);
	        GridPane.setConstraints(lineChart, 0, 4, 3, 1);
	        

	        

	        //Add everything to grid
	        grid.getChildren().addAll(temLabel, tempChangeInput, cattleLabel, cattleInput,horseLabel, horseInput, predictButton);
	    
	        windows.setScene(p_scene);
	  
	        windows.show();
	        //windows.setScene(l_scene);
	   

	        
	       
	        CalculationClass calculation = new CalculationClass();
	        predictButton.setOnAction(e -> { 
	        	cleanData();
	            int numberHorses = Integer.parseInt(horseInput.getText());;
		        int numberCattle = Integer.parseInt(cattleInput.getText());;
		        double tempchange = Double.parseDouble(tempChangeInput.getText());;
	        	
	        	System.out.println(numberHorses+" "+ numberCattle +" "+ tempchange);
	 	        calculation.calculate(numberHorses, numberCattle, tempchange);
	        	createChart(calculation);
		        grid.getChildren().add(lineChart);

		        
		        grid.getChildren().add(textArea);
		        GridPane.setConstraints(textArea, 0, 5, 3, 1);
	        }
	       );
	        
	}
	public void cleanData(){

			
		
if(grid.getChildren().contains(lineChart)){
    grid.getChildren().remove(lineChart);
    grid.getChildren().remove(textArea);
	content="";
	
	series.getData().clear();
	series2.getData().clear();
	lineChart.getData().remove(series);
	lineChart.getData().remove(series2);
}


	
	}
 

	private void createChart(CalculationClass calculation) {

	

		xAxis.setLabel("months");
	
		lineChart.setTitle("prediction");

		series.setName("Cattle");
		series2.setName("Horses");

		// for horses:
		ArrayList<Double> horses = calculation.getPopulationHorsesWholePeriod();
		ArrayList<Double> cattle = calculation.getPopulationCattleWholePeriod();

		int month = 0;
		int yearCount=0;
		for (double listItem : horses) {
			
	
				series.getData().add(new XYChart.Data(month, listItem));
				
			
			
			System.out.println("population horses in month"+month+": "+listItem);
			textArea.setText(textArea.getText()+"population horses in month"+month+": "+listItem+"\n");
			month++;
		}
		month = 0;
		 yearCount=0;
		for (double listItem : cattle) {

		
			series2.getData().add(new XYChart.Data(month, listItem));
			
			System.out.println("population cattle in month"+month+": "+listItem);
			textArea.setText(textArea.getText()+"population cattle in month"+month+": "+listItem+"\n");
			month++;
		}
	
		lineChart.getData().add(series);
		lineChart.getData().add(series2);
		
		lineChart.setCreateSymbols(false);
        
        
       
	}

}
