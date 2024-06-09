package net.pdeins.lovecraftmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class ModItems {

    public static final Item LOVECRAFT_MOD_SYMBOL = registerItem("lovecraft_mod_symbol",
            new Item(new FabricItemSettings().maxCount(1)));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(LovecraftMod.MOD_ID, name), item);
    }

    public static void registerModItem(){
        LovecraftMod.LOGGER.debug("Registering Mod Item for " + LovecraftMod.MOD_ID);
    }
}
