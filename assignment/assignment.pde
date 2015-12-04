//Please Note parts of this project will only work in processing 3



//FIXME: Using the 0 key to change back is not working
//FIXME: Use text align to in all vis's and menu to stop the text going weird


ArrayList<Draw> draw = new ArrayList<Draw>();
ArrayList<GameData> games = new ArrayList<GameData> ();
ArrayList<Genre> gameGenre = new ArrayList<Genre> ();
ArrayList<Developer> devs = new ArrayList<Developer> ();
OOPAssignmentUtils util = new OOPAssignmentUtils();
Menu menu;

void setup() 
{
	size(800, 800);
	background(255);

	menu = new Menu(color(153, 255, 204), color(0, 204, 0), color(102, 204, 204), color(51, 204, 255));
	draw.add(menu);
	DrawBarChart barChart = new DrawBarChart();
	draw.add(barChart);
	DrawTrendGraph trendGraph = new DrawTrendGraph();
	draw.add(trendGraph);
	DrawAreaGraph areaGraph = new DrawAreaGraph();
	draw.add(areaGraph);
	GenreVis genreVis = new GenreVis();
	draw.add(genreVis);

	

	
	games = util.populate();	
	gameGenre = util.populateGenre();	
	devs = util.populateDeveloper();

	Developer dev = new Developer();
	dev.developerFrequency();
	for(int i = 0 ; i < devs.size() ; i ++)
	{
		dev.avgDevScore(devs.get(i).name, i);
	}

	draw.get(0).drawVis();

}

// boolean[] keys = new boolean[512];
void keyPressed()
{
}

boolean isMenu = true;



void mousePressed()
{
	util.updateMenu();
}


void draw()
{
	if(keyPressed)
  	{
  		if(key == '0')
  		{
  			isMenu = true;
  			util.updateMenu();
  		}
  	}
}