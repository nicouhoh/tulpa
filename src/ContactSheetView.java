public class ContactSheetView extends Organelle {

    Nothing nothing;
    ContactSheet contactSheet;
    Scroller scroller;

    SearchPanel searchPanel;

    public ContactSheetView(){

        nothing = new Nothing(49, 255);
        addChild(nothing);
        contactSheet = new ContactSheet();
        scroller = new Scroller(contactSheet);
        nothing.addChild(scroller);

        searchPanel = new SearchPanel();
        addChild(searchPanel);
    }

    public void resize(float windowX, float windowY, float windowW, float windowH){
        setBounds(windowX, windowY, windowW, windowH);

        Cell main = new Cell(x, y, w, h);
        Cell sidePanel = main.divideLeft(searchPanel.openWidth);
        nothing.setBounds(main);
        scroller.setBounds(main);
        searchPanel.setBounds(sidePanel);
    }
}