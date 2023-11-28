import processing.core.PApplet;

import javax.swing.*;

public class Scroller extends Organelle implements Mousish, Wheelish {

    Organelle host;
    ScrollerRail rail;
    ScrollerGrip grip;

    float scrollW = 10;
    float scrollSpeed = 50;


    // TODO keep the same width visually but make the scroll rail and grip wider for mouse collision purposes! easier to grab

    public Scroller(Organelle organelle) {
        host = organelle;
        rail = new ScrollerRail(this);
        grip = new ScrollerGrip(this);
        addChild(rail);
        addChild(host);
        rail.addChild(grip);
        addMousish(this);
        addWheelish(this);
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        updateChildren();
    }

    @Override
    public void updateChildren(){
        host.update(x, y, w - scrollW, h);
        rail.update(x, y, w, h);
        grip.setGrip(h, host.h, host.latitude);
    }

    public void nudgeHostLatitude(float amount){
        jumpToLatitude(host.latitude + amount);
    }

    public void jumpToLatitude(float y){
        host.latitude = PApplet.constrain(y, 0, host.h - h);
        grip.setGrip(rail.h, host.h, host.latitude);
    }

    public void setHostLatitudeToGrip(){
        host.latitude = (grip.y * host.h) / h;
    }

    public void moveGrip(float y){
        grip.y = PApplet.constrain(y, this.y, this.h - grip.h);
        setHostLatitudeToGrip();
    }

    public int isOffscreen(Organelle organelle){
        // returns 0 if the organelle is onscreen, 1 if it's off the bottom of the screen, and -1 if it's off the top.
        // for now it counts as "offscreen" if more than half of the organelle isn't visible
        if (organelle.y + organelle.h/2 < host.getLatitude()) return -1;
        else if (organelle.y + organelle.h/2 - host.getLatitude() > h) return 1;
        else return 0;
    }

    public void jumpToOrganelle(Organelle organelle, float padding){
        switch (isOffscreen(organelle)){
            case 0 -> {return;}
            case -1 -> jumpToLatitude(organelle.y - padding);
            case 1 -> jumpToLatitude(organelle.y + organelle.h - h + padding);
        }
    }

    public void wheel(Controller controller, int count){
        nudgeHostLatitude(count * scrollSpeed);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        System.out.println("clicked " + this);
    }

    @Override
    public void buttonPress(Controller controller, int mod){}

}
