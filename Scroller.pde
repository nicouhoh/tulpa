class Scroller{
  
  
  // I mean REALLY I want to just use the OS's native scrolling. HOW? HOW???
  
  int xpos;
  int scrollWidth;
  int nubHeight;
  int nubY;
  color barColor;
  color nubColor;
  int yPan;
  boolean grabbed;
  
  Scroller(){
    scrollWidth = 10;
    nubY = 0;
    nubHeight = 40;
    barColor = color(0, 0, 0, 50);
    nubColor = color(200, 200, 200, 255);
  }
    
  void drawScroller(){
    xpos = width - scrollWidth;
    noStroke();
    fill(barColor);
    rect(xpos, 0, scrollWidth, height);
    fill(nubColor);
    rect(xpos, nubY, scrollWidth, nubHeight);
  }
}
