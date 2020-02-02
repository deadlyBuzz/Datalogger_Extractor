/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alan Curley
 */
public class dataLogger_FilterObj {
    private final BooleanProperty selected;
    private final StringProperty name;
    private final StringProperty description;
    
    public dataLogger_FilterObj(boolean selected, String name, String description){
        this.selected = new SimpleBooleanProperty(selected);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);        
    }
    
    public BooleanProperty selectedProperty() { return selected; }
    
    public StringProperty nameProperty() { return name; }
    
    public StringProperty descriptionProperty() {return description; }
    
}
