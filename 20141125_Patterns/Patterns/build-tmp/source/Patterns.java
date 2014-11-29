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

public class Patterns extends PApplet {

//Small app to generate triangular patterns using noise.

int cWidth = 400;
int cHeight = 720;

int margins = 15;
float xDiv = 1;
float yDiv = 1;

float a,b;

PVector v1;

public void setup() 
{
	size(cWidth, cHeight);
	//noLoop();
}

public void draw() 
{
	background(230);
	xDiv = constrain((mouseX/10), 1, 1000);
	yDiv = constrain((mouseY/10), 1, 1000);
	a = (cWidth-(margins*2))/xDiv;
	b = (cHeight-(margins*2))/yDiv;
	// Custom measures based loop

	// for (int i = margins; i < a*xDiv; i+=a) {
	// 	for (int j = margins; j < b*yDiv; j+=b) {
	// 		v1 = new PVector(i,j);
	// 		//println("v1: "+v1);
	// 		createTriangles1(v1);
	// 	}
	// }

	// Standard double loop.

	for (int i = 0; i < xDiv; ++i) 
	{
		for (int j = 0; j < yDiv; ++j) 
		{
			v1 = new PVector((i*a)+margins, (j*b)+margins);

			if (j%2 == 0) //Even Rows.
			{		
				if (i%2 == 0) // Even columns.
				{
					createTriangles(v1,0);
					// fill(255);
					// ellipse((i*a)+margins, (j*b)+margins, 10, 10);

				}else // Odd columns.
				{
					createTriangles(v1,1);
					// fill(15);
					// ellipse((i*a)+margins, (j*b)+margins, 10, 10);

				}
			}else if (j%2 != 0) //Odd Rows
			{			
				if (i%2 == 0) // Even points.
				{
					createTriangles(v1,2);
					// fill(255);
					// ellipse((i*a)+margins, (j*b)+margins, 10, 10);

				}else // Odd Points
				{
					createTriangles(v1,3);
					// fill(15);
					// ellipse((i*a)+margins, (j*b)+margins, 10, 10);

				}	
			}
			

		}
		
	}

}

public void createTriangles(PVector v, int identifier)
{
	beginShape();
	fill(0);
	noStroke();

	switch (identifier) {
		case 0: vertex(v.x, v.y);
				vertex(v.x, v.y+b);
				vertex(v.x+a, v.y);
				break;

		case 1: vertex(v.x, v.y);
				vertex(v.x, v.y+b);
				vertex(v.x+a, v.y+b);
				break;

		case 2: vertex(v.x, v.y);
				vertex(v.x+a, v.y);
				vertex(v.x+a, v.y+b);
				break;

		case 3: vertex(v.x, v.y+b);
				vertex(v.x+a, v.y+b);
				vertex(v.x+a, v.y);
				break;
	}
	
	endShape(CLOSE);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Patterns" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
