// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.HashMap;
import java.io.File;
import java.util.Iterator;
import java.util.Base64;
import java.util.ArrayList;

public class Kraken
{
    private static final String none = "|NONE";
    
    public byte[] run() {
        final ArrayList<String> texpacks = new ArrayList<String>();
        int bytes = 0;
        final long cur = System.currentTimeMillis();
        final String crc = this.crc(texpacks);
        bytes += crc.getBytes().length;
        for (final String tex : texpacks) {
            bytes += tex.getBytes().length;
        }
        if (bytes == crc.getBytes().length) {
            bytes += "|NONE".getBytes().length;
        }
        String tosend = "";
        try {
            tosend += crc;
            boolean nopacks = true;
            for (final String tex2 : texpacks) {
                tosend = tosend + "|" + tex2;
                nopacks = false;
            }
            if (nopacks) {
                tosend += "|NONE";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        final byte[] ts = Base64.getEncoder().encode(tosend.getBytes());
        System.out.println("Galacticraft packet assembled");
        return ts;
    }
    
    public String crc(final ArrayList<String> texpacks) {
        try {
            final ArrayList<File> checks = this.walk(null, ".");
            final Map<String, Integer> map = new HashMap<String, Integer>();
            for (final File eval : checks) {
                if (!eval.getParentFile().getName().equals("lib") && !eval.getParentFile().getName().contains("texturepacks")) {
                    final String xt = eval.getName().toLowerCase();
                    if (xt.endsWith(".jar") || xt.endsWith(".zip")) {
                        final ZipFile zipFile = new ZipFile(eval.getAbsolutePath());
                        final Enumeration<? extends ZipEntry> e = zipFile.entries();
                        while (e.hasMoreElements()) {
                            final ZipEntry entry = (ZipEntry)e.nextElement();
                            final String entryName = entry.getName();
                            final String crc = entry.getSize() + "";
                            if (!map.containsKey(crc)) {
                                map.put(crc, 1);
                            }
                            map.put(crc, map.get(crc) + 1);
                        }
                    }
                    else {
                        if (!xt.equals(".class")) {
                            continue;
                        }
                        if (!map.containsKey(xt)) {
                            map.put(xt, 1);
                        }
                        map.put(xt, map.get(xt) + 1);
                    }
                }
                else {
                    if (!eval.getParentFile().getName().contains("texture")) {
                        continue;
                    }
                    final String xt = eval.getName().toLowerCase();
                    texpacks.add(xt);
                }
            }
            Double result = 0.0;
            for (final String sequence : map.keySet()) {
                final Double frequency = (Double)(map.get(sequence) / map.size());
                result -= frequency * (Math.log(frequency) / Math.log(2.0));
            }
            return result + "";
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return "failed";
        }
    }
    
    public ArrayList<File> walk(ArrayList<File> dirs, final String path) {
        final File root = new File(path);
        if (dirs == null) {
            dirs = new ArrayList<File>();
        }
        if (!root.isDirectory()) {
            final String name = root.getName();
            if (name.endsWith("jar") || name.endsWith("class") || name.endsWith("zip")) {
                dirs.add(root);
            }
        }
        else {
            final File[] listFiles;
            final File[] list = listFiles = root.listFiles();
            for (final File f : listFiles) {
                this.walk(dirs, f.getAbsolutePath());
            }
        }
        return dirs;
    }
}
