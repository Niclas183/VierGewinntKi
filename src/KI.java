import java.util.List;
class Pair<X, Y> {
    public X x;
    public Y y;
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
public class KI extends Spieler{

    public Spieler other;
    public static final int MAXDEPTH = 10;
    public KI(int n, String name, Spieler other, boolean maximizer) {
        super(n, name, maximizer);
        this.other = other;
    }

    public Pair<Integer, Integer> AlphaBetaPruningMax(Node node, int alpha, int beta)
    {
        if(node.isTerminal() || node.depth>=MAXDEPTH)
        {
            return new Pair<>(node.utility(), -1);
        }
        Pair<Integer, Integer> erg = new Pair<>(Integer.MIN_VALUE, -1);
        List<Node> children = node.expand();
        for(Node child: children)
        {
            Pair<Integer, Integer> temp = AlphaBetaPruningMin(child, alpha, beta);
            if(temp.x > erg.x)
            {
                erg.x = temp.x;
                erg.y = child.action;
                alpha = Integer.max(alpha, erg.x);
            }
            if(erg.x>=beta)
                return erg;
        }
        return erg;
    }

    public Pair<Integer, Integer> AlphaBetaPruningMin(Node node, int alpha, int beta)
    {
        if(node.isTerminal())
        {
            return new Pair<>(node.utility(), -1);
        }
        Pair<Integer, Integer> erg = new Pair<>(Integer.MAX_VALUE, -1);
        List<Node> children = node.expand();
        for(Node child: children)
        {
            Pair<Integer, Integer> temp =  AlphaBetaPruningMax(child, alpha, beta);
            if(temp.x < erg.x)
            {
                erg.x = temp.x;
                erg.y = child.action;
                beta = Integer.min(beta, erg.x);
            }
            if(erg.x<=alpha)
                return erg;
        }
        return erg;
    }

    public int AlphaBetaPruning(Spielfeld sp)
    {
        Node start = new Node(sp.field, this, other, 0);
        Pair<Integer, Integer> action;
        if(maximizer)
        {
            action = AlphaBetaPruningMax(start, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        else {
            action = AlphaBetaPruningMin(start, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        System.out.println(action.x);
        return action.y;
    }

    @Override
    public void setzteSpielstein(Spielfeld sp) {
        int action = AlphaBetaPruning(sp);
        sp.setzeStein(action,getNummer());
    }
}
