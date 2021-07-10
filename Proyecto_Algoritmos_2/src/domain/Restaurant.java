/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Dell 7470
 */
public class Restaurant {

    private static int autoId;
    private String name;
    private String location;
        private int id;

    public Restaurant(String name, String location) {
       this.id=autoId;
        this.name = name;
        this.location=location;
        autoId++;
    }

    public Restaurant(String name, String location, int id) {
       this.id=id;
       this.name = name;
       this.location=location;
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

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        Restaurant.autoId = autoId;
    }

   

    @Override
    public String toString() {
        return name;
    }

    public String secondToString() {
        return "restaurant,"+name + "," + location+","+ id;
    }
}
