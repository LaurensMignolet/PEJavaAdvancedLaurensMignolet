package be.pxl.ja.image;

import be.pxl.ja.common.DistanceFunction;

import java.awt.*;

public class GrayscalePixel implements PixelToInt, DistanceFunction<GrayscalePixel>  {
    private int greyscale;

    public GrayscalePixel(int greyscale) {
        this.greyscale = greyscale;
    }

    public int getGreyscale() {
        return greyscale;
    }

    @Override
    public int toRGB() {
        return new Color(greyscale, greyscale, greyscale).getRGB();
    }

    @Override
    public String toString() {
        return "GrayScalepixel: " + Integer.toString(greyscale);
    }

    @Override
    public double distance(GrayscalePixel param) {
        return Math.abs(this.greyscale - param.greyscale);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrayscalePixel that = (GrayscalePixel) o;

        return getGreyscale() == that.getGreyscale();
    }

    @Override
    public int hashCode() {
        return getGreyscale();
    }
}
