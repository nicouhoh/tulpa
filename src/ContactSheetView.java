import java.util.ArrayList;

public class ContactSheetView extends Organelle {

    Atmosphere atmosphere;
    ContactSheet contactSheet;
    Scroller scroller;

    SidePanel sidePanel;
    SearchHeader searchHeader;

    boolean filtered = false;

    public ContactSheetView(TulpaHeart heart){

        atmosphere = new Atmosphere(49, 255);
        addChild(atmosphere);
        contactSheet = new ContactSheet();
        scroller = new Scroller(contactSheet);
        atmosphere.addChild(scroller);

        sidePanel = new SidePanel(heart);
        addChild(sidePanel);
    }

    public void setup(ArrayList<Organelle> thumbs){
        contactSheet.addChildren(thumbs);
        scroller.resetScroll();
    }

    public void resize(float windowX, float windowY, float windowW, float windowH){
        setBounds(windowX, windowY, windowW, windowH);

        Cell main = getBounds();
        Cell side = main.divideLeft(this.sidePanel.openWidth);
        if (searchHeader != null) searchHeader.setBounds(main.divideTop(40));
        atmosphere.setBounds(main);
        scroller.setBounds(main);
        sidePanel.setBounds(side);
        main.divideRight(scroller.scrollW);
        contactSheet.setBounds(main);
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

    public void displayThumbnails(ArrayList<Organelle> thumbs, String query){
        clearContactSheet();
        setupSearchHeader(query);
        setup(thumbs);
        filtered = true;
    }

    public void displayAllThumbnails(ArrayList<Organelle> thumbs, float latitude){
        clearContactSheet();
        clearSearchHeader();
        setup(thumbs);
        filtered = false;
        scroller.setHostLatitude(latitude);
    }
}