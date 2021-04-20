package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.parser.c.ICParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.ICPPParserExtensionConfiguration;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.cdt.internal.core.dom.parser.BacktrackException;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;

public class MyGNUCSourceParser extends GNUCPPSourceParser {

    public MyGNUCSourceParser(IScanner scanner, ParserMode parserMode, IParserLogService logService,
                              ICPPParserExtensionConfiguration config) {
        super(scanner, parserMode, logService, config, null);
    }

    public MyGNUCSourceParser(IScanner scanner, ParserMode parserMode, IParserLogService logService,
                              ICPPParserExtensionConfiguration config, IIndex index) {
        super(scanner, parserMode, logService, config, index);
    }

    @Override
    protected IASTStatement handleFunctionBody() throws BacktrackException, EndOfFileException {
        // full parse
        return functionBody();
    }
}
