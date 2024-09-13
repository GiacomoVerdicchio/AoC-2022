import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AoC1 {
    public static void main(String[] args) throws IOException {
        String path = "src/main/resources/input.txt";


        try {
            ParserFile parserFile = new ParserFile(path);

            int mostCalories = parserFile.findElfWithMostCalories();
            System.out.println("Most calories "+mostCalories);

            int most3elvesCalories = parserFile.findTop3ElvesWithMostCalories();
            System.out.println("Most 3 elves carrying calories "+ most3elvesCalories);

        }catch (FileNotFoundException e )
        {
            System.out.println("Wrong path");
        }
        catch (RuntimeException e)
        {
            System.out.println("Empty elves structure");
        }
    }

}



class ParserFile{
    private final String path;
    private ArrayList<Elf> elves;

    ParserFile(String path) throws FileNotFoundException {
        this.path = path;
        elves= new ArrayList<>();

        addNewElf();
        populateStruct();
    }

    /** Reads all the file and populate the structures
     * @throws FileNotFoundException
     */
    void populateStruct() throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if(line.equals(""))
            {
                addNewElf();
            }
            else{
                int castCaloriesToInteger = Integer.parseInt(line);
                getLastElf().addCalories(castCaloriesToInteger);
            }
        }
    }

    /** It iterates through all the elves to find the one with the most calories carried
     * @return the higher number of calories
     */
    public int findElfWithMostCalories() throws RuntimeException
    {
        if(elves.isEmpty())
        {
            throw new RuntimeException("Error in the size");
        }

        int mostCalories = 0;
        for(Elf e : elves)
        {
            if(e.getTotCalories()>mostCalories)
            {
                mostCalories = e.getTotCalories();
            }
        }
        return mostCalories;
    }


    /** Here I create a vector with only the total calories of each elf. Then I order it and then I take the first 3
     *  calories and I sum them.
     * @return
     * @throws RuntimeException
     */
    public int findTop3ElvesWithMostCalories() throws RuntimeException
    {
        if(elves.isEmpty())
        {
            throw new RuntimeException();
        }

        ArrayList<Integer> caloriesArrayOrdered = new ArrayList<>();
        for(Elf e : elves)
        {
            caloriesArrayOrdered.add(e.getTotCalories());
        }
        Collections.sort(caloriesArrayOrdered,Collections.reverseOrder());

        int mostCalories = 0;
        for(int e=0;e<3;e++)
        {
            mostCalories += caloriesArrayOrdered.get(e);
        }
        return mostCalories;
    }

    public Elf getLastElf() {
        return elves.get(elves.size()-1);
    }

    public void addNewElf() {
        elves.add(new Elf());
    }
}

class Elf {
    private int totCalories;
    private ArrayList<Integer> listOfCalories;

    public Elf()
    {
        totCalories=0;
        listOfCalories= new ArrayList<>();
    }

    public int getTotCalories() {
        return totCalories;
    }

    /** Add the calories to the array and also update the counter of the mostCalories
     * @param calories
     */
    public void addCalories(int calories) {
        listOfCalories.add(calories);
        updateTotalCalories(calories);
    }


    public void updateTotalCalories(int calories) {
        totCalories+= calories;
    }

}