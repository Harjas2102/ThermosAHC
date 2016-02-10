// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

public class PlayerInfo
{
    public long lastCheck;
    public String texturePacks;
    public String crc;
    public boolean isvalid;
    
    public PlayerInfo(final long last, final String tex, final String crc, final boolean isvalid) {
        this.lastCheck = last;
        this.crc = crc;
        this.texturePacks = tex;
        this.isvalid = isvalid;
    }
}
