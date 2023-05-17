import java.util.Scanner;

public class Spieler {

    private int nummer;
    private String name;

    public Spieler(int n, String name)
    {
        nummer = n;
        this.name = name;
    }

    public void setzteSpielstein(Spielfeld sp)
    {
        int zug;
        Scanner sc = new Scanner(System.in);
        do{
            zug=sc.nextInt();
            if(zug<=0 || zug>sp.field[0].length || sp.field[0][zug-1]!=0)
                System.out.println("Ungültiger Zug! Bitte erneut eingeben!");
        }while((zug<=0 || zug>sp.field[0].length || sp.field[0][zug-1]!=0));
        for(int i = sp.field.length-1; i>=0; i--)
        {
            if(sp.field[i][zug-1]==0) {
                sp.field[i][zug - 1] = nummer;
                break;
            }

        }
    }
    public String getName() {
        return name;
    }

    public int getNummer() {
        return nummer;
    }
}
