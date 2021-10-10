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
    private ArrayList<Element> elementsList;
    private String descriptionList;
    
    public List(){
        this.idList = (int)(Math.random()*9999+1);
    }
    
    public List(String nameList){
        this.nameList = nameList;
        this.descriptionList = "Sin descripción";
    }

    public String getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(String descriptionList) {
        this.descriptionList = descriptionList;
    }
    
    public List(int idList, String nameList, ArrayList<Element> elementsList) {
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

    public ArrayList<Element> getElementsList() {
        return elementsList;
    }

    public void setElementsList(ArrayList<Element> elementsList) {
        this.elementsList = elementsList;
    }
    
    public int generateID(){
        return this.idList = (int)(Math.random()*9999+1);
    }
    
}
