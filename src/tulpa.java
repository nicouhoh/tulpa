import drop.*;

import processing.core.PApplet;
import processing.event.MouseEvent; // TODO eventually get rid of these when we figure out mouse wheel stuff for real.

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
  field = new Field(library);
  field.initializeField();
  input = new Input(library, field);
  registerMethod("keyEvent", input);
  registerMethod("mouseEvent", input);
//  registerMethod("mouseWheelEvent", input);
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


// INPUT-------------------------------------- TODO it's days are numbered, vs. Input.java

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
