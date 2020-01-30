package com.leisurexi.jvm.compile;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * @author: leisurexi
 * @date: 2020-01-28 9:12 下午
 * @description: 程序名称规范的编译器插件：如果程序命名不合规范，将会输出一个编译器的WARNING信息
 * @since JDK 1.8
 */
public class NameChecker {

    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnvironment) {
        this.messager = processingEnvironment.getMessager();
    }

    /**
     * 对Java程序命名进行检查，根据《Java语言规范（第3版）》6.8 节的要求，Java
     * 程序命名应当符合下列各式：
     * 类或接口：符合驼式命名法，首字母大写。
     * 方法：符合驼式命名法，首字母小写。
     * 字段：
     * 类、实例变量：符合驼式命名法，首字母小写。
     * 常量：要求全部大写。
     *
     * @param element
     */
    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    /**
     * 名称检查器实现类，继承了 JDK 1.8 中新提供的 ElementScanner8
     * 将会以 Visitor 模式访问抽象语法树中的元素
     */
    private class NameCheckScanner extends ElementScanner8<Void, Void> {

        /**
         * 此方法用于检查Java类
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        /**
         * 检查方法命名是否合法
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    String msg = String.format("一个普通方法%s不应当与类名重复，避免与够赞函数产生混淆", name);
                    messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量名是否合法
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            //如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null ||
                    heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * 判断一个变量是否是常量
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            } else if (e.getKind() == ElementKind.FIELD &&
                    e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 检查传入的 Element 是否符合驼式命名法，如果不符合，则输出警告信息
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            String msg;
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    msg = String.format("名称%s应当以小写字母开头", name);
                    messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    msg = String.format("名称%s应当以大写字母开头", name);
                    messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
                    return;
                }
            } else {
                conventional = false;
            }

            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            }
            if (!conventional) {
                msg = String.format("名称%s应当符合驼式命名法 (Camel Case Names)", name);
                messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
            }
        }

        /**
         * 大写命名检查，要求第一个字母必须使大写的英文字母，其余部分可以使下划线或大写字母
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }

            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "常量" + name
                        + "应当全部以大写字母或下划线命名，并且以字母开头", e);
            }
        }

    }

}
