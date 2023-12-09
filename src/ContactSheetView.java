public class ContactSheetView extends Organelle {

    Nothing nothing;
    ContactSheet contactSheet;
    Scroller scroller;

    public ContactSheetView(){
        nothing = new Nothing(49, 255);
        addChild(nothing);
        contactSheet = new ContactSheet();
        scroller = new Scroller(contactSheet);
        nothing.addChild(scroller);
    }
}