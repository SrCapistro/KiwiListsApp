/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlObject;

import Objects.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author josuecg
 */
public class ControlList {
    
    public boolean addNewList(List newList){
        boolean saved = false;   
        File f = null;
        FileOutputStream fo = null;
        ObjectOutputStream oo = null;
        try{
            f = new File("lists.kwl");
            fo = new FileOutputStream(f, true);
            oo = new ObjectOutputStream(fo);
            oo.writeObject(newList);
            saved = true;
            fo.close();
            oo.close();
           }
            catch(java.io.IOException e1){
                 e1.printStackTrace();
                saved = false;
            }
            catch(Exception e1){
                 e1.printStackTrace();
                saved = false;
            }
            finally{
               if(oo!=null){
                   try {
                       oo.close();
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
               }
            }
        return saved;
    }
    
    public void removeList(List list){
        ArrayList<List> listOfLists = new ArrayList<List>();
        File f = null;
        listOfLists = returnLists();
        for(List listToFind:listOfLists){
            if(listToFind.getIdList()==list.getIdList()){
                listOfLists.remove(listToFind);
                break;
            }
        }
     
        deleteFile();
        
        for(List listToSave:listOfLists){
            this.addNewList(listToSave);
        }
    }
    
    public List findListSelected(String nameList){
        ArrayList<List> searchList;
        searchList = this.returnLists();
        List listToFind = new List();
        for(List listElement: searchList){
            if(nameList.equals(listElement.getNameList())){
                listToFind = listElement;
                break;
            }
        }
        return listToFind;
    }
    
    public int modifyList(List list){
        int isModified = 0;
        ArrayList<List> listOfLists = new ArrayList<List>();
        File f = null;
        listOfLists = returnLists();
        for(List listToFind:listOfLists){
            if(listToFind.getIdList()==list.getIdList()){
                listOfLists.remove(listToFind);
                listOfLists.add(list);
                isModified = 1;
                break;
            }
        }
     
        deleteFile();
        
        for(List listToSave:listOfLists){
            this.addNewList(listToSave);
        }
        
        return isModified;
    }
    
    public void deleteFile(){
        File f = null;
        f = new File("lists.kwl");
        f.delete();
    }
    /***
     * This method return the lists registered on the system
     * @return an arrayList of the list registered
     */
    public ArrayList<List> returnLists(){
        ArrayList<List> listReturned = new ArrayList<List>();
        File f = null;
        FileInputStream fi = null;
        try{
            f = new File ("lists.kwl");
            fi = new FileInputStream(f);
            List list = null;
            while(true){
                ObjectInputStream oi = new ObjectInputStream(fi);
                list = (List) oi.readObject();
                listReturned.add(list);
            }
            
        }
        catch(IOException | ClassNotFoundException e1){
            
        }
        finally{
            try{
                fi.close();
            }
            catch(java.io.IOException e1){
               
            }catch(java.lang.NullPointerException n1){
                
            }
        }
        return listReturned;
    }
    
    public boolean proveNameList(String nameList){
        boolean exists = false;
        ArrayList<List> listOfLists = this.returnLists();
        for(List list:listOfLists){
            if(nameList.equals(list.getNameList())){
                exists = true;
                break;
            }
        }
        return exists;
    }
    
    public int countLists(){
        int numLists =0;
        ArrayList<List> lists;
        lists = returnLists();
        numLists = lists.size();
        return numLists;
    }
    
}
