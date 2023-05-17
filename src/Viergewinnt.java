public class Viergewinnt {

    public static void main(String[] args) {
        int anzZug = 0;
        Spielfeld sp = new Spielfeld(6,7);
        sp.initialisiereSpielfeld();
        boolean zug = true;
        Spieler[] spieler = {new Spieler(1,"Max"),new Spieler(2,"Mustermann")};
        do
        {
            sp.druckeSpielfeld();
            System.out.println("Spieler " + spieler[anzZug%spieler.length].getNummer() + ": " + spieler[anzZug%spieler.length].getName());
            System.out.print("Spalte: ");
            spieler[anzZug%spieler.length].setzteSpielstein(sp);
            System.out.println();
        }while(!(sp.testeSieg(spieler[anzZug++%spieler.length]) || sp.testeUnentschieden()));
        sp.druckeSpielfeld();
        if(sp.testeUnentschieden())
            System.out.println("Spiel ist unentschieden!");
        else
            System.out.println(spieler[(anzZug-1)%spieler.length].getName() + " hat das Spiel gewonnen!");
    }



}
