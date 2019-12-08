package be.pxl.ja.image;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ImageReader myReader = new ImageReader();
        ImageWriter myWriter = new ImageWriter();

        Path p = Paths.get("src\\main\\resources\\bobDeKat.jpg");
        System.out.println(p.toFile());
        System.out.println(myReader.readImage(p));
        List<List<RGBPixel>> tokioRGB = myReader.readImage(p);

        Path p2 = Paths.get("src\\main\\resources\\bobDeKatGrey.jpg");

        try {
            //for(List<RGBPixel> pixelList : tokioRGB){
            //    for(RGBPixel pixel: pixelList){
            //        pixel.convertToGreyScale();
            //    }
            //}

            tokioRGB.stream().forEach(pixels ->  pixels.stream().forEach(pixel -> pixel.convertToGreyScale()));

            myWriter.writeImage(p2, tokioRGB);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
