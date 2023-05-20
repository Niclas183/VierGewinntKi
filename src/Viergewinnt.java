public class Viergewinnt {

    public static void main(String[] args) {
        int anzZug = 0;
        Spielfeld sp = new Spielfeld(6, 7);
        sp.initialisiereSpielfeld();
        Spieler s1 = new Spieler(1, "Niclas", false);
        Spieler s2 = new KI(2, "KI", s1, true);
        Spieler[] spieler = {s1, s2};
        do {
            sp.druckeSpielfeld();
            System.out.println("Spieler " + spieler[anzZug % spieler.length].getNummer() + ": " + spieler[anzZug % spieler.length].getName());
            spieler[anzZug % spieler.length].setzteSpielstein(sp);
            System.out.println();
        } while (!(sp.testeSieg(spieler[anzZug++ % spieler.length]) || sp.testeUnentschieden()));
        sp.druckeSpielfeld();
        if (sp.testeUnentschieden())
            System.out.println("Spiel ist unentschieden!");
        else
            System.out.println(spieler[(anzZug - 1) % spieler.length].getName() + " hat das Spiel gewonnen!");
    }


}
