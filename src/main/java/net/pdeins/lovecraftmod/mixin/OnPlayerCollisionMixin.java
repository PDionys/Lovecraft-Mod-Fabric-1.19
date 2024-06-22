package net.pdeins.lovecraftmod.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.ProgressRecord;
import net.pdeins.lovecraftmod.util.ProgressionData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class OnPlayerCollisionMixin {

    @Shadow public abstract ItemStack getStack();

    @Shadow private int pickupDelay;

    @Inject(method = "onPlayerCollision", at = @At("TAIL"))
    protected void injectOnCollisionMethod(PlayerEntity player, CallbackInfo ci){

        if(this.getStack().isIn(ItemTags.FLOWERS) && this.pickupDelay == 0){
            if(!ProgressionData.getProgression(((IEntityDataSaver) player), ProgressionData.getProgressIdElement(1))){
                ProgressRecord.changeProgressAndJournal(((ServerPlayerEntity) player), player.getServer(),
                        ProgressionData.getProgressIdElement(1));
                ProgressRecord.playSoundAndAnimation(((ServerPlayerEntity) player));
            }
        }
    }
}
