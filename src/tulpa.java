import drop.*;
import processing.core.PApplet;
import processing.event.KeyEvent;


public class tulpa  extends PApplet  {
SDrop sdrop;

public Library lib;
public Visipalp vis;

public KeyInput ki;

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

registerMethod("keyEvent", this);
//  registerMethod("mouseEvent", callosum.ear);
}

public void draw(){
  checkResize();
  vis.showtime(this.g, mousePressed? mouseButton : 0, ki);
  resized = false;

  ki = null;
}

public void checkResize(){
    if (w != width || h != height){
        w = width;
        h = height;
        resized = true;
    }
}

public void keyEvent(KeyEvent e){
  ki = new KeyInput(e.getAction(), e.getKey(), e.getKeyCode(), e.getModifiers());
    if (ki.action == 1) {
        System.out.println("KEY EVENT");
        System.out.println("action: " + ki.action);
        System.out.println("key: " + ki.key);
        System.out.println("keycode: " + ki.kc);
        System.out.println("modifiers: " + ki.mod);
        System.out.println("");
    }
}


//public void dropEvent(DropEvent dropEvent){
//    callosum.ear.dropEvent(dropEvent);
//}

    // STARTING (YER) ENGINES
public static void main (String av[])
  { PApplet.main ("tulpa"); }
}
