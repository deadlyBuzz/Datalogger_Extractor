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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author a_curley
 */
public class FXMLSaveDataController implements Initializable {   
    private ArrayList<dataLogger_Obj> thisData;
    private String pathString;
    private String thisDefaultPath;
    
    @FXML
    private TextField saveFilePathTextField;
    
    @FXML
    private TextArea descriptionTextArea;
    
    @FXML Button SaveButton;

    /**
     * Set the data to be used in the saveData dialog.
     * @param data 
     */
    public void setData(ArrayList<dataLogger_Obj> data){
        thisData = new ArrayList<>(data);
    }
    
    /**
     * Set the path for the save location for default (from where the file was pulled)
     */    
    public void setDefaultPath(String defaultPath){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  // Thanks https://www.javatpoint.com/java-get-current-date
        LocalDateTime now = LocalDateTime.now();
        thisDefaultPath = defaultPath;
        pathString = defaultPath.substring(0, defaultPath.length()-4).concat(dtf.format(now)).concat(".MDL");
        saveFilePathTextField.setText(pathString);
    }
    
    @FXML
    /**
     * When the 'Save' button is pressed - Save the output
     */
    private void saveOutput(ActionEvent event){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  // Thanks https://www.javatpoint.com/java-get-current-date
        LocalDateTime now = LocalDateTime.now();

        try{
            PrintWriter outWriter = new PrintWriter(new BufferedWriter(
            new FileWriter(pathString)),true);
            outWriter.println("DataLogger Export File");
            outWriter.println("DataLoggers Extracted from File ".concat(thisDefaultPath));
            outWriter.println("Extract Performed at ".concat(dtf.format(now)));
            outWriter.println("NOTE: Date of Extract is not neccessarily the date of Capture");
            outWriter.println();
            outWriter.println("Purpose for DataLogger:");
            outWriter.println(descriptionTextArea.getText());
            outWriter.println();
            outWriter.println("<<<<BEGIN DATA>>>>");
            thisData.forEach((n) -> {
                outWriter.println(n.getCSVData());
            });
            
            outWriter.close();
            
        }
        catch(IOException IOE){
            System.err.println("Error Creating MDL Output file.");
            System.err.println(IOE.getMessage());
        }

        //Thank you https://stackoverflow.com/questions/11468800/javafx2-closing-a-stage-substage-from-within-itself
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        //stage.getOnCloseRequest().handle(null);
        stage.close();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
