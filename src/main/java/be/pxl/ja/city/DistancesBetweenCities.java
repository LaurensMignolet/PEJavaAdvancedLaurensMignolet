package be.pxl.ja.city;

import be.pxl.ja.common.DistanceUtil;

import java.util.ArrayList;

public class DistancesBetweenCities {

	public static void main(String[] args) {
		City leuven = new City("Leuven", 50.88151970000001, 4.6967578);
		City roermond = new City("Roermond", 51.19417, 5.9875);
		City maastricht = new City("Maastricht", 50.84833, 5.68889);
		City aken = new City("Aken", 50.77664, 6.08342);

		ArrayList<City> cities = new ArrayList<>();
		cities.add(leuven);
		cities.add(maastricht);
		cities.add(roermond);
		cities.add(aken);

		cities.sort(City::compareTo);

		for(City city: cities){
			System.out.println(city.toString());
		}

		City hasselt = new City("Hasselt", 50.93106, 5.33781);

		City closest = DistanceUtil.findClosest(cities, hasselt);
		System.out.println(String.format("de stad %s ligt het dichtst bij %s", closest.toString(), hasselt.toString()));


	}

}
