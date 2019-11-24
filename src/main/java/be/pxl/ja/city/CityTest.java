package be.pxl.ja.city;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class CityTest {

    City cityA = new City("Hasselt", 50.93106, 5.33781);
    City cityB = new City("Antwerpen", 51.21989, 4.40346);
    City leuven = new City("Leuven", 50.88151970000001, 4.6967578);
    City roermond = new City("Roermond", 51.19417, 5.9875);
    City maastricht = new City("Maastricht", 50.84833, 5.68889);
    City aken = new City("Aken", 50.77664, 6.08342);

    double distance = cityA.distance(cityB);
    double distanceB = cityB.distance(cityA);


    //afstand tussen deze twee cooordinaten zou 72.75 kilometer moeten zijn; https://gps-coordinates.org/distance-between-coordinates.php

    @Test
    public void ShouldCalculateCorrectDistance() {
        Assertions.assertEquals(72.75, distance);
        Assertions.assertEquals(96.71, leuven.distance(roermond));
        Assertions.assertEquals(28.84, maastricht.distance(aken));



    }

    @Test
    public void DistanceShouldBeTheSameBothWays() {
        Assertions.assertEquals(distance, distanceB);
        Assertions.assertEquals(roermond.distance(leuven), leuven.distance(roermond));
        Assertions.assertEquals(aken.distance(maastricht), maastricht.distance(aken));
    }
}
