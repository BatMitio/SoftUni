package com.dkv.springintroex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtils {
    String[] readFileContent(String path) throws IOException;
}
