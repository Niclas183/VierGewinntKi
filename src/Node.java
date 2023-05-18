import java.util.LinkedList;
import java.util.List;
public class Node {

    public int[][] field;
    public Spieler s;
    public Spieler other;
    public int action = -1;
    public int numberCoins;
    public int depth;
    public boolean draw = false;
    private final int weight1 = 7;
    private final int weight2 = 2;
    private final int weight3 = 1;

    public boolean isTerminal()
    {
        boolean won = true;
        // Nur für other überprüfen, da der Spieler aus der Ebene vorher der gespielt hat in diesem Knoten other ist.
        // Spalte
        for(int i = 0; i<field[0].length; i++)
        {
            for(int j = field.length-1; j>=3; j--)
            {
                won = true;
                for(int k = j; k>j-4; k--) {
                    if (field[k][i] != other.getNummer()) {
                        won = false;
                        break;
                    }
                }
                if(won)
                    return true;
                else
                    won = true;
            }
        }
        //Zeile
        for(int i = 0; i<field.length; i++)
        {
            for(int j = 0; j<field[i].length-3; j++)
            {
                won = true;
                for(int k = j; k<j+4; k++) {
                    if (field[i][k] != other.getNummer()) {
                        won = false;
                        break;
                    }
                }
                if(won)
                    return true;
                else
                    won = true;
            }
        }
        //Diagonale
        for(int i = 0; i<field[0].length; i++)
        {
            for(int j = field.length-1; j>=3; j--)
            {
                won = true;
                //Diagonale unten nach oben rechts
                if(i<field[0].length-3) {
                    for (int k = 0; k<4; k++) {
                        if (field[j-k][i+k] != other.getNummer()) {
                            won = false;
                            break;
                        }
                    }
                    if (won)
                        return true;
                    else
                        won = true;
                }
                //Diagonale unten nach oben links
                if(i-3>=0) {
                    for (int k = 0; k<4; k++) {
                        if (field[j-k][i-k] != other.getNummer()) {
                            won = false;
                            break;
                        }
                    }
                    if (won)
                        return true;
                    else
                        won = true;
                }
            }
        }
        if(numberCoins == field.length * field[0].length) {
            draw = true;
            return true;
        }
        return false;
    }

    public int utility()
    {
        if(draw)
            return 0;
        if(depth>=KI.MAXDEPTH)
        {
            Spieler maximizer = other;
            Spieler minimizer = s;
            if(s.maximizer)
            {
                maximizer = s;
                minimizer = other;
            }
            int erg = 0;
            //Bewertung für Maximizer
            int emptybefore;
            int count;
            int emptyafter;
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                emptyafter = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==0 && count==0)
                        emptybefore++;
                    if(field[i][j]==0 && count!=0)
                    {
                        if(emptybefore>=1 && count==3)
                        {
                            erg+=weight1;
                            count=0;
                            emptybefore=1;
                        }
                    }
                    if(field[i][j]==maximizer.getNummer()) {
                        count++;
                    }
                }
            }

            //Bewertung für Minimizer
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                emptyafter = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==0 && count==0)
                        emptybefore++;
                    if(field[i][j]==0 && count!=0)
                    {
                        if(emptybefore>=1 && count==3)
                        {
                            erg-=weight1;
                            count=0;
                            emptybefore=1;
                        }
                    }
                    if(field[i][j]==minimizer.getNummer()) {
                        count++;
                    }
                }
            }
            if(Math.abs(erg)>0)
                System.out.println(erg);
            return erg;
        }
        if(other.maximizer)
            return 100;
        else
            return -100;

        /*
        +
        Maximierer
        3er mit zwei freien feldern (links und rechts und nur bei horizontal) * 70%
        3er mit einem freien feld(links oder rechts oder in der mitte bei horizontal, mind ein freies feld oberhalb bei vertikal, mind. ein freies feld für diagoale in beide richtungen) * 20%
        2er mit zwei freien feldern(2*links oder links+rechts oder 2*rechts bei horizontal, mind. zwei freie felder oberhalb bei vertikal, mind. zwei freie feld für diagoale in beide richtungen) * 10%

        -
        Minimierer
        3er mit zwei freien feldern (links und rechts und nur bei horizontal) * 70%
        3er mit einem freien feld(links oder rechts bei horizontal, mind ein freies feld oberhalb bei vertikal, mind. ein freies feld für diagoale in beide richtungen) * 20%
        2er mit zwei freien feldern(2*links oder links+rechts oder 2*rechts bei horizontal, mind. zwei freie felder oberhalb bei vertikal, mind. zwei freie feld für diagoale in beide richtungen) * 10%
        */
    }
    public Node(int[][] field, Spieler s, Spieler other, int depth)
    {
        this.depth = depth;
        this.s=s;
        this.other=other;
        this.numberCoins = 0;
        this.field = new int[field.length][field[0].length];
        for(int i = 0; i<field.length; i++)
        {
            for(int j = 0; j<field[i].length; j++)
            {
                if(field[i][j]!=0)
                {
                    numberCoins++;
                }
                this.field[i][j]=field[i][j];
            }
        }
    }

    public void setzeStein(int spalte)
    {
        numberCoins++;
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
                Node temp = new Node(field, s, other, depth+1);
                temp.setzeStein(i);
                temp.s = other;
                temp.other=s;
                erg.add(temp);
            }
        }
        return erg;
    }



}
