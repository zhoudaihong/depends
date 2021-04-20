package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.ASTGenericVisitor;
import org.eclipse.cdt.core.dom.ast.IASTNode;

import java.util.ArrayList;
import java.util.List;

public class CppChildCollector extends ASTGenericVisitor {
    private final IASTNode fNode;
    private List<IASTNode> fNodes;

    public CppChildCollector(IASTNode node) {
        super(true);
        fNode = node;
    }

    public IASTNode[] getChildren() {
        this.includeInactiveNodes = true;
        fNode.accept(this);
        if (fNodes == null)
            return IASTNode.EMPTY_NODE_ARRAY;

        return fNodes.toArray(new IASTNode[fNodes.size()]);
    }

    @Override
    protected int genericVisit(IASTNode child) {
        if (fNodes == null) {
            if (child == fNode)
                return PROCESS_CONTINUE;
            fNodes = new ArrayList<>();
        }
        fNodes.add(child);
        return PROCESS_SKIP;
    }
}
