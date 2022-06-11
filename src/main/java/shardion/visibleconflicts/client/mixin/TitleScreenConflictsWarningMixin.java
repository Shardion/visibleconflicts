package shardion.visibleconflicts.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shardion.visibleconflicts.VisibleConflicts;
import shardion.visibleconflicts.client.gui.ConflictingModsWarningScreen;

@Mixin(TitleScreen.class)
public class TitleScreenConflictsWarningMixin {
    private static boolean modConflictsWarningScreenShown = false;
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        if (!modConflictsWarningScreenShown) {
            MinecraftClient.getInstance().setScreen(new ConflictingModsWarningScreen(MinecraftClient.getInstance().currentScreen));
            modConflictsWarningScreenShown = true;
        }
    }
}
