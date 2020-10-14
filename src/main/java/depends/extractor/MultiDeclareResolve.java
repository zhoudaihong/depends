package depends.extractor;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.MultiDeclareEntities;

import java.util.*;

public class MultiDeclareResolve {

    public static int getDistanceOfParent(Entity entity,Entity destination) {
        Entity parentOfEntity = entity;
        Entity parentOfDestination = destination;
        while( parentOfEntity.getClass() != FileEntity.class) {
            parentOfEntity = parentOfEntity.getParent();
            if(parentOfEntity == null) return -1;
        }
        while( parentOfDestination.getClass() != FileEntity.class) {
            parentOfDestination = parentOfDestination.getParent();
            if(parentOfDestination == null) return -1;
        }
        int distance = 0;
        int lengthOfThis = parentOfEntity.getQualifiedName().length();
        int lengthOfOther = parentOfDestination.getQualifiedName().length();

        char[] parentOfEntityName =  parentOfEntity.getQualifiedName().toCharArray();
        char[] destinationName = parentOfDestination.getQualifiedName().toCharArray();

        for(int i = 0; i < Math.min(lengthOfThis,lengthOfOther); ++i) {
            if(parentOfEntityName[i] == destinationName[i]){
                ++distance;
            }else{
                break;
            }
        }

        return distance;
    }

    public static List<Entity> selectMostRelative(MultiDeclareEntities multiDeclareEntities,Entity destination) {
        List<Entity> result = new ArrayList<>();
        Map<Entity, Integer> distances = new HashMap<>();
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
