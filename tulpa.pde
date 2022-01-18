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
  if (w != width || h != height){
    w = width;
    h = height;
    resized = true;
    
  }
  
  field.updateField();
  resized = false;
}

void keyPressed(){
  if (key == CODED){
    if (keyCode == UP){
      println("translate up");
      
    }
    else if (keyCode == DOWN){
      println("translate down");
       //<>//
    }
  }
}
