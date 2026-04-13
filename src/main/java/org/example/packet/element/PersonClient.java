package org.example.packet.element;

import org.example.packet.enums.Color;
import org.example.packet.enums.Country;

public class PersonClient {
    private String name;
    private Coordinates coordinates;
    private Float height;
    private Float weight;
    private Color color;
    private Country country;
    private Location location;

    public PersonClient(String name, Coordinates coordinates, Float height, Float weight, Color color,
                        Country country, Location location) {
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.weight = weight;
        this.color = color;
        this.country = country;
        this.location = location;
    }
    public String getName() {
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public Float getHeight() {
        return height;
    }

    public Float getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public Country getCountry() {
        return country;
    }

    public Location getLocation() {
        return location;
    }
}
