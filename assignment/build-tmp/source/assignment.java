import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Assignment extends PApplet {

//Please Note parts of this project will only work in processing 3

public void setup () {
	
	background (255);

	Draw draw = new Draw ();
	OOPAssignmentUtils util = new OOPAssignmentUtils();

	ArrayList<GameData> games = new ArrayList<GameData> ();
	games = util.populate ();

	

	ArrayList<Genre> gameGenre = new ArrayList<Genre> ();
	gameGenre = util.populateGenre();

	
	ArrayList<Developer> devs = new ArrayList<Developer> ();
	devs = util.populateDeveloper();


	

	// draw.drawBarChart(games);
	// draw.drawDeveloperVisualization(games, devs);
	// draw.drawTrendGraph(games);
	draw.drawGenreVisualization(gameGenre);
}
class Developer {
	String name;
	int freq;

	Developer () {
		name = "";
		freq = 0;
	}

	Developer (String line) {
			name = line;
			freq = 0;
	}

	/*
		Find the most popular dev updated
			-compare the values in ArrayList<GameData> with the dev name in ArrayList<Developer>
			-add 1 to the freq variable if it is a match
			-do a wordle for to show the most popular developer
	*/
	

	public ArrayList<Developer> developerFrequency (ArrayList<GameData> gameInfo,
								  ArrayList<Developer> devs) {
		for (int i = 0 ; i < gameInfo.size () ; i ++) {
			for (int j = 0 ; j < devs.size () ; j ++) {
				if (gameInfo.get(i).developerName.equals (devs.get(j).name)) {
					devs.get(j).freq ++;
				}
			}
		}

		return devs;
	}

	public int findMostFreqDeveloper (ArrayList<Developer> devs) {
		int maxIndex = 0;

		for (int i = 0 ; i < devs.size () ; i ++) {
			if (devs.get(i).freq > devs.get(maxIndex).freq) {
				maxIndex = i;
			}
		}

		return maxIndex;
	}
}
class Draw {

	public Draw () {
		
	}

	public void drawBarChart (ArrayList<GameData> gameData) {
	
		float border = width * 0.1f;
		float verticalRange = height - (border * 2.0f);
		float horRange = width - (border * 2.0f);
		float barWidth = verticalRange / gameData.size ();
		float y = height - border - barWidth; // - barwidth because it drwas down from the x,y point down



		line (border, height - border, border, border);
		line (border, height - border, width - border, height - border);

		for (int i = 0; i < gameData.size (); ++i) {
			fill (255, 0, 0);
			rect (border, y, map (gameData.get(i).criticReviewScore, 0, 100, border, horRange), barWidth);
			fill (0, 255, 0);
			rect (border, y, map (gameData.get(i).userReviewScore, 0, 100, border, horRange), barWidth);

			//text for the y axis
			fill (0);
			text (gameData.get(i).gameName, border * 1.2f, y + (barWidth * 0.9f));
			y -= barWidth;
		}

		// for (int i = 0; i <= gameData.size (); ++i) {
		// 	float yaxisLine = height - border - ((float)i * barWidth);
		// 	line (border, yaxisLine, border * 0.8f, yaxisLine);
		// }

		//Key for the graph
		fill (255, 0, 0);
		rect (border, height * 0.02f, border, height * 0.02f);
		fill(0);
		text ("Critic Review", border + (width * 0.02f), height * 0.035f);
		fill (0, 255, 0);
		rect (border, height * 0.06f, border, height * 0.02f);
		fill(0);
		text ("User Review", border + (width * 0.02f), height * 0.075f);

		fill(0);
		textAlign(CENTER, CENTER);
		text("Top 50 PC Games of All Time Critic and User Scores", width / 2.0f, border * 0.5f);

		for (int i = 0; i <= 10; ++i) {
			float xaxisLine = border + ((horRange / 10.0f) * (float) i);
			line (xaxisLine, height - border, xaxisLine, height - (border * 0.8f));
			text(i * 10, xaxisLine, height - (border * 0.6f));
		}
	}

	public void drawGenreVisualization (ArrayList<Genre> gameGenre) {
	
		//TODO: Come up with icons 
		//TODO: Replace the ellipses with the icons that i will make later
		//	1.Action is a gun with the ammo count as its percentage of the games
		//	2.Action Adventure is a map with a knife on it 
		// 	3.Adventure is a map
		//	4. Strategy is a lighbulb in a head shape
		// 	5.RPG is a dice with the number as its percentage
		//	6.Simulation is going to be a stick man
		//	7.Puzzle will be a few puzzle pieces
		// 	8. Sport is going to be a ball
		//  9. MMORPG is going to be a globe thing around the dice(like the RPG one)
		//need to use map or make a ratio to make the circles/any shape i choose bigger
		//

		//Not want i want it to be just testing if the other functions work
		Genre genre = new Genre();
		int sum = genre.sumGenre(gameGenre);
	 	float horRange = width / 3.0f;
	 	float vertRange = height / 3.0f;
	 	float radius = width;
	 	float x = 0.0f;
	 	float y = vertRange / 2.0f;

		for (int i = 0; i < gameGenre.size (); ++i) {

			if (i % 3 == 0) { //FIXME: need to change the hard coding here 
				x = horRange / 2.0f;
				//only need y to increase after the first loop
				if (i > 0) {
					y += vertRange;
				}
			}
			float ratio = (float) gameGenre.get(i).amount / (float) sum;
			fill(random(0, 255), 0, 0);
			ellipse(x, y, radius * ratio, radius * ratio);
			fill(255);

			x += horRange; 
		}
	}

	public void drawTrendGraph (ArrayList<GameData> gameData) {
		//TODO: Put label on the xaxis and put in a key
		float border = width * 0.1f;
		float verticalRange = height - (border * 2.0f);
		float horRange = width - (border * 2.0f);

		for (int i = 1; i < gameData.size (); ++i) {
			

			float x1 = (float) map (i - 1, 0, gameData.size (), border, width - border);
			float y1 = (float) map (gameData.get (i - 1).criticReviewScore, 0, 100, height - border, border);
			float x2 = (float) map (i, 0, (float) gameData.size (), border, width - border);
			float y2 = (float) map (gameData.get (i).criticReviewScore, 0, 100, height - border, border);

			float x3 = map (i - 1, 0, gameData.size (), border, width - border);
			float y3 = map (gameData.get (i - 1).userReviewScore, 0, 100, height - border, border);
			float x4 = map (i, 0, gameData.size (), border, width - border);
			float y4 = map (gameData.get (i).userReviewScore, 0, 100, height - border, border);

			stroke (0);
			line (x1, y1, x2, y2);
			stroke (0, 255, 0);
			line(x3, y3, x4, y4);

			//TODO: Figure out a way to make this more appealing to the eye
			fill (0);
			if(i % 2 == 0)
				text (gameData.get(i).gameName, x1, height - (border * 0.6f));
			else 
				text (gameData.get(i).gameName, x1, height - (border * 0.4f));
		}

		stroke(0);
		line (border, height - border, border, border);
		line (border, height - border, width - border, height - border);

		for (int i = 0; i <= gameData.size (); ++i) {
			float xaxisLine = (float) map (i, 0, (float) gameData.size (), border, width - border);
			line (xaxisLine, height - border, xaxisLine, height - (border * 0.8f));
		}

		fill(0);
		textAlign(CENTER, CENTER);
		text("Top 50 PC Games of All Time Critic and User Scores", width / 2.0f, border * 0.5f);

		for (int i = 0; i <= 10; ++i) {
			float yaxisLine = height - border - ((verticalRange / 10.0f) * (float) i);
			line (border, yaxisLine, border * 0.8f, yaxisLine);
			text(i * 10, border * 0.6f, yaxisLine);

		}
	}

	public void drawFolderIcon () {
		float border = width * 0.05f;
		float vertRange = height - (border * 2.0f);
		float horRange = width - (border * 2.0f);

		stroke (245, 241, 222);
		fill (245, 241, 222);
		rect (border, border, horRange, vertRange);
		rect (border, border, (width * 0.2f), - (width * 0.02f));

		fill (0);
		text ("Devloper", (border * 1.05f), border);
	}

	public void drawDeveloperVisualization (ArrayList<GameData> gameInfo,
									 ArrayList<Developer> devs) {
		devs = new Developer().developerFrequency (gameInfo, devs);
		drawFolderIcon ();

		int points = devs.size ();
		float cx = width / 2.0f;
		float cy = height / 2.0f;
		float radius = width * 0.2f;
		float theta = 0.0f;
		float theataInc = TWO_PI / 2.0f * (float) points;
		float lastX = cx;
		float lastY = cy - radius;

		

		for (int i = 0 ; i < points * 2 ; ++i) {
			//dont want it to draw when mod 2 == 0
			//NOTE: Drawing at all points for now
			float theata = (float) i * theataInc;
			float x, y;

			//FIXME: The points arent changing 
			x = cx + sin(theta) * radius;
			y = cy - cos(theta) * radius;

			stroke (0);
			ellipse (x, y, 10, 10);
			line (x, y, lastX, lastY);
			println(x + ", " + y + ", " + i + ", " + lastX + ", " + lastY);
	      	lastX = x;
	      	lastY = y; 
		}

		ellipseMode(RADIUS);
		fill(155, 111, 155);
		ellipse (cx, cy, radius, radius);
		ellipseMode(CENTER);
		fill(255);
		ellipse (cx, cy, radius, radius);
	}
}
class GameData {

	float criticReviewScore;
	float userReviewScore;
	String gameName;
	String developerName;
	

	GameData () {
		criticReviewScore = 0;
		userReviewScore = 0;
		gameName = "";
		developerName = "";
	};

	GameData (String line) {
		String[] parts = line.split(",");

		criticReviewScore = Float.parseFloat(parts[0]);
		userReviewScore = Float.parseFloat(parts[1]);
		gameName = parts[2];
		developerName = parts[3];
	}

	
}

class Genre {
	int amount;
	String genre;

	Genre () {
		amount = 0;
		genre = "";
	}

	Genre (String line) {
		String[] s = line.split(",");
		amount = Integer.parseInt(s[0]);
		genre = s[1];
	}

	/*
	TODO: Make some sort of visualsation for the most popular genre of game 
	TODO: Read in the values of the most popular genre in the file PCGamesGenre.csv
	TODO: Maybe make an icon for each of the genres myself, gun for action

	*/
	

	public int sumGenre (ArrayList<Genre> gameGenre) {
		int sum = 0;

		for (Genre g : gameGenre) {
			sum += g.amount;
		}
		
		return sum;
	}
}
class OOPAssignmentUtils {

	public OOPAssignmentUtils () {
		
	}

	//put all the populate the functions here
	public ArrayList populateGenre () {

		ArrayList<Genre> genre = new ArrayList<Genre> ();
		String[] lines = loadStrings("PCGamesGenre.csv");

		for (String s : lines) {
			Genre values = new Genre (s);
			genre.add (values);
		}

		return genre;
	}

	public ArrayList<GameData> populate () {
		ArrayList<GameData> gameInfo = new ArrayList<GameData> ();

		String[] arr = loadStrings ("PCGames.csv");

		for (String s : arr) {
			GameData data = new GameData (s);
			gameInfo.add (data);
		}

		return gameInfo;
	}

	public ArrayList populateDeveloper () {
		String[] values = loadStrings ("PCGameDevs.csv");
		ArrayList<Developer> devs = new ArrayList<Developer> ();

		for (String s : values) {
			Developer dev = new Developer (s);
			devs.add (dev);
		}

		return devs;
	}
}
  public void settings() { 	size (1000, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Assignment" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
