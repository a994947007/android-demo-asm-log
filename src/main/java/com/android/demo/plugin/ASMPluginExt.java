package com.android.demo.plugin;

public class ASMPluginExt {
    private String asmPackage;

    public ASMPluginExt() {}

    public ASMPluginExt(String asmPackage) {
        this.asmPackage = asmPackage;
    }

    public String getAsmPackage() {
        return asmPackage;
    }

    public void setAsmPackage(String asmPackage) {
        this.asmPackage = asmPackage;
    }
}
