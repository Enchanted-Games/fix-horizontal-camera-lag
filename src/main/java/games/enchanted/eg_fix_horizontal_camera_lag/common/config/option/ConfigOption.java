package games.enchanted.eg_fix_horizontal_camera_lag.common.config.option;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigOption<T> {
    private @Nullable T pendingValue;
    protected T value;
    private final T defaultValue;

    private final String jsonKey;

    ConfigOption(T initialValue, T defaultValue, String jsonKey) {
        this.value = initialValue;
        this.defaultValue = defaultValue;
        this.jsonKey = jsonKey;
    }

    public String getJsonKey() {
        return this.jsonKey;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public @Nullable T getPendingValue() {
        return this.pendingValue;
    }

    public T getPendingOrCurrentValue() {
        return this.pendingValue == null ? this.value : this.pendingValue;
    }

    public void setPendingValue(@NotNull T value) {
        this.pendingValue = value;
    }

    protected void setValueOrPending(T value) {
        if(isDirty()) {
            this.setPendingValue(value);
        } else {
            this.value = value;
        }
    }

    public void clearPendingValue() {
        this.pendingValue = null;
    }

    public void applyPendingValue() {
        if(this.pendingValue == null) return;
        this.value = this.pendingValue;
        this.pendingValue = null;
    }

    /**
     * Resets value to default
     *
     * @param force if true, clears pending value and set current value to default. If false, sets pending value
     *              to default if one is already present, otherwise set current value to default
     */
    public void resetToDefault(boolean force) {
        if(force) {
            this.clearPendingValue();
            this.value = this.getDefaultValue();
        } else {
            this.setValueOrPending(getDefaultValue());
        }
    }

    public abstract JsonElement toJson();

    public abstract void fromJson(JsonObject json);

    public boolean isDirty() {
        return this.pendingValue != null;
    }
}
