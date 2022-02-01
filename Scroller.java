public class Scroller{
  // I mean REALLY I want to just use the OS's native scrolling. HOW? HOW???
  // would be nice to see an indication on the scrollbar of offscreen selected clippings

  
  color scrollC;
  color gripC;
  
  float gripY;
  float gripH;
  
  int scrollW;
  int scrollDist;
  
  boolean grabbed;
  float grabY;
  
  public Scroller(){
    gripY = 0;
    scrollW = 10;
    scrollC = #1A1A1A;
    gripC = #6C6C6C;
    scrollDist = 1;
    grabbed = false;
  }
  
  public void updateScroller(float contentH){
    gripH = height / contentH * height;
    if(grabbed){
      gripY = mouseY - grabY;
    }
    gripY = constrain(gripY, 0, height - gripH);
  }
  
  public void drawScroller(){
    noStroke();
    fill(scrollC);
    rect(width - scrollW, 0, scrollW, height);
    fill(gripC);
    rect(width - scrollW, gripY, scrollW, gripH);
  }
  
  public void grabScroller(){
    if (mouseY > gripY && mouseY < gripY + gripH){
      grabbed = true;
      println("grabbed");
      grabY = mouseY - gripY;
    }
  }
  
  public void moveScroller(int direction){
    gripY += direction * scrollDist;
  }
}
