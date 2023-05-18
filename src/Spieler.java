import java.util.Scanner;

public class Spieler {

    private int nummer;
    private String name;
    public boolean maximizer;

    public Spieler(int n, String name, boolean maximizer)
    {
        nummer = n;
        this.maximizer = maximizer;
        this.name = name;
    }

    public void setzteSpielstein(Spielfeld sp)
    {
        int zug;
        Scanner sc = new Scanner(System.in);
        System.out.print("Spalte: ");
        do{
            zug=sc.nextInt();
            if(zug<=0 || zug>sp.field[0].length || sp.field[0][zug-1]!=0)
                System.out.println("Ungültiger Zug! Bitte erneut eingeben!");
        }while((zug<=0 || zug>sp.field[0].length || sp.field[0][zug-1]!=0));
        sp.setzeStein(zug-1,nummer);
    }
    public String getName() {
        return name;
    }

    public int getNummer() {
        return nummer;
    }
}
