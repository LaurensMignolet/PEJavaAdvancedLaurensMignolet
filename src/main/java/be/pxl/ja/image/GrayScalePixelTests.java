package be.pxl.ja.image;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GrayScalePixelTests {

    GrayscalePixel pix = new GrayscalePixel(80);
    GrayscalePixel pix2 = new GrayscalePixel(30);
    @Test
    public void ShouldCalculateCorrectDistance() {
        Assertions.assertEquals(50, pix.distance(pix2));
        Assertions.assertEquals(50, pix2.distance(pix));
    }

    @Test
    public void DistanceBetweenSamePixelShouldBeZero() {
        Assertions.assertEquals(0, pix.distance(pix));

    }
    @Test
    public void DistanceShouldBeTheSameBothWays() {
        Assertions.assertEquals(pix.distance(pix2), pix2.distance(pix));
    }
}
