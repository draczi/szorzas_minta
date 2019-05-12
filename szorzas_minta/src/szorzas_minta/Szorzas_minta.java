
package szorzas_minta;

/**
 *
 * @author Drácz Istvan
 */
public class Szorzas_minta {

    /**
     * 2 pozitiv szam szorzatat visszadó fv
     * @param első szám a first 
     * @param második szám a second
     * @return ha first vagy second negatív szám a fv -1 értékkel tér vissza, ha mind2 szám pozitív akkor first és second szorzatával.
     */
    public static int multiplyNatural(int first, int second) {
        if (first < 1 || second < 1) {
            return -1;
        }
        return first * second;
    }

    static Object multiplyNaturals(int parseInt, int parseInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
