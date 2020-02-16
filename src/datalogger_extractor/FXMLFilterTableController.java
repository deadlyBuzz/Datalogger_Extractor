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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.*;
import java.lang.Boolean;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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
    TableView tableView;
    //TableView newTableView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView DataLoggerTableView;
    
    @FXML
    private Button EngageButton;
    
    @FXML
    private Button outputButton;
    
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
        
        tableView = new TableView();
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
    
    @FXML
    private void outputFiltered(ActionEvent event){
        //System.out.println("Make it so pressed - for debug sake.");
        TableView tempData = (TableView)contentPane.getChildren().get(0);
        ObservableList<dataLogger_Obj> items = tempData.getItems();
        System.out.println("Data,TimeStamp,Diff,Desc");
        items.stream().filter((n) -> (n.isSelected())).forEachOrdered((n) -> { // Updated from netbeans suggestions.
            n.getCSVFiltered().forEach((s) -> {
                System.out.println(s);
                //System.out.println(n.getCSVData());
            });
        });
    }
    
    @FXML
    private void filterTableData(ActionEvent event){
        if(FilterTextField.getText().length()>0)
            try{
                // Start by checking if the filter is a genuine Regex   
                Pattern.compile(FilterTextField.getText());
                ArrayList<dataLogger_Obj> filterArray = new ArrayList<>();
                for(dataLogger_Obj d: masterData)
                    if(d.objName.matches(FilterTextField.getText()))
                        filterArray.add(d);
                tableView.setItems(FXCollections.observableList(filterArray));
            }
            catch(PatternSyntaxException e){

            }
    }
    @FXML
    private void showChartView(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLChartView.fxml"));
            //Scene scene = new Scene((Pane)fxmlLoader.load());            
                        
            Stage newStage = new Stage();
            newStage.setScene(new Scene((Pane)fxmlLoader.load()));

            FXMLChartViewController controller = fxmlLoader.<FXMLChartViewController>getController();
            ArrayList<dataLogger_Obj> filteredData = new ArrayList<>();
            for(dataLogger_Obj n: masterData)
                if(n.isSelected())
                    filteredData.add(n);
                
            controller.setData(filteredData);

            newStage.show();            
        }
        catch(IOException ioe){
            ioe.printStackTrace(System.err);
        }
    }
    
    @FXML
    private void saveOutput(ActionEvent event){
        Pattern.compile(FilterTextField.getText());
        ArrayList<dataLogger_Obj> filterArray = new ArrayList<>();
        for(dataLogger_Obj d: masterData)
            if(d.objName.matches(FilterTextField.getText()))
                filterArray.add(d);

        if(filterArray.size()>0){
            FileChooser saveJFC = new FileChooser();
            saveJFC.setTitle("Select where to save the file");
            File saveFile = saveJFC.showSaveDialog(new Stage());
            if(saveFile!=null)
            try{
                PrintWriter outWriter = new PrintWriter(new BufferedWriter(
                                new FileWriter(saveFile)),true);
                    for(dataLogger_Obj f:filterArray)
                        for(String s:f.getCSVFiltered())
                        outWriter.println(s);                                
            }
            catch(IOException IOE){
                IOE.printStackTrace(System.err);
            }
            //label.setText("Saving Complete");
        }   
    }
    
}
