import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.nio.FloatBuffer; 
import KinectPV2.*; 
import javax.media.opengl.GL2; 
import peasy.*; 
import nervoussystem.obj.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class kinect2_20141008_01 extends PApplet {

/*
Copyright (C) 2014  Thomas Sanchez Lengeling.
 KinectPV2, Kinect for Windows v2 library for processing
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */








PeasyCam cam;

private KinectPV2 kinect;

float a = 0;
int zval = 50;
float scaleVal = 260;
float depthVal = 0;
boolean capture = false;
int len;

public void setup() {
  size(1280, 720, P3D);

  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(50);
  cam.setMaximumDistance(500);

  kinect = new KinectPV2(this);
  kinect.enableDepthImg(true);
  kinect.enablePointCloud(true);
  depthVal = kinect.getThresholdDepthPointCloud();
  kinect.activateRawDepth(true);

  kinect.init();
}

public void draw() {
  background(0);

  //image(kinect.getDepthImage(), 0, 0, 320, 240);

  kinect.setThresholdPointCloud(depthVal);
  FloatBuffer pointCloudBuffer = kinect.getPointCloudPosFloatBuffer();

  PJOGL pgl = (PJOGL)beginPGL();
  GL2 gl2 = pgl.gl.getGL2();

  gl2.glEnable( GL2.GL_BLEND );
  gl2.glEnable(GL2.GL_POINT_SMOOTH);      

  gl2.glEnableClientState(GL2.GL_VERTEX_ARRAY);
  gl2.glVertexPointer(3, GL2.GL_FLOAT, 0, pointCloudBuffer);

  gl2.glTranslatef(width/2, height/2, zval);
  gl2.glScalef(scaleVal, -1*scaleVal, -1*scaleVal);
  gl2.glRotatef(a, 0.0f, 0.0f, 0.0f);

  gl2.glDrawArrays(GL2.GL_POINTS, 0, kinect.WIDTHDepth * kinect.HEIGHTDepth);

  gl2.glDisable(GL2.GL_BLEND);
  //print (pointCloudBuffer.capacity());
  endPGL();

  if (capture == true) {

    int j;
    FloatBuffer copyCloudBuffer = pointCloudBuffer.duplicate();
    float[] ptArray = new float[copyCloudBuffer.capacity()];
    //PVector[] ptExport;

    copyCloudBuffer.get(ptArray);
    len = ptArray.length/3;
    //print (len);
    PVector[] ptExport = new PVector[len];

    beginRecord("nervoussystem.obj.OBJExport", "lucia_004.obj");

    //rotateY(radians(45.0));

    beginShape(POINTS);
    for (int i = 0; i < len; ++i) 
    {   
      j = i*3; 
      ptExport[i]= new PVector (ptArray[j],ptArray[j+1],ptArray[j+2]);
      vertex(ptArray[j],ptArray[j+1],ptArray[j+2]);
    }
    endShape();
    endRecord();
    //writeOBJ(ptExport);
    capture = false;
  }

  stroke(255, 0, 0);
  text(frameRate, 50, height- 50);
  stroke(255,0,0);
  sphere(5);
}

public void mousePressed() {

  println(frameRate);
  //saveFrame();
}

public void keyPressed() {

  if (key == 'm') {
    capture = true;
  }

  if (key == 'a') {
    zval +=1;
    println(zval);
  }
  if (key == 's') {
    zval -= 1;
    println(zval);
  }

  if (key == 'z') {
    scaleVal += 0.1f;
    println(scaleVal);
  }
  if (key == 'x') {
    scaleVal -= 0.1f;
    println(scaleVal);
  }

  if (key == 'q') {
    a += 1;
    println(a);
  }
  if (key == 'w') {
    a -= 1;
    println(a);
  }

  if (key == 'c') {
    depthVal -= 0.01f;
    println(depthVal);
  }

  if (key == 'v') {
    depthVal += 0.01f;
    println(depthVal);
  }
}

// public void CapturePoints()
// {
//   int j;
//   float[] ptArray;
//   //PVector[] ptExport;
//   ptArray = pointCloudBuffer.array();
//   len = ptArray.length/3;
//   PVector[] ptExport = new PVector[len];

//   beginRecord("nervoussystem.obj.OBJExport", "filename.obj");
//   beginShape(POINTS);
//   for (int i = 0; i < len; ++i) 
//  {   
//    j = i*3; 
//    ptExport[i]= new Pvector (ptArray[j],ptArray[j+1],ptArray[j+2]);
//    vertex(ptArray[j],ptArray[j+1],ptArray[j+2]);
//  }
//  endShape();
//  endRecord();
// }

public void writeOBJ(PVector[] points){
  try{
    PrintWriter writer = new PrintWriter(sketchPath("points.obj"), "UTF-8");
    for(PVector v:points){
      writer.printf("v %f %f %f\n",v.x,v.y,v.z);
    }
    writer.close();
    println("saved");
  }catch(Exception e){
    println(e);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "kinect2_20141008_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
