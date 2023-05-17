import java.util.LinkedList;
import java.util.List;

public class Node {

    public int[][] field;
    public Spieler s;
    public Spieler other;
    public int alpha;
    public int beta;
    public int heuristic;
    public int action = -1;


    public int heuristic()
    {
        /*
        +
        Maximierer
        3er mit zwei freien feldern (links und rechts und nur bei horizontal) * 70%
        3er mit einem freien feld(links oder rechts bei vertikal, mind ein freies feld oberhalb bei vertikal, mind. ein freies feld für diagoale in beide richtungen) * 20%
        2er mit zwei freien feldern(2*links oder links+rechts oder 2*rechts bei horizontal, mind. zwei freie felder oberhalb bei vertikal, mind. zwei freie feld für diagoale in beide richtungen) * 10%

        -
        Minimierer
        3er mit zwei freien feldern (links und rechts und nur bei horizontal) * 70%
        3er mit einem freien feld(links oder rechts bei vertikal, mind ein freies feld oberhalb bei vertikal, mind. ein freies feld für diagoale in beide richtungen) * 20%
        2er mit zwei freien feldern(2*links oder links+rechts oder 2*rechts bei horizontal, mind. zwei freie felder oberhalb bei vertikal, mind. zwei freie feld für diagoale in beide richtungen) * 10%
        */
        return 0;
    }
    public Node(int[][] field, Spieler s, Spieler other)
    {
        this.s=s;
        this.other=other;
        this.field = new int[field.length][field[0].length];
        for(int i = 0; i<field.length; i++)
        {
            for(int j = 0; j<field[i].length; j++)
            {
                this.field[i][j]=field[i][j];
            }
        }
    }

    public void setzeStein(int spalte)
    {
        action=spalte;
        for(int i = field.length-1; i>=0; i--)
        {
            if(field[i][spalte]==0) {
                field[i][spalte] = s.getNummer();
                return;
            }
        }
    }
    public List<Node> expand()
    {
        List<Node> erg = new LinkedList<>();
        for(int i = 0; i < field[0].length; i++)
        {
            if(field[0][i] == 0)
            {
                Node temp = new Node(field, s, other);
                temp.setzeStein(i);
                temp.s = other;
                temp.other=s;
            }
        }
        return erg;
    }



}
