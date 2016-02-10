// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import cpw.mods.fml.common.TickType;
import cofh.skins.CapeHandler;
import java.util.Iterator;
import java.util.Map;
import java.util.EnumSet;
import java.util.ArrayList;
import cpw.mods.fml.common.ITickHandler;

public class ServerHandl implements ITickHandler
{
    long prev;
    private ArrayList texpacks;
    
    public ServerHandl() {
        this.prev = System.currentTimeMillis();
        this.texpacks = new ArrayList();
    }
    
    public void tickStart(final EnumSet type, final Object... tickData) {
        final long cur = System.currentTimeMillis();
        if (cur - this.prev > 30000L) {
            for (final Map.Entry inst : PSH.instance.lastCheck.entrySet()) {
                if (cur - inst.getValue().lastCheck < 30000L) {
                    this.applyPlayer(inst.getKey());
                }
                else {
                    this.removePlayer(inst.getKey());
                }
            }
            this.prev = cur;
        }
    }
    
    public void applyPlayer(final String name) {
        for (final sq ep : PSH.instance.players) {
            if (ep != null && !ep.M && ep.am().toLowerCase().equals(name)) {
                CapeHandler.registerCape(ep.c_(), "https://accretion.us/img/customclientcape.png", false);
                CapeHandler.sendAddPacket(ep.c_());
                break;
            }
        }
    }
    
    public void removePlayer(final String name) {
        for (final sq ep : PSH.instance.players) {
            if (ep != null && ep.am().toLowerCase().equals(name)) {
                CapeHandler.removeCape(ep.c_(), false);
                CapeHandler.sendRemovePacket(ep.c_());
                break;
            }
        }
    }
    
    public void tickEnd(final EnumSet type, final Object... tickData) {
    }
    
    public EnumSet ticks() {
        return EnumSet.of(TickType.SERVER);
    }
    
    public String getLabel() {
        return "PSH-ticker";
    }
}
