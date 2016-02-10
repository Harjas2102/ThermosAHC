// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import java.util.HashMap;
import java.util.HashSet;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.Mod;

@Mod(modid = "PSH", name = "PSH", version = "0.0.0")
@NetworkMod(clientSideRequired = false, serverSideRequired = false, channels = { "PSH" }, packetHandler = ServerPacketHandler.class)
public class PSH
{
    @Mod.Instance("PSH")
    public static PSH instance;
    public HashSet players;
    public HashMap lastCheck;
    
    public PSH() {
        this.players = new HashSet();
        this.lastCheck = new HashMap();
    }
    
    @ForgeSubscribe
    public void onEntityUpdate(final LivingDeathEvent event) {
        if (event.entityLiving instanceof sq && this.players.contains(event.entityLiving) && event.entityLiving.M) {
            final sj entityzombie = new sj(event.entityLiving.q);
            entityzombie.b((double)event.entityLiving.cx, (double)event.entityLiving.cy, (double)event.entityLiving.cz, 0.0f, 0.0f);
            entityzombie.bJ();
            entityzombie.i(true);
            event.entityLiving.q.d((mp)entityzombie);
        }
    }
    
    @Mod.PreInit
    public void preInit(final FMLPreInitializationEvent event) {
    }
    
    @Mod.ServerStarting
    public void serverLoad(final FMLServerStartingEvent event) {
        System.out.println("[PSH] Registered /xrc command!");
        event.registerServerCommand((z)new CheckRay());
        System.out.println("[PSH] Registered /lectro ommand!");
        event.registerServerCommand((z)new Lectro());
    }
    
    @SideOnly(Side.SERVER)
    @Mod.Init
    public void load(final FMLInitializationEvent event) {
        TickRegistry.registerTickHandler((ITickHandler)new ServerHandl(), Side.SERVER);
    }
    
    @Mod.PostInit
    public void postInit(final FMLPostInitializationEvent event) {
    }
}
