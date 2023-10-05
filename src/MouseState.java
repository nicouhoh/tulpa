import processing.event.MouseEvent;
import processing.core.PVector;

public class MouseState extends MouseEvent{

    float latitude;

    public MouseState(Object nativeObject, long millis, int action, int modifiers, int x, int y, int button, int count) {
        super(nativeObject, millis, action, modifiers, x, y, button, count);
    }

    public MouseState(MouseEvent e){
        super(e.getNative(), e.getMillis(), e.getAction(), e.getModifiers(), e.getX(), e.getY(), e.getButton(), e.getCount());
    }

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float lat){
        latitude = lat;
    }

    public void addLatitude(float lat) { latitude += lat; }


}
