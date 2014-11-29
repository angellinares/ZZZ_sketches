import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 
import java.util.Map; 
import peasy.*; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Set; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Set; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Set; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LOGOTOP5_004 extends PApplet {






World world;

OscP5 oscP5;

NetAddress myRemoteLocation;

ArrayList<PVector> points = new ArrayList<PVector>();


int val1, val2;

int sizeX = 800;
int sizeY = 800;
 
int eventCounter = 0;

PeasyCam cam;

public void setup(){
  world = new World();

  cam = new PeasyCam(this, width/2, height/2, 0, 300);
  
  size(sizeX, sizeY,P3D);

  // if(frame!= null){
  //   frame.setResizable(true);
  // }

  oscP5 = new OscP5(this, 12000);
}

public synchronized void draw(){

  background(150);

  pushMatrix();
  translate(width/2, height/2, width/2);
  noFill();
  stroke(0,15);
  strokeWeight(0.5f);
  box(width);
  popMatrix();

  pushMatrix();
  translate(width/2, height/2);

  Breed turtleBreed = world.getBreed("turtles");
  if(turtleBreed != null) {
    Set<Agent> turtles = turtleBreed.getAgents();
    
    for(Agent t: turtles) {
      Object xcor = t.get("xcor");
      Object ycor = t.get("ycor");
      if(xcor != null && ycor != null) {
        
        PVector p = new PVector ((Float) xcor*5, -(Float) ycor*5, (float)eventCounter/250);

        if (!points.contains(p)){
          points.add(p);
        }

      }
    }  
  }
  
  for (PVector p : points){

    stroke(map(p.z, 0, (float)eventCounter/500,0,255));
    // stroke(255);
    strokeWeight(3);
    point(p.x, p.y, p.z);
    // color c = color(255,0,map(p.z,0,100,0,255));
    // set((int)p.x,(int)p.y,c);
    
  }

  popMatrix();

  hint(DISABLE_DEPTH_TEST);
  cam.beginHUD();
  fill(255);
  textSize(10);
  text("fps: "+frameRate,10,height-30);
  text("n.points: "+points.size(),10,height-45);
  cam.endHUD();
  hint(ENABLE_DEPTH_TEST);

  if (keyPressed && (key == 'c')){
    save(frameCount+".png");
  }

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
  public synchronized void oscEvent(OscMessage theOscMessage) {
    String path = theOscMessage.addrPattern();
    String[] splitted = path.split("/");
    
    String breedName = splitted[1];
    String varName = splitted[2].toLowerCase();

    if (varName.equals("ycor")){
      eventCounter++;
    }
    
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
    
    Breed breed = world.getBreed(breedName);
    if(breed == null) {
      breed = new Breed();
      Agent agent = new Agent(id, varName, varValue);
      
      breed.addAgent(id, agent);
      world.addBreed(breedName, breed);
      
    } else {
      Agent agent = breed.getAgent(id);
      if(agent == null) {
        agent = new Agent(id, varName, varValue);
        breed.addAgent(id, agent);
      
      } else {
        agent.add(varName, varValue);
      }
    }
    println("path: "+path);
  }

public float translateCor(float cor) {
 float result = cor + 16;
 result = ((result * width) / 32);
 return result;
}





/**
 * Class Agent - Store all the variables of an agent
 */
class Agent {
	Integer id;
	HashMap<String, Object> variables;

	Agent(Integer id) {
		variables = new HashMap<String, Object>();
		this.id = id;
	}
	
	Agent(Integer id, String varName, Object varValue) {
		variables = new HashMap<String, Object>();
		this.id = id;
		
		variables.put(varName, varValue);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer ident) {
		id = ident;
	}

	public void add(String name, Object variable) {
		variables.put(name, variable);
	}

	public Object get(String name) {
		return variables.get(name);
	}

	public HashMap<String, Object> getAgentMap() {
		return variables;
	}

	public Set<String> getVariableNames() {
		return variables.keySet();
	}

	public Set<Object> getVariables() {
		return new HashSet<Object>(variables.values());
	}
}




/**
 * Class Breed - Store all the agents of a breed
 */
class Breed {
	HashMap<Integer, Agent> breeds;

	Breed() {
		breeds = new HashMap<Integer, Agent>();
	}

	public void addAgent(Integer id, Agent agent) {
		breeds.put(id, agent);
	}

	public Agent getAgent(Integer id) {
		return breeds.get(id);
	}

	public HashMap<Integer, Agent> getBreedMap() {
		return breeds;
	}

	public Set<Agent> getAgents() {
		return new HashSet<Agent>(breeds.values());
	}
}




/**
 * Class World - Store all the breeds
 */
class World {
	HashMap<String, Breed> world;

	World() {
		world = new HashMap<String, Breed>();
	}

	public void addBreed(String breedName, Breed breed) {
		world.put(breedName, breed);
	}

	public Breed getBreed(String breed) {
		return world.get(breed);
	}

	public HashMap<String, Breed> getBreedMap() {
		return world;
	}

	public Set<String> getBreedNames() {
		return world.keySet();
	}

	public Set<Breed> getBreeds() {
		return new HashSet<Breed>(world.values());
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LOGOTOP5_004" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
