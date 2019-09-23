/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Alan Curley
 */
public class FXMLDocumentController implements Initializable{
    Stage parentStage;
    File l5kFile;
    
    @FXML
    private Label label;
    
    @FXML 
    private TextField textField; 
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void makeItSoAction(ActionEvent event){
        
    }
    
    /**
     * Needs the stupid fucking stage to implement
     * https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
     * Mixed with a little "https://stackoverflow.com/questions/14349001/double-click-is-not-being-caught-by-onmouseevent-javafx2" to manage mouse events
     * @param event 
     */
    @FXML
    private void selectPathAction(MouseEvent event){        
        //System.out.println("Ah, Bollocks");
        if ((event.getButton().equals(MouseButton.PRIMARY))&(event.getClickCount()==2)){
        //*
        // Lets see if this Bollocks is the culprit
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("L5K Files", "*.l5k"));
         l5kFile = fileChooser.showOpenDialog(new Stage());
         textField.setText(l5kFile.getPath());
        //*/
        }           
    }
    
    @FXML
    private void enterPathAction(ActionEvent event){
        try{
            l5kFile = new File(textField.getText());
        }
        catch(Exception e){
            System.err.println("F***ed up building a file from path:"+textField.getText());
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStage(Stage thisStage){
        parentStage = thisStage;
    }
    
}
