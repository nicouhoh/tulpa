// import drop.*;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
//import processing.core.PConstants;


public class tulpa extends PApplet {


    private TulpaHeart heart;
    private Controller controller;

    int w, h;

    public static tulpa SOLE = null;
    // SDrop sdrop;

    public void setup() {
        w = width;
        h = height;
        surface.setResizable(true);
        surface.setSize(1000, 1000);
        registerMethod("pre", this); // set up for detecting window resize

        SOLE = this;
        heart = new TulpaHeart();
        controller = new Controller(heart, g);



        // sdrop = new SDrop(this);

        registerMethod("keyEvent", this);
        registerMethod("mouseEvent", this);
    }

    public void pre(){
        if (w != width || h != height){
            //Sketch window has resized
            w = width;
            h = height;
            controller.resizeWindow();
        }
    }

    public void draw() {
        controller.draw();
    }

    // INPUT -----------------------------------------------------------------

    public void keyEvent(KeyEvent e) {
        if (key == ESC){
            key = '\uDD1E';
        }
        controller.receiveKeyEvent(e);
    }

    public void mouseEvent(MouseEvent e) {
//        if (e.getAction() == MouseEvent.MOVE) return;
        controller.receiveMouseEvent(e);
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
