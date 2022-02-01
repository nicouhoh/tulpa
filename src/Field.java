
import processing.core.PApplet;
import processing.core.PGraphics;


public class Field{

  Library library;
    
  float w;
  int columns;
  Scroller scroller;
  float latitude;
  float clipSize;
  float pillow;
  float foot;
  float scrollDist;
  
  
  public Field(Library libraryIn){
    library = libraryIn; //<>//
    scroller = new Scroller();
    latitude = 0;
    columns = 5;
    pillow = 10;
    clipSize = 200;
    fussMenagerie();
  }
  
  public void initializeField(){
    w = tulpa.SOLE.width - scroller.scrollW;
  }
  
  public void updateField(PGraphics g){
    w = tulpa.SOLE.width - scroller.scrollW;
    if (tulpa.SOLE.resized){
      fussMenagerie();
    }
    scroller.updateScroller(foot);
    followScroller();
    Showtime(g);
  }
  
  public void fussMenagerie(){
    clipSize = PApplet.constrain((w - (pillow * (columns + 1))) / columns, 10, 9999999);
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

  public void Showtime(PGraphics g){
    g.push();
    g.background(50);
    g.translate(0, latitude);
    for (int i = 0; i < library.clippings.length; i++){
      library.clippings[i].update(g, latitude);
    }
    g.pop();
    
    scroller.drawScroller(g);
  }
  
  public void followScroller(){
     latitude = -(scroller.gripY / tulpa.SOLE.height) * foot;
  }
}
