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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//import org.apache.poi.hssf.*;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
        

/**
 * FXML Controller class
 *
 * @author a_curley
 */
public class FXMLDataTableController implements Initializable {
    TableView thisTableView;
    ArrayList<DataloggerTableEntry> tableData;
    ObservableList<DataloggerTableEntry> tableDataOL;
    
    @FXML
    private Pane contentPane;
    
    @FXML
    private CheckBox showAllCheckbox;
    
    @FXML
    private Button exportButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(ArrayList<dataLogger_Obj> objects){
        tableData = new ArrayList<>();
        for(dataLogger_Obj d: objects)
            tableData.addAll(d.getTableEntries());
        tableDataOL = FXCollections.observableArrayList(tableData);
        
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
        
        TableColumn idCol = new TableColumn();
        idCol.setText("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        TableColumn dataCol = new TableColumn();
        dataCol.setText("Data");
        dataCol.setCellValueFactory(new PropertyValueFactory("data"));
        dataCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        TableColumn timeStampCol = new TableColumn();
        timeStampCol.setText("Time Stamp");
        timeStampCol.setCellValueFactory(new PropertyValueFactory("timeStamp"));
        timeStampCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        TableColumn diffCol = new TableColumn();
        diffCol.setText("Diff");
        diffCol.setCellValueFactory(new PropertyValueFactory("diff"));
        diffCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        
        TableColumn DescriptionCol = new TableColumn();
        DescriptionCol.setText("Description");
        DescriptionCol.setCellValueFactory(new PropertyValueFactory("desc"));
        DescriptionCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        
        
        TableColumn CommentCol = new TableColumn();
        CommentCol.setText("Comment");
        CommentCol.setCellValueFactory(new PropertyValueFactory("comment"));
        CommentCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));    
        
        thisTableView = new TableView();
        thisTableView.setItems(tableDataOL);
        thisTableView.setEditable(true);
        thisTableView.getColumns().addAll(idCol,dataCol,timeStampCol,diffCol,DescriptionCol,CommentCol);
        thisTableView.setPrefWidth(contentPane.getPrefWidth());
        thisTableView.setPrefHeight(contentPane.getPrefHeight());
        thisTableView.autosize();
        try{
            contentPane.getChildren().add(thisTableView);     
        }
        catch(Exception e){
            System.err.println("oh oh, Me Go Boom boom");
            e.printStackTrace(System.err);
        }
        exportButton.setDisable(tableData.isEmpty());
    }

    @FXML
    /**
     * Export the table on the screen to a CSV File with an auto formula
     */
    private void exportData(ActionEvent event){
            FileChooser saveJFC = new FileChooser();
            saveJFC.setTitle("Select where to save the file");
            File saveFile = saveJFC.showSaveDialog(new Stage());
            if(saveFile!=null)
            try{
                PrintWriter outWriter = new PrintWriter(new BufferedWriter(
                                new FileWriter(saveFile)),true);
                outWriter.println("ID,Data,TimeStamp,Diff,Description,Comment");
                for(DataloggerTableEntry f:tableData)                    
                        outWriter.println(f.getCSVLine());
                outWriter.close();
            }
            catch(IOException IOE){
                IOE.printStackTrace(System.err);
            }
        
    }
    
    //@FXML
    /**
     * Export the table on the screen to an XLSX File with auto formula
     */
//    private void exportXLData(ActionEvent event){
//        Workbook wb = new HSSFWorkbook();
//    }
}
