public class Library{
  
  Clipping[] clippings;
  String path;
  int nextid;
  
  public Library(){
    nextid = 0; //<>//
    path = sketchPath() + "/data/";
    clippings = incubateDir(new File(path));
  }
  
  public void addToLibrary(Clipping clip){
    clippings = (Clipping[])append(clippings, clip);
  }
  
  public void addToLibrary(Clipping[] clips){
    clippings = (Clipping[])concat(clippings, clips);
  }
  
  public Clipping incubateFile(File file){
    println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){
      Clipping clipping = new Clipping(this, file);
      return clipping;
    }else{ return null; }
  }
  
  public Clipping[] incubateDir(File dir){
    println("INCUBATE DIR");
    File[] files = listFiles(dir);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < files.length; i++){
      if (files[i].getName().contains(".jpg")){
        brood = (Clipping[])append(brood, incubateFile(files[i]));
      } else if(files[i].isDirectory()){
        brood = (Clipping[])concat(brood, incubateDir(files[i]));
      }
    }
    println("BROOD: " + brood);
    return brood;
  }
  
  public String getid(){
    String date = str(year()) + nf(month(), 2) + nf(day(), 2);
    String id = date + nf(nextid, 3);
    nextid++;
    return id;
  }
}
