/*
2014 CC. Ángel Linares García.

Modified version of Thomas Sanchez Lengeling 
PointCloudOGL.pde example contained in KinectPV2 library.

Just press "m" to capture the pointcloud displayed into *.obj file.
Modify "filename" variable to change where the pointcloud is ging to be saved.
PeasyCam library integrated for navigation porpuses.
 */

import java.nio.FloatBuffer;

import KinectPV2.*;
import javax.media.opengl.GL2;
import peasy.*;
import nervoussystem.obj.*;

PeasyCam cam;

private KinectPV2 kinect;

float a = 0;
int zval = 50;
float scaleVal = 260;
float depthVal = 0;
boolean capture = false;
int len;

str filename = "test.obj"

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

  endPGL();


  /*
  Where the capture is done.
  A copy of the OGL buffer is created and passed to a float array. 
  The array is "parsed" into a PVector array that is written into a OBJ file
  using nervous system "nervoussystem.obj.*" library. 
  */
  if (capture == true) {

    int j;
    FloatBuffer copyCloudBuffer = pointCloudBuffer.duplicate();
    float[] ptArray = new float[copyCloudBuffer.capacity()];

    copyCloudBuffer.get(ptArray);
    len = ptArray.length/3;
    PVector[] ptExport = new PVector[len];

    beginRecord("nervoussystem.obj.OBJExport", filename);

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
    capture = false;
  }

  stroke(255, 0, 0);
  //text(frameRate, 50, height- 50);
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
    scaleVal += 0.1;
    println(scaleVal);
  }
  if (key == 'x') {
    scaleVal -= 0.1;
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
    depthVal -= 0.01;
    println(depthVal);
  }

  if (key == 'v') {
    depthVal += 0.01;
    println(depthVal);
  }
}