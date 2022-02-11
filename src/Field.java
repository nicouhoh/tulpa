import processing.core.PApplet;
import processing.core.PGraphics;

import processing.event.KeyEvent;

import java.util.ArrayList;


public class Field{

  Library library;
    
  float w;
  int columns;
  Scroller scroller;
  float latitude;
  float clipSize;
  float pillow;
  float sPillow;
  float foot;

  boolean sardine;
  
  
  public Field(Library libraryIn){
    library = libraryIn;
    scroller = new Scroller();
    latitude = 0;
    columns = 5;
    pillow = 10;
    sPillow = 2;
    clipSize = 200;
    sardine = false;
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
    showtime(g);
  }
  
  public void fussMenagerie(){
    if(sardine){
      packSardines();
      return;
    }
    clipSize = PApplet.constrain((w - (pillow * (columns + 1))) / columns, 10, 9999999);
    float x = pillow;
    float y = pillow;
    for (int i = 0; i < library.clippings.size(); i++){
      x = pillow + (i % columns) * (pillow + clipSize);
      y = pillow + (i / columns) * (pillow + clipSize);
      library.clippings.get(i).setSize(clipSize, clipSize);
      library.clippings.get(i).setPos(x, y);
    }
    foot = y + clipSize + pillow;
  }

  public void zoom(int z){
    columns -= z;
    fussMenagerie();
  }

  public void switchView(){
    sardine = !sardine;
    fussMenagerie();
  }

  public void packSardines() {
    ArrayList<Clipping> row = new ArrayList<Clipping>();
    float rowWidth = sPillow;
    float rowHeight = sPillow;
    float x = sPillow;
    float y = sPillow;

    for (Clipping clip : library.clippings) {
      float clipW = clip.img.width;
      float clipH = clip.img.height;
      float newWidth = (clipW / clipH) * clipSize;
      float nextY = clipSize;
      if (rowWidth + newWidth > w + sPillow) {    // if it dont fit
        // resize the row to fit the window
        float ratio = (w - sPillow) / rowWidth;
        for (Clipping c : row){
          c.setSize(c.displayW * ratio, c.displayH * ratio);
          c.setPos(c.xpos * ratio, c.ypos);
          nextY = c.displayH;
        }
        x = sPillow;
        y += nextY + sPillow;
        rowWidth = sPillow;
        row.clear();
      }
      rowWidth += newWidth;
      clip.setSize(newWidth, clipSize);
      clip.setPos(x, y);
      x += newWidth + sPillow;
      row.add(clip);
    }
    foot = y + clipSize + sPillow;
  }

  public void showtime(PGraphics g){
    g.push();
    g.background(50);
    g.translate(0, latitude);
    for (int i = 0; i < library.clippings.size(); i++){
      library.clippings.get(i).update(g, latitude);
    }
    g.pop();
    
    scroller.drawScroller(g);
  }

  public void followScroller(){
     latitude = -(scroller.gripY / tulpa.SOLE.height) * foot;
  }
}
