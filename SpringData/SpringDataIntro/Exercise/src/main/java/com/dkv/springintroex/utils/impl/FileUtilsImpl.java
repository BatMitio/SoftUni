package com.dkv.springintroex.utils.impl;

import com.dkv.springintroex.models.Author;
import com.dkv.springintroex.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class FileUtilsImpl implements FileUtils {
    @Override
    public String[] readFileContent(String path) throws IOException {
        Set<String> result = new LinkedHashSet<>();
        BufferedReader content = new BufferedReader(new FileReader(path));
        String line;
        while((line = content.readLine()) != null) {
            line = content.readLine();
            if(line == null) break;
            if(!line.equals("")) result.add(line);
        }
        return result.toArray(String[]::new);
    }
}
