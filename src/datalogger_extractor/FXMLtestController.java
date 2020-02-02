/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class
 *
 * @author Alan Curley
 */
public class FXMLtestController implements Initializable {

    @FXML
    private Label label;
    /**
     * Initializes the controller class.
     */
    
    public void setLabelText(String text){
        label.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        label.setText("This is a label - Initialised values");
    }    
    
}
