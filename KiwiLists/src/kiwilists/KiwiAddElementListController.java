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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author josuecg
 */
public class KiwiAddElementListController implements Initializable {
    public boolean hasSaved = false;
    String listName = KiwiWorkFlowController.nameListTo;
    String listSelected = KiwiWorkFlowController.listSelected;
    String elementSelected = KiwiWorkFlowController.elementSelected;
    ControlList controlList = new ControlList();
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNameList;  
    @FXML
    private Label lbTextEmpty;
    @FXML
    private TextArea tbDescription;
    
    @FXML
    public void buttonAddElementMouseClicked(){
        System.out.println(elementSelected);
        if(tfNameList.getText().equals("")){
            lbTextEmpty.setVisible(true);
        }else{
            if(KiwiWorkFlowController.elementStatus == 1){
                saveElement();
            }else if(KiwiWorkFlowController.elementStatus == 0){
                saveList();
            }else if(KiwiWorkFlowController.elementStatus == 3){
                editList(listSelected);
            }else if(KiwiWorkFlowController.elementStatus == 4){
                editElementList(listName,elementSelected);
            }
            
        }
    }
    public void saveElement(){
        List listToModify = controlList.findListSelected(listName);
        List newListModify = controlList.findListSelected(KiwiWorkFlowController.nameListTo);
        Element newElement = new Element(tfNameList.getText());
        newListModify.getElementsList().add(newElement);
        controlList.modifyList(newListModify);
        hasSaved = true;
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
    
    public void saveList(){
        List newList = new List();
        ArrayList<Element> listElements = new ArrayList<Element>();
        listElements.add(new Element("New element 1"));
        listElements.add(new Element("New element 2"));
        listElements.add(new Element("New eleent 3"));
        newList.setNameList(tfNameList.getText());
        newList.setDescriptionList(tbDescription.getText());
        newList.setElementsList(listElements);
        controlList.addNewList(newList);
        hasSaved = true;
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
    
    public void editList(String nameList){
        List listModified = controlList.findListSelected(nameList);
        listModified.setDescriptionList(this.tbDescription.getText());
        listModified.setNameList(this.tfNameList.getText());
        controlList.modifyList(listModified);
        hasSaved = true;
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
    
    public void editElementList(String list, String elementSelect){
        List listModified = controlList.findListSelected(list);
        ArrayList<Element> listElement = listModified.getElementsList();
        Element elementToModify = findElement(elementSelect, listElement);
        for(Element element:listElement){
            if(element.getElementID()==elementToModify.getElementID()){
                elementToModify.setNameElement(this.tfNameList.getText());
                elementToModify.setDescriptionElement(this.tbDescription.getText());
                listElement.remove(element);
                listElement.add(elementToModify);
                listModified.setElementsList(listElement);
                controlList.modifyList(listModified);
                break;
            }
        }
    }
    
    public Element findElement(String elementToFind, ArrayList<Element> listElement){
        Element elementFound = null;
        for(Element element:listElement){
            if(elementToFind.equals(element.getNameElement())){
                elementFound = element;
                System.out.println(elementFound.getNameElement());
                break;
            }
        }
        return elementFound;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbTextEmpty.setVisible(false);
    }    
    
}
