import oscP5.*;
import netP5.*;
import java.util.Map;

World world;
OscP5 oscP5;

NetAddress myRemoteLocation;


int sizeX = 700;
int sizeY = 700;

void setup() {
	size(sizeX, sizeY,P3D);
	oscP5 = new OscP5(this, 12000);
}

synchronized void draw() {
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

synchronized void oscEvent(OscMessage theOscMessage) {
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