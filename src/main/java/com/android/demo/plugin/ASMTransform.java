package com.android.demo.plugin;

import com.android.build.api.transform.*;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.builder.internal.compiler.DirectoryWalker;
import com.android.demo.core.ASMLogHandler;
import com.android.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ASMTransform extends Transform {

    @Override
    public String getName() {
        return "ASMTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) {
        System.out.println("TASK asm-log，开始执行transform 2");
        transformInvocation.getInputs().forEach(transformInput -> {
            transformInput.getJarInputs().forEach(jarInput -> {
                File destJar = transformInvocation.getOutputProvider().getContentLocation(jarInput.getName(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(), Format.JAR);
                try {
                    FileUtils.copyFile(jarInput.getFile(), destJar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            transformInput.getDirectoryInputs().forEach(directoryInput ->
                    processClassDirectory(directoryInput, transformInvocation.getOutputProvider()));
        });
    }

    private void processClassDirectory(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        try {
            DirectoryWalker.builder()
                    .root(directoryInput.getFile().toPath())
                    .action((root, file) -> {
                        if (file != null) {
                            processClassFile(file.toFile());
                        }
                    })
                    .build().walk();
            File destDir = outputProvider.getContentLocation(directoryInput.getName(),
                    directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
            FileUtils.copyDirectory(directoryInput.getFile(), destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processClassFile(File classFile) {
        FileOutputStream fos = null;
        try {
            if (classFile == null
                    || !classFile.exists()
                    || !classFile.getName().endsWith(".class")) {
                return;
            }
            System.out.println("processFile:" + classFile.getName());
            ASMHandler asmHandler =  new ASMLogHandler();
            byte[] classBytes = asmHandler.handle(classFile);
            fos = new FileOutputStream(classFile);
            fos.write(classBytes);
            fos.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
