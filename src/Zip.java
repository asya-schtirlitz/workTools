import java.io.*;
import java.util.zip.*;

public class Zip {
    static final int BUFFER = 2048;
    public static void main (String argv[]) {
        try {
            File zipfile = new File("c:\\zip\\myfigs.zip");

            BufferedInputStream origin = null;
            FileOutputStream dest = new
                    FileOutputStream("c:\\zip\\myfigs.zip");
            ZipOutputStream out = new ZipOutputStream(new
                    BufferedOutputStream(dest));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // get a list of files from current directory
            File f = new File("c:\\tempIDEA\\.");
            String files[] = f.list();

            for (int i=0; i<files.length; i++) {
                System.out.println("Adding: "+files[i]);
                System.out.println("Number of files: "+files.length);
                FileInputStream fi = new FileInputStream("c:\\tempIDEA\\" + files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(files[i]);
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0,
                        BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
