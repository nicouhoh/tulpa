import processing.event.MouseEvent;

public class Squeak extends MouseEvent{

    float latitude;
    boolean consumed = false;
    ClawMachine katla;

    public Squeak(Object nativeObject, long millis, int action, int modifiers, int x, int y, int button, int count) {
        super(nativeObject, millis, action, modifiers, x, y, button, count);
    }

    public Squeak(MouseEvent e){
        super(e.getNative(), e.getMillis(), e.getAction(), e.getModifiers(), e.getX(), e.getY(), e.getButton(), e.getCount());
    }

    public Squeak(MouseEvent e, ClawMachine k){
        super(e.getNative(), e.getMillis(), e.getAction(), e.getModifiers(), e.getX(), e.getY(), e.getButton(), e.getCount());
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

    // consumes only if the organelle has a Palpable for this type of Squeak
    // TODO I think there's a more elegant way, and if so I would be happy
    public void consume(Organelle organelle){
        switch (getAction()){
            case Squeak.MOVE -> {
                consume();
            }
            case Squeak.KEY -> {
                if (organelle.mousishes.isEmpty()) return;
                consume();
            }
            case Squeak.WHEEL -> {
                if (organelle.wheelish == null) return;
                consume();
            }
            case Squeak.DRAG -> {
                if (organelle.draggish == null) return;
                consume();
            }
        }
    }


}
