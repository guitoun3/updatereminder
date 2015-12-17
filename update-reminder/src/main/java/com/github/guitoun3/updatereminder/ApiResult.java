package com.github.guitoun3.updatereminder;

public class ApiResult {
    public Content update_reminder;

    public ApiResult() {}

    public class Content {
        public boolean enabled;
        public String versionCode;
        public boolean force_update;
        public String message;

        public Content(){}

        @Override
        public String toString() {
            return "ApiResult{" +
                    "enabled=" + enabled +
                    ", versionCode='" + versionCode + '\'' +
                    ", force_update=" + force_update +
                    ", message=" + message +
                    '}';
        }
    }

    public boolean enabled() {
        return update_reminder != null && update_reminder.enabled;
    }

    public boolean forceUpdate() {
        return update_reminder != null && update_reminder.force_update;
    }

    public Integer getVersionCode() {
        if (update_reminder == null) {
            return null;
        }

        try {
            return Integer.parseInt(update_reminder.versionCode);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getMessage() {
        return update_reminder != null ? update_reminder.message : null;
    }

    @Override
    public String toString() {
        return update_reminder != null ? update_reminder.toString() : "{}";
    }
}
