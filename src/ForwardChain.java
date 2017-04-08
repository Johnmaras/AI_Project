import java.util.*;
import Queue.*;

public class ForwardChain {
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
        System.out.print("Give the literal you want to prove(format: with negation -a, w/o negation a):\n");
        String in_literal = s.nextLine();
        Literal q = Functions.getInputLiteral(in_literal);
        System.out.println("--------------------------------------");
        System.out.println();

        boolean b = PL_FC(KB, q);
        q.print();
        System.out.println("is " + b);
    }

    private static boolean PL_FC(CNFClause KB, Literal q){
        HashSet<Literal> agenda = new HashSet<>();
        Hashtable<CNFSubClause, Integer> counter = new Hashtable<>();
        ListQueue<Literal> metopo;

        for(CNFSubClause sc: KB.getSubclauses()){
            Literal inf = sc.getLiterals().last();
            if(!inf.getNeg()){
                if(sc.getLiterals().size() == 1){
                    agenda.add(inf);
                }else{
                    counter.put(sc, sc.getLiterals().size() - 1);
                }
            }
        }
        metopo = new ListQueue<>(agenda);
        Literal a;
        Literal aCopy;
        while(!metopo.isEmpty()){
            a = metopo.get();
            if(a.equals(q)) return true;
            aCopy = new Literal(a.getName(), !a.getNeg());
            ArrayList<CNFSubClause> toRemoveC = new ArrayList<>();
            Iterator<CNFSubClause> iterCounter = counter.keySet().iterator();
            while(iterCounter.hasNext()){
                CNFSubClause infsub = iterCounter.next();
                if(infsub.getLiterals().contains(aCopy)){
                    counter.put(infsub, counter.get(infsub) - 1);
                }
                if(counter.get(infsub) == 0){
                    if(infsub.getLiterals().last().equals(q)) return true;
                    if(counter.keySet().contains(infsub)) metopo.push(infsub.getLiterals().last());
                    for(CNFSubClause tempsc: counter.keySet()){
                        if(tempsc.getLiterals().last().equals(infsub.getLiterals().last())){
                            toRemoveC.add(tempsc);
                        }
                    }
                }
            }
            for(CNFSubClause sc: toRemoveC){
                counter.remove(sc);
            }
            toRemoveC.clear();
        }
        return false;
    }
}