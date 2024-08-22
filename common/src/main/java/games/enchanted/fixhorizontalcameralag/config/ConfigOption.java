package games.enchanted.fixhorizontalcameralag.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public abstract class ConfigOption<T> {
    protected final String key;
    protected T value;

    public ConfigOption(String key, T defaultValue) {
        this.key = key;
        value = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public void setVale(T value) {
        this.value = value;
    }

    abstract JsonElement toJson();

    abstract void parseFromJson(JsonElement json) throws JsonParseException;

    public static class BooleanOption extends ConfigOption<Boolean> {
        public BooleanOption(String key, Boolean defaultValue) {
            super(key, defaultValue);
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(getValue());
        }

        @Override
        public void parseFromJson(JsonElement json) throws JsonParseException {
            if (json.isJsonPrimitive()) {
                setVale(json.getAsBoolean());
            } else {
                throw new JsonParseException("json value was not a json primitive");
            }
        }
    }
}