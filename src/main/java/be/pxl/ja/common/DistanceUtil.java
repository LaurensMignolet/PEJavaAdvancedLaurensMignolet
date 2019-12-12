package be.pxl.ja.common;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DistanceUtil {
    public static<T extends DistanceFunction> T  findClosest(Set<T> elements, T otherElement){
        Iterator iterator = elements.iterator();
        T closest = (T) iterator.next();

        while (iterator.hasNext()) {
            T current = ((T) iterator.next());
            double distance = current.distance(otherElement);

            if(distance < closest.distance(otherElement)){
                closest = current;
            }
        }
        return closest;
    }
}
