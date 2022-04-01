/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.java;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class JavaHandlerContext extends HandlerContext {

	public JavaHandlerContext(EntityRepo entityRepo,Inferer inferer) {
		super(entityRepo,inferer);
	}

	public Entity foundNewPackage(String packageName) {
		Entity pkgEntity = entityRepo.getEntity(packageName);
		String pckAbstractPath = currentFileEntity.file2Package(packageName);
		if (pkgEntity == null) {
			pkgEntity = createNewPckWithJavaPath(packageName, pckAbstractPath);
		}else{
			if(pkgEntity instanceof MultiDeclareEntities){
				boolean findFlag = false;
				for(Entity e : ((MultiDeclareEntities) pkgEntity).getEntities()){
					if(pckAbstractPath.equals(((PackageEntity)e).getJavaPath())){
						pkgEntity = e;
						findFlag = true;
						break;
					}
				}if(!findFlag){
					pkgEntity = createNewPckWithJavaPath(packageName, pckAbstractPath);
				}
			}else if( !(pckAbstractPath.equals(((PackageEntity)pkgEntity).getJavaPath())) ){
				pkgEntity = createNewPckWithJavaPath(packageName, pckAbstractPath);
			}
		}
		Entity.setParent(currentFileEntity,pkgEntity);
		return pkgEntity;
	}

	private Entity createNewPckWithJavaPath(String packageName, String path){
		Entity pkgEntity = new PackageEntity(packageName, idGenerator.generateId());
		((PackageEntity)pkgEntity).setJavaPath(path);
		entityRepo.add(pkgEntity);
		return pkgEntity;
	}

	public BlockEntity foundNewBlock(GenericName simpleName, boolean isStatic) {
		BlockEntity blockEntity = new BlockEntity(simpleName, lastContainer(), idGenerator.generateId(), isStatic);
		entityRepo.add(blockEntity);
		return blockEntity;
	}
}
