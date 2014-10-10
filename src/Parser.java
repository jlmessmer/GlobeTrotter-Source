import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jmessmer
 *
 */
public class Parser {
	
	public Parser(){
		super();
	}
	
	public ArrayList<Location> parseLoc(String path)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		
		@SuppressWarnings("unused")
		Location l;
		
		InputStream is = getClass().getResourceAsStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = null;
		boolean started = false;

		try {
		    reader = new BufferedReader(isr);
		    String text = null;
		    String name = "";
		    int lat = 0;
		    int lon = 0;
		    String attrName = "";
		    long attrPrice;
		    int attrScore;
		    int startIndex = 0;
		    ArrayList<Attraction> arr = new ArrayList<Attraction>();
		    while ((text = reader.readLine()) != null) {
		    	text = text.trim();
		    	switch(text){
		    		case "name":
		    			started = true;
		    			//arr.clear();
		    			name = reader.readLine().trim();
		    			//System.out.println(name);
		    			lat = Integer.parseInt(reader.readLine().trim());
		    			lon = Integer.parseInt(reader.readLine().trim());
		    			break;
		    		case "Attraction":
		    			//reader.readLine();
		    			attrName = reader.readLine().trim();
		    			attrScore = Integer.parseInt(reader.readLine().trim());
		    			attrPrice = Long.parseLong(reader.readLine().trim());
		    			Attraction a = new Attraction(attrPrice, attrName, attrScore);
		    			arr.add(a);
		    			startIndex++;
		    			break;
		    		case "end":
		    			if(started == true){
		    				List<Attraction> attList = arr.subList(startIndex - 3, arr.size());
		    				ArrayList<Attraction> subList = new ArrayList<Attraction>(attList);
		    				locs.add(new Location(name, lat, lon, subList));
		    			}
		    			//arr.clear();
		    			break;
		    	}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		return locs;
	}
	
	public Player getSave(InputStreamReader isr){
		Player p = null;
		String name = "";
		int diff = 0;
		int score = 0;
		int money = 0;
		int distanceTraveled = 0;
		String loc = "";
		ArrayList<String> placesTraveled = new ArrayList<String>();
		ArrayList<String> placesLeft = new ArrayList<String>();
		ArrayList<String> attrNames = new ArrayList<String>();
		BufferedReader reader = null;
		int whichPlaces = 0;
		
		try {
		    reader = new BufferedReader(isr);
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		    	text = text.trim();
		    	switch(text){
	    	 		case "name":
	    	 			name = reader.readLine();
	    	 			break;
	    	 		case "difficulty":
	    	 			diff = Integer.parseInt(reader.readLine());
	    	 			break;
	    	 		case "money":
	    	 			money = Integer.parseInt(reader.readLine());
	    	 			break;
	    	 		case "score":
	    	 			score = Integer.parseInt(reader.readLine());
	    	 			break;
	    	 		case "Distance Traveled":
	    	 			distanceTraveled = Integer.parseInt(reader.readLine());
	    	 			break;
	    	 		case "Location":
	    	 			loc = reader.readLine();
	    	 			break;
	    	 		case "Places Traveled":
	    	 			whichPlaces = 0;
	    	 			break;
	    	 		case "Places Left":
    	 				whichPlaces = 1;
    	 				break;
	    	 		case "Attractions Seen":
	    	 			whichPlaces = 2;
	    	 			break;
	 				default:
 						switch(whichPlaces){
 							case 0:
 								placesTraveled.add(text);
 								break;
 							case 1:
 								placesLeft.add(text);
 								break;
 							case 2:
 								attrNames.add(text);
							default:
								break;
 						}
 						break;
	    	 			
		    	}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		p = new Player(name, money, score, diff, distanceTraveled, loc, placesTraveled, placesLeft, attrNames);
		return p;
	}

}