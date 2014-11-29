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

public class build_test_000 extends PApplet {

public void setup() {
	size(512, 512);
}

public void draw() {
	rect(20, 20, 5, 5);
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build_test_000" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
