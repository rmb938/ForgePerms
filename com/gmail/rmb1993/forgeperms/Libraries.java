package com.gmail.rmb1993.forgeperms;

import cpw.mods.fml.relauncher.ILibrarySet;

/**
 *
 * @author Ryan
 */
public class Libraries implements ILibrarySet {

    private static String[] libraries = { "snakeyaml-1.11.jar" };
    private static String[] checksums = { "8f33a218f0bcad15223dd8c3af31f1b65c80c7a7" };

    @Override
    public String[] getLibraries()
    {
        return libraries;
    }

    @Override
    public String[] getHashes()
    {
        return checksums;
    }

    @Override
    public String getRootURL()
    {
        return "http://repo2.maven.org/maven2/org/yaml/snakeyaml/1.11/%s";
    }
}
