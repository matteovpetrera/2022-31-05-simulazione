package it.polito.tdp.nyc.model;

public class City {
	private double latitude;
	private double longitude;
	private String city;
	public City(double latitude, double longitude, String city) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	
}
