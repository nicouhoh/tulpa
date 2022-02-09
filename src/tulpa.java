
import drop.*;
import test.*;

import processing.core.PApplet;
import processing.event.MouseEvent;

import drop.DropEvent;


public class tulpa  extends PApplet  {
SDrop sdrop;

public Library library;
public Field field;
public Scroller scroller;
public Input input;
int w, h;
public boolean resized = false;

public static tulpa SOLE = null;

public void setup(){
  SOLE = this;

  surface.setSize(500, 500);
  surface.setResizable(true);
  sdrop = new SDrop(this);
  library = new Library();
  registerMethod ("keyEvent", library);
  field = new Field(library);
  field.initializeField();
  registerMethod ("mouseEvent", library);
  input = new Input(library, field);
}

public void draw(){
  checkResize();
  field.updateField(this.g);
  resized = false;
}
  
public void checkResize(){
    if (w != width || h != height){
    w = width;
    h = height;
    resized = true;
  }
}


// INPUT--------------------------------------

public void keyPressed(){
  input.keyInput();
}

public void mousePressed(){
  input.mouseDown();
}

public void mouseReleased(){
  input.mouseUp();
}

public void mouseWheel(MouseEvent event){
  input.wheel(event);
}

public void dropEvent(DropEvent dropEvent){
  input.dropInput(dropEvent);
}

    // STARTING (YER) ENGINES
public static void main (String av[])
  { PApplet.main ("tulpa"); }
}
