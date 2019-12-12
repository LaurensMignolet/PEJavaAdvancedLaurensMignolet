package be.pxl.ja.image;
import be.pxl.ja.common.DistanceUtil;
import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class ImageArt {
    public static void main(String[] args) throws IOException {
        RGBPixel prussianBlue = new RGBPixel(0, 48, 80);
        RGBPixel desaturatedCyan = new RGBPixel(112, 150, 160);
        RGBPixel peachYellow = new RGBPixel(250, 227, 173);
        RGBPixel lava = new RGBPixel(218, 20, 21);
        List < RGBPixel > faireyColors = Arrays.asList(prussianBlue, lava, desaturatedCyan, peachYellow);
        ImageReader myReader = new ImageReader();
        ImageWriter myWriter = new ImageWriter();

        Scanner input = new Scanner(System.in);
        System.out.println("please give the name of the picture you want to convert");
        System.out.println("The image must be located in your resources folder: ");
        String picName = input.next();

        Loader lod = new Loader("loading... ");
        lod.start();

        try {

            Path p = Paths.get("src\\main\\resources\\" + picName + ".jpg");  
            Path p2 = Paths.get("src\\main\\resources\\"+ picName + "_grayscale.jpg");
            Path p3 = Paths.get("src\\main\\resources\\"+ picName + "_Fairey.jpg");

            List < List < RGBPixel >> tokioRGB = myReader.readImage(p);

            List < List < GrayscalePixel >> tokioGray = tokioRGB.stream().map(rgbPixels -> rgbPixels.stream().map(pixel -> pixel.convertToGreyScale()).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(p2, tokioGray);

            TreeSet < GrayscalePixel > treeGray = new TreeSet < > (new Comparator < GrayscalePixel > () {
                @Override
                public int compare(GrayscalePixel o1, GrayscalePixel o2) {
                    return o1.getGreyscale() - o2.getGreyscale();
                }
            });
            tokioGray.forEach(grayscalePixels -> grayscalePixels.stream().forEach(grayscalePixel -> treeGray.add(grayscalePixel)));
            HashMap < GrayscalePixel, RGBPixel > tranlationMap = new HashMap < > (createTranslationMap(faireyColors, treeGray));
            List < List < GrayscalePixel >> grayfair = tokioGray.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> DistanceUtil.findClosest(new ArrayList < > (tranlationMap.keySet()), grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(p3, grayfair.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> tranlationMap.get(grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList()));

            lod.stop();
            System.out.println();
            System.out.println("the grayscale image was succesfully generated at " + p2 );
            System.out.println("the Fairey image was succesfully generated at " + p3 );

        } catch (IOException e) {
            lod.stop();
            System.out.println(e.getMessage());
        }catch (Exception e){
            lod.stop();
            System.out.println(e.getMessage());
        }finally {
            lod.stop();
        }
    }


    private static Map < GrayscalePixel, RGBPixel > createTranslationMap(List < RGBPixel > faireyColors, TreeSet < GrayscalePixel > allGreyscalePixels) {
        int size = allGreyscalePixels.size() / faireyColors.size();
        Map < GrayscalePixel, RGBPixel > translationMap = new HashMap < > ();
        Iterator < GrayscalePixel > iterator = allGreyscalePixels.iterator();
        int startIndex = size / 2;
        List < Integer > preferedIndeces = new ArrayList < > ();
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

class Loader extends Thread {
    String message;

    public Loader(String message) {
        this.message = message;
    }
    public void run() {
        System.out.print(message + " ");
        for (int i = 0; i < 9999; i++) {
            System.out.print("#");
            try {
                sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}