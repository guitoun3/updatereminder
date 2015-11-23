package com.github.guitoun3.updatereminder;

public class ApiResult {

    public boolean enabled;
    public String version;
    public boolean force_update;

    public ApiResult() {}

    @Override
    public String toString() {
        return "ApiResult{" +
                "enabled=" + enabled +
                ", version='" + version + '\'' +
                ", force_update=" + force_update +
                '}';
    }
}
