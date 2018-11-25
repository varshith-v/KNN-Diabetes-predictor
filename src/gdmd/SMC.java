
package gdmd;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Comparator;

class Patient{
    String patient_vals;
    float distance;
    
    float simpleMatch(String str1){
        int i = 0;
        int match = 0;
        while(i < str1.length()-2){
            if(this.patient_vals.charAt(i) == str1.charAt(i))
                match++;
            i = i+2;
        }
        return 1-((float)match/(str1.length()-1));
    }
    
    float hamann(String str1){
        int i = 0;
        int match = 0, noMatch = 0;
        while(i < str1.length()-2){
            if(this.patient_vals.charAt(i) == str1.charAt(i))
                match++;
            else
                noMatch++;
            i = i+2;
        }
        return (float)(match-noMatch)/(match+noMatch);
    }
}
class PatientComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
        if(o1.distance == o2.distance)
            return 0;
        else if (o1.distance > o2.distance)
            return 1;
        else
            return -1;
    }
}

public class SMC {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        ArrayList<Patient> testData = new ArrayList<>();
        ArrayList<Patient> trainData = new ArrayList<>();
        ArrayList<Patient> sortedTrainData;
        int trueDiagnose = 0, falseDiagnose = 0;
        File file = new File("C:\\Users\\varsh\\Documents\\NetBeansProjects\\Diabetes\\src\\gdmd\\GDMD-testdata.txt");
        LineNumberReader lr = new LineNumberReader(new FileReader(file));
        String line = null;
        
        //Reading of data from file
        while(lr.getLineNumber() < 1000){
            line = lr.readLine();
            Patient temp = new Patient();
            temp.patient_vals = line;
            trainData.add(temp);
        }
        while((line = lr.readLine())!= null){
            Patient temp = new Patient();
            temp.patient_vals = line;
            testData.add(temp);
        }
        
        //Calculate hamming distance
        for(int i=0; i<testData.size(); i++) {
        Patient test1 = testData.get(i);
        float dist;
          for (Patient temp: trainData) {
              //dist = temp.hamann(test1.patient_vals);
              dist = temp.simpleMatch(test1.patient_vals);
              temp.distance = dist;
              //System.out.println(temp.distance);
           }
        sortedTrainData =  new ArrayList<>(trainData);
        //Sort Training data acoording to the hamming distance;
        sortedTrainData.sort(new PatientComparator());
        
        for (Patient temp: sortedTrainData)
            System.out.println(temp.distance);
        
        int k = 15;
        
        int pos=0, neg=0;
        char result = '0';
        
        for(int j=0; j<k; j++) {
            if('0' == sortedTrainData.get(j).patient_vals.charAt(20))
                neg++;
            else
                pos++;
        }
        
        
        System.out.println("\n\n------- ( pos:"+pos + "  neg:"+neg + " ) -------\n\n");
        
        if(neg > pos)
            result = '0';
        else if(pos > neg)
            result = '1';
        
        if(result == test1.patient_vals.charAt(20))
            trueDiagnose++;
        else
            falseDiagnose++;
        }
        
        
        System.out.println("_________________________________________\n");
        System.out.println("Correct:" + trueDiagnose + "  False:" + falseDiagnose);
        double accuracy = ((double)trueDiagnose / (trueDiagnose + falseDiagnose))*100;
        System.out.println("\nAccuracy: " + accuracy);
        
     }
}
