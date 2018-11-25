
package diabetes;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;


class KNN{
    float Pregnancies;
    float Glucose,BloodPressure, SkinThickness, Insulin, BMI, DiabetesPedigreeFunction, Age;
    float Outcome;
    double dist;
    
    
    KNN(String Pregnancies,String Glucose,String BloodPressure,String SkinThickness,String Insulin,String BMI,String DiabetesPedigreeFunction,String Age,String val) {
        this.Pregnancies = Float.parseFloat(Pregnancies);
        this.Glucose = Float.parseFloat(Glucose);
        this.BloodPressure = Float.parseFloat(BloodPressure);
        this.SkinThickness = Float.parseFloat(SkinThickness);
        this.Insulin = Float.parseFloat(Insulin);
        this.BMI = Float.parseFloat(BMI);
        this.DiabetesPedigreeFunction = Float.parseFloat(DiabetesPedigreeFunction);
        this.Age = Float.parseFloat(Age);
        this.Outcome = Float.parseFloat(val);
      
    }
    
    void calc_distance(KNN testData){
      this.dist = Math.sqrt(Math.pow((this.Pregnancies - testData.Pregnancies ),2) + Math.pow((this.Glucose - testData.Glucose),2)
              + Math.pow((this.BloodPressure - testData.BloodPressure),2)+ Math.pow((this.SkinThickness - testData.SkinThickness),2)
              + Math.pow((this.Insulin - testData.Insulin),2)+ Math.pow((this.BMI - testData.BMI),2)
              + Math.pow((this.DiabetesPedigreeFunction - testData.DiabetesPedigreeFunction),2)+ Math.pow((this.Age - testData.Age),2)
              );
    }
}



class KnnComparator implements Comparator<KNN> {

    @Override
    public int compare(KNN o1, KNN o2) {
        if(o1.dist == o2.dist)
            return 0;
        else if (o1.dist > o2.dist)
            return 1;
        else
            return -1;
    }
    
    
}
public class KNN_Demo{

    public static void main(String[] args) throws IOException{
        ArrayList<KNN> trainData = new ArrayList<>();
        ArrayList<KNN> testData = new ArrayList<>();
        ArrayList<KNN> sortedTrainData;
        File file = new File("C:\\Users\\varsh\\Documents\\NetBeansProjects\\Diabetes\\src\\diabetes\\pima_standardized.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        LineNumberReader lr = new LineNumberReader(new FileReader(file));
        String line = null;
        int truePositive=0, trueNegative=0, falsePositive=0, falseNegative=0;
        
        while(lr.getLineNumber() < 500){
            line = lr.readLine();
            String vals[] = line.split(",");
            KNN temp = new KNN(vals[0],vals[1],vals[2],vals[3],vals[4],vals[5],vals[6],vals[7],vals[8]);
            trainData.add(temp);
        }
        
        while((line = lr.readLine())!=null){
            String vals[] = line.split(",");
            KNN temp = new KNN(vals[0],vals[1],vals[2],vals[3],vals[4],vals[5],vals[6],vals[7],vals[8]);
            testData.add(temp);
        }

        
        
        for(int i=0; i<testData.size(); i++) {
        KNN test1 = testData.get(i);
          for (KNN temp: trainData) {
              temp.calc_distance(test1);
              System.out.println(temp.dist);
           }
        sortedTrainData =  new ArrayList<>(trainData);
        sortedTrainData.sort(new KnnComparator());
        
        int k = 30;
        
        int pos=0, neg=0;
        double result = 2.0;
        
        for(int j=0; j<k; j++) {
            double val = sortedTrainData.get(j).Outcome;
            if(val == 0.0)
                neg++;
            else
                pos++;
        }
        
        
        System.out.println("\n\n------- ( pos:"+pos + "  neg:"+neg + " ) -------\n\n");
        
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
        
        
        System.out.println("_________________________________________\n");
        double accuracy = ((double)(truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative))*100;
        System.out.println("TruePositive:"+truePositive + "  FalseNegative:" + falseNegative);
        System.out.println("FalsePositive:"+falsePositive + "  TrueNegative:" + trueNegative);
        System.out.println("\nAccuracy: " + accuracy);
        
        
    }
}
            
        
        
            
        


