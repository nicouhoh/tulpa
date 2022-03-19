import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;


public class Field{

  Library library;
    
  float w;
  float h;
  int sheetZoom;
  Scroller scroller;
  float latitude;
  float clipSize;
  float pillow;
  float sPillow;
  float foot;

  boolean sardine;

  float zoomPillow;
  
  public Field(Library libraryIn){
    library = libraryIn;
    scroller = new Scroller();
    latitude = 0;
    sheetZoom = 5;
    pillow = 10;
    sPillow = 2;
    clipSize = 200;
    sardine = false;
    zoomPillow = 30;
    fussMenagerie();
  }
  
  public void initializeField(){
    w = tulpa.SOLE.width - scroller.scrollW;
  }
  
  public void updateField(PGraphics g){
    float latCheck = latitude;
    w = tulpa.SOLE.width - scroller.scrollW;
    h = tulpa.SOLE.height;
    if (tulpa.SOLE.resized){
      fussMenagerie();
    }
//    followScroller();
    showtime(g);
    if (latCheck != latitude) System.out.println(latitude);
  }
  
  public void fussMenagerie(){
    if(sardine){
      packSardines();
      return;
    }
    clipSize = PApplet.constrain((w - (pillow * (sheetZoom + 1))) / sheetZoom, 10, 9999999);
    float x = pillow;
    float y = pillow;
    for (int i = 0; i < library.clippings.size(); i++){
      x = pillow + (i % sheetZoom) * (pillow + clipSize);
      y = pillow + (i / sheetZoom) * (pillow + clipSize);
      library.clippings.get(i).setSize(clipSize, clipSize);
      library.clippings.get(i).setPos(x, y);
    }
    foot = y + clipSize + pillow;
    updateScroller();
  }

  public void sheetZoom(int z){
    sheetZoom -= z;
    fussMenagerie();
  }

  public void switchView(){
    sardine = !sardine;
    fussMenagerie();
  }

  public void packSardines() {
    clipSize = PApplet.constrain(h / sheetZoom, 10, 9999999);
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
    updateScroller();
  }

  public void showtime(PGraphics g){
    g.push();
    g.background(50);
    g.translate(0, -latitude);
    Clipping zoomClip = null;
    for (int i = 0; i < library.clippings.size(); i++){
      library.clippings.get(i).update(g, latitude);
    }
    g.pop();
    
    scroller.drawScroller(g);
    if(library.zoom) {
      g.fill(0, 230);
      g.rect(0, 0, w, h);
      library.selected.get(0).zoomDisplay(g, w, h, zoomPillow);
    }
  }

// SCROLLING ---------------------------------------

  public void followScroller(){
     goTo((scroller.gripY / tulpa.SOLE.height) * foot, 0);
  }

  public void moveScroller(int direction){
    scroller.gripY += direction * scroller.scrollDist;
    updateScroller();
  }

  public void updateScroller(){
    scroller.update(foot);
    followScroller();
  }

  public void grabScroller(){
    if (tulpa.SOLE.mouseY > scroller.gripY && tulpa.SOLE.mouseY < scroller.gripY + scroller.gripH){
      scroller.grab();
    }
  }

  public void dragScroller(float contentH){
    if(scroller.grabbed){
      scroller.gripY = tulpa.SOLE.mouseY - scroller.grabY;
      updateScroller();
    }
  }

  public void goTo(float y, float where){ // pick a point, where on screen do you want it
    latitude = y - where;
  }

  public void arrowFollow(){
    if(library.selected.size() != 1) return;

    Clipping clip = library.selected.get(0);
    if (clip.ypos > latitude + h){
      goTo(clip.ypos, 0);
    }
  }
  // END SCROLLING --------------------------------------------
}