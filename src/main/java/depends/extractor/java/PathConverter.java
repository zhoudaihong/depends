package depends.extractor.java;

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
        return result;
    }

}
