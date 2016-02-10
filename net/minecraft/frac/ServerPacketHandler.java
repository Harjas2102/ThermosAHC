// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.frac;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Base64;
import java.util.Scanner;
import java.net.URL;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.IPacketHandler;

public class ServerPacketHandler implements IPacketHandler
{
    public void onPacketData(final cg manager, final dk packet, final Player player) {
        if (packet.a.equals("PSH")) {
            this.handle(packet, player);
        }
    }
    
    private void handle(final dk packet, final Player player) {
        final DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.c));
        try {
            final Scanner decoders = new Scanner(new URL("https://accretion.us/acplus/validcrcs.txt").openStream(), "UTF-8").useDelimiter("\\A");
            String allowed = "";
            while (decoders.hasNextLine()) {
                allowed += decoders.nextLine();
            }
            decoders.close();
            byte[] str = new byte[inputStream.available()];
            inputStream.readFully(str);
            str = Base64.getDecoder().decode(str);
            final String[] reach = new String(str, "UTF-8").split("\\|");
            final sq ep = (sq)player;
            if (allowed.contains(reach[0])) {
                final PlayerInfo pi = new PlayerInfo(System.currentTimeMillis(), new String(str, "UTF-8").substring(reach[0].length()), reach[0], true);
                if (!PSH.instance.lastCheck.containsKey(ep.am().toLowerCase())) {
                    ep.a("Thank you for using Lectro! If you encounter any bugs or crashes, please notify a staff member.");
                }
                PSH.instance.lastCheck.put(ep.am().toLowerCase(), pi);
                PSH.instance.players.add(ep);
                this.putPlay(pi, ep, true);
            }
            else {
                System.out.println("WARNING Bad CRC for " + ep.am());
                final PlayerInfo pi = new PlayerInfo(System.currentTimeMillis(), new String(str, "UTF-8").substring(reach[0].length()), reach[0], false);
                PSH.instance.lastCheck.put(ep.am().toLowerCase(), pi);
                PSH.instance.players.add(ep);
                this.putPlay(pi, ep, false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("[PSH] Error handling packet for " + ((sq)player).c_() + "!");
        }
    }
    
    public void putPlay(final PlayerInfo pi, final sq ep, final boolean valid) throws Exception {
        final File f = new File("/var/www/html/acplus/logs/" + ep.c_().toLowerCase());
        if (!f.exists()) {
            f.createNewFile();
        }
        final Writer w = new BufferedWriter(new FileWriter(f, true));
        w.append((CharSequence)(System.currentTimeMillis() + "," + ep.c_().toLowerCase() + "," + pi.crc + "," + pi.texturePacks + "," + valid + "\n"));
        w.close();
    }
}
