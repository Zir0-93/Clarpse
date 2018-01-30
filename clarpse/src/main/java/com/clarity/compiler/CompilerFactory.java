package com.clarity.compiler;

/**
 * Factory to retrieve appropriate parsing tool for our projects.
 */
public class CompilerFactory {

    public static final ClarpseCompiler getParsingTool(final String parseType) throws Exception {
        switch (parseType.toLowerCase()) {

        case "java":
            return new ClarpseJavaCompiler();
        case "javascript":
            return new ClarpseJSCompiler();
        case "go":
            return new ClarpseGoCompiler();
        case "php":
                return new ClarpsePHPCompiler();
        default:
            throw new Exception("Could not find parsing tool for: " + parseType);
        }

    }
}
