package com.gmail.rmb1993.forgeperms;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import java.util.Map;

public class ForgePermsPlugin implements IFMLLoadingPlugin, IFMLCallHook {

    @Override
    public String[] getLibraryRequestClass() {
        return new String[] {"com.gmail.rmb1993.forgeperms.Libraries"};
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return "com.gmail.rmb1993.forgeperms.ForgePermsContainer";
    }

    @Override
    public String getSetupClass() {
        return "com.gmail.rmb1993.forgeperms.ForgePermsPlugin";
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public Void call() throws Exception {
        return null;
    }
}