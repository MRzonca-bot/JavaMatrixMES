import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class GlobalData {
    static int counter = 1; //LICZNIK
    private static int Pid1, Pid2;
    private static boolean bool = true;
    private static double ta, a, q;
    {counter++;}
    public int id1, id2, bc;
    private double k, s, l; //k - wspolczynnik, l - dlugosc

    public double[][] HL = {{0, 0},{0, 0}};        //    [c][-c]          tabela z wartosciami
                                                   //    [-c][c]

    public double[][] BL = {{0, 0},{0, 0}};        //    [0][0]           tabela z wartosciami z warunkami brzegowymi
                                                   //    [0][qs]

    public double[][] P ={{0},{0}};                //     [0]             warunki brzegowe
                                                   //   [-aSta]

    public GlobalData(int id1, int id2, double k, double s, double l, int bc) { //KONSTRUKTOR
        this.id1 = id1-1;
        this.id2 = id2-1;
        this.k = k;
        this.s = s;
        this.l = l;
        this.bc = bc; // WARUNKI BRZEGOWE
    }

    public void fillArray() {                                //OBLICZANE JEST C I WPISYWANE DO HL
        double c = this.s * this.k / this.l;
        this.HL[0][0] = c;
        this.HL[0][1] = -c;
        this.HL[1][0] = -c;
        this.HL[1][1] = c;

        if (bool && this.bc == 1) { //jesli pierwszym wezlem jest BC1
            this.P[0][0] = q * this.s; //A * S idze do wektora
            Pid1=id1;
            bool = false;
        } else if (bool && this.bc == 2){//jesli pierwszym wezlem jest BC2
            this.P[0][0] = -1 * a * this.s * ta;
            this.BL[0][0] = a * this.s;
            Pid1=id1;
            bool = false;
        } else if (this.bc == 1){ //ostatni wezel jesli pierwszym byl BC2
            this.P[1][0] = q * this.s;
            Pid2=id2;

        } else if (this.bc == 2){//ostatni wezel jesli pierwszym byl BC1
            this.BL[1][1] = a * this.s; //A * S idze to macierzy
            this.P[1][0] =  -1 * a * this.s * ta; // idze do wektora
            Pid2=id2;
        }
    }

    public static double getTa() {
        return ta;
    }

    public static void setTa(double ta) {
        GlobalData.ta = ta;
    }

    public static double getA() {
        return a;
    }

    public static void setA(double a) {
        GlobalData.a = a;
    }

    public static double getQ() {
        return q;
    }

    public static void setQ(double q) {
        GlobalData.q = q;
    }

    public static int getPid1() {
        return Pid1;
    }

    public static int getPid2() {
        return Pid2;
    }

    @Override
    public String toString(){
        return " ta "+ ta +"\n" + " a  "+ a +"\n" + " q  "+ q;
    }
}