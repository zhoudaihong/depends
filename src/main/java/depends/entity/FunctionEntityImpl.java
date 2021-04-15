package depends.entity;

import depends.relations.Inferer;

public class FunctionEntityImpl extends FunctionEntity {
	Entity implementedFunction = null;
	public FunctionEntityImpl() {
		super();
	}
    public FunctionEntityImpl(GenericName simpleName, Entity parent, Integer id, GenericName returnType) {
		super(simpleName,parent,id,returnType);
	}
	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		implementedFunction = inferer.lookupTypeInImported((FileEntity)(getAncestorOfType(FileEntity.class)),this.getQualifiedName());
		if(implementedFunction != null){
				solveReloadFunction();
		}
	}
	public Entity getImplemented() {
		return implementedFunction;
	}

	private void solveReloadFunction(){
		String implSignature = this.getArgSignature();
		if(implementedFunction instanceof FunctionEntity &&
				(((FunctionEntity) implementedFunction).getArgSignature().equals(this.getArgSignature())))	{
			return ;
		}
		if(implementedFunction.getMutliDeclare() != null){
			for(Entity entity : implementedFunction.getMutliDeclare().getEntities()){
				if(entity instanceof FunctionEntity){
					if(((FunctionEntity) entity).getArgSignature().equals(implSignature)){
						implementedFunction =  entity;
						return ;
					}
				}
			}
		}
		System.out.println("Can not find true method!");
		return ;
	}

    
}
