import java.util.Scanner;

public class Spielfeld {

    int x;
    int y;
    int[][] field;

    public Spielfeld(int x, int y) {
        this.x = x;
        this.y = y;
        field = new int[x][y];
    }

    public void initialisiereSpielfeld() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = 0;
            }
        }
    }

    public void setzeStein(int zug, int nummer) {
        for (int i = field.length - 1; i >= 0; i--) {
            if (field[i][zug] == 0) {
                field[i][zug] = nummer;
                break;
            }

        }
    }

    public void druckeSpielfeld() {
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < field[i].length; j++) {
                System.out.print("| " + field[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(" -----------------------------");
        for (int i = 0; i < field[0].length; i++)
            System.out.print("   " + (i + 1));
        System.out.println();
    }

    public boolean testeUnentschieden() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public boolean testeSieg(Spieler s) {
        boolean won = true;
        // Spalte
        for (int i = 0; i < field[0].length; i++) {
            for (int j = field.length - 1; j >= 3; j--) {
                won = true;
                for (int k = j; k > j - 4; k--) {
                    if (field[k][i] != s.getNummer()) {
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
        //Zeile
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length - 3; j++) {
                won = true;
                for (int k = j; k < j + 4; k++) {
                    if (field[i][k] != s.getNummer()) {
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
        //Diagonale
        for (int i = 0; i < field[0].length; i++) {
            for (int j = field.length - 1; j >= 3; j--) {
                won = true;
                //Diagonale unten nach oben rechts
                if (i < field[0].length - 3) {
                    for (int k = 0; k < 4; k++) {
                        if (field[j - k][i + k] != s.getNummer()) {
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
                if (i - 3 >= 0) {
                    for (int k = 0; k < 4; k++) {
                        if (field[j - k][i - k] != s.getNummer()) {
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
        return false;
    }
}
