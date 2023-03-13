import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MysqlConvertH2Test {
    @Test
    public void h2(){
        try {
            System.out.println(convert("C:\\tmp\\tables_xxl_job.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static String convert(String filePath) throws IOException {
        String[] rawSQL = new String(Files.readAllBytes(Paths.get(filePath))).split("\\n");
        StringBuilder builder = new StringBuilder();

        for(String line : rawSQL) {
            if(line.contains("CHARACTER SET utf8 COLLATE utf8_general_ci")) {
                line = line.replaceAll("CHARACTER SET utf8 COLLATE utf8_general_ci", "");
            } else if(line.contains("INDEX")) {
                continue;
            } else if(line.contains("IF NOT EXISTS")) {
                line = line.replaceAll("IF NOT EXISTS", "");
            } else if(line.contains("--")) {
                continue;
            } else if(line.contains("ENGINE")) {
                line = line.replaceAll("\\).*ENGINE.*(?=)", ");");
            }else if (line.contains("USING BTREE")){
                line = line.replaceAll("USING BTREE*","");
            }else if (line.contains("int")){
                line = line.replaceAll("int\\(-?[1-9]\\d*\\)","int");
            }else if (line.contains("datetime")){
                line = line.replaceAll("\\sdatetime\\s"," TIMESTAMP ");
            }else if (line.contains("mediumtext")){
                line = line.replaceAll("mediumtext","Blob");
            }else if (line.contains("text")){
                line = line.replaceAll("text","Blob");
            }else if (line.contains("KEY") && !line.contains("PRIMARY")){
                line = line.replaceAll("KEY","UNIQUE");
            }
            if (line.contains("UNIQUE KEY")){
                line = line.replaceAll("UNIQUE KEY","UNIQUE");
            }

            line = line.replace("`", "");
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}
