import drop.*;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;


public class tulpa  extends PApplet  {

    SDrop sdrop;

    public Library lib;
    public Visipalp vis;

    public KeyInput ki;
    public MouseInput mi = new MouseInput(0, 0, 0, 0, 0);

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
    registerMethod("mouseEvent", this);
    }

    public void draw(){
      checkResize();
      vis.showtime(this.g, getMouseInput(), ki);
      ki = null;
      resized = false;
//      printMouseDebug();

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

    public void mouseEvent(MouseEvent e){

        mi.x = e.getX();
        mi.y = e.getY();
        mi.mod = e.getModifiers();

        int action = e.getAction();

        if (action == MouseEvent.ENTER ||
            action == MouseEvent.EXIT) {
            return;
        }

        if (action == MouseEvent.PRESS){
            mi.button = e.getButton();
        } else if (action == MouseEvent.RELEASE){
            mi.button = 0;
        }

        if (action == MouseEvent.WHEEL){
            mi.wheel = e.getCount();
        }

    }

//    public void printMouseDebug(){
//        g.textSize(72);
//        g.fill(255, 0, 255);
//        g.text("button :" + mi.button +
//                "\nx: " + mi.x +
//                "\ny: " + mi.y +
//                "\nwheel: " + mi.wheel +
//                "\nmod: " + mi.mod,
//                100, 100);
//    }

    public MouseInput getMouseInput(){
        MouseInput squeaks = mi;
        return squeaks;
    }


    //public void dropEvent(DropEvent dropEvent){
    //    callosum.ear.dropEvent(dropEvent);
    //}

        // STARTING (YER) ENGINES
    public static void main (String av[])
      { PApplet.main ("tulpa"); }
}
