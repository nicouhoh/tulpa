import processing.event.MouseEvent;

public class Squeak extends MouseEvent{

    // TODO I had some kind of idea about making an adjustedMouseEvent subclass for use with scrollers but I can't remember why.

    float latitude;
    boolean consumed = false;
    Mouse mouse;
    ClawMachine katla;

    public Squeak(Object nativeObject, long millis, int action, int modifiers, int x, int y, int button, int count, Mouse mouse) {
        super(nativeObject, millis, action, modifiers, x, y, button, count);
        this.mouse = mouse;
    }

    public Squeak(MouseEvent e, Mouse mouse){
        super(e.getNative(), e.getMillis(), e.getAction(), e.getModifiers(), e.getX(), e.getY(), e.getButton(), e.getCount());
        this.mouse = mouse;
    }

    public Squeak(MouseEvent e, Mouse mouse, ClawMachine k){
        super(e.getNative(), e.getMillis(), e.getAction(), e.getModifiers(), e.getX(), e.getY(), e.getButton(), e.getCount());
        this.mouse = mouse;
        this.katla = k;
    }

    public Squeak(Squeak s, float latitude){
        super(s.getNative(), s.getMillis(), s.getAction(), s.getModifiers(), s.getX(), s.getY() + (int)latitude, s. getButton(), s.getCount());
    }

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

    public void adjustForLatitude(){

    }


}
