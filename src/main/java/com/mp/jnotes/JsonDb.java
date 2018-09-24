package com.mp.jnotes;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonDb {

    public void create(String inputFile, List<Nota> list) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        String ext = FilenameUtils.getExtension(inputFile);
        if (ext.isEmpty()) {
            inputFile += ".json";
        }
        FileUtils.writeStringToFile(new File(inputFile), json, StandardCharsets.UTF_8);
    }
}
