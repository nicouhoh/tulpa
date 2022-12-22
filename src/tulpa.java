import drop.*;
import processing.core.PApplet;


public class tulpa  extends PApplet  {
SDrop sdrop;

public Library lib;
public Visipalp vis;

int w, h;
public boolean resized = false;

public static tulpa SOLE = null;

public void setup(){
  SOLE = this;

  surface.setSize(500, 500);
  surface.setResizable(true);

  sdrop = new SDrop(this);

  lib = new Library();
  vis = new Visipalp(this, lib);

//  registerMethod("keyEvent", callosum.ear);
//  registerMethod("mouseEvent", callosum.ear);
}

public void draw(){
  checkResize();
  vis.showtime(this.g, mouseButton);
  resized = false;
}
  
public void checkResize(){
    if (w != width || h != height){
        w = width;
        h = height;
        resized = true;
    }
}

//public void dropEvent(DropEvent dropEvent){
//    callosum.ear.dropEvent(dropEvent);
//}

    // STARTING (YER) ENGINES
public static void main (String av[])
  { PApplet.main ("tulpa"); }
}
