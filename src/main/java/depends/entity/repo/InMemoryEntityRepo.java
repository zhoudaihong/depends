package depends.entity.repo;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.GenericName;
import depends.entity.MultiDeclareEntities;

import java.util.*;
import java.util.Map.Entry;


public class InMemoryEntityRepo extends SimpleIdGenerator implements EntityRepo {

	public class EntityＭapIterator implements Iterator<Entity>{

		private Iterator<Entry<Integer, Entity>> entryIterator;

		public EntityＭapIterator(Set<Entry<Integer, Entity>> entries) {
			this.entryIterator = entries.iterator();
		}
		@Override
		public boolean hasNext() {
			return entryIterator.hasNext();
		}

		@Override
		public Entity next() {
			return entryIterator.next().getValue();
		}
		
	}
	
	private Map<String, Entity> allEntieisByName;
	private Map<Integer, Entity> allEntitiesById;
	private List<Entity> allFileEntitiesByOrder;

	public InMemoryEntityRepo() {
		allEntieisByName = new TreeMap<>();
		allEntitiesById = new TreeMap<>();
		allFileEntitiesByOrder = new LinkedList<>();
	}

	@Override
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}

	@Override
	public Entity getEntity(Integer entityId) {
		return allEntitiesById.get(entityId);
	}

	@Override
	public void add(Entity entity) {
		allEntitiesById.put(entity.getId(), entity);
		String name = entity.getRawName().uniqName();
		if (entity.getQualifiedName() != null && !(entity.getQualifiedName().isEmpty())) {
			name = entity.getQualifiedName();
		}
		if (allEntieisByName.containsKey(name)) {
			Entity existedEntity = allEntieisByName.get(name);
			if (existedEntity instanceof MultiDeclareEntities) {
				((MultiDeclareEntities) existedEntity).add(entity);
			} else {
				MultiDeclareEntities eMultiDeclare = new MultiDeclareEntities(existedEntity, this.generateId());
				eMultiDeclare.add(entity);
				allEntieisByName.put(name, eMultiDeclare);
			}
		} else {
			allEntieisByName.put(name, entity);
		}
		if (entity.getParent() != null)
			Entity.setParent(entity, entity.getParent());
	}

	@Override
	public Iterator<Entity> entityIterator() {
		return new EntityＭapIterator(allEntitiesById.entrySet());
	}

	
	@Override
	public void update(Entity entity) {
	}

	@Override
	public Entity getEntity(GenericName rawName) {
		return this.getEntity(rawName.uniqName());
	}

	@Override
	public Collection<Entity> getFileEntities() {
		return allFileEntitiesByOrder;
	}

	@Override
	public Iterator<Entity> sortedFileIterator() {
		return allFileEntitiesByOrder.iterator();
	}

	@Override
	public void addFile(FileEntity fileEntity) {
		allFileEntitiesByOrder.add(fileEntity);
	}

	public void clear() {
		this.allEntieisByName.clear();
		this.allEntitiesById.clear();
		this.allFileEntitiesByOrder.clear();
	}

	public void doNull() {
		this.allEntieisByName = null;
		this.allEntitiesById = null;
		this.allFileEntitiesByOrder = null;
	}

//	public boolean checkForOne(Relation relation, Entity entity){
//		if (relation.getEntity().getMutliDeclare() != null) {
//			List<Entity> e = new ArrayList<>();
//			e = MultiDeclareResolve.selectMostRelative(relation.getEntity().getMutliDeclare(), entity);
//			if (!e.contains(relation.getEntity())) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private Map<Entity,Relation> wrongRelations = new HashMap<>();
//
//	public void checkRelations(){
//		for(Entity entity:allEntitiesById.values()){
//			if(entity.getAncestorOfType(FileEntity.class) != null) {
//				for (Relation relation : entity.getRelations()) {
//					if (checkForOne(relation, entity) == false) {
//						wrongRelations.put(entity,relation);
//					}
//				}
//			}
//		}
//	}
//
//	private Map<Entity,Relation> contain0 = new HashMap<>();
//	private Map<Entity,Relation> parameter0 = new HashMap<>();
//	private Map<Entity,Relation> annotation0 = new HashMap<>();
//	private Map<Entity,Relation> use0 = new HashMap<>();
//	private Map<Entity,Relation> mixin0 = new HashMap<>();
//	private Map<Entity,Relation> call0 = new HashMap<>();
//	private Map<Entity,Relation> impllink0 = new HashMap<>();
//	private Map<Entity,Relation> create0 = new HashMap<>();
//	private Map<Entity,Relation> throw0 = new HashMap<>();
//	private Map<Entity,Relation> cast0 = new HashMap<>();
//	private Map<Entity,Relation> inherit0 = new HashMap<>();
//	private Map<Entity,Relation> implement0 = new HashMap<>();
//	private Map<Entity,Relation> return0 = new HashMap<>();
//	private Map<Entity,Relation> import0 = new HashMap<>();
//
//	public void checkRelations(){
//		for(Entity entity:allEntitiesById.values()){
//			for (Relation relation : entity.getRelations()) {
//				if(relation.getType().equals("Contain") && !relation.getEntity().getQualifiedName().equals("built-in")){
//					contain0.put(entity,relation);
//				}else if(relation.getType().equals("Parameter")){
//					parameter0.put(entity,relation);
//				}else if(relation.getType().equals("Annotation")){
//					annotation0.put(entity,relation);
//				}else if(relation.getType().equals("Use")){
//					use0.put(entity,relation);
//				}else if(relation.getType().equals("MixIn")){
//					mixin0.put(entity,relation);
//				}else if(relation.getType().equals("Call")){
//					call0.put(entity,relation);
//				}else if(relation.getType().equals("ImplLink")){
//					impllink0.put(entity,relation);
//				}else if(relation.getType().equals("Creat")){
//					create0.put(entity,relation);
//				}else if(relation.getType().equals("Throw")){
//					throw0.put(entity,relation);
//				}else if(relation.getType().equals("Cast")){
//					cast0.put(entity,relation);
//				}else if(relation.getType().equals("Extend")){
//					inherit0.put(entity,relation);
//				}else if(relation.getType().equals("Implement")){
//					implement0.put(entity,relation);
//				}else if(relation.getType().equals("Return")){
//					return0.put(entity,relation);
//				}else if(relation.getType().equals("Import")){
//					import0.put(entity,relation);
//				}
//			}
//		}
//	}

}
