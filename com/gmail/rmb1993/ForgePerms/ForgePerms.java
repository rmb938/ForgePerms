package com.gmail.rmb1993.ForgePerms;

import cpw.mods.fml.common.CertificateHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

@NetworkMod(clientSideRequired = false, serverSideRequired = false)
@Mod(modid = "ForgePerms", name = "Forge Permissions", version = "0.1")
public class ForgePerms {

    @Instance(value = "ForgePerms")
    public static ForgePerms instance;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Forge Perms Loaded");
        File mcDir = computeExistingClientHome();
        File libDir = null;
        try {
            libDir = setupLibDir(mcDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        File libFile = new File(libDir, "common-dbutils-1.5.jar");
        
        System.out.println("File: "+libFile.getPath());
        
        URLClassLoader ucl = (URLClassLoader) getClass().getClassLoader();
        RelaunchClassLoader actualClassLoader = new RelaunchClassLoader(ucl.getURLs());
        try {
            actualClassLoader.addURL(libFile.toURI().toURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        try {
            actualClassLoader.findClass("org.apache.commons.dbutils.QueryRunner");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void downloadFile(File libFile, String rootUrl, String realFilePath, String hash) {
        try {
            URL libDownload = new URL(String.format(rootUrl, realFilePath));
            FMLRelaunchLog.info("Downloading file %s", libDownload.toString());
            URLConnection connection = libDownload.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "FML Relaunch Downloader");
            int sizeGuess = connection.getContentLength();
            performDownload(connection.getInputStream(), sizeGuess, hash, libFile);
            FMLRelaunchLog.info("Download complete");
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            FMLRelaunchLog.severe("There was a problem downloading the file %s automatically. Perhaps you "
                    + "have an environment without internet access. You will need to download "
                    + "the file manually or restart and let it try again\n", libFile.getName());
            libFile.delete();
            throw new RuntimeException("A download error occured", e);
        }
    }

    private static ByteBuffer downloadBuffer = ByteBuffer.allocateDirect(1 << 22);
    
    private static void performDownload(InputStream is, int sizeGuess, String validationHash, File target) {
        if (sizeGuess > downloadBuffer.capacity()) {
            throw new RuntimeException(String.format("The file %s is too large to be downloaded by FML - the coremod is invalid", target.getName()));
        }
        downloadBuffer.clear();

        int bytesRead, fullLength = 0;
        try {
            byte[] smallBuffer = new byte[1024];
            while ((bytesRead = is.read(smallBuffer)) >= 0) {
                downloadBuffer.put(smallBuffer, 0, bytesRead);
                fullLength += bytesRead;
            }
            is.close();
            downloadBuffer.limit(fullLength);
            downloadBuffer.position(0);
        } catch (InterruptedIOException e) {
            // We were interrupted by the stop button. We're stopping now.. clear interruption flag.
            Thread.interrupted();
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            String cksum = generateChecksum(downloadBuffer);
            if (cksum.equals(validationHash)) {
                downloadBuffer.position(0);
                FileOutputStream fos = new FileOutputStream(target);
                fos.getChannel().write(downloadBuffer);
                fos.close();
            } else {
                throw new RuntimeException(String.format("The downloaded file %s has an invalid checksum %s (expecting %s). The download did not succeed correctly and the file has been deleted. Please try launching again.", target.getName(), cksum, validationHash));
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }
    
    private static String generateChecksum(ByteBuffer buffer) {
        return CertificateHelper.getFingerprint(buffer);
    }

    /**
     * @return
     */
    private File computeExistingClientHome() {
        Class<? super Object> mcMaster = ReflectionHelper.getClass(getClass().getClassLoader(), "net.minecraft.client.Minecraft");
        // If we get the system property we inject into the old MC, setup the
        // dir, then pull the value
        String str = System.getProperty("minecraft.applet.TargetDirectory");
        if (str != null) {
            str = str.replace('/', File.separatorChar);
            ReflectionHelper.setPrivateValue(mcMaster, null, new File(str), "minecraftDir", "an", "minecraftDir");
        }
        // We force minecraft to setup it's homedir very early on so we can
        // inject stuff into it
        Method setupHome = ReflectionHelper.findMethod(mcMaster, null, new String[]{"getMinecraftDir", "getMinecraftDir", "b"});
        try {
            setupHome.invoke(null);
        } catch (Exception e) {
            // Hmmm
        }
        File minecraftHome = ReflectionHelper.getPrivateValue(mcMaster, null, "minecraftDir", "an", "minecraftDir");
        return minecraftHome;
    }

    /**
     * @param mcDir
     * @return
     */
    private static File setupLibDir(File mcDir) {
        File libDir = new File(mcDir, "lib");
        try {
            libDir = libDir.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to canonicalize the lib dir at %s", mcDir.getName()), e);
        }
        if (!libDir.exists()) {
            libDir.mkdir();
        } else if (libDir.exists() && !libDir.isDirectory()) {
            throw new RuntimeException(String.format("Found a lib file in %s that's not a directory", mcDir.getName()));
        }
        return libDir;
    }

    @Init
    public void load(FMLInitializationEvent e) {
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent e) {
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent e) {
    }

    @ServerStopping
    public void serverStopping(FMLServerStoppingEvent e) {
    }
}