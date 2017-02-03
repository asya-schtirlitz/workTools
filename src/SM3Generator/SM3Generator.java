package SM3Generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static SM3Generator.Constants.*;


public class SM3Generator {
    public static void main(String args[]) {

        for(Stages stage:Stages.values()) {

            BufferedReader tsvFile = null; //TODO: Откуда берем поля "c:\\zip\\test.tsv")); //
            try {
                tsvFile = new BufferedReader(new FileReader(SM3DD_TABLE));

                String dataRow = tsvFile.readLine();
                String targetFile = FILE_PATH + stage.getName() + FILE_EXTENSION; //"c:\\zip\\bres_pov_out.txt";//TODO: Куда мы пишем поля "c:\\zip\\test.txt"; //
                String checkFile = FILE_PATH + stage.getName() + "fromXSL" + FILE_EXTENSION;
                Map<String, List<Field>> fieldsMap = new HashMap<String, List<Field>>();

                while (dataRow != null) {
                    String[] dataArray = dataRow.split("\t", -1);
                    Field element = new Field("", "", "", false);
                    boolean isArray = false;

                    //является ли тип тега массивом
                    if (!dataArray[13].equals("0")) {
                        isArray = true;
                    }

                    if (!isContainField(dataArray[0], checkFile)) { //TODO: Содержится ли поле уже в файле преобразования
                        if (isContainField(dataArray[0], INOUT_FIELDS)) {
                            element = new Field(dataArray[0], dataArray[10], NAMESPACE_IO, isArray);
                        } else {
                            element = new Field(dataArray[0], dataArray[10], NAMESPACE_AF, isArray);
                        }

                    } else {
                        System.out.println(dataArray[0]);
                        dataRow = tsvFile.readLine();
                        continue;
                    }
                    //Заполняем map
                    fieldsMap = createMap(element, dataArray[stage.getColumn()], stage);
                    dataRow = tsvFile.readLine();
                }

                writeToFile(fieldsMap, targetFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(tsvFile!=null){tsvFile.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    private static String createTag(String s, String whichTag){
        String newStr = new String();
        if (whichTag.equals(OPEN_TAG)){
            newStr = "<" + s + ">\n";
        }else if (whichTag.equals(CLOSE_TAG)){
            newStr = "</" + s + ">\n";
        }

        return newStr;
    }

    private static boolean isContainField(String field, String path) throws IOException {
        BufferedReader tsvFile = new BufferedReader(new FileReader(path));
        String dataRow = tsvFile.readLine();
        while (dataRow != null) {
            if (field.equals(dataRow)){
                return true;
            }
            dataRow = tsvFile.readLine();
        }
        return false;
    }

    public static void writeToFile(Map<String, List<Field>> fieldsMap, String filePath) throws IOException {
        for(Map.Entry<String, List<Field>> entry: fieldsMap.entrySet()){
            String element = entry.getKey();
            Files.write(Paths.get(filePath),createTag(element, OPEN_TAG).getBytes(), StandardOpenOption.APPEND);
            List<Field> fields = entry.getValue();
            for(Field field : fields){
                Files.write(Paths.get(filePath),field.template.replaceAll(REPLACEABLE_NAME,field.name).getBytes(), StandardOpenOption.APPEND);
            }
            Files.write(Paths.get(filePath),createTag(element, CLOSE_TAG).getBytes(), StandardOpenOption.APPEND);
        }
    }

    public static Map<String, List<Field>> createMap(Field element, String column, Stages stage){
        Map<String, List<Field>> fieldsMap = new HashMap<String, List<Field>>();
        if (!column.equals("")){ //TODO: в какое преобразование пишем
            if (!fieldsMap.containsKey(stage.getName())){
                List<Field> fieldsList = new ArrayList<Field>();
                fieldsList.add(element);
                fieldsMap.put(stage.getName(),fieldsList);
            } else{
                fieldsMap.get(stage.getName()).add(element);
            }
        }
        return fieldsMap;
    }


}

