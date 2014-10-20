import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class Interface {

	public JFrame frame;
	private final JPanel panel_1 = new JPanel();
	private JTextField nameField;
	private final Parser parse = new Parser();
	private Player p = null;
	
	private final ArrayList<Location> locs = parse.parseLoc(System.getProperty("user.dir") + "/data/locations.txt");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		final String[] locStrings = new String[locs.size()];
		for(int i = 0; i < locStrings.length; i++){
			locStrings[i] = locs.get(i).getName();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "mainMenu");
		
		JLabel lblGlobetrotter = new JLabel("GlobeTrotter");
		lblGlobetrotter.setFont(new Font("American Typewriter", Font.PLAIN, 30));
		
		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				File f = null;
				File save = null;
				try {
					f = new File(new File(".").getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(f);
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "TXT Files", "txt");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(frame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	save = chooser.getSelectedFile();
			    }
			    InputStream is = null;
				try {
					is = new FileInputStream(save.getAbsolutePath());
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}//Main.class.getResourceAsStream(save.getAbsolutePath());
						
				InputStreamReader isr = new InputStreamReader(is);
				p = parse.getSave(isr);
				//System.out.println(p);
				
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});
		
		JButton newGameButton = new JButton("New Game");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(155)
							.addComponent(loadGameButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(159)
							.addComponent(newGameButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(125)
							.addComponent(lblGlobetrotter)))
					.addContainerGap(141, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(62)
					.addComponent(lblGlobetrotter)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(newGameButton)
					.addGap(40)
					.addComponent(loadGameButton)
					.addGap(89))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().add(panel_1, "newGame");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		//JComboBox comboBox = new JComboBox();
		
		final JComboBox<String> startLocBox = new JComboBox<String>();
		
		for(String s : locStrings){
			startLocBox.addItem(s);
		}
		
		final JRadioButton easyButton = new JRadioButton("Easy");
		easyButton.setSelected(true);
		
		final JRadioButton medButton = new JRadioButton("Medium");
		
		final JRadioButton hardButton = new JRadioButton("Hard");
		
		ButtonGroup difficulties = new ButtonGroup();
		difficulties.add(easyButton);
		difficulties.add(medButton);
		difficulties.add(hardButton);
		JLabel lblNewLabel = new JLabel("Difficulty");
		
		JButton startButton = new JButton("Start!");
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int difficulty = 1;
				int money = 1000000;
				if(medButton.isSelected()){
					money = 500000;
					difficulty = 2;
				}
				else if(hardButton.isSelected()){
					money = 100000;
					difficulty = 3;
				}
				String startLoc = String.valueOf(startLocBox.getSelectedItem());
				String name = nameField.getText();
				ArrayList<String> locNames = new ArrayList<String>();
				for(Location l : locs){
					locNames.add(l.getName());
				}
				p = new Player(name, money, 0, difficulty, 0, startLoc, new ArrayList<String>(), locNames, new ArrayList<String>());
				Main.save(p);
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("New Game");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(150)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(hardButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(medButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(easyButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(startLocBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(nameField, Alignment.LEADING)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(168)
							.addComponent(lblNewLabel_1))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(173)
							.addComponent(startButton)))
					.addContainerGap(166, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(15, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addGap(18)
					.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(startLocBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(easyButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(medButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(hardButton)
					.addGap(18)
					.addComponent(startButton)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6, "gameOverPane");
		
		JLabel lblNewLabel_6 = new JLabel("Game Over");
		
		final JLabel gameOverDistance = new JLabel("");
		
		final JLabel gameOverPoints = new JLabel("");
		
		JButton gameOverBackButton = new JButton("OK");
		gameOverBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainMenu");
			}
		});
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGap(167)
					.addComponent(gameOverBackButton)
					.addContainerGap(166, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panel_6.createSequentialGroup()
					.addGap(189)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(gameOverPoints)
						.addComponent(gameOverDistance)
						.addComponent(lblNewLabel_6))
					.addContainerGap(193, Short.MAX_VALUE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addComponent(lblNewLabel_6)
					.addGap(64)
					.addComponent(gameOverDistance)
					.addGap(18)
					.addComponent(gameOverPoints)
					.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
					.addComponent(gameOverBackButton)
					.addContainerGap())
		);
		panel_6.setLayout(gl_panel_6);

		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, "goSomewhere");
		
		final JLabel placeCostLabel = new JLabel("");
		
		final JLabel placePointsLabel = new JLabel("");
		
		final JComboBox<String> placesBox = new JComboBox<String>();
		placesBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String dest = String.valueOf(placesBox.getSelectedItem());
				Location destination = null;
				Location current = null;
				for(Location l : locs){
					if(l.getName().equals(dest)){
						destination = l;
					}
					if(l.getName().equals(p.getCurrentLoc())){
						current = l;
					}
				}
				int distance = Main.calculateDistance(current, destination);
				int cost = (int) Math.round(50 + (distance * 0.11));
				int score = distance / 50;
				if(distance > 1000){
					score = score + 50;
				}
				placeCostLabel.setText("Cost: " + cost);
				placePointsLabel.setText("Points: " + score);
				
				
			}
		});
		JLabel lblNewLabel_3 = new JLabel("Go Somewhere");
		
		JButton goPlaceButton = new JButton("Go!");
		goPlaceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String destination = String.valueOf(placesBox.getSelectedItem());
				System.out.println(destination);
				Main.travel(p, destination);
				Main.save(p);
				boolean gameOver = checkIfGameOver(p);
				if(gameOver){
					gameOverPoints.setText("Score: " + p.getScore());
					gameOverDistance.setText("Total Distance Traveled" + p.getDistanceTraveled() + " km");
					CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
					cl.show(frame.getContentPane(), "gameOverPane");
				}
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});

		JButton goPlaceBackButton = new JButton("Back");
		goPlaceBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});
		goPlaceBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});

				
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap(190, Short.MAX_VALUE)
					.addComponent(lblNewLabel_3)
					.addGap(168))
				.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
					.addGap(152)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(placesBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
								.addComponent(goPlaceButton)
								.addGap(18)
								.addComponent(goPlaceBackButton)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(placePointsLabel)
								.addComponent(placeCostLabel))))
					.addContainerGap(130, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addGap(18)
					.addComponent(placesBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(placeCostLabel)
					.addGap(18)
					.addComponent(placePointsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(goPlaceButton)
						.addComponent(goPlaceBackButton))
					.addGap(77))
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, "doStuff");
		
		JLabel lblNewLabel_4 = new JLabel("Do Stuff");
		
		final JComboBox<String> stuffBox = new JComboBox<String>();
		
		JButton completeStuffButton = new JButton("Do That!");
		completeStuffButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Location currentLoc = null;
				ArrayList<Location> locs = parse.parseLoc("data/locations.txt");
				for(Location l : locs){
					if(l.getName().equals(p.getCurrentLoc())){
						currentLoc = l;
					}
				}
				String attChosen = String.valueOf(stuffBox.getSelectedItem());
				ArrayList<Attraction> atts = currentLoc.getAttractions();
				Attraction attractionChosen = null;
				for(Attraction at : atts){
					if(at.getName().equals(attChosen)){
						attractionChosen = at;
					}
				}
				//Attraction a = atts.get(choice - 1);
				ArrayList<String> attSeen = p.getAttsBeen();
				attSeen.add(attractionChosen.getName());
				p.setScore(p.getScore() + attractionChosen.getPopularity());
				p.setMoney(p.getMoney() - (int)attractionChosen.getPrice());
				p.setAttsBeen(attSeen);
				
				
				//System.out.println(p);
				Main.save(p);
				boolean gameOver = checkIfGameOver(p);
				if(gameOver){
					gameOverPoints.setText("Score: " + p.getScore());
					gameOverDistance.setText("Total Distance Traveled" + p.getDistanceTraveled() + " km");
					CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
					cl.show(frame.getContentPane(), "gameOverPane");
				}
				else{
					CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
					cl.show(frame.getContentPane(), "mainLoop");
				}
			}
		});
		
		JButton stuffBackButton = new JButton("Cancel");
		stuffBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});
		
		final JLabel attCostLabel = new JLabel("");
		
		final JLabel attPointsLabel = new JLabel("");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(190)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(stuffBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(attCostLabel, Alignment.LEADING)
								.addComponent(attPointsLabel, Alignment.LEADING)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(113)
							.addComponent(completeStuffButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(stuffBackButton)))
					.addContainerGap(147, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_4)
					.addGap(45)
					.addComponent(stuffBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(attCostLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(attPointsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(stuffBackButton)
						.addComponent(completeStuffButton))
					.addGap(64))
		);
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5, "stats");
		
		JLabel lblNewLabel_5 = new JLabel("Player Stats");
		
		final JLabel cashLabel = new JLabel("New label");
		
		final JLabel ptsLabel = new JLabel("New label");
		
		final JLabel milesLabel = new JLabel("New label");
		
		JButton statsBackButton = new JButton("Back");
		statsBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainLoop");
			}
		});
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(185)
							.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
								.addComponent(milesLabel)
								.addComponent(ptsLabel)
								.addComponent(cashLabel)
								.addComponent(lblNewLabel_5)))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(162)
							.addComponent(statsBackButton)))
					.addContainerGap(171, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_5)
					.addGap(18)
					.addComponent(cashLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ptsLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(milesLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
					.addComponent(statsBackButton)
					.addContainerGap())
		);
		panel_5.setLayout(gl_panel_5);
		newGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "newGame");
				//Main.newGame();
				
			}
		});
		
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "mainLoop");
		
		JLabel lblNewLabel_2 = new JLabel("Main Menu");
		
		JButton goButton = new JButton("Go Somewhere");
		goButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				placesBox.removeAllItems();
				String[] locsLeft = Main.getLocsLeft(p);
				for(String s : locsLeft){
					placesBox.addItem(s);
				}
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "goSomewhere");
			}
		});
		
		JButton doStuffButton = new JButton("Do Something");
		doStuffButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Location currentLoc = null;
				for(Location l : locs){
					if(l.getName().equals(p.getCurrentLoc())){
						currentLoc = l;
					}
				}
				ArrayList<Attraction> attsList = currentLoc.getAttractions();
				attCostLabel.setText("Cost: " + (int)Math.round(attsList.get(0).getPrice()));
				attPointsLabel.setText("Points: " + (int)Math.round(attsList.get(0).getPopularity()));
				stuffBox.removeAllItems();
				String[] atts = Main.getAtts(p);
				for(String s : atts){
					stuffBox.addItem(s);
				}
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "doStuff");
			}
		});
		
		JButton statsButton = new JButton("Stats");
		statsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				cashLabel.setText("Cash: " + Integer.toString(p.getMoney()));
				ptsLabel.setText("Points: " + Integer.toString(p.getScore()));
				milesLabel.setText("Miles Traveled: " + Integer.toString(p.getDistanceTraveled()));
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "stats");
			}
		});
		
		JButton exitMenuButton = new JButton("Exit To Menu");
		exitMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				CardLayout cl = (CardLayout)frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "mainMenu");
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(190)
							.addComponent(lblNewLabel_2))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(162)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(doStuffButton)
								.addComponent(goButton)
								.addComponent(exitMenuButton)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(185)
							.addComponent(statsButton)))
					.addContainerGap(165, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2)
					.addGap(36)
					.addComponent(goButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(doStuffButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statsButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(exitMenuButton)
					.addContainerGap(86, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
	
	}
	
	private final boolean checkIfGameOver(Player p){
		boolean over = false;
		int totalAtts = locs.size() * 3;
		System.out.println("Out Of Money: " + (p.getMoney() <= 0));
		System.out.println("Out of Dests: " + (p.getLocsLeft().size() == 0));
		if(p.getMoney() <= 0 || p.getAttsBeen().size() >= totalAtts){
			over = true;
		}
		return over;
	}
}