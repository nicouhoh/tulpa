class Clipping{
  
  PImage image;
  String filename;
  int xpos;
  int ypos;
  int clipSize = 30;
  int padding = 5;
  int wide;
  int tall;
  
  
  Clipping(String imagePath){
    image = loadImage(imagePath);
    print("INCUBATING: " + imagePath + ".... \n");
    filename = imagePath;
    //wide = image.width;
  }
  
  void display(int wide, int tall){
    image(image, xpos, ypos, wide, tall);
  }
  
  void setPos(int x, int y){
   xpos = x;
   ypos = y;
  }
}
