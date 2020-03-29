/*
 * Copyright 2020 a_curley.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package datalogger_extractor;
    
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is a class that represents an entry in a table showing data 
 * @author a_curley
 */
public class DataloggerTableEntry {
    private final IntegerProperty id;
    private final IntegerProperty data;
    private final DoubleProperty timeStamp;
    private final DoubleProperty diff;
    private final StringProperty desc;
    private final StringProperty comment;
    
    /**
     * Default constructor - creates an empty entry
     */
    public DataloggerTableEntry(){
        this.id = new SimpleIntegerProperty(0);
        this.data = new SimpleIntegerProperty(0);
        this.timeStamp = new SimpleDoubleProperty(0);
        this.diff = new SimpleDoubleProperty(0);
        this.desc = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
    }
    
    /**
     * Constructor for a DataloggerTableEntry
     * @param dataEntry CSV String in the format "data(Integer),timestamp(Double),Description(string)
     */
    public DataloggerTableEntry(String dataEntry){
        this();
        String[] tempString = dataEntry.split(",");
        if(tempString.length==3){
            setData(Integer.valueOf(tempString[0]));
            setTimeStamp(Double.valueOf(tempString[1]));
            setDesc(tempString[2]);
        }                                
    }
    
    /**
     * Constructor for DataloggerTableEntry
     * @param newData (String) data entry 
     * @param newTimeStamp (String) timestamp
     * @param newDesc (String) Description
     */
    public DataloggerTableEntry(String newData, String newTimeStamp, String newDesc){
        this();
        setData(Integer.valueOf(newData));
        setTimeStamp(Double.valueOf(newTimeStamp));
        setDesc(newDesc);
    }
    
    /**
     * Constructor for DataloggerTableEntry
     * @param newData   
     * @param newTimeStamp
     * @param newDesc 
     */
    public DataloggerTableEntry(Integer newData, Double newTimeStamp, String newDesc){
        this();
        setData(newData);
        setTimeStamp(newTimeStamp);
        setDesc(newDesc);
    }
    
    /* Property return routines */
    public IntegerProperty idProperty() {return id;}
    public IntegerProperty dataProperty() {return data;}
    public DoubleProperty timeStampProperty() {return timeStamp;}
    public DoubleProperty diffProperty() {return diff;}
    public StringProperty descProperty() { return desc; }
    public StringProperty commentProperty() { return comment; }
    
    /* Getters */
    public Integer getID() { return this.id.getValue();}
    public Integer getData() { return this.data.getValue();}
    public Double getTimeStamp() { return this.timeStamp.getValue();}
    public Double getDiff() { return this.diff.getValue();}
    public String getDesc() { return this.desc.getValue();}
    public String getComment() { return this.comment.getValue();}
    
    /* Setters */
    public void setID(Integer newID){ this.id.setValue(newID); }
    public void setData(Integer newData){ this.data.setValue(newData); }
    public void setTimeStamp(Double newTimeStamp) { this.timeStamp.setValue(newTimeStamp);}
    public void setDiff(Double newDiff) { this.diff.setValue(newDiff);}
    public void setDesc(String newDesc) { this.desc.setValue(newDesc); }
    public void setComment(String newComment) { this.comment.setValue(newComment); }
    
    /* Custom methods */
    public String getCSVLine(){
        StringBuilder returnString = new StringBuilder();
        returnString.append(this.id.getValue());
        returnString.append(",");     
        returnString.append(this.data.getValue());
        returnString.append(",");     
        returnString.append(this.timeStamp.getValue());
        returnString.append(",");     
        returnString.append("\"=OFFSET($A$1,ROW()-1,COLUMN()-2)-IF(ISNUMBER(OFFSET($A$1,ROW()-2,COLUMN()-2)),OFFSET($A$1,ROW()-2,COLUMN()-2),0)\"");
        returnString.append(",");     
        returnString.append(this.desc.getValue());
        returnString.append(",");     
        returnString.append(this.comment.getValue());
        
        return returnString.toString();
    }
    
}
