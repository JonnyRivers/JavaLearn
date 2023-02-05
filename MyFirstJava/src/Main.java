import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        int[] numbers = {1, 2, 3, 4};
        for(int number : numbers)
        {
            System.out.println(number);
        }

        IntStream largerNumbers = Arrays.stream(numbers).filter(x -> x > 2);
        for(int largerNumber : largerNumbers.toArray())
        {
            System.out.println(largerNumber);
        }

        Person[] people = { new Person("Jonny") };
        for(Person person : people)
        {
            System.out.println(person.GetName());
        }

        Path filePath = Paths.get("C:\\Users\\jonny_\\Documents\\Sports Interactive\\Football Manager 2021\\crash dumps\\upload_parameters.json");
        List<String> lines = Files.readAllLines(filePath);
        for(String line : lines)
        {
            System.out.println(line);
        }
        Gson gson = new Gson();
        Person personFromJson = gson.fromJson("{ _name: Bob}", Person.class);
        System.out.println(personFromJson.GetName());

        List<String> names = new ArrayList<String>();
        names.add("Fred");

        var fplClient = new FPLClient();
        FPLStatic fplStatic = fplClient.GetStatic();
        if(fplStatic != null)
        {
            int maxMinutes = fplStatic.Elements.stream().mapToInt(x -> x.Minutes).max().orElse(0);
            int minMinutesRequired = maxMinutes / 2;

            for(FPLTeam team : fplStatic.Teams)
            {
                System.out.println(team.Name);

                int numToTake = 5;
                System.out.println("Top " + numToTake + " xA per 90");
                Stream<FPLElement> elementsThisTeam = fplStatic.Elements.stream().filter(x -> x.Team == team.Id && x.Minutes > minMinutesRequired);
                Stream<FPLElement> topXA = elementsThisTeam.sorted((x1, x2) -> Double.compare(x2.XAPer90, x1.XAPer90)).limit(numToTake);
                for(FPLElement element : topXA.toList())
                {
                    System.out.println(String.format("%s %s %,.2f", element.FirstName, element.SecondName, element.XAPer90));
                }

                System.out.println("Top " + numToTake + " xG per 90");
                elementsThisTeam = fplStatic.Elements.stream().filter(x -> x.Team == team.Id && x.Minutes > minMinutesRequired);
                Stream<FPLElement> topXG = elementsThisTeam.sorted((x1, x2) -> Double.compare(x2.XGPer90, x1.XGPer90)).limit(numToTake);
                for(FPLElement element : topXG.toList())
                {
                    System.out.println(String.format("%s %s %,.2f", element.FirstName, element.SecondName, element.XGPer90));
                }

                System.out.println();
            }

            int numToTake = 10;
            Stream<FPLElement> topXA = fplStatic.Elements.stream()
                .filter(x -> x.Minutes > minMinutesRequired)
                .sorted((x1, x2) -> Double.compare(x2.XAPer90, x1.XAPer90))
                .limit(numToTake);
            System.out.println("Top " + numToTake + " xA per 90");
            for(FPLElement element : topXA.toList())
            {
                System.out.println(String.format("%s %s %,.2f", element.FirstName, element.SecondName, element.XAPer90));
            }
            System.out.println();

            Stream<FPLElement> topXG = fplStatic.Elements.stream()
                .filter(x -> x.Minutes > minMinutesRequired)
                .sorted((x1, x2) -> Double.compare(x2.XGPer90, x1.XGPer90))
                .limit(numToTake);
            System.out.println("Top " + numToTake + " xG per 90");
            for(FPLElement element : topXG.toList())
            {
                System.out.println(String.format("%s %s %,.2f", element.FirstName, element.SecondName, element.XGPer90));
            }
        }
    }
}