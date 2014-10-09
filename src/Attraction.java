/**
 * 
 * @author jmessmer
 *
 */
public class Attraction {
	
	public long price;
	public String name;
	public int popularity;

	public Attraction(long price, String name, int popularity) {
		super();
		this.price = price;
		this.name = name;
		this.popularity = popularity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}	
	
	@Override
	public String toString() {
		return "Attraction [price=" + price + ", name=" + name
				+ ", popularity=" + popularity + "]";
	}

}
