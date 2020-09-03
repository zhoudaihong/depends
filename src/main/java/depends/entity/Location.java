package depends.entity;

import java.io.Serializable;

public class Location implements Serializable {
    Integer startLine = null;
    Integer endLine = null;
    Integer loc = null;
    public Integer getStartLine(){
        return startLine;
    }
    public void setStartLine(int startLine){
        this.startLine = startLine;
    }
    public Integer getEndLine(){
        return endLine;
    }
    public void setEndLine(int endLine){
        this.endLine = endLine;
    }
    public Integer getLoc(){
        return loc;
    }
    public void setLoc(int loc){
        this.loc = loc;
    }
}
