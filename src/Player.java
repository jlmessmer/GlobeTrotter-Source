

import java.util.ArrayList;

/**
 * 
 * @author jmessmer
 *
 */
public class Player {
	public String name;
	public int difficulty;
	public int money;
	public int score;
	public int distanceTraveled;
	public String currentLoc;
	public ArrayList<String> locsBeen;
	public ArrayList<String> locsLeft;
	public ArrayList<String> attsBeen;
	
	public Player(String name, int money, int score, int difficulty, 
			int distanceTraveled, String currentLoc, 
			ArrayList<String> locsBeen, ArrayList<String> locsLeft, 
			ArrayList<String> attsBeen) {
		super();
		this.name = name;
		this.difficulty = difficulty;
		this.money = money;
		this.score = score;
		this.distanceTraveled = distanceTraveled;
		this.currentLoc = currentLoc;
		this.locsBeen = locsBeen;
		this.locsLeft = locsLeft;
		this.attsBeen = attsBeen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public String getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}

	public ArrayList<String> getLocsBeen() {
		return locsBeen;
	}

	public void setLocsBeen(ArrayList<String> locsBeen) {
		this.locsBeen = locsBeen;
	}

	public ArrayList<String> getLocsLeft() {
		return locsLeft;
	}

	public void setLocsLeft(ArrayList<String> locsLeft) {
		this.locsLeft = locsLeft;
	}

	public ArrayList<String> getAttsBeen() {
		return attsBeen;
	}

	public void setAttsBeen(ArrayList<String> attsBeen) {
		this.attsBeen = attsBeen;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", difficulty=" + difficulty
				+ ", money=" + money + ", score=" + score
				+ ", distanceTraveled=" + distanceTraveled + ", currentLoc="
				+ currentLoc + ", locsBeen=" + locsBeen + ", locsLeft="
				+ locsLeft + ", attsBeen=" + attsBeen + "]";
	}


}
