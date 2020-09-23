package depends.extractor;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.MultiDeclareEntities;

import java.util.*;

public class MultiDeclareResolve {

    public static int getDistanceOfParent(Entity entity,FileEntity destination) {
        Entity parentOfEntity = entity;
        while( parentOfEntity.getClass() != FileEntity.class) {
            parentOfEntity = parentOfEntity.getParent();
        }
        int distance = 0;
        int lengthOfThis = parentOfEntity.getQualifiedName().length();
        int lengthOfOther = destination.getQualifiedName().length();

        for(int i = 0; i < (lengthOfThis < lengthOfOther ? lengthOfThis : lengthOfOther); ++i) {
            if(parentOfEntity.getQualifiedName().charAt(i) == destination.getQualifiedName().charAt(i))
            ++distance;
        }

        return distance;
    }

    public static List<Entity> selectMostRelative(MultiDeclareEntities multiDeclareEntities,FileEntity destination) {
        List<Entity> result = new ArrayList();
        Map<Entity, Integer> distances = new HashMap();
        int max = -1;
        for (Entity entity:multiDeclareEntities.getEntities()
             ) {
            int distance = getDistanceOfParent(entity,destination);
            distances.put(entity, distance);
            if (distance > max) {
                max = distance;
            }
        }

        for(Map.Entry<Entity,Integer> e : distances.entrySet()){
            if(e.getValue() == max){
                result.add(e.getKey());
            }
        }

        return result;
    }

}
