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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author josuecg
 */
public class KiwiAddElementListController implements Initializable {
    public static boolean hasSaved = false;
    String listName = KiwiWorkFlowController.nameListTo;
    ControlList controlList = new ControlList();
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNameList;  
    @FXML
    private Label lbTextEmpty;
    
    @FXML
    public void buttonAddElementMouseClicked(){
        List listToModify = controlList.findListSelected(listName);
        if(tfNameList.getText().equals("")){
            lbTextEmpty.setVisible(true);
        }else{
             listToModify.getElementsList().add(tfNameList.getText());
             controlList.modifyList(listToModify);
             hasSaved = true;
             Stage stage = (Stage) btnAdd.getScene().getWindow();
             stage.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(listName);
        lbTextEmpty.setVisible(false);
    }    
    
}
