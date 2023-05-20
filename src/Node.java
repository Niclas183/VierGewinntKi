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
    private final int weight4 = 5;
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
            //3er Reihe mit Links und Rechts frei und 3er mit links oder rechts frei
            int emptybefore;
            int count;
            int emptyafter;
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==0 && count==0)
                        emptybefore++;
                    else if(field[i][j]==0 && count!=0)
                    {
                        if(emptybefore>=1 && count==3)
                        {
                            erg+=weight1;
                        }
                        if(count==3 && emptybefore==0)
                        {
                            erg+=weight2;
                        }
                        emptybefore=1;
                        count=0;
                    }
                    else if(field[i][j]==maximizer.getNummer()) {
                        count++;
                    }
                    else if(field[i][j]==minimizer.getNummer())
                    {
                        if(emptybefore>=1 && count==3)
                            erg+=weight2;
                        emptybefore=0;
                        count=0;
                    }
                }
            }
            //3er mit X_XX
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                int count2 = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==maximizer.getNummer()) {
                        if(count==1 && emptybefore>=1)
                            count2++;
                        else
                            count++;
                        if(count2==2) {
                            erg += weight4;
                            count=0;
                            count2=0;
                            emptybefore=0;
                        }
                    }
                    else if(field[i][j]==0) {
                        if (count > 0 && count2==0)
                            emptybefore++;
                        else {
                            count = 0;
                            emptybefore = 0;
                        }
                    }
                    else if(field[i][j]==minimizer.getNummer())
                    {
                        count=0;
                        count2=0;
                        emptybefore=0;
                    }
                }
            }
            //3er mit XX_X
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==maximizer.getNummer()) {
                        if(emptybefore>=1) {
                            erg += weight4;
                            count=0;
                            emptybefore=0;
                        }
                        else
                            count++;
                    }
                    else if(field[i][j]==0)
                    {
                        if(count>1)
                            emptybefore++;
                        else{
                            count=0;
                            emptybefore=0;
                        }
                    }
                    else if(field[i][j]==minimizer.getNummer())
                    {
                        count=0;
                        emptybefore=0;
                    }
                }
            }

            //Diagonale 3er
            for(int i = 3; i<field.length; i++)
            {
                for(int j = 0; j<field[0].length-3; j++)
                {
                    if(field[i][j]==maximizer.getNummer() && field[i-1][j+1]==maximizer.getNummer() && field[i-2][j+2]==0 && field[i-3][j+3]==maximizer.getNummer())
                        erg+=weight4;
                    else if(field[i][j]==maximizer.getNummer() && field[i-1][j+1]==maximizer.getNummer() && field[i-2][j+2]==maximizer.getNummer() && field[i-3][j+3]==0)
                        erg+=weight4;
                    else if(field[i][j]==maximizer.getNummer() && field[i-1][j+1]==0 && field[i-2][j+2]==maximizer.getNummer() && field[i-3][j+3]==maximizer.getNummer())
                        erg+=weight4;
                    else if(field[i][j]==0 && field[i-1][j+1]==maximizer.getNummer() && field[i-2][j+2]==maximizer.getNummer() && field[i-3][j+3]==maximizer.getNummer())
                        erg+=weight4;
                }
            }

            //2er und 3er spalte
            for(int i = 0; i<field[0].length; i++)
            {
                count = 0;
                for(int j = field.length-1; j>=2; j--) {
                    if(field[j][i]==maximizer.getNummer()) {
                        count++;
                        if (count == 2 && field[j-1][i]==0)
                            erg+=weight3;
                        else if (count == 3 && field[j-1][i]==0)
                            erg+=weight2;
                    }
                    else
                        count=0;
                }
            }

            //Bewertung für Minimizer
            //3er Reihe mit Links und Rechts frei und 3er mit links oder rechts frei
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==0 && count==0)
                        emptybefore++;
                    else if(field[i][j]==0 && count!=0)
                    {
                        if(emptybefore>=1 && count==3)
                        {
                            erg-=weight1;
                        }
                        if(count==3 && emptybefore==0)
                        {
                            erg-=weight2;
                        }
                        emptybefore=1;
                        count=0;
                    }
                    else if(field[i][j]==minimizer.getNummer()) {
                        count++;
                    }
                    else if(field[i][j]==maximizer.getNummer())
                    {
                        if(emptybefore>=1 && count==3)
                            erg-=weight2;
                        emptybefore=0;
                        count=0;
                    }
                }
            }
            //3er mit X_XX
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                int count2 = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==minimizer.getNummer()) {
                        if(count==1 && emptybefore>=1)
                            count2++;
                        else
                            count++;
                        if(count2==2) {
                            erg -= weight4;
                            count=0;
                            count2=0;
                            emptybefore=0;
                        }
                    }
                    else if(field[i][j]==0) {
                        if (count > 0 && count2==0)
                            emptybefore++;
                        else {
                            count = 0;
                            emptybefore = 0;
                        }
                    }
                    else if(field[i][j]==maximizer.getNummer())
                    {
                        count=0;
                        count2=0;
                        emptybefore=0;
                    }
                }
            }
            //3er mit XX_X
            for(int i = 0; i<field.length; i++)
            {
                emptybefore = 0;
                count = 0;
                for(int j = 0; j<field[i].length; j++) {
                    if(field[i][j]==minimizer.getNummer()) {
                        if(emptybefore>=1) {
                            erg -= weight4;
                            count=0;
                            emptybefore=0;
                        }
                        else
                            count++;
                    }
                    else if(field[i][j]==0)
                    {
                        if(count>1)
                            emptybefore++;
                        else{
                            count=0;
                            emptybefore=0;
                        }
                    }
                    else if(field[i][j]==maximizer.getNummer())
                    {
                        count=0;
                        emptybefore=0;
                    }
                }
            }

            //Diagonale 3er
            for(int i = 3; i<field.length; i++)
            {
                for(int j = 0; j<field[0].length-3; j++)
                {
                    if(field[i][j]== minimizer.getNummer() && field[i-1][j+1]==minimizer.getNummer() && field[i-2][j+2]==0 && field[i-3][j+3]==minimizer.getNummer())
                        erg-=weight4;
                    else if(field[i][j]==minimizer.getNummer() && field[i-1][j+1]==minimizer.getNummer() && field[i-2][j+2]==minimizer.getNummer() && field[i-3][j+3]==0)
                        erg-=weight4;
                    else if(field[i][j]==minimizer.getNummer() && field[i-1][j+1]==0 && field[i-2][j+2]==minimizer.getNummer() && field[i-3][j+3]==minimizer.getNummer())
                        erg-=weight4;
                    else if(field[i][j]==0 && field[i-1][j+1]==minimizer.getNummer() && field[i-2][j+2]==minimizer.getNummer() && field[i-3][j+3]==minimizer.getNummer())
                        erg-=weight4;
                }
            }

            //2er und 3er spalte
            for(int i = 0; i<field[0].length; i++)
            {
                count = 0;
                for(int j = field.length-1; j>=2; j--) {
                    if(field[j][i]==minimizer.getNummer()) {
                        count++;
                        if (count == 2 && field[j-1][i]==0)
                            erg-=weight3;
                        else if (count == 3 && field[j-1][i]==0)
                            erg-=weight2;
                    }
                    else
                        count=0;
                }
            }

            return erg;
        }
        if(other.maximizer)
            return 100;
        else
            return -100;

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
