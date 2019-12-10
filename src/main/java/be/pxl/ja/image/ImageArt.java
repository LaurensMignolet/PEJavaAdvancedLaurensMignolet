package be.pxl.ja.image;

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
        List<RGBPixel> faireyColors = Arrays.asList(prussianBlue, lava, desaturatedCyan, peachYellow);

        //

        ImageReader myReader = new ImageReader();
        ImageWriter myWriter = new ImageWriter();

        Path p = Paths.get("src\\main\\resources\\bobDeKat.jpg");
        //System.out.println(p.toFile());
        //System.out.println(myReader.readImage(p));
        List<List<RGBPixel>> tokioRGB = myReader.readImage(p);

        Path p2 = Paths.get("src\\main\\resources\\grayscale.jpg");

        try {

            /*hier zet ik de rgbpixellijst om naar een grayscalepixellijst met behulp van streams.
            deze lijst gebruik ik daarna om de grayscale.jpg te genereren
                dit is vraag 4a
            */
            List<List<GrayscalePixel>> tokioGray = tokioRGB.stream().map(rgbPixels -> rgbPixels.stream().map(pixel -> pixel.convertToGreyScale()).collect(Collectors.toList())).collect(Collectors.toList());
            myWriter.writeImage(p2, tokioGray);




            //4B

            //maak treeset en vul deze
            TreeSet<GrayscalePixel> treeGray = new TreeSet<>(
                    new Comparator<GrayscalePixel>() {
                        @Override
                        public int compare(GrayscalePixel o1, GrayscalePixel o2) {
                            return o1.getGreyscale() - o2.getGreyscale();
                        }
                    }

            );
            tokioGray.forEach(grayscalePixels -> grayscalePixels.stream().forEach(grayscalePixel -> treeGray.add(grayscalePixel)));


            //creeër de hashmap om nadien te weten elke rgb bij welke grayscale hoort
            HashMap<GrayscalePixel, RGBPixel> tranlationMap = new HashMap<>(createTranslationMap(faireyColors, treeGray));
            System.out.println(tranlationMap);


            //eerst de grayscale naar het dichtste bij omzetten

            List<List<GrayscalePixel>> grayfair = new ArrayList<>();
            for(int i = 0; i < tokioGray.size(); i++){
                grayfair.add(new ArrayList<GrayscalePixel>());
                for(int j = 0; j < tokioGray.get(i).size(); j++){
                    int indexsmall = 0;
                    for(int c = 0; c < tranlationMap.keySet().size(); c++){
                        if(tokioGray.get(i).get(j).distance((GrayscalePixel)tranlationMap.keySet().toArray()[c]) < tokioGray.get(i).get(j).distance((GrayscalePixel)tranlationMap.keySet().toArray()[indexsmall])){
                            indexsmall = c;
                        }
                    }

                    grayfair.get(i).add((GrayscalePixel)tranlationMap.keySet().toArray()[indexsmall]);
                }
            }

            /*System.out.println(grayfair.get(0).get(0));
            System.out.println(tranlationMap.get(new GrayscalePixel(224)));
            System.out.println(grayfair);¨*/

            //nu de dichtste bij omzetten met behulp van map
            //List<List<RGBPixel>> FaireyPic = grayfair.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> tranlationMap.get(grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList());

            Path p3 = Paths.get("src\\main\\resources\\Fairey.jpg");
            myWriter.writeImage(p3, grayfair.stream().map(grayscalePixels -> grayscalePixels.stream().map(grayscalePixel -> tranlationMap.get(grayscalePixel)).collect(Collectors.toList())).collect(Collectors.toList()));




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
