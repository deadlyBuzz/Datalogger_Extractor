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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.lang.Boolean;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author a_curley
 */
public class FXMLFilterTableController implements Initializable {
    Stage parentStage;
    ArrayList<dataLogger_Obj> masterData;
    ObservableList<dataLogger_Obj> tableData;
    //TableView newTableView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView DataLoggerTableView;
    
    @FXML
    private Button EngageButton;
    
    @FXML
    private TextField FilterTextField;
    
    @FXML
    private Pane contentPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Initialising");
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
    
    /**
     * Set the data to be filtered and displayed through public access
     * @param data 
     */
    public void setData(ArrayList<dataLogger_Obj> data){
        masterData = new ArrayList<>(data);
        tableData = FXCollections.observableArrayList(masterData);
        
        
        //Not sure what this but its in the demo??!??  Ensemble8
        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }
 
            @Override
            public Object fromString(String string) {
                return string;
            }
        };
        
        // Data has now been populated, Build the table        
        TableColumn selectedCol = new TableColumn();
        selectedCol.setText("Selected");
        selectedCol.setMinWidth(70);
        selectedCol.setCellValueFactory(new PropertyValueFactory("selected")); // This is important - "Selected" doesnt work
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        
        TableColumn nameCol = new TableColumn();
        nameCol.setText("name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn descriptionCol = new TableColumn();
        descriptionCol.setText("description");
        descriptionCol.setMinWidth(200);
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        TableView tableView = new TableView();
        //DataLoggerTableView.setItems(tableData);
        tableView.setItems(tableData);
        tableView.setEditable(true);
        tableView.setPrefHeight(contentPane.getPrefHeight());
        tableView.setPrefWidth(contentPane.getPrefWidth());
        tableView.autosize();
        tableView.getColumns().addAll(selectedCol, nameCol, descriptionCol);
        contentPane.getChildren().add(tableView);
    }
    
    @FXML
    private void GenerateOutput(ActionEvent event){
        System.out.println("Make it so pressed - for debug sake.");
        TableView tempData = (TableView)contentPane.getChildren().get(0);
        ObservableList<dataLogger_Obj> items = tempData.getItems();
        for(dataLogger_Obj n: items){
            if(n.isSelected())
                System.out.println(n.nameProperty().get());
        }
        //DataLoggerTableView.refresh();
    }
    
}
