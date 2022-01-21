class Input{
  
  Field field;
  Scroller scroller;
  
  Input(Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
  }
  
  void keyInput(){
    if (key == CODED){
      switch(keyCode){
        case UP:
          println("scroll up");
          break;
        case DOWN:
          println("scroll down");
          break;
      }
    }
  }
  
  void mouseDown(){
    if (mouseX > field.w){
      scroller.grabScroller();
    }
  }
  
  void mouseUp(){
    if (scroller.grabbed){
      scroller.grabbed = false;
    }
  }
  
  void wheelInput(MouseEvent event){
    scroller.moveScroller(event.getCount());
  }
  
}
