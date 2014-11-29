import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Agent - Store all the variables of an agent
 */
class Agent {
  Integer id;
  int age;
  HashMap<String, Object> variables;
  Agent(Integer id) {
    variables = new HashMap<String, Object>();
    this.id = id;
  }

  Agent(Integer id, Integer age, String varName, Object varValue) {
    variables = new HashMap<String, Object>();
    this.id = id;
    this.age = age;

    //println("Updating " + varName + " to " + varValue  + " for " + this.id);
    variables.put(varName, varValue);
  }

  Integer getId() {
    return id;
  }

  void setId(Integer ident) {
    id = ident;
  }

  void add(String name, Object variable) {
    variables.put(name, variable);
  }

  Object get(String name) {
    return variables.get(name);
  }

  HashMap<String, Object> getAgentMap() {
    return variables;
  }

  Set<String> getVariableNames() {
    return variables.keySet();
  }

  Set<Object> getVariables() {
    return new HashSet<Object>(variables.values());
  }  
  
  void updateAge (){
    age+=1;
  }
  
  void render(PVector loc, int estado, float edad) {
    updateAge();
    int f = 10;
    int xx = 20;

    color cstart = color(30, 30, 200);
    color cend = color(200, 200, 200);
    color current = color(30+edad, 30+edad, 200);
    float R =0;
    for (int i=0;i<500;i++) {
      int r1=3;
      int r2=10;
      int rango1=100;
      int rango2=140;
      float stepR=(r2-r1)/(1.0*(rango2-rango1)) ;   

      if (estado==1 && i<rango1) {
        current=color(0, 0);
      }
      else {
        current=color(30+edad, 30+edad, 200);
      }

      if (i<rango1) {
        R=r1;
      }
      else if (i>=rango1 && i<rango2) {
        R = R + stepR;
      }
      else {
        R=r2;
      }
      float y = loc.y + R*cos(i*f*2*PI/360);
      float z = loc.z + R*sin(i*f*2*PI/360);
      float x = loc.x + ((i*2*PI/360)*xx);
      strokeWeight(2);
      stroke(current);
      if (edad>200) {
        current=cend;
      }

      point(x, y, z);
    }
  }
}

