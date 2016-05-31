package util;

public class Triplet<X,Y,Z> {
    private X first;
    private Y second;
    private Z third;

    public Triplet(X first, Y second, Z third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public X getFirst(){return first;}
    public Y getSecond(){return second;}
    public Z getThird(){return third;}
}
