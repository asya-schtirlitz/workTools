import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class tsvReader{
    public static void main(String args[]) throws Exception{
        BufferedReader tsvFile = new BufferedReader(new FileReader("C:\\\\test\\\\test.txt"));
        String dataRow = tsvFile.readLine();
        String filePath = "c:\\zip\\test.txt";
        String textTemplate = "<xsl:choose>\n" +
                "\t<xsl:when test=\"TODO:FieldName\">\n" +
                "\t\t<xsl:apply-templates select=\"TODO:FieldName\" mode=\"mandatory_text\"/>\n" +
                "\t</xsl:when>\n" +
                "\t<xsl:otherwise>\n" +
                "\t\t<xsl:call-template name=\"default_text\">\n" +
                "\t\t\t<xsl:with-param name=\"text_tag\">FieldName</xsl:with-param>\n" +
                "\t\t</xsl:call-template>\n" +
                "\t</xsl:otherwise>\n" +
                "</xsl:choose>";
        int i = 1;

        while (dataRow != null){
            List<String> list = new ArrayList<String>();
            String[] dataArray = dataRow.split("\t");
            String tag = new String();


            if (i==1){
                tag = dataArray[1];
                Files.write(Paths.get(filePath),createTag(dataArray[1],"OPEN").getBytes(), StandardOpenOption.APPEND);
            }

            if (tag.equals(dataArray[1])){
                i=0;
                Files.write(Paths.get(filePath),createField(dataArray[0],textTemplate).getBytes(), StandardOpenOption.APPEND);
            }
            else{
                i=1;
                Files.write(Paths.get(filePath),createTag(dataArray[1],"CLOSE").getBytes(), StandardOpenOption.APPEND);
            }

            for (String item:dataArray){
                list.add(item);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()){
                String txt = it.next();
                //System.out.print(txt + ", ");
            }
            //System.out.println();
            dataRow = tsvFile.readLine();


        }




        tsvFile.close();
        System.out.println();

    }

    private static String createField(String s, String template){
        String newStr = template.replaceAll("fieldName",s)+"\n";
        return newStr;
    }

    private static String createTag(String s, String whichTag){
        String newStr = new String();
        if (whichTag.equals("OPEN")){
            newStr = "<" + s + ">\n";
        }else if (whichTag.equals("CLOSE")){
            newStr = "</" + s + ">\n";
        }

        return newStr;
    }




}
