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

package depends.extractor.java.context;

import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.java.JavaParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterListContextHelper {

	FormalParameterListContext context;
	private IdGenerator idGenerator;
	private List<String> annotations;
	private FunctionEntity container;

	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext,FunctionEntity container, IdGenerator idGenerator) {
		this.context = formalParameterListContext;
		this.container = container;
		annotations = new ArrayList<>();
		this.idGenerator = idGenerator;
		if (context!=null)
			extractParameterTypeList();
	}

	public FormalParameterListContextHelper(FormalParametersContext formalParameters,FunctionEntity container, IdGenerator idGenerator) {
		this(formalParameters.formalParameterList(),container,idGenerator);
	}



	public void extractParameterTypeList() {
		if (context != null) {
			if (context.formalParameter() != null) {
				for (FormalParameterContext p : context.formalParameter()) {
//					foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
					foundParamterDefintion(p);
				}
				if (context.lastFormalParameter()!=null) {
					LastFormalParameterContext p = context.lastFormalParameter();
//					foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
					foundParameterDefintion(p);
				}
			}
		}
		return;
	}

	private void foundParamterDefintion(FormalParameterContext p) {
		VarEntity parameter = foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
		StringBuilder typeIdentifier = new StringBuilder();
		typeIdentifier.append(p.typeType().getText());
		typeIdentifier.append(p.variableDeclaratorId().getText().replace(p.variableDeclaratorId().IDENTIFIER().getText(), ""));
		parameter.setTypeIdentifier(typeIdentifier.toString());
	}
	
	private void foundParameterDefintion(LastFormalParameterContext p) {
		VarEntity parameter = foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
		StringBuilder typeIdentifier = new StringBuilder();
		typeIdentifier.append(p.typeType().getText());
		if(p.getText().contains("...")) {
			typeIdentifier.append("...");
		}
		parameter.setTypeIdentifier(typeIdentifier.toString());
	}

	private VarEntity foundParameterDefintion(TypeTypeContext typeType, TerminalNode identifier, List<VariableModifierContext> variableModifier) {
		GenericName type = GenericName.build(ClassTypeContextHelper.getClassName(typeType));
		GenericName varName = GenericName.build(identifier.getText());
		VarEntity varEntity = new VarEntity(varName,type,container,idGenerator.generateId());
		container.addParameter(varEntity);
		container.addToArgSignature("_" + varEntity.getRawType());
		for ( VariableModifierContext modifier:variableModifier) {
			if (modifier.annotation()!=null) {
				this.annotations.add(QualitiedNameContextHelper.getName(modifier.annotation().qualifiedName()));
			}
		}
		return varEntity;
	}

	public List<String> getAnnotations() {
		return annotations;
	}


}
