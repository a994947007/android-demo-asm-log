package com.android.demo.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ASMPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("TASK ASM Log");
        final ASMPluginExt asmPluginExt = project.getExtensions().create("asmPlugin", ASMPluginExt.class);
/*        project.afterEvaluate(pro -> {
            System.out.println("asmPlugin配置读取完毕");
            AppExtension appExtension = pro.getExtensions().getByType(AppExtension.class);
            System.out.println("asm-log插桩包：" + asmPluginExt.getAsmPackage());
            appExtension.registerTransform(new ASMTransform(asmPluginExt.getAsmPackage()));
        });*/
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        System.out.println("asm-log插桩包：" + asmPluginExt.getAsmPackage());
        appExtension.registerTransform(new ASMTransform());
    }
}