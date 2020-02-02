/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger_extractor;

import java.io.*;
import static java.lang.Boolean.TRUE;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan Curley
 */
public class FXMLDocumentController implements Initializable{
    Stage parentStage;
    File l5kFile;
    
    String regexTagMatch = "^[\t]+TAG";
    String regexEndTagMatch = "^[\t]+END_TAG";
    String findMatch = "^\\t+([a_zA-Z0-9_]+)\\W: udt_AOI_RX1_DataLogger_ResultSet (\\(Description := ([^)]+)\\))?.+";
    String endMatch = "^\\t+[^\\]]+\\]\\];";
    String programName = "^\\t*PROGRAM\\W+([a-zA-Z0-9_]+).+";
    ArrayList<String> masterStringData;
    ArrayList<dataLogger_Obj> masterData;
    
    StringBuilder masterOutput = new StringBuilder();
    
    @FXML
    private Button button2;
    
    @FXML
    private Label label;
    
    @FXML 
    private TextField textField; 
    
    @FXML
    private Button testButton;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Default Handle button action event - replaced with makeItSoAction
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void makeItSoAction(ActionEvent event){
        // Have a look at https://github.com/deadlyBuzz/STLAnalyser/blob/master/src/stlanalyser/stlAnalyserWindow.java DoInBackground()
        // Opens a file via a reader and iterates through it line by line.
        String readLine = new String();
        StringBuilder objString = new StringBuilder();
        boolean tagMode = false;
        boolean findMode = false;        
        Integer entryNumber = 1;
        Integer lineNumber = 0;
        Integer caseNo = 0;
        String progName = "";
        masterOutput = new StringBuilder();
        try{
            BufferedReader fileReader;
            fileReader = new BufferedReader(new FileReader(l5kFile));
            readLine = fileReader.readLine();            
            masterStringData = new ArrayList<>();
            masterData = new ArrayList<>();
            
            do{
                if(readLine!=null){
                    lineNumber++;
//                    if(lineNumber==67564)
//                        System.out.println("debug");
                    if(readLine.matches(programName))
                        progName = readLine.replaceAll(programName, "$1").concat(":");
                    switch(caseNo){
                        // Static operation - Looking for a tag region
                        case 0:
                        // Have read a line into the system.
                            if(readLine.matches(regexTagMatch)){
                                caseNo = 1;
                            }
                            break;
                        // Found Tag Region - Looking for either end of Tag region
                        // or a new tag to find.
                        case 1:
                            if(readLine.matches(regexEndTagMatch))
                                caseNo = 0;
                            else if(readLine.matches(findMatch)){
                                caseNo = 2;
                                objString.append(entryNumber.toString().concat(" ").concat(progName));                                
                                objString.append(readLine.replaceAll("\t", ""));
                                entryNumber++;
                            }
                            break;
                        //Found the start of the tag - start building the string and search for the end string
                        case 2:
                            if(readLine.matches(regexEndTagMatch)){
                                caseNo = 0;
                                //System.out.println(objString.toString());                            
                                dataLogger_Obj thisObj = new dataLogger_Obj(objString.toString());
                                System.out.println(thisObj.getCSVData());
                                //masterOutput.append(thisObj.getCSVData());
                                //masterOutput.append("\n");
                                masterStringData.add(thisObj.getCSVData());
                                masterData.add(thisObj);
                                objString = new StringBuilder();
                            }
                            else if(readLine.matches(endMatch)){
                                caseNo = 1;
                                objString.append(readLine.replaceAll("\t", ""));
                                dataLogger_Obj thisObj = new dataLogger_Obj(objString.toString());
                                System.out.println(thisObj.getCSVData());
                                //masterOutput.append(thisObj.getCSVData());
                                //masterOutput.append("\n");
                                masterStringData.add(thisObj.getCSVData());
                                masterData.add(thisObj);
                                objString = new StringBuilder();
                            }
                            else
                                objString.append(readLine.replaceAll("\t", "")); // jUST Part of the string                            
                            break;                            
                        default:
                            break;
                    }
                }
                readLine = fileReader.readLine();
            }while(readLine!=null);            
            label.setText("Reading Complete");
            button2.setVisible(TRUE);
        }
        catch(IOException IOE){
            IOE.printStackTrace(System.err);            
        }
    }
    
    @FXML
    private void saveOutput(ActionEvent event){
        if(masterStringData.size()>0){
            FileChooser saveJFC = new FileChooser();
            saveJFC.setTitle("Select where to save the file");
            File saveFile = saveJFC.showSaveDialog(new Stage());
            if(saveFile!=null)
            try{
                PrintWriter outWriter = new PrintWriter(new BufferedWriter(
                                new FileWriter(saveFile)),true);
                masterStringData.forEach((n) -> { // <<<<AC1 Code hints update this.
                    outWriter.println(n);
                });                
            }
            catch(IOException IOE){
                IOE.printStackTrace(System.err);
            }
            label.setText("Saving Complete");
        }   
    }
    
    @FXML
    /**
     * THis is the method run when the Save button should produce a new screen with
     * a table view
     */
    private void selectSaveOutput(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource("FXMLFilterTable.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            FXMLFilterTableController controller = fxmlLoader.<FXMLFilterTableController>getController();

            controller.setData(masterData);
            
            Scene scene = new Scene(root);
            
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
            
        }
        catch(IOException ioe){
            ioe.printStackTrace(System.err);
        }
        
    }
    
    
    @FXML
    private void testFunction(ActionEvent event){
        // About time I had one of these.
        //
        Stage stage = new Stage();
        try{
        
            Parent root = FXMLLoader.load(getClass().getResource("FXMLtest.fxml"));
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        }
        catch(Exception IOE){
            System.out.println(IOE.getMessage());
        }
//        File testFile = new File("file.txt");
//        try{
//            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(testFile)),true);
//            outWriter.println("Hey.");
//            System.out.println("Done");
//        }
//        catch(IOException ioe){
//            ioe.printStackTrace(System.err);
//        }
        
    }
    
    @FXML
    private void testFunctionB(ActionEvent event){
        // A new one because the first one works and I dont want to touch it... EVER.
        System.out.println("Test function B");
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLtest.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("FXMLtest.fxml"));        
        
            Scene scene = new Scene((Pane)loader.load());

            stage.setScene(scene);
            stage.show();
        
        }
        catch(Exception IOE){
            System.out.println(IOE.getMessage());
        }

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
    
    /** 
    * Taken from the "JAVA For Dummies" Book by Bob Lowe and Barry Burd<br/>
    * This function gives back a BufferedReader object in which points
    * to the file provided in the @Name parameter.
    * 
    * Updated to have passed a File rather than a String.
    * 
    * @param name
    * @return Bufferedreader
    */
    private BufferedReader getReader(File passedFile){
        BufferedReader in = null;
        try{               
            in = new BufferedReader(
            new FileReader(passedFile));
        }
        catch(FileNotFoundException e){
            System.out.println("The File does not exist");
            JOptionPane.showMessageDialog(null, "The File does not exist");
            System.exit(0);
        }
//        catch(IOException e){
//            System.out.println("I/O Error");
//            JOptionPane.showMessageDialog(null, "I/O Error");
//            System.exit(0);
//        }
        return in;
    }      
    
}
