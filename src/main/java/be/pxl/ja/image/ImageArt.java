package be.pxl.ja.image;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ImageArt {

    public static void main(String[] args) throws IOException {

        RGBPixel prussianBlue = new RGBPixel(0, 48, 80);
        RGBPixel desaturatedCyan = new RGBPixel(112, 150, 160);
        RGBPixel peachYellow = new RGBPixel(250, 227, 173);
        RGBPixel lava = new RGBPixel(218, 20, 21);
        List<RGBPixel> faireyColors = Arrays.asList(prussianBlue, lava, desaturatedCyan, peachYellow);

        //

        ImageReader myReader = new ImageReader();
        ImageWriter myWriter = new ImageWriter();

        Path p = Paths.get("src\\main\\resources\\tokio.jpg");
        System.out.println(p.toFile());
        System.out.println(myReader.readImage(p));
        List<List<RGBPixel>> tokioRGB = myReader.readImage(p);

        Path p2 = Paths.get("src\\main\\resources\\grayscale.jpg");

        try {

            /*hier zet ik de rgbpixellijst om naar een grayscalepixellijst met behulp van streams
                dit is vraag 4a
                       */
            List<List<GrayscalePixel>> tokioGray = tokioRGB.stream().map(rgbPixels -> rgbPixels.stream().map(pixel -> pixel.convertToGreyScale()).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(p2, tokioGray);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }



    }

    private static Map<GrayscalePixel, RGBPixel> createTranslationMap(List<RGBPixel> faireyColors, TreeSet<GrayscalePixel> allGreyscalePixels) {
        int size = allGreyscalePixels.size() / faireyColors.size();

        Map<GrayscalePixel, RGBPixel> translationMap = new HashMap<>();
        Iterator<GrayscalePixel> iterator = allGreyscalePixels.iterator();
        int startIndex = size / 2;
        List<Integer> preferedIndeces = new ArrayList<>();
        for (int group = 0; group < faireyColors.size(); group++) {
            preferedIndeces.add(startIndex);
            startIndex += size;
        }
        int index = 0;
        while (iterator.hasNext()) {
            GrayscalePixel grayscalePixel = iterator.next();
            if (preferedIndeces.contains(index)) {
                int position = preferedIndeces.indexOf(index);
                translationMap.put(grayscalePixel, faireyColors.get(position));
            }
            index++;
        }
        return translationMap;
    }
}
