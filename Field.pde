class Field{

  int size;
  Scroller scroller;
  
  Field(){
    scroller = new Scroller();
  }
  
  void initializeField(){
    size = width - scroller.scrollWidth;
    wranglist = new Wranglist();
  }
  
  void updateField(){
    size = width - scroller.scrollWidth;
    if (resized){
      wranglist.FussMenagerie();
    }
    wranglist.Showtime();
    scroller.drawScroller();
  }
}
