/*
 * Represents a literal; a variable
 */
public class Literal implements Comparable<Literal>{
    //The name of the literal
    private String Name;
    //Whether or not the literal is negated; if negation is true then it is negated
    private boolean negation;

    public Literal(){}

    public Literal(String n, boolean neg){
        this.Name = n;
        this.negation = neg;
    }

    public Literal(Literal literal){
        this.Name = literal.getName();
        this.negation = literal.getNeg();
    }
    
    public void print(){
        if(negation)
            System.out.print("not_" + Name + " ");
        else
            System.out.print(Name + " ");
    }
        
    public void setName(String n)
    {
        this.Name = n;
    }
    
    public String getName()
    {
        return this.Name;
    }
    
    public void setNeg(boolean b)
    {
        this.negation = b;
    }
    
    public boolean getNeg()
    {
        return this.negation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (negation != literal.negation) return false;
        return Name.equals(literal.Name);
    }

    @Override
    public int hashCode() {
        int result = Name.hashCode();
        result = 31 * result + (negation ? 1 : 0);
        return result;
    }

    //@Override
    public int compareTo(Literal x){
            int a = 0;
            int b = 0;
            
            if(!x.getNeg())
                a = 10000;
            
            if(!this.getNeg())
                b = 10000;
            
            return Name.compareTo(x.getName()) + b - a;
    }    
}
