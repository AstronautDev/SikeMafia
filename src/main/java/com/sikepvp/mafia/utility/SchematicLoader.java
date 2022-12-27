package com.sikepvp.mafia.utility;

import com.sikepvp.mafia.Mafia;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;

public class SchematicLoader {

    ClipboardReader clipboardReader;
    ClipboardFormat clipboardFormat;
    Clipboard clipboard;
    String completionMessage;

    Mafia plugin;
    public SchematicLoader(Mafia plugin) {
        this.plugin = plugin;
    }

    String schematicName;
    public SchematicLoader(Mafia plugin, String schematicName) {
        this.schematicName = schematicName;
        this.plugin = plugin;
        loadSchematic();
    }

    public void loadSchematic() {
        try {
            File schematicFile = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics/" + schematicName + ".schem");
            clipboardFormat = ClipboardFormats.findByFile(schematicFile);
            clipboardReader = clipboardFormat.getReader(new FileInputStream(schematicFile));
            clipboard = clipboardReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pasteSchematic(Location playerLoc) {
        double xPos = playerLoc.getX();
        double yPos = playerLoc.getY();
        double zPos = playerLoc.getZ();

        World adaptedWorld = BukkitAdapter.adapt(playerLoc.getWorld());
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(xPos, yPos, zPos)).ignoreAirBlocks(true).build();
        try {
            Operations.complete(operation);
            editSession.flushSession();
        } catch(WorldEditException e) {
            completionMessage = ChatColor.RED + "Something went wrong! Contact an administrator.";
            e.printStackTrace();
        }
    }

    public void sendCompletionMessage(Player p) {
        p.sendMessage(completionMessage);
    }

}
