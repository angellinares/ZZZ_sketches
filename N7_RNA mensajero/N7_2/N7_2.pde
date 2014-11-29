import oscP5.*;
import netP5.*;
import java.util.Map;
import peasy.*;

World world;

OscP5 oscP5;

NetAddress myRemoteLocation;

ArrayList<PVector> points = new ArrayList<PVector>();
//ArrayList<PVector> pointsStore = new ArrayList<PVector>();

int val1, val2;

int sizeX = 600;
int sizeY = 600;

int functionCounter = 0;

PShape buffer;

boolean drawing = false;

PeasyCam cam;

void setup() {
  world = new World();

  cam = new PeasyCam(this, width/2, height/2, 0, 1000);

  size(sizeX, sizeY, P3D);
  noSmooth();

  buffer = createShape(GROUP);
  buffer.beginShape(GROUP);

  // if(frame!= null){
  //   frame.setResizable(true);
  // }

  oscP5 = new OscP5(this, 12000);
}

synchronized void draw() {


  background(120);

  pushMatrix();
  translate(width/2, height/2, width/2);
  noFill();
  stroke(0, 15);
  strokeWeight(0.5);
  box(width);
  popMatrix();

  pushMatrix();
  translate(width/2, height/2);

  Breed turtleBreed = world.getBreed("turtles");
  if (turtleBreed != null) {
    Set<Agent> turtles = turtleBreed.getAgents();

    for (Agent t: turtles) {
      Object xcor = t.get("xcor");
      Object ycor = t.get("ycor");

      Object state = t.get("estadomrna");
      if (state != null) {
        println(state);
      }

      if (xcor != null && ycor != null && state != null) {
        PVector p = new PVector ((Float) xcor*10, -(Float) ycor*10);
        //ellipse((Float) xcor*10,-(Float) ycor*10,12,12);
        t.render (p, int((Float) state), t.age);
        if (!points.contains(p)) {
          points.add(p);

          //if (points.size()%10 == 0){
          loadTempShape(points);
          points.clear();
          //}
        }
      }
    }
  }

  //beginShape(POINTS);

  // Normal loop for representation.
  /*
  for (PVector p : points){
   
   stroke(map(p.z, 0, (float)eventCounter/500,0,255));
   // stroke(255);
   strokeWeight(3);
   point(p.x, p.y, p.z);
   // color c = color(255,0,map(p.z,0,100,0,255));
   // set((int)p.x,(int)p.y,c);
   //endShape(); 
   }
   */
  popMatrix();

  if (buffer.getChildCount()>0) {
    shape(buffer);
    drawing = true;
  }
  else {

    drawing = false;
  }

  hint(DISABLE_DEPTH_TEST);
  cam.beginHUD();
  fill(255);
  textSize(10);
  //if (turtleBreed != null && ! turtleBreed.breeds.isEmpty()) {
   // text("Mrna: " + turtleBreed.breeds.size(), 10, height - 20);
  //} 
  text("fps: "+frameRate, 10, height-30);
  text("n.points: "+points.size(), 10, height-45);
  text("Buffer call: "+functionCounter, 10, height-60);
  text("Drawing buffer: "+str(drawing), 10, height-75);
  cam.endHUD();
  hint(ENABLE_DEPTH_TEST);

  if (keyPressed && (key == 'c')) {
    save(frameCount+".png");
  }

  if (keyPressed && (key == 'r')) {
    setup();
  }
}



void loadBuffer(PShape temp) {

  buffer.addChild(temp);
}

void loadTempShape(ArrayList<PVector> pointsToSave) {

  PShape tempShape;
  tempShape = createShape(POINTS);
  tempShape.beginShape(POINTS);
  tempShape.translate(width/2, height/2, 0);
  tempShape.strokeWeight(3);
  tempShape.stroke(0);

  for (PVector p : pointsToSave) {

    tempShape.vertex(p.x, p.y, p.z);
  }

  tempShape.endShape();

  loadBuffer(tempShape);

  functionCounter++;
}

/**
 * We need to obtain the information from 'addressPattern', 'typeTag'
 * and 'oscArgument':
 * 
 * The 'addressPattern' is like: '/breedName/VARNAME'
 * The 'typeTag' is like: 'if' or 'is'
 *     'if' says that the first argument is an Integer and the second
 *       is a Float.
 *     'is' says that the first argument is an Integer and the second
 *       is a String.
 * The 'oscArgument' is an array of with the arguments.
 */
synchronized void oscEvent(OscMessage theOscMessage) {
  String path = theOscMessage.addrPattern();
  String[] splitted = path.split("/");

  String breedName = splitted[1];
  String varName = splitted[2].toLowerCase();

  char[] types = theOscMessage.typetag().toCharArray();

  int id = theOscMessage.get(0).intValue();
  Object varValue = null;

  OscArgument arg = theOscMessage.get(1);
  switch(types[1]) {
  case 'f':
    varValue = arg.floatValue();
    break;
  case 's':
    varValue = arg.stringValue();
    break;
  default:
    varValue = arg;
    break;
  }

  //println("Updating " + varName + " to " + varValue);

  Breed breed = world.getBreed(breedName);
  if (breed == null) {
    breed = new Breed();
    Agent agent = new Agent(id, 0, varName, varValue);

    breed.addAgent(id, agent);
    world.addBreed(breedName, breed);
  } 
  else {
    Agent agent = breed.getAgent(id);
    if (agent == null) {
      agent = new Agent(id, 0, varName, varValue);
      breed.addAgent(id, agent);
    } 
    else {
      agent.add(varName, varValue);
    }
  }
}

float translateCor(float cor) {
  float result = cor + 16;
  result = ((result * width) / 32);
  return result;
}

