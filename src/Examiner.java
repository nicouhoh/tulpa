public class Examiner extends Organelle implements Mousish {

    Picture picture;
    Skrivbord skrivbord;

    Clipping clipping;

    public Examiner(){
        picture = new Picture();
        skrivbord = new Skrivbord();
        addChild(picture);
        addChild(skrivbord);
        addMousish(this);
    }

    public void updateChildren(){
        picture.performUpdate(x, y, w, h);
        skrivbord.performUpdate(picture.x, picture.y + picture.h + 50, picture.w, h);
    }

    public void setClipping(Clipping c){
        clipping = c;
        setUp();
    }

     public void setUp(){
        picture.setImage(clipping.img);
        skrivbord.setPassage(new Passage("Test String"));
//        skrivbord.setPassage(clipping.passage);
     }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        controller.changeMode(new ContactSheetMode(controller));
    }

    @Override
    public void buttonPress(Controller controller, int mod) {

    }
}
