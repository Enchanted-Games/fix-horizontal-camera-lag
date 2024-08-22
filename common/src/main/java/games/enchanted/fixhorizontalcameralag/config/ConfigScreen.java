package games.enchanted.fixhorizontalcameralag.config;

import games.enchanted.fixhorizontalcameralag.FixCameraLagMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConfigScreen extends Screen {
    private final Screen parentScreen;
    private final ConfigHandler configHandler;

    private List<ConfigValue<?>> configValues;

    public ConfigScreen(Screen parent, ConfigHandler configHandler) {
        super(Component.translatable("%s.configScreen.title".formatted(FixCameraLagMod.MOD_ID)));
        this.parentScreen = parent;
        this.configHandler = configHandler;
    }

    @Override
    protected void init() {
        ConfigValue<Boolean> isModEnabledConfigValue = new ConfigValue<>(configHandler.isModEnabled);
        configValues = List.of(isModEnabledConfigValue);

        // mod enabled button
        addRenderableWidget(startBooleanValueButton(isModEnabledConfigValue)
            .bounds(width / 2 - 100, height / 2 - 20, 200, 20)
        .build());
//        addRenderableWidget(startBooleanValueButton(isModEnabledConfigValue)
//            .bounds(width / 2 - 100, height / 2 + 6, 200, 20)
//        .build());

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
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredString(font, title, width / 2, 30, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        saveConfigValues();
        assert minecraft != null;
        minecraft.setScreen(parentScreen);
    }

    private void saveConfigValues() {
        for (ConfigValue<?> value : configValues) {
            if (value.hasChanged()) {
                value.updateOption();
            }
        }
        configHandler.save();
    }

    private Button.Builder startBooleanValueButton(ConfigValue<Boolean> value) {
        String configKey = value.getConfigOption().getKey();
        Component buttonContent = Component.translatable(getTranslationKeyForOption(configKey));
        Component buttonTooltipText = Component.translatable(getTranslationKeyForOption(configKey) + ".tooltip");

        return Button.builder(CommonComponents.optionNameValue(buttonContent, trueFalseOptionStatus(value.getValue())),
            button -> {
                boolean newValue = !value.getValue();
                value.setValue(newValue);

                Component buttonValue = trueFalseOptionStatus(newValue);

                button.setMessage(CommonComponents.optionNameValue(buttonContent, buttonValue));
            })
        .tooltip(Tooltip.create(buttonTooltipText));
    }

    public static Component trueFalseOptionStatus(boolean value) {
        return value ? CommonComponents.GUI_YES : CommonComponents.GUI_NO;
    }

    public static String getTranslationKeyForOption(String configKey) {
        return "%s.option.%s".formatted(FixCameraLagMod.MOD_ID, configKey);
    }

    public static Screen createConfigScreen(Screen parentScreen) {
        return new ConfigScreen(parentScreen, ConfigHandler.INSTANCE);
    }
}