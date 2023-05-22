import java.util.List;

class Pair<X, Y> {
    public X x;
    public Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}

public class KI extends Spieler {

    public Spieler other;
    public static final int MAXDEPTH = 10;

    public KI(int n, String name, Spieler other, boolean maximizer) {
        super(n, name, maximizer);
        this.other = other;
    }

    public Pair<Integer, Integer> AlphaBetaPruningMax(Node node, int alpha, int beta) {
        if (node.isTerminal() || node.depth >= MAXDEPTH) {
            return new Pair<>(node.utility(), -1);
        }
        Pair<Integer, Integer> erg = new Pair<>(Integer.MIN_VALUE, -1);
        List<Node> children = node.expand();
        for (Node child : children) {
            Pair<Integer, Integer> temp = AlphaBetaPruningMin(child, alpha, beta);
            if (temp.x > erg.x) {
                erg.x = temp.x;
                erg.y = child.action;
                alpha = Integer.max(alpha, erg.x);
            }
            if (erg.x >= beta)
                return erg;
        }
        return erg;
    }

    public Pair<Integer, Integer> AlphaBetaPruningMin(Node node, int alpha, int beta) {
        if (node.isTerminal() || node.depth >= MAXDEPTH) {
            return new Pair<>(node.utility(), -1);
        }
        Pair<Integer, Integer> erg = new Pair<>(Integer.MAX_VALUE, -1);
        List<Node> children = node.expand();
        for (Node child : children) {
            Pair<Integer, Integer> temp = AlphaBetaPruningMax(child, alpha, beta);
            if (temp.x < erg.x) {
                erg.x = temp.x;
                erg.y = child.action;
                beta = Integer.min(beta, erg.x);
            }
            if (erg.x <= alpha)
                return erg;
        }
        return erg;
    }

    public int AlphaBetaPruning(Spielfeld sp) {
        Node start = new Node(sp.field, this, other, 0);
        Pair<Integer, Integer> action;
        if (maximizer) {
            action = AlphaBetaPruningMax(start, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            action = AlphaBetaPruningMin(start, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        return action.y;
    }

    public int Expectimax(Spielfeld sp) {
        // Expectimax User is always the maximizer. The other Players nodes use the expected value (with equal distribution)
        Node start = new Node(sp.field, this, other, 0);
        Pair<Integer, Integer> action = ExpectimaxMax(start);
        return action.y;
    }

    public Pair<Integer, Integer> ExpectimaxMax(Node node) {
        if (node.isTerminal() || node.depth >= MAXDEPTH-2) {
            return new Pair<>(node.utility(), -1);
        }
        Pair<Integer, Integer> erg = new Pair<>(Integer.MIN_VALUE, -1);
        List<Node> children = node.expand();
        for (Node child : children) {
            int temp = ExpectimaxExpected(child);
            if (temp > erg.x) {
                erg.x = temp;
                erg.y = child.action;
            }
        }
        return erg;
    }

    public int ExpectimaxExpected(Node node) {
        if (node.isTerminal() || node.depth >= MAXDEPTH-2) {
            return node.utility();
        }
        int erg = 0;
        List<Node> children = node.expand();
        for (Node child : children) {
            Pair<Integer, Integer> temp = ExpectimaxMax(child);
            erg += temp.x;
        }
        return erg / children.size();
    }


    @Override
    public void setzteSpielstein(Spielfeld sp) {
        int action = AlphaBetaPruning(sp);
        sp.setzeStein(action, getNummer());
    }
}
