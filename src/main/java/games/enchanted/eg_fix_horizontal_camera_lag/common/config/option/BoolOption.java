package games.enchanted.eg_fix_horizontal_camera_lag.common.config.option;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class BoolOption extends ConfigOption<Boolean> {
    public BoolOption(Boolean initialValue, Boolean defaultValue, String jsonKey) {
        super(initialValue, defaultValue, jsonKey);
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void fromJson(JsonObject json) {
        Boolean value = json.has(getJsonKey()) ? json.get(getJsonKey()).getAsBoolean() : getDefaultValue();
        this.setValueOrPending(value);
    }
}
