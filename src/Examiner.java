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
        if (clipping == null) return;
        if (clipping.img == null){
            picture.performUpdate(0, 0, 0, 0);
            skrivbord.performUpdate(x, y + 50, w, h);
        }
        else {
            picture.performUpdate(x, y, w, h);
            skrivbord.performUpdate(picture.x, picture.y + picture.h + 50, picture.w, h);
        }
    }

    public void setClipping(Clipping c){
        clipping = c;
        setUp();
    }

     public void setUp(){
        picture.setImage(clipping.img);
        if (clipping.passage == null) skrivbord.setBuffer(new Passage("Test String"));
        else skrivbord.setBuffer(clipping.passage);
     }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        System.out.println(clipping.imgPath);
        controller.changeMode(new ContactSheetMode(controller));
    }

    @Override
    public void buttonPress(Controller controller, int mod) {

    }
}
