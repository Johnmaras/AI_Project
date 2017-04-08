import java.io.*;
import java.util.Scanner;

public class Functions {

    public static String getInputFile(String[] args){
        Scanner s = new Scanner(System.in);
        String filename;
        if(args.length == 0){
            System.out.print("Give the file path of the Knowledge Base: \n");
            filename = s.nextLine();
            while(!Functions.checkFile(filename)){
                System.out.print("File Error! Please check the file and enter the path again: \n");
                filename = s.nextLine();
            }
        }else{
            filename = args[0];
        }
        return filename;
    }

    public static CNFClause getInputClauses(String in_clause){
        //CNFSubClause clause = new CNFSubClause();
        CNFClause clause = new CNFClause();
        String[] subclauses = in_clause.trim().split("\\^");
        for(String subclause: subclauses){
            CNFSubClause sub = new CNFSubClause();
            String[] lits = subclause.trim().split("v");
            for(String lit: lits){
                sub.getLiterals().add(getInputLiteral(lit));
            }
            clause.getSubclauses().add(sub);
        }
        return clause;
    }

    public static Literal getInputLiteral(String in_literal){
        boolean negation = in_literal.trim().startsWith("-");
        return new Literal((negation ? in_literal.trim().substring(1).trim() : in_literal.trim()).toLowerCase(), negation);
    }


    public static CNFClause readFile(String filename){
        CNFClause KB = new CNFClause();

        try{
            String line;
            File f;
            BufferedReader reader;
            f = new File(filename);
            reader = new BufferedReader(new FileReader(f));
            line = reader.readLine();
            while(line != null){
                //CNFSubClause newSub;
                while(line != null && !isEmpty(line)){
                    //newSub = getInputClause(line);
                    KB.getSubclauses().addAll(getInputClauses(line).getSubclauses());
                    line = reader.readLine();
                }
                line = reader.readLine();
            }
            return KB;
        }catch(NullPointerException e){
            System.err.println("File not found");
        }catch(FileNotFoundException e){
            System.err.println("Error opening file");
        }catch (IOException e){
            System.out.println("Line : Sudden end.");
        }

        return null;
    }

    public static boolean checkFile(String filename){
        try{
            File f = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            reader.readLine();
            return true;
        }catch(NullPointerException e){
            System.err.println("File not found");
        }catch(FileNotFoundException e){
            System.err.println("Error opening file");
        }catch (IOException e){
            System.out.println("Sudden end.");
        }

        return false;
    }

    private static boolean isEmpty(String line){
        return line.equals("") || line.trim().startsWith("#");
    }
}
