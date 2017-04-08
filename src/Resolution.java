import java.util.Scanner;
import java.util.Vector;


public class Resolution {
    public static void main(String[] args){
        String filename = Functions.getInputFile(args);
        CNFClause KB = Functions.readFile(filename);
        if(KB == null){
            System.out.printf("the Knowledge Base is empty. Please check the contents of the file: %s", filename);
            return;
        }
        System.out.println("----------Knowledge Base is:----------");
        KB.print();
        System.out.println("--------------------------------------");

        Scanner s = new Scanner(System.in);
        System.out.print("Give the clause you want to prove, the negation of it(format is: a v -b v c v...): \n");
        String in_clause = s.nextLine();
        CNFClause clause = Functions.getInputClauses(in_clause);
        boolean b = true;
        for(CNFSubClause subClause: clause.getSubclauses()){
            //Running resolution
            b = PL_Resolution(KB, subClause);
            if(!b) break;
        }
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.println("The negation of: ");
        clause.print();
        System.out.println("is " + b);
    }

    //The resolution algorithm
    private static boolean PL_Resolution(CNFClause KB, CNFSubClause a){
        //We create a CNFClause that contains all the clauses of our Knowledge Base
        CNFClause clauses = new CNFClause();
        clauses.getSubclauses().addAll(KB.getSubclauses());

        //...plus a clause containing the negation of the literal we want to prove
        clauses.getSubclauses().add(a);

        System.out.print("We want to prove the negation of: ");
        a.print();
        System.out.println();

        boolean stop = false;
        //We will try resolution till either we reach a contradiction or cannot produce any new clauses
        while(!stop){
            Vector<CNFSubClause> newsubclauses = new Vector<>();
            Vector<CNFSubClause> subclauses = clauses.getSubclauses();

            //For every pair of clauses Ci, Cj...
            for(int i = 0; i < subclauses.size(); i++){
                CNFSubClause Ci = subclauses.get(i);

                for(int j = i+1; j < subclauses.size(); j++){
                    CNFSubClause Cj = subclauses.get(j);

                    //...we try to apply resolution and we collect any new clauses
                    Vector<CNFSubClause> new_subclauses_for_ci_cj = CNFSubClause.resolution(Ci, Cj);

                    //We check the new subclauses...
                    for(int k = 0; k < new_subclauses_for_ci_cj.size(); k++){
                        CNFSubClause newsubclause = new_subclauses_for_ci_cj.get(k);

                        //...and if an empty subclause has been generated we have reached contradiction; and the literal has been proved
                        if(newsubclause.isEmpty()){
                            System.out.println("Resolution between");
                            Ci.print();
                            System.out.println("and");
                            Cj.print();
                            System.out.println("\nproduced empty sublause!!!");
                            System.out.println("\n--------------------------------------");
                            return true;
                        }
                        
                        //All clauses produced that don't exist already are added
                        if(!newsubclauses.contains(newsubclause) && !clauses.contains(newsubclause)){
                            newsubclauses.add(newsubclause);
                        }
                    }                           
                }
            }
            
            boolean newClauseFound = false;

                //Check if any new clauses were produced in this loop
                for(int i = 0; i < newsubclauses.size(); i++){
                    if(!clauses.contains(newsubclauses.get(i))){
                        clauses.getSubclauses().addAll(newsubclauses);
                        newClauseFound = true;
                    }
                }

                //If not then Knowledge Base does not logically infer the literal we wanted to prove
                if(!newClauseFound){
                    System.out.println("We did't find any new clauses...");
                stop = true;
            }   
        }
        return false;
    }
}
