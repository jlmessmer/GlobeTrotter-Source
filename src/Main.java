import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author jmessmer
 *
 */
public class Main {
	
	private static File save;
	
	private static Scanner keyboard = new Scanner(System.in);
	
	private static String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	public static void main(String[] args){
		mainMenu();
	}
	
	public static void mainMenu(){
		clearConsole();
		System.out.println("Main Menu");
		System.out.println("[1]New Game\n[2]Load Game\n[3]Options\n[4]Quit");
		System.out.print("Select an option: ");
		int option = keyboard.nextInt();
		switch(option){
			case 1:
				newGame();
				break;
			case 2:
				loadGame();
				break;
			case 3:
				options();
				break;
			case 4:
				System.exit(0);
				break;
			default:
				//System.out.println("You've...You've killed me...");
				break;
		}
	}
	
	public static void options(){
		clearConsole();
		System.out.println("[1]Change Difficulty\n[2]Main Menu");
		System.out.print("Select an option: ");
		int option = keyboard.nextInt();
		switch(option){
			case 1:
				break;
			case 2:
				mainMenu();
				break;
		}
	}
	
	public static void newGame(){
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.print("What's your name? ");
		String name = keyboard.next();
		System.out.println("Hello, " + name);
		
		Parser p = new Parser();		
		ArrayList<Location> locs = p.parseLoc("data/locations.txt");
		for(int i = 0 ; i < locs.size(); i++){
			System.out.println("[" + (i+1) + "]" + locs.get(i).getName());
		}
		System.out.print("Where would you like to begin your journey? ");
		int option = keyboard.nextInt();
		String startLoc = locs.get(option - 1).getName();
		System.out.println(locs.get(option -1).getName());
		
		System.out.println("[1]N00b\n[2]Amateur\n[3]MLG 360 No Scope Triple Kill");
		System.out.print("Choose your difficulty: ");
		int difficulty = keyboard.nextInt();
		
		//File file = new File(save);
		save = new File("/" + decodedPath + "/saves/" + name + ".txt");
		try {
			save.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        FileWriter fr = null;
        try {
            fr = new FileWriter(save);
            fr.write("name\r\n");
            fr.write(name + "\r\n");
            fr.write("difficulty\r\n");
            fr.write(difficulty + "\r\n");
            fr.write("money\r\n");
            switch(difficulty){
			case 1:
				fr.write(1000000 + "\r\n");
				break;
			case 2:
				fr.write(500000 + "\r\n");
				break;
			case 3:
				fr.write(100000 + "\r\n");
				break;
			default:
				fr.write(1000000 + "\r\n");
				break;
		}
		fr.write("score\r\n");
		fr.write("0\r\n");
		fr.write("Distance Traveled\r\n");
		fr.write("0\r\n");
		fr.write("Location\r\n");
		fr.write(startLoc + "\r\n");
		fr.write("Places Traveled\r\n");
		fr.write("Places Left\r\n");
		for(Location l : locs){
			fr.write(l.getName() + "\r\n");
		}
		fr.write("Attractions Seen\r\n");
		fr.close();
            
            //fr.write(data);
        } catch (IOException e) {
        	System.out.println(save.getAbsolutePath());
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		loop();
	}
	
	public static void loadGame(){
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		File folder = new File("/" + decodedPath + "/saves/");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("[" + (i+1) + "]" + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	        System.out.print("Select Your Save: ");
	        int s = keyboard.nextInt();
	        save = listOfFiles[(s - 1)];
	        System.out.println(save.getName());
	        loop();
	}
	
	public static void loop(){
		Parser parse = new Parser();
		//System.out.println(save.getPath());
		try {
			System.out.println("==========\nPath: " + save.getCanonicalPath() + "\n==========");
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = null;
		try {
			is = new FileInputStream(save.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Main.class.getResourceAsStream(save.getAbsolutePath());
				
		InputStreamReader isr = new InputStreamReader(is);
		Player p = parse.getSave(isr);
		
		//System.out.println(p);
		
		int attractions = 3 * parse.parseLoc("data/locations.txt").size();
		
		//System.out.println(p.toString());
		//System.out.println(p.getAttsBeen().size() + " " + attractions);
		while(p.getMoney() > 0 && (p.getLocsLeft().size() > 0 && p.getAttsBeen().size() <= attractions)){		
			System.out.println("[1]Travel\n[2]Do Things\n[3]View Stats\n[4]Quit");
			System.out.print("What Do You Want to Do, " + p.getName() + "? ");
			switch(keyboard.next()){
				case "1":
					travel(p);
					break;
				case "2":
					doStuff(p);
					break;
				case "3":
					showInfo(p);
					break;
				case "4":
					save(p);
					mainMenu();
					break;
				case "quit":
					save(p);
				default:
					break;
			}
		}
		endGame(p);
	}
	
	public static void save(Player p){
		//System.out.println(p);
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		save = new File("/" + decodedPath + "/saves/" + p.getName() + ".txt");
		try {
			save.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        FileWriter fr = null;
        try {
            fr = new FileWriter(save);
            fr.write("name\r\n");
            fr.write(p.getName() + "\r\n");
            fr.write("difficulty\r\n");
            fr.write(p.getDifficulty() + "\r\n");
            fr.write("money\r\n");
            fr.write(p.getMoney() + "\r\n");
			fr.write("score\r\n");
			fr.write(p.getScore() + "\r\n");
			fr.write("Distance Traveled\r\n");
			fr.write(p.getDistanceTraveled() + "\r\n");
			fr.write("Location\r\n");
			fr.write(p.getCurrentLoc() + "\r\n");
			fr.write("Places Traveled\r\n");
			for(String s : p.getLocsBeen()){
				fr.write(s + "\r\n");
			}
			fr.write("Places Left\r\n");
			for(String s : p.getLocsLeft()){
				fr.write(s + "\r\n");
			}
			fr.write("Attractions Seen\r\n");
			for(String s : p.getAttsBeen()){
				fr.write(s + "\r\n");
			}
			fr.close();
	            
	            //fr.write(data);
	        } catch (IOException e) {
	        	System.out.println(save.getAbsolutePath());
	            e.printStackTrace();
	        }finally{
	            //close resources
	            try {
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}
	
	public static void travel(Player p){
		int index = 1;
		for(int i = 0; i < p.getLocsLeft().size(); i++){
			if(!p.getLocsLeft().get(i).equals(p.getCurrentLoc())){
				System.out.println("[" + (i+1) + "]" + p.getLocsLeft().get(i));
			}
			index++;
		}
		System.out.println("[" + (index) + "]Main Menu");
		int choice = keyboard.nextInt();
		if(choice == index){
			loop();
		}
		else{
			System.out.println("Hi!");
			Parser parse = new Parser();
			ArrayList<Location> locs = parse.parseLoc("data/locations.txt");
			
			Location locOne = null;
			Location locTwo = null;
			
			for(Location l : locs){
				if(l.getName().equals(p.getLocsLeft().get(choice - 1))){
					locTwo = l;
				}
				if(l.getName().equals(p.getCurrentLoc())){
					locOne = l;
				}
			}
			
			int distance = calculateDistance(locOne, locTwo);
			
			int attSeen = 0;
			ArrayList<Attraction> locAtts = locOne.getAttractions();
			ArrayList<String> playerAtts = p.getAttsBeen();
			for(Attraction a : locAtts){
				for(String s : playerAtts){
					if(a.getName().equals(s)){
						attSeen++;
					}
				}
			}
			
			String loc = p.getLocsLeft().get(choice - 1);
			p.setMoney(p.getMoney() - 2000);
			
			for(int i = 0; i < p.getLocsLeft().size(); i++){
				if(p.getLocsLeft().get(i).equals(locOne.getName())){
					if(attSeen >= 3){
						ArrayList<String> locsLeft = p.getLocsLeft();
						locsLeft.remove(i);
						p.setLocsLeft(locsLeft);
					}
				}
			}
			if(attSeen >= 3){
				p.getLocsBeen().add(p.getCurrentLoc());
			}
			p.setScore(p.getScore() + (distance * 10));
			if(distance > 50){
				p.setScore(p.getScore() + 25);
			}
			p.setDistanceTraveled(p.getDistanceTraveled() + distance);
			p.setCurrentLoc(loc);
			save(p);
			loop();
		}
	}

	public static void doStuff(Player p){
		System.out.println("Attractions:");
		String loc = p.getCurrentLoc();
		Parser parse = new Parser();
		Location currentLoc = null;
		ArrayList<Location> locs = parse.parseLoc("data/locations.txt");
		for(Location l : locs){
			if(l.getName().equals(loc)){
				currentLoc = l;
			}
		}
		int index = 1;
		ArrayList<Attraction> atts = currentLoc.getAttractions();
		System.out.println(p.getAttsBeen());
		for(int i = 0; i < atts.size(); i++){
			boolean seen = false;
			for(String s : p.getAttsBeen()){
				if(atts.get(i).getName().equals(s)){
					seen = true;
					atts.remove(i);
				}
			}
			if(seen == false){
				System.out.println("[" + (i+1) + "]" + atts.get(i).getName()
						+ ": $" + atts.get(i).getPrice() + ", "
						+ atts.get(i).getPopularity() + " pts");
			index++;
			}
			else{
				i--;
			}
		}
		System.out.println("[" + (index) + "]Main Menu");
		int choice = keyboard.nextInt();
		if(choice == index){
			loop();
		}
		else{
			Attraction a = atts.get(choice - 1);
			ArrayList<String> attSeen = p.getAttsBeen();
			attSeen.add(a.getName());
			p.setScore(p.getScore() + a.getPopularity());
			p.setMoney(p.getMoney() - (int)a.getPrice());
			p.setAttsBeen(attSeen);
			
			ArrayList<Attraction> attns = currentLoc.getAttractions();
			System.out.println(attns.size());
			if(currentLoc.getAttractions().size() <= 1){
				System.out.println("Aha");
				for(int i = 0; i < p.getLocsLeft().size(); i++){
					ArrayList<String> locsLeft = p.getLocsLeft();
					locsLeft.remove(i);
					p.setLocsLeft(locsLeft);
				}
			}
			//System.out.println(p);
			save(p);
			loop();
		}

	}
	
	public static void showInfo(Player p){
		System.out.println("Player Info\n===========");
		System.out.println("Points: " + p.getScore());
		System.out.println("Cash: " + p.getMoney());
		System.out.println("Distance Traveled: " + p.getDistanceTraveled());
		System.out.println("~~~Press Any Key (but enter/return) To Return~~~");
		if(keyboard.next() != null){
			loop();
		}
	}
	
	public static int calculateDistance(Location s, Location e){
		long latS = s.getLat();
		long lonS = s.getLon();
		long latE = e.getLat();
		long lonE = e.getLon();
		
		double dLat = Math.toRadians(latS-latE);
		double dLon = Math.toRadians(lonS - lonE);
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +  
		         Math.cos(Math.toRadians(latS)) * Math.cos(Math.toRadians(latE)) *  
		         Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.asin(Math.sqrt(a));
		double d = 6371 * c;
		
		//double longDist = Math.sqrt(((latE*latE) - (latS*latS)) + ((lonE*lonE) - (lonS*lonS)));
		System.out.println(d);
		return (int)Math.round(d);
	}
	
	public static void endGame(Player p){
		System.out.println("Game Over\n========\nLet's See How You Did!");
		System.out.println("Name: " + p.getName());
		System.out.println("Cash Left: " + p.getMoney());
		System.out.println("Distance Traveled: " + p.getDistanceTraveled());
		System.out.println("===================");
		System.out.println("Total Points: " + p.getScore());
		mainMenu();
	}
	
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        
	    }
	}

}