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
}
