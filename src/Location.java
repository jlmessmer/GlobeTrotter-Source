import java.util.ArrayList;

/**
 * 
 * @author jmessmer
 *
 */

public class Location {
	
	public String name;
	public int lat;
	public int lon;
	public ArrayList<Attraction> attractions;
	
	public Location(String n, int l, int lo, ArrayList<Attraction> a){
		name = n;
		lat = l;
		lon = lo;
		attractions = a;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public long getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public ArrayList<Attraction> getAttractions() {
		return attractions;
	}

	public void setAttractions(ArrayList<Attraction> attractions) {
		this.attractions = attractions;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", lat=" + lat + ", lon=" + lon
				+ ", attractions=" + attractions + "]";
	}
}
