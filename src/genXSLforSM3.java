import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class genXSLforSM3 {
    public static void main(String args[]) throws Exception{
        String tag = new String();
        BufferedReader tsvFile = new BufferedReader(new FileReader("c:\\zip\\test.tsv"));
        String dataRow = tsvFile.readLine();
        String filePath = "c:\\zip\\test.txt";

        int i = 1;

        while (dataRow != null){
            List<String> list = new ArrayList<String>();
            String[] dataArray = dataRow.split("\t");

            Field element = new Field(dataArray[0], dataArray[7]);
            System.out.println(element.template);
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
            "</xsl:choose>";

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
