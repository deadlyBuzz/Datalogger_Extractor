/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Alan Curley
 */
public class FXMLChartViewController implements Initializable {
    ArrayList<dataLogger_Obj> displayData;
    private LineChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Pane contentPane;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button showButton;
    
    @FXML
    private CheckBox alldataCheckBox;
    
    @FXML
    private Button testButton;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(ArrayList<dataLogger_Obj> data){
        displayData = data;
        int upperTimeBound = 0;
        int lowerTimeBound = 0; //Integer.MAX_VALUE;
        int upperDataBound = 0;
        int lowerDataBound = Integer.MAX_VALUE;
        
        
        //ArrayList<LineChart.Series> thisSeries = new ArrayList<>();
        ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList();
        for(dataLogger_Obj n: displayData){
            lineChartData.add(n.getDataSeries());
            if(n.getMaxValue()>upperDataBound)
                upperDataBound = n.getMaxValue();
            if(n.getMinValue()<lowerDataBound)
                lowerDataBound = n.getMinValue();
            if(n.getMaxTimeStamp()>upperTimeBound)
                upperTimeBound = n.getMaxTimeStamp();
            if(n.getMinTimeStamp()>lowerTimeBound)
                lowerTimeBound = n.getMinTimeStamp();
        }

        xAxis = new NumberAxis("Time Stamp", lowerTimeBound, upperTimeBound, 10000);
        //xAxis = new NumberAxis();
        //xAxis.setLabel("Time Stamp");
        yAxis = new NumberAxis("Values", lowerDataBound, upperDataBound, 10000);
        //yAxis = new NumberAxis();
        //yAxis.setLabel("Values");
        
        chart = new LineChart(xAxis, yAxis, lineChartData);        
        contentPane.getChildren().add(chart);            
    }
    
    @FXML
    private void testFunction(ActionEvent event){
        
    }
}
