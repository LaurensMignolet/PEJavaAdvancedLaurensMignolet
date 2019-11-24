package be.pxl.ja.city;

import be.pxl.ja.common.DistanceFunction;

public class City implements DistanceFunction<City>, Comparable<City> {
	private String name;
	private double latitude;
	private double longitude;

	public City(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public double distance(City city) {
		double radTheta = Math.toRadians(longitude - city.longitude);
		double radLatitude = Math.toRadians(latitude);
		double radOtherLatitude = Math.toRadians(city.latitude);
		double dist = Math.sin(radLatitude) * Math.sin(radOtherLatitude) + Math.cos(radLatitude) * Math.cos(radOtherLatitude) * Math.cos(radTheta);
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515 * 1.609344;

		//voor nauwkeuriger resultaat onderstaande lijn wissen.
		dist = Math.round(dist *100)/100.0;
		return dist;
	}

	@Override
	public int compareTo(City o) {
		if((int)this.name.charAt(0) < (int) o.name.charAt(0)){
			return -1;
		}else {
			if ((int)this.name.charAt(0) > (int) o.name.charAt(0)){
				return 1;
			}else return 0;
		}
	}



	/*
		Source: https://www.geodatasource.com/developers/java
	    Calculates the distance between 2 points when given latitude and longitude in decimal degrees

		double radTheta = Math.toRadians(longitude - other.longitude);
		double radLatitude = Math.toRadians(latitude);
		double radOtherLatitude = Math.toRadians(other.latitude);
		double dist = Math.sin(radLatitude) * Math.sin(radOtherLatitude) + Math.cos(radLatitude) * Math.cos(radOtherLatitude) * Math.cos(radTheta);
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
	 */
}
