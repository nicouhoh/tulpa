import processing.core.PGraphics;

public class Skrivbord extends Skrivsak {

    int margin = 50;
    int minW = 400;
    int maxW = 800;
    int minH =  50;

    public Skrivbord(){
        super();
        setFont(tulpa.SOLE.getSkrivBordFont());
        setMargin(50);
        this.palimpsest = "Click here to type";
        buffer = new GapBuffer(50);
        addMousish(this);
    }

    @Override
    public void esc(Controller controller){
        controller.saveClippingText();
        controller.changeContext(new ContactSheetContext(controller));
    }

    @Override
    public void drawText(PGraphics g, String string, int cursorPos, float textX, float textY, float textW, float textH){
        scribe.text(g, string, cursorPos, textX, textY, textW, textH);
    }
}