//Small app to generate triangular patterns.

int cWidth = 400;
int cHeight = 720;

int margins = 15;
float xDiv = 1;
float yDiv = 1;

float a,b;

PVector v1;

void setup() 
{
	size(cWidth, cHeight);
	//noLoop();
}

void draw() 
{
	background(230);
	xDiv = constrain((mouseX/10), 1, 1000);
	yDiv = constrain((mouseY/10), 1, 1000);
	a = (cWidth-(margins*2))/xDiv;
	b = (cHeight-(margins*2))/yDiv;

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

				}else // Odd columns.
				{
					createTriangles(v1,1);

				}
			}else if (j%2 != 0) //Odd Rows
			{			
				if (i%2 == 0) // Even points.
				{
					createTriangles(v1,2);

				}else // Odd Points
				{
					createTriangles(v1,3);

				}	
			}
		}		
	}
}

void createTriangles(PVector v, int identifier)
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