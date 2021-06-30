/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class Place {
    private String name;
    private int ID;
    private static int autoID;
        public Place(String name){
            this.name=name;
            this.ID=autoID;
            autoID++;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static int getAutoID() {
        return autoID;
    }

    public static void setAutoID(int autoID) {
        Place.autoID = autoID;
    }

    @Override
    public String toString() {
        return name + "," + ID;
    }
        
}
