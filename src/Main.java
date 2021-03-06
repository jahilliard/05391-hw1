import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import java.awt.Robot;


import java.awt.AWTException;



public class Main extends PApplet
{
	//THE TEAM MEMBERS PARTICIPANT NUMBER
	final int participant = 1;
	int trialNumber = 0;
	int begUseX = 0;
	int begUseY = 0;
	int boundsX;
	int boundsY;
	int boundsWidth;
	int previousTime;
	
	// you shouldn't need to edit any of these variables
	int margin = 300; // margin from sides of window
	final int padding = 0; // padding between buttons and also their width/height
	final int size = 60; // total size of button including padding on both sides
	ArrayList<Integer> trials = new ArrayList<Integer>(); //contains the order of buttons that activate in the test
	int trialNum = 0; //the current trial number (indexes into trials array above)
	int startTime = 0; // time starts when the first click is captured.
	int userX = mouseX; //stores the X position of the user's cursor
	int userY = mouseY; //stores the Y position of the user's cursor
	int finishTime = 0; //records the time of the final click
	int hits = 0; //number of succesful clicks
	int misses = 0; //number of missed clicks

	// You can edit variables below here and also add new ones as you see fit
	int numRepeats = 3; //sets the number of times each button repeats in the test (you can edit this)

	Minim minim;
	AudioPlayer player;
	AudioInput input;
	Robot robot;

	public void draw()
	{
		margin = width/3; //scale the padding with the size of the window
		background(0); //set background to black

		if (trialNum >= trials.size()) //check to see if test is over
		{
			fill(255); //set fill color to white
			//write to screen
			text("Finished!", width / 2, height / 2); 
			text("Hits: " + hits, width / 2, height / 2 + 20);
			text("Misses: " + misses, width / 2, height / 2 + 40);
			text("Accuracy: " + (float)hits*100f/(float)(hits+misses) +"%", width / 2, height / 2 + 60);
			text("Total time taken: " + (finishTime-startTime) / 1000f + " sec", width / 2, height / 2 + 80);
			text("Average time for each button: " + ((finishTime-startTime) / 1000f)/(float)(hits+misses) + " sec", width / 2, height / 2 + 100);			
			return; //return, nothing else to do now test is over
		}

		fill(255); //set fill color to white
		text((trialNum + 1) + " of " + trials.size(), 40, 20); //display what trial the user is on

		for (int i = 0; i < 16; i++)// for all button
			drawButton(i); //draw button
		
		stroke(255, 0, 0);
		
		Rectangle bounds = getButtonLocation(trials.get(trialNum));
		line(userX, userY, bounds.x+bounds.width/2, bounds.y+bounds.height/2);
		
		if ((userX > bounds.x && userX < bounds.x + bounds.width) && (userY > bounds.y && userY < bounds.y + bounds.height)) // test to see if hit was within bounds
		{
			if (!player.isPlaying() || player.position() > 1000)
				player.play(0);
		}
		
		noStroke();

		// you shouldn't need to edit anything above this line! You can edit below this line as you see fit
		
		fill(255, 0, 0); // set fill color to red
		ellipse(userX, userY, 20, 20); //draw user cursor as a circle with a diameter of 20

	}
	
	public void keyPressed() {
		if (key == ' ') {
			mousePressed();
		}
	}

	public void mousePressed() // test to see if hit was in target!
	{
				
		if (trialNum >= trials.size()){
			return;	
		}

		if (trialNum == 0) //check if first click
			startTime = millis();

		if (trialNum == trials.size() - 1) //check if final click
		{
			finishTime = millis();
			//write to terminal
			System.out.println("Hits: " + hits);
			System.out.println("Misses: " + misses);
			System.out.println("Accuracy: " + (float)hits*100f/(float)(hits+misses) +"%");
			System.out.println("Total time taken: " + (finishTime-startTime) / 1000f + " sec");
   			System.out.println("Average time for each button: " + ((finishTime-startTime) / 1000f)/(float)(hits+misses) + " sec");
 	}

		Rectangle bounds = getButtonLocation(trials.get(trialNum));

		// YOU CAN EDIT BELOW HERE IF YOUR METHOD REQUIRES IT (shouldn't need to edit above this line)

		if ((userX > bounds.x && userX < bounds.x + bounds.width) && (userY > bounds.y && userY < bounds.y + bounds.height)) // test to see if hit was within bounds
		{
//			System.out.println("HIT! " + trialNum + " " + (millis() - startTime)); // success
			hits++;
			boundsX = bounds.x;
			boundsY = bounds.y;
			boundsWidth = bounds.width;
			trialNumber++;
			System.out.print(participant);
			System.out.print(", ");
			System.out.print(trialNumber);
			System.out.print(", ");
			System.out.print(begUseX);
			System.out.print(", ");
			System.out.print(begUseY);
			System.out.print(", ");
			System.out.print(boundsX);
			System.out.print(", ");
			System.out.print(boundsY);
			System.out.print(", ");
			System.out.print(boundsWidth);
			System.out.print(", ");
			System.out.print(millis() - previousTime );
			System.out.print(", ");
			System.out.print("1");
			System.out.println("");
			previousTime = millis();
			begUseX = userX;
			begUseY = userY;
		} else
		{
//			System.out.println("MISSED! " + trialNum + " " + (millis() - startTime)); // fail
			misses++;
			boundsX = bounds.x;
			boundsY = bounds.y;
			boundsWidth = bounds.width;
			trialNumber++;
			System.out.print(participant);
			System.out.print(", ");
			System.out.print(trialNumber);
			System.out.print(", ");
			System.out.print(begUseX);
			System.out.print(", ");
			System.out.print(begUseY);
			System.out.print(", ");
			System.out.print(boundsX);
			System.out.print(", ");
			System.out.print(boundsY);
			System.out.print(", ");
			System.out.print(boundsWidth);
			System.out.print(", ");
			System.out.print(millis() - previousTime );
			System.out.print(", ");
			System.out.print("0");
			System.out.println("");
			previousTime = millis();
			begUseX = userX;
			begUseY = userY;
		}
		
		player.pause();

		//can manipulate cursor at the end of a trial if you wish
//		userX = width/2; //example manipulation
//		userY = height/2; //example manipulation
		 
		trialNum++; // Increment trial number
	}

	
	public void updateUserMouse() // YOU CAN EDIT THIS
	{
		// you can do whatever you want to userX and userY (you shouldn't touch mouseX and mouseY)
		int newX = userX + mouseX - pmouseX, newY = userY + mouseY - pmouseY;
		
		if (newX > margin && newX < margin + 4*size) {
			userX = newX;
		}
		
		if (newY > margin && newY < margin + 4*size) {
			userY = newY;
		}
//		userX += mouseX - pmouseX; //add to userX the difference between the current mouseX and the previous mouseX
//		userY += mouseY - pmouseY; //add to userY the difference between the current mouseY and the previous mouseY
	}

	
	
	
	
	
	
	
	
	// ===========================================================================
	// =========SHOULDN'T NEED TO EDIT ANYTHING BELOW THIS LINE===================
	// ===========================================================================

	public void setup()
	{
		previousTime = millis();
		begUseX = userX;
		begUseY = userY;
		size(900,900); // set the size of the window
		noCursor(); // hides the system cursor (can turn on for debug, but should be off otherwise!)
		noStroke(); //turn off all strokes, we're just using fills here (can change this if you want)
		textFont(createFont("Arial",16));
		textAlign(CENTER);
		frameRate(120);
		ellipseMode(CENTER); //ellipses are drawn from the center (BUT RECTANGLES ARE NOT!)
		// ====create trial order======
		for (int i = 0; i < 16; i++)
			// number of buttons in 4x4 grid
			for (int k = 0; k < numRepeats; k++)
				// number of times each button repeats
				trials.add(i);

		Collections.shuffle(trials); // randomize the order of the buttons
		System.out.println("trial order: " + trials);
		
		minim = new Minim(this);
		player = minim.loadFile("/Users/justinhilliard/Documents/workspace/05391-hw1/sound.mp3");
		input = minim.getLineIn();
		
		userX = width / 2;
		userY = height / 2;
		
		try {
			robot = new Robot();
		} catch (Exception e) {};
		
		robot.mouseMove(userX, userY);
	}

	public Rectangle getButtonLocation(int i)
	{
		double x = (i % 4) * (size) + margin;
		double y = (i / 4) * (size) + margin;

		return new Rectangle((int)x, (int)y, size - padding*2, size - padding*2);
	}
	
	public void drawButton(int i)
	{
		Rectangle bounds = getButtonLocation(i);

		stroke(100);
		if (trials.get(trialNum) == i) {
			if ((userX > bounds.x && userX < bounds.x + bounds.width) && (userY > bounds.y && userY < bounds.y + bounds.height)) // test to see if hit was within bounds
			{
				fill(0, 255, 0);				
			}
			else {
				fill(255, 255, 0); // if so, fill yellow
			}	
		}
		else
			fill(200); // if not, fill gray

		rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public void mouseMoved() // Don't edit this
	{
		updateUserMouse();
	}

	public void mouseDragged() // Don't edit this
	{
		updateUserMouse();
	}

}
