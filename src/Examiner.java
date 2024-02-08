import processing.core.PApplet;
import processing.core.PVector;

public class Examiner extends Organelle implements Mousish {

    Picture picture;
    Skrivbord skrivbord;

    Clipping clipping;

    float margin = 50;
    float minSkrivAllowance = 100;

    public Examiner(){
        picture = new Picture();
        skrivbord = new Skrivbord();
        addChild(picture);
        addChild(skrivbord);
        addMousish(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        Cell exam = getBounds();
        exam.divideTop(margin);
        arrangeExaminer(exam);
    }

    private void arrangeExaminer(Cell exam) {
        if (picture.image != null){
            arrangeImageAndText(exam);
        }
        else {
            arrangeTextOnly(exam);
        }
    }

    private void arrangeImageAndText(Cell exam) {
        PVector size = picture.fitImage(exam.w - margin * 2, PApplet.min(tulpa.SOLE.height - margin * 2 - minSkrivAllowance, clipping.img.height));
        picture.setBounds(exam.divideTop(size.y).shrink((exam.w - size.x) / 2, 0));
        exam.divideTop(margin);
        skrivbord.setBounds(exam.fit(PApplet.constrain(picture.w, skrivbord.minW, skrivbord.maxW), exam.h));
    }

    private void arrangeTextOnly(Cell exam) {
        picture.setBounds(0, 0, 0, 0);
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

    public void setClipping(Clipping c){
        clipping = c;
        setUp();
    }

    public void setUp(){
        if (clipping.img != null) picture.setUp(clipping.img);
        else picture.noImage();
        skrivbord.setBuffer(clipping);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        controller.saveClippingText();
        controller.changeContext(new ContactSheetContext(controller));
    }

    @Override
    public void buttonPress(Controller controller, int mod) {}

}
