import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

public class Visipalp {

    PGraphics g;
    TulpaHeart heart;
    Controller instrument;

    ContactSheetView contactSheetView;

    ExaminerView examinerView;

    public Visipalp(PGraphics g, Controller instrument, TulpaHeart heart){
        this.g = g;
        this.heart = heart;
        this.instrument = instrument;

        materialize();
    }

    public void materialize(){
        contactSheetView = new ContactSheetView();
        contactSheetView.contactSheet.addChildren(manifestClippings(heart.getLibrary().clippings));

        examinerView = new ExaminerView();

        update();
    }

    public void draw(){
        contactSheetView.performDraw(g, 0, tulpa.SOLE.h);
    }

    public void drawClippingView(){
        if (heart.selectedClippings.size() == 1) {
            examinerView.performDraw(g, 0, tulpa.SOLE.height);
        }
    }

    public void update() {
        contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
        examinerView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    public ArrayList<Organelle> manifestClippings(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbs = new ArrayList<Organelle>();
        for (Clipping clip : clippings){
            thumbs.add(new Thumbnail(g, clip));
        }
        return thumbs;
    }

    public void refreshContactSheet(){
        contactSheetView.contactSheet.children = manifestClippings(heart.getLibrary().clippings);
        update();
    }

    public Thumbnail verticalStep(Thumbnail thumbnail, int direction){
        int thumbIndex = contactSheetView.contactSheet.getThumbnails().indexOf(thumbnail);
        while (thumbIndex + direction >= 0 && thumbIndex + direction < contactSheetView.contactSheet.getThumbnails().size()) {
            thumbIndex = PApplet.constrain(thumbIndex + direction, 0, contactSheetView.contactSheet.getThumbnails().size());
            Thumbnail t2 = contactSheetView.contactSheet.getThumbnails().get(thumbIndex);
            float centerX = thumbnail.x + thumbnail.w / 2;
            if (centerX > t2.x && centerX < t2.x + t2.w) return t2;
        }
        return thumbnail;
    }
}