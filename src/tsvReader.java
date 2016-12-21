import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class tsvReader{
    public static void main(String args[]) throws Exception{
        BufferedReader tsvFile = new BufferedReader(new FileReader("C:\\test\\test.txt"));
        String dataRow = tsvFile.readLine();

        while (dataRow != null){
            List<String> list = new ArrayList<String>();
            String[] dataArray = dataRow.split("\t");
            for (String item:dataArray){
                list.add(item);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()){
                String txt = it.next();
                System.out.print(txt + ", ");
            }
            System.out.println();
            dataRow = tsvFile.readLine();
        }
        tsvFile.close();
        System.out.println();
    }
}
