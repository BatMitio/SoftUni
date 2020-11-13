package course.springdata.springdataintro.utils.impl;

import course.springdata.springdataintro.utils.FileUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public String[] readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Set<String> fileContent = new LinkedHashSet<>();
        while(true) {
            String line = bufferedReader.readLine();
            if(line == null) break;
            if(!"".equals(line)) fileContent.add(line);
        }
        return fileContent.toArray(String[]::new);
    }
}