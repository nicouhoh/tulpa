import processing.core.PGraphics;
import processing.event.MouseEvent;
public class Controller {

    TulpaHeartInterface heart;
    Visipalp visipalp;

    public Controller(TulpaHeartInterface heart, PGraphics g){
        this.heart = heart;
        visipalp = new Visipalp(g, this, heart);
    }

    public void draw(){
        visipalp.draw();
    }

    public void resizeWindow(){
        visipalp.sycamore.update(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    public void passMouseInput(MouseEvent e){
        visipalp.receiveMouseInput(e);
    }
}
