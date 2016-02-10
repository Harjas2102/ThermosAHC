// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.List;

public class CheckRay implements z
{
    private static final String name = "xraycheck";
    private final List aliases;
    private final hz ds;
    private static final String bad = "§c";
    private static final String good = "§a";
    private static final String sector = "§";
    
    public CheckRay() {
        this.aliases = new ArrayList();
        this.ds = (hz)MinecraftServer.D();
        this.aliases.add("xcheck");
        this.aliases.add("xchk");
        this.aliases.add("xrc");
    }
    
    public String c() {
        return "xraycheck";
    }
    
    private boolean isOp(final ab icommandsender) {
        final String name = icommandsender.c_().toLowerCase();
        return this.ds.ad().i().contains(name) || name.contains("console") || name.contains("rcon");
    }
    
    public String a(final ab icommandsender) {
        if (this.isOp(icommandsender)) {
            return "xraycheck <user>";
        }
        return "stop - Halt the server runtime JVM";
    }
    
    public List b() {
        return this.aliases;
    }
    
    public void b(final ab icommandsender, final String[] astring) {
        if (this.isOp(icommandsender)) {
            if (astring.length == 0) {
                icommandsender.a("§cInvalid arguments -- see usage");
            }
            else if (astring.length == 2 && astring[0].toLowerCase().equals("view")) {
                final PlayerInfo pi = PSH.instance.lastCheck.get(astring[1].toLowerCase());
                if (pi != null) {
                    icommandsender.a("[X-Ray info for: §9" + astring[1] + "§" + "f ]");
                    if (pi.isvalid) {
                        icommandsender.a("§a+ Valid Entropy of: " + pi.crc);
                    }
                    else {
                        icommandsender.a("§c- BAD Entropy of: " + pi.crc);
                    }
                    if (pi.texturePacks.equals("|NONE")) {
                        icommandsender.a("+ Texture Packs: §a" + pi.texturePacks);
                    }
                    else {
                        icommandsender.a("+ Texture Packs: §c" + pi.texturePacks);
                    }
                }
                else {
                    icommandsender.a("§cNo player by the name of §9" + astring[1] + "§" + "ffound using the client ");
                }
            }
            else if (astring.length == 1 && astring[0].toLowerCase().equals("list")) {
                icommandsender.a("[X-Ray Overview ( " + PSH.instance.lastCheck.size() + " players)]");
                for (final Map.Entry ent : PSH.instance.lastCheck.entrySet()) {
                    final String tex = ent.getValue().texturePacks.replace("\\|NONE", "");
                    if (System.currentTimeMillis() - ent.getValue().lastCheck > 30000L) {
                        if (ent.getValue().isvalid) {
                            icommandsender.a("+ " + ent.getKey() + "§" + "7 = old & valid " + tex);
                        }
                        else {
                            icommandsender.a("+ " + ent.getKey() + "§" + "6 = old & invalid " + tex);
                        }
                    }
                    else if (ent.getValue().isvalid) {
                        icommandsender.a("+ " + ent.getKey() + "§a" + " = valid " + tex);
                    }
                    else {
                        icommandsender.a("- " + ent.getKey() + "§c" + " = invalid " + tex);
                    }
                }
            }
            else if (astring.length == 1 && astring[0].toLowerCase().equals("help")) {
                icommandsender.a("§a[X-Ray Help]");
                icommandsender.a("Aliases: /xrc, /xcheck, xchk");
                icommandsender.a("+ /xrc help : Show this info");
                icommandsender.a("+ /xrc list : View all custom client users");
                icommandsender.a("+ /xrc view PlayerFullName : View detailed info for that player");
            }
            else {
                icommandsender.a("§cInvalid usage, please ask Fracica for proper usage :(");
            }
        }
        else {
            icommandsender.a("§4You are not authorized to use this command.");
        }
    }
    
    public boolean b(final ab icommandsender) {
        return this.isOp(icommandsender);
    }
    
    public List a(final ab icommandsender, final String[] astring) {
        return null;
    }
    
    public boolean a(final String[] astring, final int i) {
        return astring.length > 0 && astring[0].toLowerCase().equals("view") && i == 1;
    }
    
    public int compareTo(final Object o) {
        return 0;
    }
}
