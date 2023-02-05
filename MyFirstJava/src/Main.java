import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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
            for(FPLElement element : fplStatic.Elements)
            {
                System.out.println(String.format("%s %s", element.FirstName, element.SecondName));
            }
        }
    }
}