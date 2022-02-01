class Field{

  Library library;
    
  float w;
  int columns;
  Scroller scroller;
  float latitude;
  float clipSize;
  float pillow;
  float foot;
  float scrollDist;
  
  
  Field(Library libraryIn){
    library = libraryIn; //<>//
    scroller = new Scroller();
    latitude = 0;
    columns = 5;
    pillow = 10;
    clipSize = 200;
    fussMenagerie();
  }
  
  void initializeField(){
    w = width - scroller.scrollW;
  }
  
  void updateField(){
    w = width - scroller.scrollW;
    if (resized){
      fussMenagerie();
    }
    scroller.updateScroller(foot);
    followScroller();
    Showtime();
  }
  
  void fussMenagerie(){
    clipSize = constrain((w - (pillow * (columns + 1))) / columns, 10, 9999999);
    float x = pillow;
    float y = pillow;
    for (int i = 0; i < library.clippings.length; i++){
      x = pillow + (i % columns) * (pillow + clipSize);
      y = pillow + (i / columns) * (pillow + clipSize);
      library.clippings[i].setSize(clipSize, clipSize);
      library.clippings[i].setPos(x, y);
    }
    foot = y + clipSize + pillow;
  }

  void Showtime(){
    push();
    background(50);
    translate(0, latitude);
    for (int i = 0; i < library.clippings.length; i++){
      library.clippings[i].update(latitude);
    }
    pop();
    
    scroller.drawScroller();
  }
  
  void followScroller(){
     latitude = -(scroller.gripY / height) * foot;
  }
}
