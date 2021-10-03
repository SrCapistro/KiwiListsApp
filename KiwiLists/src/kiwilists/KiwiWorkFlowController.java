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
import Objects.List;
import Objects.ListenerVariable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;


/**
 *
 * @author josuecg
 */
public class KiwiWorkFlowController implements Initializable {
    ControlList controlList = new ControlList();
    public static String nameListTo;
    boolean isElement = false;
    int contador = 0;
    @FXML
    private ListView listView;
    @FXML
    private Label listName;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnBack;
    @FXML
    public void buttonBackClicked(){
        listView.setEditable(false);
        btnEdit.setVisible(true);
        isElement = false;
        listName.setText("MIS LISTAS");
        chargeLists();
        btnBack.setVisible(false);
        System.out.println(isElement);
    }
    
    @FXML
    public void buttonAddListClicked() throws IOException{
         ListenerVariable hiloListener = new ListenerVariable();
         Thread nuevoh = new Thread(hiloListener);
        if(!isElement){
            int idList = (int)(Math.random()*99999+1);
            ArrayList<String> listElements = new ArrayList<String>();
            listElements.add("Element 1");
            listElements.add("Element 2");
            listElements.add("Element 3");
            String nameList = "List "+controlList.countLists();
            List newList = new List();
            newList.setNameList(nameList);
            newList.setIdList(idList);
            newList.setElementsList(listElements);
            controlList.addNewList(newList);
            chargeLists();
        }else{
           nuevoh.start();
           Stage newWindow = new Stage();
           newWindow.setTitle("Add element");
           FXMLLoader loader = new FXMLLoader(getClass().getResource("KiwiAddElementList.fxml"));
           newWindow.setScene(new Scene(loader.load()));
           newWindow.show();
           nuevoh.interrupt();
        }
    }
    
    @FXML 
    public void buttonEditClicked(final ActionEvent event){
        if(!listView.getSelectionModel().isEmpty()){
            listView.setEditable(true);
            listView.edit(listView.getSelectionModel().getSelectedIndex());
            listView.setEditable(false);
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
                    isElement = true;
                    btnEdit.setVisible(false);
                    btnBack.setVisible(true);
                }catch(NullPointerException nullP){
                    //Nothing happens when the list is selected on empty item
                }
            }else if(me.getClickCount() !=2 && isElement){
                System.out.println(listView.isEditable());
                listView.setEditable(true);
                listView.setCellFactory(TextFieldListCell.forListView());
                System.out.println(me.getClickCount());
                listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>(){
                    @Override
                    public void handle(ListView.EditEvent<String> event) {
                        listView.getItems().set(event.getIndex(), event.getNewValue());
                    }
                });
            }
        });
    }
    
    /***
     * This method charge the elements of a list selectioned
     * @param nameList this is the name of the list selectioned
     */
    public void chargeElementList(String nameList){
        List listToChargeElements;
        listToChargeElements = controlList.findListSelected(nameList);
        ArrayList<String> elementsList = listToChargeElements.getElementsList();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(String listElements: elementsList){
            elements.add(listElements);
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       chargeLists();
       btnBack.setVisible(false);
       listView.setCellFactory(TextFieldListCell.forListView());
       listView.setEditable(false);
       btnEdit.disableProperty().bind(Bindings.createBooleanBinding(
               () -> listView.getSelectionModel().isEmpty(),
               listView.getSelectionModel().getSelectedItems()
       ));
    }    
    
    public class ListenerVariable implements Runnable{
        
    public void chargeElementList(String nameList){
        List listToChargeElements;
        listToChargeElements = controlList.findListSelected(nameList);
        ArrayList<String> elementsList = listToChargeElements.getElementsList();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(String listElements: elementsList){
            elements.add(listElements);
        }
        listView.setItems(elements);
    }
    
    @Override
    public void run() {
        System.out.println("Escuchando variable al cambio");
        do{
            System.out.println("Escuchando..");
        }while(KiwiAddElementListController.hasSaved!=true);
        this.chargeElementList(nameListTo);
    }
    
}
}
