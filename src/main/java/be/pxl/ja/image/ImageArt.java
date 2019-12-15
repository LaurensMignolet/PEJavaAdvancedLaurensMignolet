package be.pxl.ja.image;
import be.pxl.ja.common.DistanceUtil;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        System.out.println("The image must be located in your resources folder (no extension): ");
        String picName = input.next();

        long before = System.currentTimeMillis();
        Loader lod = new Loader("loading... ");
        lod.start();

        try {
            Path path = Paths.get("src\\main\\resources\\" + picName + ".jpg");
            Path grayPath = Paths.get("src\\main\\resources\\"+ picName + "_grayscale.jpg");
            Path faireyPath = Paths.get("src\\main\\resources\\"+ picName + "_Fairey.jpg");

            List < List < RGBPixel >> RGBList = myReader.readImage(path);

            List < List < GrayscalePixel >> grayList = RGBList.stream().map(rgbPixels -> rgbPixels.stream().map(pixel -> pixel.convertToGreyScale()).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(grayPath, grayList);

            TreeSet < GrayscalePixel > treeGray = new TreeSet < > (new Comparator < GrayscalePixel > () {
                @Override
                public int compare(GrayscalePixel o1, GrayscalePixel o2) {
                    return o1.getGreyscale() - o2.getGreyscale();
                }
            });

            /* 1. grayscale pixels are added to the treeset
             * 2. the map that hold the transformation info is created.
             * 3. the grayscale pixels are set to the grayscale value of the closes key in from the map.
             * the closest key is found with the distanceUtil.findclosest() method
             * 4. the image is written from a stream that takes the value of the grayscale and uses it as a key in the map,
             * the map gives a RGB pixel, it is used to write the image.
             * */
            grayList.forEach(grayscalePixels -> grayscalePixels.stream().forEach(grayscalePixel -> treeGray.add(grayscalePixel)));
            HashMap < GrayscalePixel, RGBPixel > tranlationMap = new HashMap < > (createTranslationMap(faireyColors, treeGray));
            List < List < GrayscalePixel >> tranlatedGrayPixelsList = grayList.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> DistanceUtil.findClosest(tranlationMap.keySet(), grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(faireyPath, tranlatedGrayPixelsList.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> tranlationMap.get(grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList()));

            lod.stop();
            System.out.println();
            System.out.println("the grayscale image was succesfully generated at " + grayPath );
            System.out.println("the Fairey image was succesfully generated at " + faireyPath );

            long after = System.currentTimeMillis();
            double seconds  = (after - before) / 1000.0;
            System.out.println("the program finished in " + seconds + " seconds to process a " + RGBList.get(0).size() + " x " + RGBList.size() + " image" );

            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(grayPath.toFile());
                Desktop.getDesktop().open(faireyPath.toFile());
            }
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