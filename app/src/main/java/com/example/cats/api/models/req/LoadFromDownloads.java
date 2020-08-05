package com.example.cats.api.models.req;

import java.io.File;

public class LoadFromDownloads {
    File file;
    String sub_id;

    public LoadFromDownloads(File file, String sub_id) {
        this.file = file;
        this.sub_id = sub_id;
    }

}
