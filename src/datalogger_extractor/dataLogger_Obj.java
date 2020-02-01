/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.util.ArrayList;

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
    public String[] thisData;
    public dataLogger_Obj(){
        
    }
    
    public dataLogger_Obj(String buildString){
        thisBuildString = buildString.replaceAll(";", ""); // Get rid of any trailing semicolons.;        
        objName = thisBuildString.replaceAll("^[0-9]+\\W([a-zA-Z0-9_:]+).*", "$1");
        if(thisBuildString.matches("^[^\\(]+\\(Description := \\\"([^\\\"]+)\\\".+"))
            objDescription = thisBuildString.replaceAll("^[^\\(]+\\(Description := \\\"([^\\\"]+)\\\".+", "$1");
        else
            objDescription = objName;        
        thisData = thisBuildString.split("[^\\[]\\[");
        for(int i=0; i<thisData.length; i++){
            thisData[i] = thisData[i].replaceAll("[\\[\\]]", "");
        }
    }
    
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
}
