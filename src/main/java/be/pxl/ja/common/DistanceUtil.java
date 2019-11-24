package be.pxl.ja.common;

import java.util.List;

public class DistanceUtil {
    public static<T extends DistanceFunction> T  findClosest(List<T> elements, T otherElement){
        T closest = elements.get(0);
        for(T element : elements){
            double distance = element.distance(otherElement);

            if(distance < closest.distance(otherElement)){
                closest = element;
            }
        }
        return closest;
    }
}
