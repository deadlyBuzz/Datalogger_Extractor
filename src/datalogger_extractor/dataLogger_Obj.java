/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *  Data Logger Object constructed from String (Or built afterwards)
 * @author a_curley
 */
public class dataLogger_Obj {
    String thisBuildString;
    String objName;
    String objData;
    String objTimeStamps;
    String objDescription;
    Boolean objSelected;
    public String[] thisData;
    
    private final BooleanProperty selected;
    private final StringProperty name;
    private final StringProperty description;
    
    /**
     * Constructor - Needs a string representing the whole object
     * @param buildString 
     */
    public dataLogger_Obj(String buildString){
        thisBuildString = buildString.replaceAll(";", ""); // Get rid of any trailing semicolons.;        
        objName = thisBuildString.replaceAll("^[0-9]+\\W([a-zA-Z0-9_:]+).*", "$1");
        objSelected = false;
        if(thisBuildString.matches("^[^\\(]+\\(Description := \\\"([^\\\"]+)\\\".+"))
            objDescription = thisBuildString.replaceAll("^[^\\(]+\\(Description := \\\"([^\\\"]+)\\\".+", "$1");
        else
            objDescription = objName;        
        thisData = thisBuildString.split("[^\\[]\\[");
        for(int i=0; i<thisData.length; i++){
            thisData[i] = thisData[i].replaceAll("[\\[\\]]", "");
        }
        
        this.selected = new SimpleBooleanProperty(objSelected);
        this.name = new SimpleStringProperty(objName);
        this.description = new SimpleStringProperty(objDescription);
        
    }
    
    public BooleanProperty selectedProperty() {return selected;}
    public StringProperty nameProperty() {return name;}
    public StringProperty descriptionProperty() {return description;}
    
    public boolean isSelected() { return selected.get(); }
    /**
     * Get the Data returned in CSV Row format
     * @return 
     */
    public String getCSVData(){
        StringBuilder returnData = new StringBuilder();
        // Data
        returnData.append(objName);
        returnData.append(" Data:,");
        returnData.append(thisData[1]);
        returnData.append(",\n");
        
        // TimeStamps
        returnData.append(objName);
        returnData.append(" TimeStamps:,");
        returnData.append(thisData[2]);
        returnData.append(",\n");
        
        // Labels
        returnData.append(objName);
        returnData.append(" Description:,");
        for (int i=0; i<100; i++) {
            returnData.append(objDescription.concat(","));
        }
        //returnData.append("\n");
        
        //return objName.concat(" Data:,").concat(thisData[1]).concat("\n").concat(objName).concat(" Timestamps:,").concat(thisData[2]);        
        return returnData.toString();
    }
    
    /**
     * Return the data only in an iterable format.
     */ 
    public ArrayList<ArrayList<String>> getData(){
        // Set up parameters (Probably messy but dont care
        ArrayList<ArrayList<String>> returnData = new ArrayList<>();
        String[] timeStampData = thisData[2].split(",");
        String[] dataData = thisData[1].split(",");
        
        // Loop through and build the DataSet.
        for(int i=0; i<timeStampData.length; i++){
            ArrayList thisEntry = new ArrayList<>();
            thisEntry.add(dataData[i]);
            thisEntry.add(timeStampData[i]);
            thisEntry.add(objDescription);
            returnData.add(thisEntry);
        }
        
        return returnData;
    }
    
    public LineChart.Series getDataSeries(){
//        ArrayList<Double> seriesXData = new ArrayList<>();
//        ArrayList<Double> seriesYData = new ArrayList<>();
        ArrayList<XYChart.Data<Double, Double>> chartData = new ArrayList<>();        
        String[] timeStampData = thisData[2].split(",");
        String[] dataData = thisData[1].split(",");
        
        //try{
            for(int n=1; n<timeStampData.length; n++){
                chartData.add(new XYChart.Data<>(Double.valueOf(timeStampData[n]), Double.valueOf(dataData[n])));
//                seriesXData.add(Double.valueOf(timeStampData[n]));
//                seriesYData.add(Double.valueOf(dataData[n]));
            }
        //}
        //catch(someexception e){
        // System.err.println(e.getMessage());
        //}
        
          
           return new LineChart.Series<>(objName, FXCollections.observableArrayList(chartData));               
    }
    
    public ArrayList<String> getCSVFiltered(){
        
        String[] timeStampData = thisData[2].split(",");
        String[] dataData = thisData[1].split(",");
        ArrayList<String> returnData = new ArrayList<>();
        for(int i=0; i<timeStampData.length; i++){
            returnData.add(dataData[i].concat(",").concat(timeStampData[i]).concat(",\"=OFFSET($A$1,ROW()-1,COLUMN()-2)-IF(ISNUMBER(OFFSET($A$1,ROW()-2,COLUMN()-2)),OFFSET($A$1,ROW()-2,COLUMN()-2),0)\",").concat(this.objDescription));            
        }
        
        return returnData;        
    }
    
    /**
     * Get the maximum value for this datalogger Object
     * @return 
     */
    public int getMaxValue(){
        int maxValue = 0;
        for(String s: thisData[1].split(","))
            if(Integer.parseInt(s)>maxValue)
                maxValue = Integer.parseInt(s);
        return maxValue;
    }
    
    /**
     * Get the Minimum value for this datalogger Object
     * @return 
     */
    public int getMinValue(){
        int minValue = Integer.MAX_VALUE;
        for(String s: thisData[1].split(","))
            if(Integer.parseInt(s)<minValue)
                minValue = Integer.parseInt(s);
        return minValue;
    }

    /**
     * Get the maximum timestamp for this datalogger Object
     * @return 
     */
    public int getMaxTimeStamp(){
        int maxValue = 0;
        for(String s: thisData[2].split(","))
            if(Integer.parseInt(s)>maxValue)
                maxValue = Integer.parseInt(s);
        return maxValue;
    }

    /**
     * Get the minimum value for this datalogger Object
     * @return 
     */

    public int getMinTimeStamp(){
        int minValue = Integer.MAX_VALUE;
        for(String s: thisData[2].split(","))
            if(Integer.parseInt(s)<minValue)
                minValue = Integer.parseInt(s);
        return minValue;
    }

    
}
