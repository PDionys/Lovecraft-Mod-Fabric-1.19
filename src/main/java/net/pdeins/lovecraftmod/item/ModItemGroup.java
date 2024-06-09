package net.pdeins.lovecraftmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class ModItemGroup {
    public static final ItemGroup LOVECRAFT = FabricItemGroup.builder(new Identifier(LovecraftMod.MOD_ID, "lovecraft_group"))
            .icon(() -> new ItemStack(ModItems.LOVECRAFT_MOD_SYMBOL))
            .entries(((displayContext, entries) -> {
                entries.add(ModItems.LOVECRAFT_MOD_SYMBOL);
            }))
            .build();

    public static void registerModItemGroup(){
        LovecraftMod.LOGGER.debug("Registering Mod Item Group for " + LovecraftMod.MOD_ID);
    }
}
