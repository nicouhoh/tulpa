import processing.event.MouseEvent;

public class Squeak {

    MouseEvent e;
    private float latitude;
    boolean consumed = false;
    ClawMachine claw;
    Organelle root;
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

    public void addLatitude(float lat) {
        latitude += lat;
    }

    public void consume(){
        consumed = true;
    }

    // consumes only if the organelle has a Palpable for this type of Squeak
    // TODO I think there's a more elegant way, and if so I would be happy
    public void consume(Organelle organelle){
        // overridden in subclasses of Squeak that need particular attention (a lot of them!)
        consume();
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

    // Returns the deepest organelle under the mouse that accepts this kind of squeak
    public Organelle captureAndBubble(Organelle root){
        if (consumed) return null;
        if (!mouseOver(root)){
            return null;
        }
        addLatitude(root.getLatitude());

        for (Organelle child : root.getChildren()){
            Organelle result = captureAndBubble(child);
            if (consumed) return result;
        }
        this.consume(root); // in here is where we check whether the organelle accepts this kind of squeak and make sure if it does we don't trigger additional squeaks up the chain.
        System.out.println(root);
        return root;
    }

    public Organelle findHotItem(Organelle root){
        Organelle result = captureAndBubble(root);
        if (result != status.getHotItem()) status.setHotItem(result);
        return result;
    }

}
