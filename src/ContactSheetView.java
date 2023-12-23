import java.util.ArrayList;

public class ContactSheetView extends Organelle {

    Nothing nothing;
    ContactSheet contactSheet;
    Scroller scroller;

    SearchPanel searchPanel;
    SearchHeader searchHeader;

    public ContactSheetView(){

        nothing = new Nothing(49, 255);
        addChild(nothing);
        contactSheet = new ContactSheet();
        scroller = new Scroller(contactSheet);
        nothing.addChild(scroller);

        searchPanel = new SearchPanel();
        addChild(searchPanel);
    }

    public void setup(ArrayList<Organelle> thumbs){
        contactSheet.addChildren(thumbs);
        scroller.resetScroll();
    }

    public void resize(float windowX, float windowY, float windowW, float windowH){
        setBounds(windowX, windowY, windowW, windowH);

        Cell main = getBounds();
        Cell sidePanel = main.divideLeft(searchPanel.openWidth);
        if (searchHeader != null) searchHeader.setBounds(main.divideTop(40));
        nothing.setBounds(main);
        scroller.setBounds(main);
        searchPanel.setBounds(sidePanel);
    }

    public void clearContactSheet(){
        contactSheet.children.clear();
    }

    public void setupSearchHeader(String query){
        searchHeader = new SearchHeader(query);
        addChild(searchHeader);
    }

    public void clearSearchHeader(){
        removeChild(searchHeader);
        searchHeader = null;
    }
}