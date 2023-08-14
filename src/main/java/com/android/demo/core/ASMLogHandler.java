package com.android.demo.core;

import com.android.demo.plugin.ASMHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ASMLogHandler implements ASMHandler {

    @Override
    public byte[] handle(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ClassReader cr = new ClassReader(fis);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            cr.accept(new ASMLogClassVisitor(Opcodes.ASM7, cw), ClassReader.EXPAND_FRAMES);
            return cw.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
