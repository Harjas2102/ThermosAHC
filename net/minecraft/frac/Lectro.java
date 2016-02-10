// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import java.util.Iterator;
import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lectro implements z
{
    private static final String name = "lectro";
    private final List aliases;
    private final hz ds;
    private static final String bad = "§c";
    private static final String good = "§a";
    private static final String sector = "§";
    public HashMap lastCmd;
    private static final int cooldown = 300;
    
    public Lectro() {
        this.aliases = new ArrayList();
        this.ds = (hz)MinecraftServer.D();
        this.lastCmd = new HashMap();
        this.aliases.add("l");
        this.aliases.add("lec");
    }
    
    public String c() {
        return "lectro";
    }
    
    private boolean isLectro(final ab icommandsender) {
        final String name = icommandsender.c_().toLowerCase();
        final PlayerInfo pi = PSH.instance.lastCheck.get(name);
        return pi != null && System.currentTimeMillis() - pi.lastCheck <= 30000L;
    }
    
    public String a(final ab icommandsender) {
        if (this.isLectro(icommandsender)) {
            return "/lectro";
        }
        return "/lectro -- Get the Lectro pack to use this!";
    }
    
    public List b() {
        return this.aliases;
    }
    
    public void b(final ab icommandsender, final String[] astring) {
        if (this.isLectro(icommandsender)) {
            sq ep = null;
            final String name = icommandsender.c_().toLowerCase();
            for (final sq e : PSH.instance.players) {
                if (e.c_().toLowerCase().equals(name)) {
                    ep = e;
                }
            }
            if (ep == null) {
                icommandsender.a("§c[Lectro] There was an error using that command, please try again in a minute. If it still doesn't work, contact Fracica.");
            }
            if (astring.length == 0 || astring.length > 1) {
                icommandsender.a("§cSee /l help for proper usage");
            }
            else if (astring[0].toLowerCase().equals("help")) {
                icommandsender.a("§a[Lectro Help]");
                icommandsender.a("Aliases: " + this.aliases.toString());
                icommandsender.a("+ /l help : Show this info");
                icommandsender.a("+ /l fire : Fire resistance for 30 seconds");
                icommandsender.a("+ /l regen: Health regeneration for 20 seconds");
            }
            else if (astring[0].toLowerCase().equals("fire")) {
                if (this.canUse(name)) {
                    icommandsender.a("§a[Lectro] Fire Resistance activated!");
                    ep.d(new ml(mk.n.H, 600, 0, false));
                }
                else {
                    this.cantUse(icommandsender);
                }
            }
            else if (astring[0].toLowerCase().equals("regen")) {
                if (this.canUse(name)) {
                    icommandsender.a("§a[Lectro] Health Regen activated!");
                    ep.d(new ml(mk.l.H, 400, 1, false));
                }
                else {
                    this.cantUse(icommandsender);
                }
            }
            else {
                icommandsender.a("§cInvalid usage, please do /l help for proper usage :(");
            }
        }
        else {
            icommandsender.a("§4Please install the Lectro pack to use this command!");
        }
    }
    
    public void cantUse(final ab s) {
        final long secs = 300L - (System.currentTimeMillis() - this.lastCmd.get(s.c_().toLowerCase())) / 1000L;
        s.a("§c[Lectro] Please wait " + secs + " seconds before using that command");
    }
    
    public boolean canUse(final String name) {
        if (!this.lastCmd.containsKey(name)) {
            this.lastCmd.put(name, System.currentTimeMillis());
            return true;
        }
        if (System.currentTimeMillis() - this.lastCmd.get(name) > 300000L) {
            this.lastCmd.put(name, System.currentTimeMillis());
            return true;
        }
        return false;
    }
    
    public boolean b(final ab icommandsender) {
        return this.isLectro(icommandsender);
    }
    
    public List a(final ab icommandsender, final String[] astring) {
        return null;
    }
    
    public boolean a(final String[] astring, final int i) {
        return false;
    }
    
    public int compareTo(final Object o) {
        return 0;
    }
}
