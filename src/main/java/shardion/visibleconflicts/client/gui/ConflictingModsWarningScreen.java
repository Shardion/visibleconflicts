package shardion.visibleconflicts.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WarningScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import shardion.visibleconflicts.VisibleConflicts;

public class ConflictingModsWarningScreen extends WarningScreen {
    private final ButtonWidget quitButton = new ButtonWidget(this.width, this.height / 2, 100, 100, Text.literal("q"), button -> { VisibleConflicts.LOGGER.info("Hello, buttons!"); });
    private static final Text WARNING_HEADER;
    private static final Text WARNING_MESSAGE;
    private static final Text CHECKBOX_MESSAGE;
    private static final Text NARRATED_MESSAGE;
    private static final Text OK_BUTTON;
    private static final Text MODS_FOLDER_BUTTON;
    Screen returnToScreen;

    public ConflictingModsWarningScreen(Screen screen) {
        super(WARNING_HEADER, WARNING_MESSAGE, CHECKBOX_MESSAGE, NARRATED_MESSAGE);
        this.returnToScreen = screen;
    }

    @Override
    protected void initButtons(int yOffset) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, 100 + yOffset, 150, 20, MODS_FOLDER_BUTTON, button -> { VisibleConflicts.LOGGER.info("Hello, buttons!"); }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, 100 + yOffset, 80, 20, OK_BUTTON, button -> { VisibleConflicts.LOGGER.info("Hello, buttons!");
            MinecraftClient.getInstance().setScreen(returnToScreen);
        }));
    }
    static {
        WARNING_HEADER = Text.translatable("title.visibleconflicts.gui.conflicting_mods_header");
        WARNING_MESSAGE = Text.translatable("title.visibleconflicts.gui.conflicting_mods_warning");
        CHECKBOX_MESSAGE = Text.translatable("title.visibleconflicts.gui.conflicting_mods_checkbox");
        NARRATED_MESSAGE = Text.translatable("title.visibleconflicts.gui.conflicting_mods_narration");
        OK_BUTTON = Text.translatable("title.visibleconflicts.gui.conflicting_mods_ok_button");
        MODS_FOLDER_BUTTON = Text.translatable("title.visibleconflicts.gui.conflicting_mods_open_mods_folder_button");
    }
}
