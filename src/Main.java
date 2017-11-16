/**
 * Created by Jeyanthasingam on 11/12/2017.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        System.out.print("Enter package:");
        Scanner scan = new Scanner(System.in);
        String packageName = scan.nextLine();
        BufferedReader br = new BufferedReader(new FileReader("polixia_db_v6.0.1.sql"));
        String everything;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        String[] tables = everything.split("CREATE TABLE");
        for(int i=1;i<tables.length;i++){
            StringBuilder mainModel = new StringBuilder();
            StringBuilder idModel = new StringBuilder();
            mainModel.append("package ");
            mainModel.append(packageName);
            idModel.append("package ");
            idModel.append(packageName);
            mainModel.append(";\n" +
                    "\n" +
                    "import lombok.Data;\n" +
                    "\n" +
                    "import javax.persistence.*;\n" +
                    "import java.util.Set;\n" +
                    "\n" +
                    "@Entity\n" +
                    "@Table(name = \"");
            idModel.append(";\n" +
                    "\n" +
                    "import lombok.Data;\n" +
                    "\n" +
                    "import javax.persistence.Column;\n" +
                    "import javax.persistence.Embeddable;\n" +
                    "import javax.persistence.Id;\n" +
                    "import java.io.Serializable;\n" +
                    "\n" +
                    "@Data\n" +
                    "@Embeddable\n" +
                    "public class ");
            String[] tabl_con = tables[i].split(" \\(");
            String[] tableName = tabl_con[0].split("`");
            mainModel.append(tableName[1]);
            mainModel.append("\")\n");
            String className;
            if(tableName[1].substring(0,2).equals("sv")){
                className=tableName[1].substring(3);
            }
            else className=tableName[1].substring(1);
            className = (className.charAt(className.length()-1)=='s')?className.substring(0,className.length()-1):className;
            mainModel.append(
                    "public class ");
            mainModel.append(className);
            idModel.append(className);
            idModel.append("Id implements Serializable {\n" +
                    "\n" +
                    "    ");
            mainModel.append(" {\n" +
                    "\n" +
                    "    @EmbeddedId\n" +
                    "    ");
            mainModel.append(className+"Id ");
            mainModel.append(className.substring(0,1).toLowerCase()+className.substring(1)+"Id");
            mainModel.append(";\n" +
                    "    ");

            String[] primary=tabl_con[2].split("\\)")[0].split("`");

            List<String> prime=new ArrayList<>();
            for(int j=1;j<primary.length;j+=2){
                prime.add(primary[j]);
            }


            String[] data =tabl_con[1].split("`");
            for(int a=1;a+1<data.length;a+=2){
                if(prime.contains(data[a])){
//                    mainModel.append("@Id\n" +
//                            "    ");
                    idModel.append("@Column(name = \"");
                    idModel.append(data[a]);
                    idModel.append("\")\n" +
                            "    private ");


                }
//                mainModel.append("@Column(name = \"");
//                mainModel.append(data[a]);
//                mainModel.append("\")\n" +
//                        "    private ");

                if(data[a+1].contains("char")){
//                    mainModel.append("String ");
                    if(prime.contains(data[a])) {
                        idModel.append("String ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("String ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }
                else if(data[a+1].contains("tinyint")){
                    if(prime.contains(data[a])) {
                        idModel.append("Boolean ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("Boolean ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }
                else if(data[a+1].contains("blob")){

                    if(prime.contains(data[a])) {
                        idModel.append("String ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("String ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }
                else if(data[a+1].contains("int")){
                    if(prime.contains(data[a])) {
                        idModel.append("Integer ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("Integer ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }
                else if(data[a+1].contains("timestamp")){
                    if(prime.contains(data[a])) {
                        idModel.append("Date ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("Date ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }
                else if(data[a+1].contains("double")){

                    if(prime.contains(data[a])) {
                        idModel.append("Double ");
                        idModel.append(data[a].substring(0, 1).toLowerCase() + data[a].substring(1));
                        idModel.append(";\n" +
                                "    ");
                    }else {
                        mainModel.append("@Column(name = \"");
                        mainModel.append(data[a]);
                        mainModel.append("\")\n" +
                                "    private ");
                        mainModel.append("Double ");
                        mainModel.append(data[a].substring(0,1).toLowerCase()+data[a].substring(1));
                        mainModel.append(";\n" +
                                "    ");
                    }
                }

                else System.out.println(data[a+1]);




            }

            idModel.append("\n" +
                    "\n" +
                    "}\n");

            mainModel.append("\n" +
                    "\n" +
                    "}\n");




            BufferedWriter bw = null;
            try {

                //Specify the file name and path here
                File file = new File("models/"+className+".java");

	 /* This logic will make sure that the file
	  * gets created if it is not present at the
	  * specified location*/
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
                bw.write(mainModel.toString());
                System.out.println("File written Successfully");

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally
            {
                try{
                    if(bw!=null)
                        bw.close();
                }catch(Exception ex){
                    System.out.println("Error in closing the BufferedWriter"+ex);
                }
            }




            try {

                //Specify the file name and path here
                File file = new File("models/"+className+"Id.java");

	 /* This logic will make sure that the file
	  * gets created if it is not present at the
	  * specified location*/
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
                bw.write(idModel.toString());
                System.out.println("File written Successfully");

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally
            {
                try{
                    if(bw!=null)
                        bw.close();
                }catch(Exception ex){
                    System.out.println("Error in closing the BufferedWriter"+ex);
                }
            }
        }
    }
}
