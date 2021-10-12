/*
 * Copyright (C) 2021 josuecg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kiwilists;

import ControlObject.ControlList;
import Objects.Element;
import Objects.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author josuecg
 */
public class KiwiWorkFlowController implements Initializable {
    public static int elementStatus = 0;
    public ControlList controlList = new ControlList();
    public static String listSelected;
    public static String elementSelected;
    public static String nameListTo;
    private boolean elementScreen = false;
    public boolean isElement = false;
    public int contador = 0;
    
    
    @FXML
    private ListView listView;
    @FXML
    private Label listName;
    @FXML
    private Button btnBack;
    @FXML
    private TextArea tbDescriptionArea;
    @FXML
    private Button btnAddList;
    @FXML
    public void buttonBackClicked(){
        listView.setEditable(false);
        isElement = false;
        listName.setText("MIS LISTAS");
        chargeLists();
        btnBack.setVisible(false);
        System.out.println(isElement);
    }
    
    /**
     * This method is for adding elements on the system, if is on the list screen, it calls the register list window
     * if is on the element's list section, it calls to the register element window
     * @param event
     * @throws IOException 
     */
    @FXML
    public void buttonAddListClicked(ActionEvent event) throws IOException{
        if(!isElement){
            addNewList();
        }else{
            addNewListElement();
        }
    }
    
    /***
     * This method listens de clicks of the lists
     * When is on the main list of lists view, dobuble clicking on it charges the elements
     * of a list, but when is on the elements view, double clicking on it allows edit the element name
     */
    @FXML
    public void listElementMouseClicked(){
       listView.setOnMouseClicked(me -> {
            if(me.getClickCount() == 2 && !isElement){
                try{
                    String nameList = listView.getSelectionModel().getSelectedItem().toString();
                    chargeElementList(nameList);
                    listName.setText(nameList);
                    nameListTo = listName.getText();
                    listName.setAlignment(Pos.CENTER);
                    tbDescriptionArea.clear();
                    isElement = true;
                    if(isElement){
                        btnBack.setVisible(true);
                    }
                }catch(NullPointerException nullP){
                    isElement = false;
                }
            }else if(me.getClickCount() !=2 && isElement){
                try{
                    elementSelected = listView.getSelectionModel().getSelectedItem().toString();
                }catch(NullPointerException nullp){
                    //Nothing happens
                }
            }else if(me.getClickCount() == 1 && !isElement){
                try{
                    listSelected = listView.getSelectionModel().getSelectedItem().toString();
                    List list = controlList.findListSelected(listView.getSelectionModel().getSelectedItem().toString());
                    tbDescriptionArea.setText(list.getDescriptionList());
                }catch(NullPointerException nullP){
                    //Nothing happens when the list is selected on empy item
                }
            }else if(me.getClickCount()!=0 && isElement){
                try{
                    elementSelected = listView.getSelectionModel().getSelectedItem().toString();
                    List listSelectedElement = controlList.findListSelected(nameListTo);
                    for(Element element: listSelectedElement.getElementsList()){
                        if(element.getNameElement().equals(elementSelected)){
                            tbDescriptionArea.setText(element.getDescriptionElement());
                        }
                    }
                }catch(NullPointerException nullp){
                    //Nothing happens just contrying the exception
                }
            }
        });
    }
    
    /***
     * This method creates the window for creating a new list on the system
     * @return 
     */
    public void addNewList() throws IOException{
        elementStatus = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KiwiAddElementList.fxml"));
        Parent parent = fxmlLoader.load();
        KiwiAddElementListController addController = fxmlLoader.<KiwiAddElementListController>getController();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Add a new list");
        stage.setResizable(false);
        stage.showAndWait();
        System.out.println(addController.hasSaved);
        if(addController.hasSaved==true){
            this.chargeLists();
        }
    }
 
    public void addNewListElement() throws IOException{
        elementStatus = 1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KiwiAddElementList.fxml"));
        Parent parent = fxmlLoader.load();
        KiwiAddElementListController addController = fxmlLoader.<KiwiAddElementListController>getController();
        
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Add a new element");
        stage.setResizable(false);
        stage.showAndWait();
        if(addController.hasSaved==true){
            this.chargeElementList(nameListTo);
        }
    }
    
    public void editList(){
        elementStatus = 3;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KiwiAddElementList.fxml"));
            Parent parent = fxmlLoader.load();
            KiwiAddElementListController addController = fxmlLoader.<KiwiAddElementListController>getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Edit list");
            stage.setResizable(false);
            stage.showAndWait();
            if(addController.hasSaved==true){
                chargeLists();
            }
        } catch (IOException ex) {
            //Nothing happens
        }
    }
    
    public void editElementList(){
        elementStatus = 4;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KiwiAddElementList.fxml"));
            Parent parent = fxmlLoader.load();
            KiwiAddElementListController addController = fxmlLoader.<KiwiAddElementListController>getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Edit element");
            stage.setResizable(false);
            stage.showAndWait();
            if(addController.hasSaved==true){
                this.chargeElementList(nameListTo);
            }
        } catch (IOException ex) {
            //Nothing happens
        }
    }
    
    /***
     * This method charge the elements of a list selectioned
     * @param nameList this is the name of the list selectioned
     */
    public void chargeElementList(String nameList){
        List listToChargeElements;
        listToChargeElements = controlList.findListSelected(nameList);
        ArrayList<Element> elementsList = listToChargeElements.getElementsList();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(Element listElements: elementsList){
            elements.add(listElements.getNameElement());
        }
        listView.setItems(elements);
    }
    
    /***
     * This method charge all the list saved on the system
     */
    public void chargeLists(){
        ArrayList<List> listReturned = controlList.returnLists();
        ObservableList<String> namesList = FXCollections.observableArrayList();
        for(List list: listReturned){
            namesList.add(list.getNameList());
        }
        listView.setItems(namesList);
    }
    
    public void createContextMenu() throws IOException{
        final ContextMenu contextOption = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem editItem = new MenuItem("Edit");
        menuItemActionDelete(deleteItem);
        menuItemActionEdit(editItem);
        contextOption.getItems().add(editItem);
        contextOption.getItems().add(deleteItem);
        deleteItem.setStyle("-fx-text-fill: white;");
        editItem.setStyle("-fx-text-fill: white;");
        hideContextMenu(contextOption);
        displayContextMenu(contextOption);
    }
    
    /***
     * This method display the context menu by the selected item on the list
     * @param contextMenu It passed a contextMenu to display
     */
    public void displayContextMenu(ContextMenu contextMenu){
        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               if(event.getButton().equals(MouseButton.SECONDARY) && listView.getSelectionModel().getSelectedItem()!=null){
                   contextMenu.show(listView,event.getScreenX(),event.getScreenY());
               }
            }
        });   
    }
    
    public void hideContextMenu(ContextMenu contextMenu){
        listView.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event ->{
            contextMenu.hide();
        });
    }
    
    public void menuItemActionDelete(MenuItem deleteItem){
        deleteItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    if(isElement){
                        List listNew = controlList.findListSelected(nameListTo);
                        for(Element element: listNew.getElementsList()){
                            if(element.getNameElement().equals(listView.getSelectionModel().getSelectedItem().toString())){
                                listNew.getElementsList().remove(element);
                                break;
                            }
                        }
                        controlList.modifyList(listNew);
                        chargeElementList(listNew.getNameList());
                    }else{
                        List listNew = controlList.findListSelected(listView.getSelectionModel().getSelectedItem().toString());
                        controlList.removeList(listNew);
                        chargeLists();
                    }
                   
                }catch(NullPointerException npe){
                   
                }
            }
        });
    }
    
    public void menuItemActionEdit(MenuItem editItem) throws IOException{
        editItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               if(isElement){
                   editElementList();
               }else{
                   editList();
               }
            }
        });
       
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       chargeLists();
       listView.setStyle("-fx-control-inner-background: #01497c;");
       this.listName.setAlignment(Pos.CENTER);
       this.tbDescriptionArea.setWrapText(true);
       btnBack.setVisible(false);
        try {
            createContextMenu();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
