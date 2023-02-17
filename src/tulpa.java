// import drop.*;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.core.PConstants;


public class tulpa extends PApplet {

    // SDrop sdrop;

    public Library lib;
    public Visipalp vis;

    public KeyInput ki = new KeyInput(0, '0', 0, 0);
    public MouseInput mi = new MouseInput(0, 0, 0, 0, 0);

    int w, h;
    public boolean resized = false;

    public static tulpa SOLE = null;

    public void setup() {
        SOLE = this;

        surface.setSize(500, 500);
        surface.setResizable(true);

        // sdrop = new SDrop(this);

        lib = new Library();
        vis = new Visipalp(this, g, lib);

        registerMethod("keyEvent", this);
        registerMethod("mouseEvent", this);
    }

    public void draw() {
        checkResize();
        vis.showtime();
        ki.key = '\0';
        ki.kc = 0;
        mi.wheel = 0; // we have to reset mouse wheel input because of the way it works
        resized = false;
    }

    public void checkResize() {
        if (w != width || h != height) {
            w = width;
            h = height;
            resized = true;
        }
    }

    // INPUT -----------------------------------------------------------------

    public void keyEvent(KeyEvent e) {
        vis.receiveKeyInput(e);
    }


    public void mouseEvent(MouseEvent e) {

        vis.receiveMouseInput(e);
//        System.out.println("Action: " + e.getAction());
//        System.out.println("Button: " + e.getButton());
//        System.out.println("Mod: " + e.getModifiers());
//        System.out.println("Pos: " + e.getX() + ", " + e.getY());

        mi.x = e.getX();
        mi.y = e.getY();
        mi.mod = e.getModifiers();

        int action = e.getAction();

        if (action == MouseEvent.ENTER ||
                action == MouseEvent.EXIT) {
            return;
        }

        if (action == MouseEvent.PRESS) {
            mi.button = e.getButton();
        } else if (action == MouseEvent.RELEASE) {
            mi.button = 0;
        }

        if (action == MouseEvent.WHEEL) {
            mi.wheel = e.getCount();
        }
    }

    //public void dropEvent(DropEvent dropEvent){
    //    callosum.ear.dropEvent(dropEvent);
    //}
    //


    // -----------------------------------------------------------------

    // STARTING (YER) ENGINES
    public static void main(String av[]) {
        PApplet.main("tulpa");
    }
}
