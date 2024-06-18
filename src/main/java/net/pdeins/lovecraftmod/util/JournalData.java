package net.pdeins.lovecraftmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pdeins.lovecraftmod.networking.ModPackets;

import java.util.Arrays;
import java.util.HashMap;


public class JournalData {
    private static final Journal[] journalPages = {
            new Journal(ProgressionData.getSpawninWorld(), "note.lovecraftmod.firstspawn_p1"),
            new Journal(ProgressionData.getSpawninWorld(), "note.lovecraftmod.firstspawn_p2")
    };

    public static void setJournalList(IEntityDataSaver player, String progress){
        //get player data
        NbtCompound nbt = player.getPersistentData();
        NbtList nbtList = nbt.getList("journalList",8);
        //load list of needed strings
        addToNbtList(progress, nbtList);
        nbt.put("journalList", nbtList);
        //sync data
        syncJournal((ServerPlayerEntity) player, nbt);
    }

    private static void addToNbtList(String progress, NbtList nbtList) {
        for(Journal j : journalPages){
            if(j.getProgress().equals(progress)){
                nbtList.add(NbtString.of(j.getJournalLine()));
            }
        }
    }

    public static void syncJournal(ServerPlayerEntity player, NbtCompound nbt) {
        PacketByteBuf buf = PacketByteBufs.create();
        try {
            buf.writeNbt(nbt);
            ServerPlayNetworking.send(player, ModPackets.JOURNAL_SYNC_ID, buf);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HashMap<Integer,String> getJournalMap(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        HashMap<Integer, String> journalList = new HashMap<>();
        NbtList nbtList = nbt.getList("journalList", 8);
        for(int i = 0; i < nbtList.size(); i++){
            journalList.put(i, nbtList.getString(i));
        }

        return journalList;
    }

    public static void saveJournalAfterDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer){
        NbtCompound oldNbt = ((IEntityDataSaver) oldPlayer).getPersistentData();
        NbtCompound newNbt = ((IEntityDataSaver) newPlayer).getPersistentData();

        newNbt.put("journalList", oldNbt.getList("journalList", 8));
    }

}
