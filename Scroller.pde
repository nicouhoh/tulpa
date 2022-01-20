class Scroller{
  // I mean REALLY I want to just use the OS's native scrolling. HOW? HOW???
  
  color scrollC;
  color gripC;
  
  float gripY;
  float gripH;
  
  int scrollW;
  int scrollDist;
  
  boolean grabbed;
  float grabY;
  
  Scroller(){
    gripY = 0;
    scrollW = 10;
    scrollC = #97E582;
    gripC = #86036E;
    scrollDist = 5;
    grabbed = false;
  }
  
  void updateScroller(float contentH, float latitude){
    gripH = height / contentH * height;
    if(grabbed){
      gripY = mouseY - grabY;
    }
    gripY = constrain(gripY, 0, height - gripH); //<>//
  }
  
  void drawScroller(){
    noStroke();
    fill(scrollC);
    rect(width - scrollW, 0, scrollW, height);
    fill(gripC);
    rect(width - scrollW, gripY, scrollW, gripH);
  }
  
  void grabScroller(){
    if (mouseY > gripY && mouseY < gripY + gripH){
      grabbed = true;
      println("grabbed");
      grabY = mouseY - gripY;
    }
  }
  
  void moveScroller(int direction){
    gripY += direction * scrollDist;
  }

 
}
