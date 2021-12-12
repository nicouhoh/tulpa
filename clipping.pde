class Clipping{
  
  PImage img;
  int xpos;
  int ypos;
  
  Clipping(String imagePath){
    print(imagePath);
    img = loadImage(imagePath);
  }
  
  void display(int w, int h){
   image(img, xpos, ypos, w, h); 
  }
  
  void setPos(int x, int y){
    xpos = x;
    ypos = y;
  }

}
