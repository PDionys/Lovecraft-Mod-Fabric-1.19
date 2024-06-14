package net.pdeins.lovecraftmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.SanityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null){
            //after death it performs
            this.persistentData = new NbtCompound();
            persistentData.putInt("sanity", SanityData.getMaxSanity());
            persistentData.putBoolean("spawnin_world", false);
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info){
        if(persistentData != null){
            nbt.put("lovecraftmod.denis_data", persistentData);
        }
    }
    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info){
        if(nbt.contains("lovecraftmod.denis_data", 10)){
            persistentData = nbt.getCompound("lovecraftmod.denis_data");
        }
    }
}
