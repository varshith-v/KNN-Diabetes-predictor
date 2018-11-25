
package diabetes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class KNN_kFold{
    private static double accuracy = 0;
    
    public static double findAccuracy(ArrayList<KNN> testData, ArrayList<KNN> trainData){
        int truePositive=0, trueNegative=0, falsePositive=0, falseNegative=0;
        
        ArrayList<KNN> sortedTrainData;
        for(int j=0; j<testData.size(); j++) {
                KNN test1 = testData.get(j);
                  for (KNN temp: trainData) {
                      temp.calc_distance(test1);
                      //System.out.println(temp.dist);
                   }
                sortedTrainData =  new ArrayList<>(trainData);
                sortedTrainData.sort(new KnnComparator());
                
                //Value of k for KNN can be changed in next line
                int k = 19;
                
                int pos=0, neg=0;
                double result = 2.0;
                
                for(int m=0; m<k; m++) {
                    double val = sortedTrainData.get(m).Outcome;
                    if(val == 0.0)
                        neg++;
                    else
                        pos++;
                }
                
                System.out.println("------- ( pos:"+pos + "  neg:"+neg + " ) -------");

                if(neg > pos)
                    result = 0.0;
                else if(pos > neg)
                    result = 1.0;

                if(result == test1.Outcome){
                    if(result == 1.0)
                        truePositive++;
                    else
                        trueNegative++;
                }
                else{
                    if(result == 1.0)
                        falsePositive++;
                    else
                        falseNegative++;
                    
                }
                    
                }
            accuracy = ((double)(truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative))*100;
            System.out.println("TruePositive:"+truePositive + "  FalseNegative:" + falseNegative);
            System.out.println("FalsePositive:"+falsePositive + "  TrueNegative:" + trueNegative);
            System.out.println("\nAccuracy : " + accuracy+"\n");
            return accuracy;
    }
    
    public static void main(String[] args) throws IOException{
        ArrayList<KNN> dataSet = new ArrayList<>();
        File file = new File("C:\\Users\\varsh\\Documents\\NetBeansProjects\\Diabetes\\src\\diabetes\\diabetes1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        LineNumberReader lr = new LineNumberReader(new FileReader(file));
        String line = null;
        double accuracy = 0;
        int no_Of_Folds;
        int perFold;
        
        
        while((line = lr.readLine())!=null){
            String vals[] = line.split(",");
            KNN temp = new KNN(vals[0],vals[1],vals[2],vals[3],vals[4],vals[5],vals[6],vals[7],vals[8]);
            dataSet.add(temp);
        }
        
        int count = dataSet.size();
        
        // Value of k for k-fold can be changed in next line
        no_Of_Folds =10;
        
        perFold = count/no_Of_Folds;
        int i =0;
        int folds=0;
        while(i < count-perFold){
            ArrayList<KNN> trainData = new ArrayList<>();
            ArrayList<KNN> testData = new ArrayList<>();
            trainData.addAll(dataSet.subList(0, i));
            testData.addAll(dataSet.subList(i, i+perFold));
            trainData.addAll(dataSet.subList(i+perFold, count));
            accuracy = accuracy + findAccuracy(testData, trainData);
            i = i+perFold;
            folds++;
        } 
        ArrayList<KNN> trainData = new ArrayList<>();
        ArrayList<KNN> testData = new ArrayList<>();
        i = i - perFold;
        
        if(i < count){
            trainData.addAll(dataSet.subList(0, i));
            testData.addAll(dataSet.subList(i, count));
            accuracy = accuracy + findAccuracy(testData,trainData);
            folds++;
        }
        System.out.println("\n__________________________________\n");
        System.out.println(no_Of_Folds + "-fold cross validation");
        System.out.println("Final Accuracy: " + accuracy/folds + "\n\n");
        System.out.println("Folds:" + folds);
    }
}


