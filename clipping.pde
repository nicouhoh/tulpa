class Clipping{
  
  PImage img;
  
  Clipping(String imagePath){
    print(imagePath);
    img = loadImage(imagePath);
  }
  
  void display(){
   image(img, 0, 0); 
  }

}
