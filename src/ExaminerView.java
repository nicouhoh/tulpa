import processing.core.PGraphics;
import processing.core.PApplet;

public class ExaminerView extends Organelle {

    Scrim scrim;
    Examiner examiner;
    Scroller scroller;

    public ExaminerView(){
        scrim = new Scrim(0, 239);
        addChild(scrim);
        examiner = new Examiner();
        scroller = new Scroller(examiner);
        scrim.addChild(scroller);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        setBounds(parentX, parentY, parentW, parentH);

        Cell view = new Cell(x, y, w, h);
        scrim.setBounds(view);
        scroller.setBounds(view);
        examiner.setBounds(view);
    }
}
