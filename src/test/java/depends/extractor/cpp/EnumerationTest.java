package depends.extractor.cpp;

import depends.deptypes.DependencyType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class EnumerationTest extends CppParserTest{
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void useEnum() throws IOException {
        String[] srcs = new String[] {
                "./src/test/resources/cpp-code-examples/EnumerationTest/a.h",
                "./src/test/resources/cpp-code-examples/EnumerationTest/a.cpp",
        };

        for (String src:srcs) {
            CppFileParser parser = createParser(src);
            parser.parse();
        }
        inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("C"), DependencyType.USE,"B.FunctualB");
        this.assertContainsRelation(repo.getEntity("C"), DependencyType.USE,"AC.A.Functual");
        this.assertContainsRelation(repo.getEntity("C"), DependencyType.USE,"nsA.A.FunctionalnsA");
    }
}
