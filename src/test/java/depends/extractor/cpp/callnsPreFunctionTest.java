package depends.extractor.cpp;

import depends.deptypes.DependencyType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class callnsPreFunctionTest extends CppParserTest{

    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void useEnum() throws IOException {
        String[] srcs = new String[] {
                "./src/test/resources/cpp-code-examples/callnsPreFunctionTest/a.h",
                "./src/test/resources/cpp-code-examples/callnsPreFunctionTest/a.cpp",
        };

        for (String src:srcs) {
            CppFileParser parser = createParser(src);
            parser.parse();
        }
        inferer.resolveAllBindings();
        System.out.println(123);
        this.assertContainsRelation(repo.getEntity("funcA"), DependencyType.CALL,"nsA.nsB.func");
    }
}
