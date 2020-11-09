import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Homework homework = new Homework();
        homework.setConnection("root", "root");

        System.out.printf("Select exercise to test (2-9) or zero for exit:");

        int exerciseNumber = Integer.parseInt(reader.readLine());
        while(exerciseNumber != 0){
            switch (exerciseNumber){
                case 2: homework.getVillainsNamesEx2();
                    break;
                case 3: homework.MinionNamesEx3();
                    break;
                case 4: homework.AddMinionEx4();
                    break;
                case 5: homework.changeTownNameCasingEx5();
                    break;
                case 6:
                    System.out.println("Exercise 6 - Remove Villain is not mandatory");;
                    break;
                case 7: homework.printAllMinionNamesEx7();
                    break;
                case 8: homework.increaseMinionsAgeEx8();
                    break;
                case 9: homework.increaseAgeWithStoredProcedureEx9();
                    break;
            }
            System.out.printf("Select exercise to test (2-9) or zero for exit:");
            exerciseNumber = Integer.parseInt(reader.readLine());
        }
    }
}
