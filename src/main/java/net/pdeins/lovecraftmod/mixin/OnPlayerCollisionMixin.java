package net.pdeins.lovecraftmod.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class OnPlayerCollisionMixin {

    @Shadow public abstract ItemStack getStack();

    @Shadow private int pickupDelay;

    @Inject(method = "onPlayerCollision", at = @At("TAIL"))
    protected void injectOnCollisionMethod(PlayerEntity player, CallbackInfo ci){

        if(this.getStack().isIn(ItemTags.FLOWERS) && this.pickupDelay == 0)
            player.sendMessage(this.getStack().getItem().getName());

    }
}
