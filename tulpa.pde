Field field;
Scroller scroller;
int w, h;
boolean resized = false;

void setup(){
  surface.setSize(500, 500);
  surface.setResizable(true);
  field = new Field();
  field.initializeField();
}

void draw(){
  checkResize();
  field.updateField();
  resized = false;
}

void keyPressed(){
  if (key == CODED){
    if (keyCode == UP){
      println("scroll up");
    }
    else if (keyCode == DOWN){
      println("scroll down");
    }
  }
}
  
void checkResize(){
    if (w != width || h != height){
    w = width;
    h = height; //<>//
    resized = true;
  }
}

void mousePressed(){
  if (mouseX > field.fieldW){
    field.scroller.grabScroller();
  }
}

void mouseReleased(){
  if (field.scroller.grabbed){
    field.scroller.grabbed = false;
  }
}

void mouseWheel(MouseEvent event){
  field.scroller.moveScroller(event.getCount());
}
  
