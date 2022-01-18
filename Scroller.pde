class Scroller{
  // I mean REALLY I want to just use the OS's native scrolling. HOW? HOW???
  color scrollC;
  color gripC;
  int gripY;
  int gripH;
  int scrollW;
  
  
  Scroller(){
    gripY = 0;
    scrollW = 10;
    scrollC = #97E582;
    gripC = #ADCFFF;
  }
  
  void updateScroller(int contentH){
    println("UPDATING GRIPH"); //<>//
    gripH = height / contentH * height;
  }
  
  void moveScroller(int distance){
    gripY += distance;
  }
  
  void drawScroller(){
    noStroke();
    fill(scrollC);
    rect(width - scrollW, 0, scrollW, height);
    fill(gripC);
    rect(width - scrollW, gripY, scrollW, gripH);
  }
 
}
