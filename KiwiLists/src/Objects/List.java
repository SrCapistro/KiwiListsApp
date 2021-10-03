/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author josuecg
 */
public class List implements Serializable{
    private int idList;
    private String nameList;
    private ArrayList<String> elementsList;

    public List() {
    }
    
    public List(String nameList) {
        this.nameList = nameList;
    }
    
    public List(int idList, String nameList, ArrayList<String> elementsList) {
        this.idList = idList;
        this.nameList = nameList;
        this.elementsList = elementsList;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public ArrayList<String> getElementsList() {
        return elementsList;
    }

    public void setElementsList(ArrayList<String> elementsList) {
        this.elementsList = elementsList;
    }
    
    
}
