import java.util.*;

/*
 * A CNFSubClause in turn consists of a disjunction of literals
 */
public class CNFSubClause implements Comparable<CNFSubClause>{
    //The literals contained in the clause
    private TreeSet<Literal> literalsSet;
            
    public CNFSubClause(){
        literalsSet = new TreeSet<>();
    }
         
    public TreeSet<Literal> getLiterals(){
        return literalsSet;
    }
    
    public Iterator<Literal> getLiteralsList(){
        return literalsSet.iterator();
    }
         
    public boolean isEmpty(){
        return literalsSet.isEmpty();
    }
    
    public void print(){
        Iterator<Literal> iter = this.getLiteralsList();
        while(iter.hasNext()){
            Literal l = iter.next();
            l.print();
            System.out.print(iter.hasNext()?"or ":"");
        }
    }

    /* Applies resolution on two CNFSubClauses
     * The resulting clause will contain all the literals of both CNFSubclauses
     * except the pair of literals that are a negation of each other.
     */
    public static Vector<CNFSubClause> resolution(CNFSubClause CNF_SC_1, CNFSubClause CNF_SC_2){
        Vector<CNFSubClause> newClauses = new Vector<CNFSubClause>();

        Iterator<Literal> iter = CNF_SC_1.getLiteralsList();

        //The iterator goes through all Literals of the first clause
        while(iter.hasNext()){
            Literal l = iter.next();
            Literal m = new Literal(l.getName(), !l.getNeg());

            //If the second clause contains the negation of a Literal in the first clause
            if(CNF_SC_2.getLiterals().contains(m)){
                //We construct a new clause that contains all the literals of both CNFSubclauses...
                CNFSubClause newClause = new CNFSubClause();

                //...except the pair the literals that were a negation of one another
                TreeSet<Literal> CNF_SC_1_Lits = new TreeSet(CNF_SC_1.getLiterals());
                TreeSet<Literal> CNF_SC_2_Lits = new TreeSet(CNF_SC_2.getLiterals());
                CNF_SC_1_Lits.remove(l);
                CNF_SC_2_Lits.remove(m);

                //Normally we have to remove duplicates of the same literal; the new clause must not contain the same literal more than once
                //But since we use HashSet only one copy of a literal will be contained anyway

                newClause.getLiterals().addAll(CNF_SC_1_Lits);
                newClause.getLiterals().addAll(CNF_SC_2_Lits);

                newClauses.add(newClause);
            }
        }//The loop runs for all literals, producing a different new clause for each different pair of literals that negate each other
        
        return newClauses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CNFSubClause subClause = (CNFSubClause) o;

        return literalsSet.equals(subClause.literalsSet);
    }

    @Override
    public int hashCode() {
        return literalsSet.hashCode();
    }
	
    //@Override
    public int compareTo(CNFSubClause x){
        int cmp = 0;
        
        Iterator<Literal> iter = x.getLiteralsList();
        
        while(iter.hasNext()){
            Literal lit = iter.next();

            for(Literal lit2 : this.getLiterals()){
                cmp = cmp + lit.compareTo(lit2);
            }
        }
        
        return cmp;
    }
}
