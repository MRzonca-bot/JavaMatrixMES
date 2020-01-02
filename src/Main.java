import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void printMatrix(double [][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static GlobalData[] addData(GlobalData[] products, GlobalData productToAdd) {
        GlobalData[] newProducts = new GlobalData[products.length + 1];
        System.arraycopy(products, 0, newProducts, 0, products.length);
        newProducts[newProducts.length - 1] = productToAdd;

        return newProducts;
    }

    public static void multiplyByMinus(double[][] matrix){
        for(int i=0; i<GlobalData.counter; i++){
            matrix[i][0]*=-1;
        }
    }

    public static void fillMatrix(double[][] HG, GlobalData[] dataArray){
        for( int i=0; i<GlobalData.counter-1; i++){
            HG[dataArray[i].id1][dataArray[i].id1] += dataArray[i].HL[0][0];
            HG[dataArray[i].id1][dataArray[i].id1] += dataArray[i].BL[0][0];

            HG[dataArray[i].id1][dataArray[i].id2] += dataArray[i].HL[0][1];
            HG[dataArray[i].id1][dataArray[i].id2] += dataArray[i].BL[0][1];

            HG[dataArray[i].id2][dataArray[i].id1] += dataArray[i].HL[1][0];
            HG[dataArray[i].id2][dataArray[i].id1] += dataArray[i].BL[1][0];

            HG[dataArray[i].id2][dataArray[i].id2] += dataArray[i].HL[1][1];
            HG[dataArray[i].id2][dataArray[i].id2] += dataArray[i].BL[1][1];
        }
    }

    public static void printBGMatrix(double[][] BG){
        for(int i= 0; i<GlobalData.counter; i++){
            System.out.println(BG[i][0]);
        }
        System.out.println("");
    }
    ////////////////////////////////////////////////////////////////////////
    //////////////////////////////MAIN//////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("File1.txt"));
        GlobalData[] dataArray = new GlobalData[0];

        GlobalData.setTa(scanner.nextDouble());
        GlobalData.setA(scanner.nextDouble());
        GlobalData.setQ(scanner.nextDouble());

        int z=0;
        while (scanner.hasNext()){
            int id = scanner.nextInt();
            int id2 = scanner.nextInt();
            double k = scanner.nextDouble();
            double s = scanner.nextDouble();
            double l = scanner.nextDouble();
            int bc = scanner.nextInt();

            GlobalData addData = new GlobalData(id, id2, k, s, l, bc);
            dataArray = addData(dataArray, addData);
            dataArray[z].fillArray();
            z++;
        }
        scanner.close();

        //GLOWNA MACIERZ
        double[][] HG = new double[GlobalData.counter][GlobalData.counter];
        //MACIERZ TEMPERATOR
        double[][] Results;
        //MACIERZ WARUNKOW BRZEGOWYCH
        double[][] BG = new double[GlobalData.counter][1];

        fillMatrix(HG, dataArray);
        printMatrix(HG);

        System.out.println(" ");
        RealMatrix matrix = new Array2DRowRealMatrix(HG, false);
        RealMatrix invm = MatrixUtils.inverse(matrix); //ODWORCENIE MATRIX
        double[][] cos = invm.getData();
        printMatrix(cos);
        System.out.println(" ");

        BG[GlobalData.getPid1()][0] += dataArray[0].P[0][0];
        BG[GlobalData.getPid1()][0] += dataArray[0].P[1][0];

        BG[GlobalData.getPid2()][0] += dataArray[GlobalData.counter-2].P[0][0];
        BG[GlobalData.getPid2()][0] += dataArray[GlobalData.counter-2].P[1][0];

        printBGMatrix(BG);

        RealMatrix invm1 = new Array2DRowRealMatrix(BG, false); //PRZYPISYWANIE BG
        Results = invm.multiply(invm1).getData(); //MNOZENIE MATRIX

        multiplyByMinus(Results);
        printMatrix(Results);
    }
}