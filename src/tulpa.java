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
  callosum = new Callosum(); // callosum is where we truly kick everything off. check constructor and bigBang();

  registerMethod("keyEvent", callosum.ear);
  registerMethod("mouseEvent", callosum.ear);
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
