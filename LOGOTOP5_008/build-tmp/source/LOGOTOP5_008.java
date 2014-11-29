import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 
import java.util.Map; 
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

public class LOGOTOP5_008 extends PApplet {





World world;
OscP5 oscP5;

NetAddress myRemoteLocation;


int sizeX = 700;
int sizeY = 700;

public void setup() {
	size(sizeX, sizeY,P3D);
	oscP5 = new OscP5(this, 12000);
}

public synchronized void draw() {
	background(150);

	// Breed turtleBreed = world.getBreed("turtles");
 //  	if(turtleBreed != null) {
 //    	Set<Agent> turtles = turtleBreed.getAgents();
    
 //   		 for(Agent t: turtles) {
 //     		 Object xcor = t.get("xcor");
 //      		 Object ycor = t.get("ycor");
 //      		}
 //      	}     	
}

public synchronized void oscEvent(OscMessage theOscMessage) {
    String path = theOscMessage.addrPattern();
    String[] splitted = path.split("/");

    //println("splitted: "+splitted);
    
    String breedName = splitted[1];
    //println("breedName: "+breedName);
    String varName = splitted[2].toLowerCase();

    // if (varName.equals("ycor")){
    //   eventCounter++;
    // }
    
    char[] types = theOscMessage.typetag().toCharArray();
    // Extract the variable/property name
    int id = theOscMessage.get(0).intValue();
    Object varValue = null;
    

    // Extract the value.
    OscArgument arg = theOscMessage.get(1);
    println("arg: "+arg);
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

    println("breedName: "+breedName +" "+ "varName: "+varName+" "+";"+varValue);


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
    String[] appletArgs = new String[] { "LOGOTOP5_008" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
