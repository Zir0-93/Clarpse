package com.clarity.test.go;

import com.clarity.compiler.ClarpseProject;
import com.clarity.compiler.Lang;
import com.clarity.compiler.RawFile;
import com.clarity.compiler.SourceFiles;
import com.clarity.sourcemodel.OOPSourceCodeModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommentsParsingTest {

    @Test
    public void testParsedSingleLineStructDoc() throws Exception {
        final String code = "package main\n //test struct doc\n type person struct {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test struct doc"));
    }

    @Test
    public void testParsMultiLineStructDoc() throws Exception {
        final String code = "package main\n //test struct\n// doc\n type person struct {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test struct doc"));
    }

    @Test
    public void testParseMultiLineInterfaceDoc() throws Exception {
        final String code = "package main\n //test interface\n// doc\n type person interface {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test interface doc"));
    }

    @Test
    public void testGoStructMethodComment() throws Exception {
        final String code = "package main\ntype person struct {}\n\n //test \n //test\n\nfunc (p person) x() int {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person.x() : (int)").get().comment().equals("test test"));
    }

    @Test
    public void testGoStructMethodDocComment() throws Exception {
        final String code = "package main\ntype person struct {}\n\n //test \n //test\n\nfunc (p person) x() int {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person.x() : (int)").get().comment().equals("test test"));
    }

    @Test
    public void testParseSingleLineStructDocSeparatedByEmptyLines() throws Exception {
        final String code = "package main\n//test struct doc\n\n\n\ntype person struct {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test struct doc"));
    }

    @Test
    public void testParseMultiLineStructDocAfterAnotherStruct() throws Exception {
        final String code = "package main\n type animal struct {}\n//test struct\n// doc\n type person struct {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test struct doc"));
    }

    @Test
    public void testParseMultiLineStructDocSeparatedByEmptyLines() throws Exception {
        final String code = "package main\n//test struct\n// doc\n\n\ntype person struct {}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person").get().comment().equals("test struct doc"));
    }

    @Test
    public void testParseMultiLineStructDocForInterfaceMethodSpece() throws Exception {
        final String code = "package main\ntype person interface { \n//test\n testMethod() int}";
        final SourceFiles rawData = new SourceFiles(Lang.GOLANG);
        rawData.insertFile(new RawFile("person.go", code));
        final ClarpseProject parseService = new ClarpseProject(rawData);
        final OOPSourceCodeModel generatedSourceModel = parseService.result();
        assertTrue(generatedSourceModel.getComponent("main.person.testMethod() : (int)").get().comment().equals("test"));
    }
}
