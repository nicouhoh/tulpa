import java.util.ArrayList;
import processing.core.PApplet;

public class ArrangerDropzoneFactory {

    ArrayList<Thumbnail> thumbnails;
    int allowance;
    float x, w;

    public ArrangerDropzoneFactory(ArrayList<Thumbnail> thumbnails, int allowance, float x, float w){
        this.thumbnails = thumbnails;
        this.allowance = allowance;
        this.x = x;
    }

    public ArrayList<Dropzone> produceAllZones() {
        ArrayList<Dropzone> result = new ArrayList<>();
        result.add(firstArranger(thumbnails.get(0), thumbnails.get(1), allowance));
        for (int i = 1; i < thumbnails.size() - 1; i++){
            Thumbnail left = thumbnails.get(i);
            Thumbnail right = thumbnails.get(i + 1);
            result.addAll(createThumbnailDropZones(left, right));
        }
        return result;
    }

    private ArrangerDropzone firstArranger(Thumbnail left, Thumbnail right, int allowance) {
        return new ArrangerDropzone(left, x, left.y, left.x - x + allowance, left.h, x);
    }

    private ArrayList<ArrangerDropzone> createThumbnailDropZones(Thumbnail left, Thumbnail right) {
        ArrayList<ArrangerDropzone> result = new ArrayList<>();
        if (rowBreak(left, right)){
            result.addAll(startAndEndOfRowDropZones(left, right));
        }
        else result.add(createDropZoneBetweenThumbnails(left, right));
        return result;
    }


    private ArrayList<ArrangerDropzone> startAndEndOfRowDropZones(Thumbnail left, Thumbnail right) {
        ArrayList<ArrangerDropzone> result = new ArrayList<>();
        result.add(rowEndArranger(left, right));
        result.add(rowStartArranger(right));
        return result;
    }

    private ArrangerDropzone rowEndArranger(Thumbnail left, Thumbnail right){
        return new ArrangerDropzone(right,
                getDropZoneX(left), left.y, allowance, left.h, left.x + left.w);
    }

    private ArrangerDropzone rowStartArranger(Thumbnail right){
        return new ArrangerDropzone(right, x, right.y, right.x - x + allowance, right.h, right.x);
    }

    private boolean rowBreak(Thumbnail left, Thumbnail right){
        return right.x <= left.x;
    }

    public ArrangerDropzone createDropZoneBetweenThumbnails(Thumbnail left, Thumbnail right){
                return new ArrangerDropzone(right,
                        getDropZoneX(left),
                        getDropZoneY(left, right),
                        getDropZoneW(left, right),
                        getDropZoneH(left, right),
                        getLineX(left, right)
                );
    }

    private float getDropZoneX(Thumbnail left) {
        return left.x + left.w - allowance;
    }

    private float getDropZoneY(Thumbnail left, Thumbnail right) {
        return PApplet.min(left.y, right.y);
    }

    private float getDropZoneW(Thumbnail left, Thumbnail right) {
        return right.x - (left.x + left.w) + 2 * allowance;
    }

    private float getDropZoneH(Thumbnail left, Thumbnail right) {
        return PApplet.max(left.h, right.h);
    }


    private float getLineX(Thumbnail left, Thumbnail right) {
        return (left.x + left.w + right.x) / 2;
    }
}
