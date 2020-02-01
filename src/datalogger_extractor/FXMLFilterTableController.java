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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author a_curley
 */
public class FXMLFilterTableController implements Initializable {
    Stage parentStage;
    //TableView newTableView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView DataLoggerTableView;
    
    @FXML
    private Button MakeItSoButton;
    
    @FXML
    private TextField FilterTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Don't know if this is needed or not.
     * @param thisStage 
     */
    public void setStage(Stage thisStage){
        parentStage = thisStage;
    }

    /**
     * Allow to set the TableView for filtering.
     * Referenced: https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
     * @param tv 
     */
    public void setTableView(TableView tv){
        //newTableView = tv;
        DataLoggerTableView = tv;
    }
    
    @FXML
    /**
     * ActionEvent for when "Make it so" is pressed.
     */
    private void makeItSoButtonAction(ActionEvent event){
        
    }
    
}
