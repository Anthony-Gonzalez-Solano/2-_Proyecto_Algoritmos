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

    public Restaurant(String name, String location) {

        this.name = name;
        this.location=location;
        autoId++;
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

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        Restaurant.autoId = autoId;
    }

   

    @Override
    public String toString() {
        return name;
    }

    public String secondToString() {
        return name + "," + location;
    }
}
