// import drop.*;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
//import processing.core.PConstants;


public class tulpa extends PApplet {

    // SDrop sdrop;

    private Conductor conductor;
    private Library library;
    private Visipalp visipalp;

//    public KeyInput ki = new KeyInput(0, '0', 0, 0);
//    public MouseInput mi = new MouseInput(0, 0, 0, 0, 0);

    int w = 1000, h = 1000;
    public boolean resized = false;

    public static tulpa SOLE = null;

    public void setup() {
        SOLE = this;

        surface.setResizable(true);
        surface.setSize(w, h);

        // sdrop = new SDrop(this);

        conductor = new Conductor(this, g);

        conductor.setup();

//        registerMethod("keyEvent", this);
//        registerMethod("mouseEvent", this);
    }

    public void draw() {
        conductor.update();
//        checkResize();
//        vis.showtime(resized);
//        ki.key = '\0';
//        ki.kc = 0;
//        mi.wheel = 0; // we have to reset mouse wheel input because of the way it works
//        resized = false;
    }

//    public void checkResize() {
//        if (w != width || h != height) {
//            w = width;
//            h = height;
//            resized = true;
//        }
//    }

    // INPUT -----------------------------------------------------------------

//    public void keyEvent(KeyEvent e) {
//        visipalp.receiveKeyInput(e);
//    }
//
//    public void mouseEvent(MouseEvent e) {
//        visipalp.receiveMouseInput(e);
//    }

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
