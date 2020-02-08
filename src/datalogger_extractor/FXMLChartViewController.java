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
        
        xAxis = new NumberAxis("Time Stamp", 0, 3, 1);
        yAxis = new NumberAxis("Values", 0, 3, 1);
        
        //ArrayList<LineChart.Series> thisSeries = new ArrayList<>();
        ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList();
        for(dataLogger_Obj n: displayData){
            lineChartData.add(n.getDataSeries());
        }
        
        chart = new LineChart(xAxis, yAxis, lineChartData);
        contentPane.getChildren().add(chart);            
    }
    
    @FXML
    private void testFunction(ActionEvent event){
        
    }
}
