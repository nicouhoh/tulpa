import processing.event.MouseEvent;

public class Squeak {

    MouseEvent e;
    float latitude;
    boolean consumed = false;
    ClawMachine claw;

    MouseStatus status;

    public Squeak(MouseEvent e){
        this.e = e;
    }

    public void squeak(Controller controller){}

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float lat){
        latitude = lat;
    }

    public void addLatitude(float lat) { latitude += lat; }

    public void consume(){
        consumed = true;
    }

    // consumes only if the organelle has a Palpable for this type of Squeak
    // TODO I think there's a more elegant way, and if so I would be happy
    public void consume(Organelle organelle){
        switch (e.getAction()){
            case MouseEvent.MOVE -> {
                consume();
            }
            case MouseEvent.KEY -> {
                if (organelle.mousishes.isEmpty()) return;
                consume();
            }
            case MouseEvent.WHEEL -> {
                if (organelle.wheelish == null) return;
                consume();
            }
            case MouseEvent.DRAG -> {
                if (organelle.draggish == null) return;
                consume();
            }
        }
    }

    public boolean mouseOver(Organelle o){
        return mouseOver(o.x, o.y, o.w, o.h, getX(), getY() + getLatitude());
    }

    public boolean mouseOver(float boxX, float boxY, float boxW, float boxH, float mouseX, float mouseY){
        return (mouseX > boxX) && (mouseX < boxX + boxW) && (mouseY > boxY) && (mouseY < boxY + boxH);
    }

    public boolean mouseOver(Dropzone z, float mouseX, float mouseY){
        return mouseOver(z.x, z.y, z.w, z.h, mouseX, mouseY);
    }

    public boolean mouseOver(Dropzone z, Squeak squeak){
        return mouseOver(z, squeak.getX(), squeak.getY() + squeak.getLatitude());
    }

    public int getX(){
        return e.getX();
    }

    public int getY(){
        return e.getY();
    }

    public int getModifiers(){
        return e.getModifiers();
    }

    public int getCount(){
        return e.getCount();
    }

    public int getAction(){
        return e.getAction();
    }

}
