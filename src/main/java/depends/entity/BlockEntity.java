package depends.entity;

public class BlockEntity extends ContainerEntity{
    private boolean isStatic;

    public BlockEntity(GenericName simpleName, Entity parent, int id, boolean isStatic) {
        super(simpleName,  parent,id);
        this.isStatic = isStatic;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

}
