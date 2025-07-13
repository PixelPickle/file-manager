package com.squirrelly_app.file_manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

    @Value("${mountPath}")
    private String mountPath;

    public String getMountPath() {
        return mountPath;
    }

}
