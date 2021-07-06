import com.opencsv.*;


import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) {


        //command line file grabber
       /*
        if((args.length) != 2){
            System.out.printf("Invalid number of arguments: %d given, needs 2.\n\nProvide input and output file name", args.length);

        }


        else
        {
            //grab from command line
            String pathToCsv=args[1];
        }
*/

        LinkedList<LinkedList<String[]>> LLParent = new LinkedList<>();


        try {
            String pathToCsv = "RSSI.csv";
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));


            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                //holds tuple
                String[] xy = {data[1], data[2]};
                String[] tempxy = new String[2];




                int beaconNum;
                //loops through .csv row starting from 4th position
                for (int i = 3; i < data.length - 1; i++) {
                    int match = 0;
                    beaconNum = i;

                    //if value is filtered, ignore



                    if (data[i].equals("\"-1000\""))
                    {
                        System.out.println("here" + data[i]);
                        continue;
                    }
                    else{

                        int j = 0;
                        while (match == 0) {

                            //run through the ll parent children to check if there is a list for this coordinate set already
                            if (LLParent.size()>j) {
                                //skip loop if the list is blank
                                if (LLParent.get(j).size()==0){
                                    continue;
                                }

                                //System.out.printf("j is :%d \nLLparent size is %d\n",j, LLParent.size());
                                tempxy[0] = LLParent.get(j).get(0)[0];
                                tempxy[1] = LLParent.get(j).get(0)[1];
                            }
                            //if j exceeds the size of the list, it means there is no match
                            else{
                                //System.out.print("too long\n");
                                break;
                            }

                            //if the x and y coords of the data
                            if (tempxy[0].equals(xy[0]) && tempxy[1].equals(xy[1])) {
                                match = 1;
                                //System.out.println(tempxy[0]);
                               // System.out.println(xy[0]);
                                //System.out.println("match");
                                break;
                            }
                            else {
                                j++;
                            }
                        }




                        if (match == 1) {
                            //check if the significant column is populated
                            //if not, populate the array at the significant column with the significant data
                            for (int k = 0; k < LLParent.get(j).size(); k++) {
                                if (LLParent.get(j).get(k)[beaconNum]== null) {
                                    LLParent.get(j).get(k)[beaconNum] = data[beaconNum];
                                }
                                //if it is populated, append new link with link[0]=tempxy[0], and link[1]=tempxy[1], link[significantCol]=significantData;
                                else {
                                    LLParent.add(new LinkedList<>());

                                    //last link in the new list, with the new data
                                    LLParent.get(j).get(LLParent.get(j).size()-1)[0] = tempxy[0];
                                    LLParent.get(j).get(LLParent.get(j).size()-1)[beaconNum] = data[beaconNum];

                                }
                            }

                        } else {
                            //make new linked list corresponding to new (x,y)
                            LinkedList<String[]> list = new LinkedList<>();
                            LLParent.add(list);
                            //generate array that will be first element in the new linked list, populate it
                            String[] dataRow = new String[36];
                            dataRow[0] = xy[0];
                            dataRow[1] = xy[1];
                            //-1 to account for the lack of key column (which is in the imported data) in the linked list.
                            dataRow[beaconNum] = data[beaconNum - 1];
                            LLParent.getLast().add(dataRow);
                            //The corresponding (x,y) pair now has its own linked list, which has one element containing the first iteration of the (x,y) pair
                        }

                    }

                }
                //get (x, y)
                //get beaconData (find value that's not -1000)
                //traverse LLParent to find (x, y) match
                // CASE 1 - no match, make new LLChild, insert beaconData
                // CASE 2 - yes match (x, y), no match beacon, insert beaconData
                // CASE 3 - yes match (x, y), yes match beacon, make new LLChild, insert beaconData


            }


            //make .csv




            CSVWriter writer = new CSVWriter(new FileWriter("output.csv"));
            for (LinkedList<String[]> strings : LLParent) {
                for (int j = 0; j < strings.size(); j++) {
                    writer.writeNext(strings.get(j));
                }

            }


            csvReader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


/*
class IOHandler {
    private String inputFileName;
    private String outputFileName;




    IOHandler(String input, String output){
        this.inputFileName = input;
        this.outputFileName = output;

    }
}
*/





/*
NOTES:
linked list going down
linked list going sideways
if array exists at xy location, insert beacon
if beacon reading exists, make new array
if no array, make new array
*/


