import processing.core.PApplet;
import processing.core.PGraphics;


public class Scroller{
  // I mean REALLY I want to just use the OS's native scrolling. HOW? HOW???
  // would be nice to see an indication on the scrollbar of offscreen selected clippings

  
  int scrollC;
  int gripC;
  
  float gripY;
  float gripH;
  
  int scrollW;
  int scrollDist;
  
  boolean grabbed;
  float grabY;
  
  public Scroller(){
    gripY = 0;
    scrollW = 10;
    scrollC = 0x1A1A1A;
    gripC = 0x6C6C6C;
    scrollDist = 1;
    grabbed = false;
  }
  
  public void updateScroller(float contentH){
    gripH = tulpa.SOLE.height / contentH * tulpa.SOLE.height;
    if(grabbed){
      gripY = tulpa.SOLE.mouseY - grabY;
    }
    gripY = PApplet.constrain(gripY, 0, tulpa.SOLE.height - gripH);
  }
  
  public void drawScroller(PGraphics g){
    g.noStroke();
    g.fill(scrollC);
    g.rect(tulpa.SOLE.width - scrollW, 0, scrollW, tulpa.SOLE.height);
    g.fill(gripC);
    g.rect(tulpa.SOLE.width - scrollW, gripY, scrollW, gripH);
  }
  
  public void grabScroller(){
    if (tulpa.SOLE.mouseY > gripY && tulpa.SOLE.mouseY < gripY + gripH){
      grabbed = true;
      System.out.println("grabbed");
      grabY = tulpa.SOLE.mouseY - gripY;
    }
  }
  
  public void moveScroller(int direction){
    gripY += direction * scrollDist;
  }
}
