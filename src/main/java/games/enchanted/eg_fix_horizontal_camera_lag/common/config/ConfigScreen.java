package games.enchanted.eg_fix_horizontal_camera_lag.common.config;

import games.enchanted.eg_fix_horizontal_camera_lag.common.ModConstants;
import games.enchanted.eg_fix_horizontal_camera_lag.common.config.option.ConfigOption;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConfigScreen extends Screen {
    private final Screen parentScreen;

    ConfigScreen(Screen parent) {
        super(Component.translatable("%s.configScreen.title".formatted(ModConstants.MOD_ID)));
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        // mod enabled button
        addRenderableWidget(startBooleanValueButton(ConfigOptions.MOD_ENABLED_OPTION)
            .bounds(width / 2 - 100, height / 2 - 20, 200, 20)
        .build());

        // done and cancel buttons
        addRenderableWidget(
            Button.builder(
                CommonComponents.GUI_DONE,
                button -> onClose()
            )
            .bounds(width / 2 - 75, height - 40, 150, 20)
        .build());
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        context.textRenderer().accept(TextAlignment.CENTER, width / 2, 30, this.title);
    }

    @Override
    public void onClose() {
        saveConfigOptions();
        minecraft.setScreen(parentScreen);
    }

    private void saveConfigOptions() {
        ConfigOptions.saveIfAnyDirtyOptions();
    }

    private Button.Builder startBooleanValueButton(ConfigOption<Boolean> value) {
        String configKey = value.getJsonKey();
        Component buttonContent = Component.translatable(getTranslationKeyForOption(configKey));
        Component buttonTooltipText = Component.translatable(getTranslationKeyForOption(configKey) + ".tooltip");

        return Button.builder(CommonComponents.optionNameValue(buttonContent, trueFalseOptionStatus(value.getPendingOrCurrentValue())),
            button -> {
                boolean newValue = !value.getPendingOrCurrentValue();
                value.setPendingValue(newValue);

                Component buttonValue = trueFalseOptionStatus(newValue);

                button.setMessage(CommonComponents.optionNameValue(buttonContent, buttonValue));
            })
        .tooltip(Tooltip.create(buttonTooltipText));
    }

    public static Component trueFalseOptionStatus(boolean value) {
        return value ? CommonComponents.GUI_YES : CommonComponents.GUI_NO;
    }

    public static String getTranslationKeyForOption(String configKey) {
        return "%s.option.%s".formatted(ModConstants.MOD_ID, configKey);
    }

    public static Screen createConfigScreen(Screen parentScreen) {
        return new ConfigScreen(parentScreen);
    }
}