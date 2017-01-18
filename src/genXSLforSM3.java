import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class genXSLforSM3 {
    public static void main(String args[]) throws Exception{
        String tag = new String();
        BufferedReader tsvFile = new BufferedReader(new FileReader("c:\\zip\\test.tsv"));
        String dataRow = tsvFile.readLine();
        String filePath = "c:\\zip\\test.txt";
        HashMap<String, List<Field>> fieldsMap = new HashMap<String, List<Field>>();

       // int i = 1;

        while (dataRow != null){
            List<String> list = new ArrayList<String>();
            String[] dataArray = dataRow.split("\t");

            Field element = new Field(dataArray[0], dataArray[7]);


            if (!fieldsMap.containsKey(dataArray[1])){
                List<Field> fieldsList = new ArrayList<Field>();
                fieldsList.add(element);
                fieldsMap.put(dataArray[1],fieldsList);
            } else{
                fieldsMap.get(dataArray[1]).add(element);
            }


            //Field element = new Field(dataArray[0], dataArray[7]);
            String newStr = element.template;
            //System.out.println(element.template.replaceAll("FieldName",element.name ));
            for (String item:dataArray){
                list.add(item);
            }
            //System.out.println("tag = " + tag + " i = " +i);

            Iterator<String> it = list.iterator();
            while (it.hasNext()){
                String txt = it.next();
                //System.out.print(txt + ", ");
            }
            //System.out.println();
            dataRow = tsvFile.readLine();

        }
        Iterator<String> i = fieldsMap.keySet().iterator();

        while (i.hasNext()){
            String stroka = i.next();
            Iterator<Field> it = fieldsMap.get(stroka).iterator();
            System.out.print(createTag(stroka, "OPEN"));
            while (it.hasNext()){
                Field elem = it.next();
                String txt = elem.template;

                System.out.print(txt.replaceAll("FieldName",elem.name));
            }
            System.out.print(createTag(stroka, "CLOSE"));
        }
        //System.out.println(fieldsMap.get("BPERSON"));

        tsvFile.close();
        System.out.println();

    }

    private static String createField(String s, String template){
        String newStr = template.replaceAll("FieldName",s)+"\n";
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

class Field{
    String name;
    String template;
    private final String templateText = "<xsl:choose>\n" +
            "\t<xsl:when test=\"TODO:FieldName\">\n" +
            "\t\t<xsl:apply-templates select=\"TODO:FieldName\" mode=\"mandatory_ReplaceableText\"/>\n" +
            "\t</xsl:when>\n" +
            "\t<xsl:otherwise>\n" +
            "\t\t<xsl:call-template name=\"default_ReplaceableText\">\n" +
            "\t\t\t<xsl:with-param name=\"ReplaceableText_tag\">FieldName</xsl:with-param>\n" +
            "\t\t</xsl:call-template>\n" +
            "\t</xsl:otherwise>\n" +
            "</xsl:choose>\n";

    public Field(String fieldNname, String type){
        this.name = fieldNname;
        this.template = "Error type";
        if (type.equals("String")){ this.template = templateText.replaceAll("ReplaceableText","text");}
        if (type.equals("Date")){ this.template = templateText.replaceAll("ReplaceableText","date");}
        if (type.equals("Numeric")){ this.template = templateText.replaceAll("ReplaceableText","numeric");}
    }

    public String toStirng(){
        return "name: " + name +
                "  template: " + template;
    }
}
