import processing.core.PGraphics;

public class Visipalp extends Organelle implements Shape{

    PGraphics g;

    int bgColor = 49;

    public Visipalp(PGraphics g){
        this.g = g;
        updater = new OrganelleUpdater();
        drawer = new BackgroundDrawer();
    }

    @Override
    public void shift(){
        x = 0;
        y = 0;
        w = tulpa.SOLE.width;
        h = tulpa.SOLE.height;
    }
}