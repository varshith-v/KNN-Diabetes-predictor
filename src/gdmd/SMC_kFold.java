
package gdmd;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Comparator;

//class Patient{
//    String patient_vals;
//    float distance;
//    
//    float simpleMatch(String str1){
//        int i = 0;
//        int match = 0;
//        while(i < str1.length()-2){
//            if(this.patient_vals.charAt(i) == str1.charAt(i))
//                match++;
//            i = i+2;
//        }
//        return 1-((float)match/(str1.length()-1));
//    }
//    
//    float hamann(String str1){
//        int i = 0;
//        int match = 0, noMatch = 0;
//        while(i < str1.length()-2){
//            if(this.patient_vals.charAt(i) == str1.charAt(i))
//                match++;
//            else
//                noMatch++;
//            i = i+2;
//        }
//        return (float)(match-noMatch)/(match+noMatch);
//    }
//}
//class PatientComparator implements Comparator<Patient> {
//    @Override
//    public int compare(Patient o1, Patient o2) {
//        if(o1.distance == o2.distance)
//            return 0;
//        else if (o1.distance > o2.distance)
//            return 1;
//        else
//            return -1;
//    }
//}

public class SMC_kFold {
    private static double accuracy = 0;
    
    public static double findAccuracy(ArrayList<Patient> testData, ArrayList<Patient> trainData){
        int trueDiagnose=0, falseDiagnose=0;
        
        ArrayList<Patient> sortedTrainData;
        for(int i=0; i<testData.size(); i++) {
                Patient test1 = testData.get(i);
                float dist;
                  for (Patient temp: trainData) {
                      //dist = temp.hamann(test1.patient_vals);
                       dist = temp.simpleMatch(test1.patient_vals);
                       temp.distance = dist;
                      //System.out.println(temp.dist);
                   }
                sortedTrainData =  new ArrayList<>(trainData);
                sortedTrainData.sort(new PatientComparator());
                
                //Value of k for KNN can be changed in next line
                int k = 19;
                
                
        
        int pos=0, neg=0;
        char result = '0';
        
        for(int j=0; j<k; j++) {
            if('0' == sortedTrainData.get(j).patient_vals.charAt(16))
                neg++;
            else
                pos++;
        }
        
        
        System.out.println("\n\n------- ( pos:"+pos + "  neg:"+neg + " ) -------\n\n");
        
        if(neg > pos)
            result = '0';
        else if(pos > neg)
            result = '1';
        
        if(result == test1.patient_vals.charAt(16))
            trueDiagnose++;
        else
            falseDiagnose++;
        }
        
            accuracy = ((double)trueDiagnose / (trueDiagnose + falseDiagnose))*100;
            System.out.println("Correct:" + trueDiagnose + "  False:" + falseDiagnose);
            System.out.println("\nAccuracy : " + accuracy+"\n");
            return accuracy;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        ArrayList<Patient> dataSet = new ArrayList<>();
        File file = new File("C:\\Users\\varsh\\Documents\\NetBeansProjects\\Diabetes\\src\\gdmd\\pima_binary1.txt");
        
        LineNumberReader lr = new LineNumberReader(new FileReader(file));
        String line = null;
        double accuracy = 0;
        int no_Of_Folds;
        int perFold;
        
        
        while((line = lr.readLine())!=null){
            Patient temp = new Patient();
            temp.patient_vals = line;
            dataSet.add(temp);
        }
        
        int count = dataSet.size();
        
        // Value of k for k-fold can be changed in next line
        no_Of_Folds =10;
        
        perFold = count/no_Of_Folds;
        int i =0;
        
        while(i < count-perFold){
            ArrayList<Patient> trainData = new ArrayList<>();
            ArrayList<Patient> testData = new ArrayList<>();
            trainData.addAll(dataSet.subList(0, i));
            testData.addAll(dataSet.subList(i, i+perFold));
            trainData.addAll(dataSet.subList(i+perFold, count));
            accuracy = accuracy + findAccuracy(testData, trainData);
            i = i+perFold;
        } 
        ArrayList<Patient> trainData = new ArrayList<>();
        ArrayList<Patient> testData = new ArrayList<>();
        i = i - perFold;
        
        if(i < count){
            trainData.addAll(dataSet.subList(0, i));
            testData.addAll(dataSet.subList(i, count));
            accuracy = accuracy + findAccuracy(testData,trainData);
           
        }
        System.out.println("\n__________________________________\n");
        System.out.println(no_Of_Folds + "-fold cross validation");
        System.out.println("Final Accuracy: " + accuracy/no_Of_Folds + "\n\n");
        
     }
}
