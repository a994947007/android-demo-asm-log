package com.android.demo.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class ASMLogMethodVisitor extends AdviceAdapter {

    private boolean used;

    protected ASMLogMethodVisitor(int api, MethodVisitor methodVisitor, int access,
                                  String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if ("Lcom/android/demo/asm/ASMLog;".equals(descriptor)) {
            used = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    protected void onMethodEnter() {
        if (!used) {
            return;
        }
        System.out.println("onMethodEnter");
/*        loadThis();
        invokeVirtual(Type.getType("Ljava/lang/Object;"), new Method("getClass", "()Ljava/lang/Class;"));
        invokeVirtual(Type.getType("Ljava/lang/Class;"), new Method("getSimpleName", "()Ljava/lang/String;"));
        visitLdcInsn(getName());
        invokeVirtual(Type.getType("Landroid/util/Log;"), new Method("i", "(Ljava/lang/String;Ljava/lang/String;)I"));
        pop();*/
        getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
        invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "()V"));
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (!used) {
            return;
        }
        super.onMethodExit(opcode);
    }
}