package depends.extractor.java;

import depends.entity.Entity;
import depends.entity.GenericName;
import depends.entity.MultiDeclareEntities;

import java.util.HashSet;
import java.util.List;

public class PathConverter {

    public static String File2Package(String filePath,String packageName){
        String result = null;
        String tempPackagePath;
        if(filePath.contains("/")){
            tempPackagePath = packageName.replace('.','/');
        }else{
            tempPackagePath = packageName.replace('.','\\');
        }
        int numOfSpace = 0;
        for(int i = 0;i < tempPackagePath.length();i++){
            if(tempPackagePath.charAt(i) == ' '){
                numOfSpace++;
            }else{
                break;
            }
        }
        tempPackagePath = tempPackagePath.substring(numOfSpace);

        if(filePath.lastIndexOf(tempPackagePath) != -1){
            result = filePath.substring(0,filePath.lastIndexOf(tempPackagePath) + tempPackagePath.length());
        }

        if(result == null)  result = "";

        return result;

    }

    public static Entity solveWrongEntityInSameNameByType(Entity fromEntity, Class clas){

        Entity preReferred = fromEntity;
        String preName = fromEntity.getRawName().getName();

        while(true){

            MultiDeclareEntities multiDeclareEntities = fromEntity.getMutliDeclare();
            if(multiDeclareEntities != null){
                List<Entity> multi = multiDeclareEntities.getEntities();
                for(Entity entity : multi){
                    if(entity.getClass() == clas){
                        return entity;
                    }
                }
            }

            fromEntity = fromEntity.getParent();
            if(fromEntity == null) return preReferred;

            Entity possibleEntity = fromEntity.getByName(preName, new HashSet<>());
            if(possibleEntity != null && possibleEntity.getClass() == clas) {
                return possibleEntity;
            }
        }
    }

}
