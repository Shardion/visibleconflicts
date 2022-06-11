package shardion.visibleconflicts.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WarningScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import shardion.visibleconflicts.VisibleConflicts;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiConsumer;

public class ConflictingModsWarningScreen extends WarningScreen {
    private final ButtonWidget quitButton = new ButtonWidget(this.width, this.height / 2, 100, 100, Text.literal("q"), button -> { VisibleConflicts.LOGGER.info("Hello, buttons!"); });
    private static final Text WARNING_HEADER;
    private static final Text WARNING_MESSAGE;
    private static final Text CHECKBOX_MESSAGE;
    private static final Text NARRATED_MESSAGE;
    private static final Text OK_BUTTON;
    private static final Text MODS_FOLDER_BUTTON;
    private String modConflictsListText;
    private MultilineText modConflictsList;
    Screen returnToScreen;

    public ConflictingModsWarningScreen(Screen screen) {
        super(WARNING_HEADER, WARNING_MESSAGE, CHECKBOX_MESSAGE, NARRATED_MESSAGE);
        this.returnToScreen = screen;
    }

    @Override
    protected void init() {
        super.init();
        String modConflictsListText = "";
        for (Map.Entry<String, ArrayList<String>> entry : VisibleConflicts.badMods.entrySet()) {
            String mod = entry.getKey();
            ArrayList<String> conflicts = entry.getValue();
            modConflictsListText = modConflictsListText.concat(mod).concat(" conflicts with:\n");
            for (String conflict : conflicts) {
                modConflictsListText = modConflictsListText.concat("    ").concat(conflict).concat("\n");
            }
        }
        this.modConflictsListText = modConflictsListText;
        this.modConflictsList = MultilineText.create(this.textRenderer, Text.of(this.modConflictsListText), this.width - 100);
        VisibleConflicts.LOGGER.info(modConflictsListText);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        int conflictsListWidth = this.width / 2 - this.modConflictsList.getMaxWidth() / 2;
        //this.modConflictsList.drawWithShadow(matrices, conflictsListWidth, 80, this.getLineHeight(), 16777215);
    }

    @Override
    protected void initButtons(int yOffset) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 100, this.height - (yOffset * 2), 100, 20, MODS_FOLDER_BUTTON, button -> { VisibleConflicts.LOGGER.info("Hello, buttons!"); }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, this.height - (yOffset * 2), 100, 20, OK_BUTTON, button -> { VisibleConflicts.LOGGER.info("Hello, buttons!");
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
