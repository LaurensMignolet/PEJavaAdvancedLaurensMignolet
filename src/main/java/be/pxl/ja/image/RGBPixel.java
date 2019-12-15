package be.pxl.ja.image;
import java.util.Arrays;

public class RGBPixel implements PixelToInt {
    private int red;
    private int green;
    private int blue;

    public RGBPixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public int toRGB() {
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }

    @Override
    public String toString() {
        return " rgbpixel: (" + red + ", " + green + ", " + blue + ")";
    }

   public GrayscalePixel convertToGreyScale(){
        int average = (int)Arrays.stream(new int[] {red,green,blue}).average().orElseThrow(IllegalStateException::new);
        return new GrayscalePixel(average);
    }
}
