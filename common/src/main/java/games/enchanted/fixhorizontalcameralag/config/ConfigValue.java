package games.enchanted.fixhorizontalcameralag.config;

public class ConfigValue<T> {
    private final ConfigOption<T> configOption;
    private final T originalValue;
    private T value;

    public ConfigValue(ConfigOption<T> option) {
        this.configOption = option;
        originalValue = this.configOption.getValue();
        value = originalValue;
    }

    public ConfigOption<T> getConfigOption() {
        return configOption;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean hasChanged() {
        return !value.equals(originalValue);
    }

    public void updateOption() {
        configOption.setVale(value);
    }
}
