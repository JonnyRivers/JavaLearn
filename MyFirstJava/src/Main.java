import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
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
            System.out.println();

            Stream<FPLElement> keepers = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 1);
            for(FPLElement keeper : keepers.toList())
            {
                double minutesPlayedPercentage = (double)keeper.Minutes / (double)maxMinutes;
                double cleanSheetProbability = Math.pow(0.9, keeper.XGCPer90 * 10);
                keeper.ExpectedPointsPer90 = minutesPlayedPercentage * 2 + keeper.XGPer90 * 6 + keeper.XAPer90 * 3 + keeper.SavesPer90 / 3 + cleanSheetProbability * 4;
            }

            numToTake = 25;
            keepers = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 1)
                    .sorted((x1, x2) -> Double.compare(x2.ExpectedPointsPer90, x1.ExpectedPointsPer90))
                    .limit(numToTake);
            System.out.println("Top " + numToTake + " Keppers points per 90");
            for(FPLElement keeper : keepers.toList())
            {
                System.out.println(String.format("%s %s %,.2f", keeper.FirstName, keeper.SecondName, keeper.ExpectedPointsPer90));
            }
            System.out.println();

            Stream<FPLElement> defenders = fplStatic.Elements.stream()
                .filter(x -> x.Minutes > minMinutesRequired)
                .filter(x -> x.ElementType == 2);
            for(FPLElement defender : defenders.toList())
            {
                double minutesPlayedPercentage = (double)defender.Minutes / (double)maxMinutes;
                double cleanSheetProbability = Math.pow(0.9, defender.XGCPer90 * 10);
                defender.ExpectedPointsPer90 = minutesPlayedPercentage * 2 + defender.XGPer90 * 6 + defender.XAPer90 * 3 + cleanSheetProbability * 4;
            }

            numToTake = 25;
            defenders = fplStatic.Elements.stream()
                .filter(x -> x.Minutes > minMinutesRequired)
                .filter(x -> x.ElementType == 2)
                .sorted((x1, x2) -> Double.compare(x2.ExpectedPointsPer90, x1.ExpectedPointsPer90))
                .limit(numToTake);
            System.out.println("Top " + numToTake + " Defenders points per 90");
            for(FPLElement defender : defenders.toList())
            {
                System.out.println(String.format("%s %s %,.2f", defender.FirstName, defender.SecondName, defender.ExpectedPointsPer90));
            }
            System.out.println();

            Stream<FPLElement> midfielders = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 3);
            for(FPLElement midfielder : midfielders.toList())
            {
                double minutesPlayedPercentage = (double)midfielder.Minutes / (double)maxMinutes;
                double cleanSheetProbability = Math.pow(0.9, midfielder.XGCPer90 * 10);
                midfielder.ExpectedPointsPer90 = minutesPlayedPercentage * 2 + midfielder.XGPer90 * 5 + midfielder.XAPer90 * 3 + cleanSheetProbability * 1;
            }

            numToTake = 25;
            midfielders = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 3)
                    .sorted((x1, x2) -> Double.compare(x2.ExpectedPointsPer90, x1.ExpectedPointsPer90))
                    .limit(numToTake);
            System.out.println("Top " + numToTake + " Midfielders points per 90");
            for(FPLElement midfielder : midfielders.toList())
            {
                System.out.println(String.format("%s %s %,.2f", midfielder.FirstName, midfielder.SecondName, midfielder.ExpectedPointsPer90));
            }
            System.out.println();

            Stream<FPLElement> forwards = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 4);
            for(FPLElement forward : forwards.toList())
            {
                double minutesPlayedPercentage = (double)forward.Minutes / (double)maxMinutes;
                forward.ExpectedPointsPer90 = minutesPlayedPercentage * 2 + forward.XGPer90 * 4 + forward.XAPer90 * 3;
            }

            numToTake = 25;
            forwards = fplStatic.Elements.stream()
                    .filter(x -> x.Minutes > minMinutesRequired)
                    .filter(x -> x.ElementType == 4)
                    .sorted((x1, x2) -> Double.compare(x2.ExpectedPointsPer90, x1.ExpectedPointsPer90))
                    .limit(numToTake);
            System.out.println("Top " + numToTake + " Forwards points per 90");
            for(FPLElement forward : forwards.toList())
            {
                System.out.println(String.format("%s %s %,.2f", forward.FirstName, forward.SecondName, forward.ExpectedPointsPer90));
            }
            System.out.println();
        }
    }
}