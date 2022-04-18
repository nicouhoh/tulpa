import drop.*;

import processing.core.PApplet;
import processing.event.MouseEvent;

import drop.DropEvent;


public class tulpa  extends PApplet  {
SDrop sdrop;

public Library library;
public Cockpit cockpit;
public Callosum callosum;
public Scroller scroller;
public Input input;
int w, h;
public boolean resized = false;

public static tulpa SOLE = null;

public EventEar ear;

public void setup(){
  SOLE = this;

  surface.setSize(500, 500);
  surface.setResizable(true);

  sdrop = new SDrop(this);
  library = new Library();
  cockpit = new Cockpit();
  callosum = new Callosum(library, cockpit);
  input = new Input(library, cockpit);
  ear = new EventEar();

//  registerMethod("keyEvent", input);
//  registerMethod("mouseEvent", input);
  registerMethod("keyEvent", ear);
  registerMethod("mouseEvent", ear);
//  registerMethod("mouseWheelEvent", input);

    callosum.debugInit();
}

public void draw(){
  checkResize();
  callosum.showtime(this.g);
  resized = false;
}
  
public void checkResize(){
    if (w != width || h != height){
    w = width;
    h = height;
    resized = true;
  }
}


// INPUT

//public void mouseWheel(MouseEvent event){
//  input.wheel(event);
//}

public void dropEvent(DropEvent dropEvent){
  input.dropInput(dropEvent);
}

    // STARTING (YER) ENGINES
public static void main (String av[])
  { PApplet.main ("tulpa"); }
}
