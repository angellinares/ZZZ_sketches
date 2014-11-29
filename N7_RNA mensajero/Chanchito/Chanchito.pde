import peasy.*;

PeasyCam cam;



void setup() {

  size (800, 800, P3D);
  //line (0,0,30,150);
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(50);
  cam.setMaximumDistance(500);
}

void draw() {

  background(0);
  int estado = 0;
  
  if(frameCount>1200){
    estado=1;
  }
  render(new PVector(10, 10, 0), estado, frameCount/(10.0));
}

void render(PVector loc, int estado, float edad) {
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
    
    if(estado==1 && i<rango1){
      current=color(0,0);
    }else{
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

