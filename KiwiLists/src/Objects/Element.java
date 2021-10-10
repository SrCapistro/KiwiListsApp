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
package Objects;

import java.io.Serializable;

/**
 *
 * @author josuecg
 */
public class Element implements Serializable{
    private String nameElement;
    private int elementID;
    private String descriptionElement;
    
    public Element(){
        
    }

    public Element(String nameElement) {
        this.nameElement = nameElement;
        this.elementID = (int)(Math.random()*9999+1);
        this.descriptionElement = "Sin descripción";
    }

    public String getNameElement() {
        return nameElement;
    }

    public void setNameElement(String nameElement) {
        this.nameElement = nameElement;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public String getDescriptionElement() {
        return descriptionElement;
    }

    public void setDescriptionElement(String descriptionElement) {
        this.descriptionElement = descriptionElement;
    }
    
    public int generateID(){
        return this.elementID = (int)(Math.random()*9999+1);
    }
}
