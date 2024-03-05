import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

public class Examiner extends Organelle implements Mousish {

    Porthole porthole;
    Skrivbord skrivbord;

    Clipping clipping;

    float margin = 50;
    float minSkrivAllowance = 100;

    public Examiner(){
        porthole = new Porthole();
        skrivbord = new Skrivbord();
        Virgo stack = new Vertigo(getBounds(), porthole, skrivbord);
        addChild(stack);
        addMousish(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        setBounds(new Cell(parentX, parentY, parentW, parentH).shrink(margin));
//        if (clipping == null) return;
//        porthole.setSize(fitImageToExaminer(porthole.image, getBounds().w));
//        if (clipping != null) arrangeExaminer(getBounds());
    }

//    private void arrangeExaminer(Cell exam) {
//        if (clipping.hasImage()){
////            arrangeImageAndText(exam);
//        }
//        else {
//            arrangeTextOnly(exam);
//        }
//    }

//     TODO we gotta refactor to get this sorted
//    private void arrangeImageAndText(Cell exam) {
//        porthole.setBounds(exam.divideTop(size.y).shrink((exam.w - size.x) / 2, 0));
//        exam.divideTop(margin);
//        skrivbord.setBounds(exam.fit(PApplet.constrain(porthole.w, skrivbord.minW, skrivbord.maxW), exam.h));
//    }

    private void arrangeTextOnly(Cell exam) {
        porthole.setBounds(0, 0, 0, 0);
        skrivbord.setBounds(
                exam.fit(
                        PApplet.constrain(
                                skrivbord.maxW,
                                skrivbord.minW,
                                exam.w - margin * 2),
                        exam.h
                )
        );
    }

    public void setUp(Clipping c){
        clipping = c;
        if (clipping.hasImage()){
            porthole.setImage(clipping.img);
        }
        else porthole.noImage();
        skrivbord.setBuffer(clipping);
    }

    @Override
    public void mouseDown(Controller controller, Squeak squeak) {
        controller.saveClippingText();
        controller.changeContext(new ContactSheetContext(controller));
    }

    @Override
    public void buttonPress(Controller controller, int mod) {}

}
