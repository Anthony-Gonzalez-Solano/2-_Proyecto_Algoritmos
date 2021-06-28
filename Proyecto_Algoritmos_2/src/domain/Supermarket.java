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
public class Supermarket {
    String name;
    String location;
    private int id;
    private static int autoID;// variable para aumentar el id de los estudiantes
    
    public Supermarket(String name, String location){
        this.id=autoID;
        this.location=location;
        this.name=name;
        autoID++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getAutoID() {
        return autoID;
    }

    public static void setAutoID(int autoID) {
        Supermarket.autoID = autoID;
    }

    @Override
    public String toString() {
        return name + "," + location + "," + id;
    }
    
}
