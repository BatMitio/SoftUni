package ex_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JDBC_Intro_Exercise {


    /*
        1. Please , don't forget to drop your database and insert a fresh one;

        2. Enter your user/password for the database to establish connection;

        3. Code overview - exercises are put into static classes, each with method run() - for simplicity.
           The method run() for each exercise holds the business logic. Some helper methods follow - print result,
           check if entity exists, etc.;

        4. At the end there are common methods for all exercises;

        5. Database should be refreshed for Exercise 7;

        6. My credentials at SoftUni - username: Gibon, facebook: www.facebook.com/petar.petkov.1848,
            email cpt.petkov@gmail.com

     */

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";

    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

        //2. Get Villains’ Names
        ExerciseTwo.run();

        //3. Get Minion Names
        // ExerciseThree.run();

        //4. Add Minion
        // ExerciseFour.run();

        //5. Change Town Names Casing
        //ExerciseFive.run();

        //6* Remove Villain
        //ExerciseSix.run();

        //Exercise 7 Print All Minion Names
        //ExerciseSeven.run();

        //Exercise 8 Increase Minions Age
        //ExerciseEight.run();

        //Exercise 9 Increase Age Stored Procedure
        //ExerciseNine.run();

        connection.close();

    }


    //Exercise 2
    static class ExerciseTwo {
        private static void run() throws SQLException {

            //Get only the villains who have more than 15 minions.
            query = "SELECT v.name, COUNT(mv.minion_id) AS `count`\n" +
                    "FROM villains AS v\n" +
                    "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                    "GROUP BY v.name\n" +
                    "HAVING count > 15\n" +
                    "ORDER BY count DESC ";

            statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            printResultSet(resultSet);

        }

        private static void printResultSet(ResultSet resultSet) throws SQLException {
            while (resultSet.next()) {
                System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("count"));
            }
        }
    }

    //Exercise 3
    static class ExerciseThree {

        private static void run() throws IOException, SQLException {
            //get villain id from console
            System.out.print("Enter villain id: ");
            int villainId = Integer.parseInt(reader.readLine());

            //exit if villain id doesn't exist
            if (!checkIfEntityExistsById(villainId, "villains")) {
                System.out.printf("No villain with ID %d exists in the database.", villainId);
                return;
            }

            //select the minions of the villain
            query = "SELECT m.name, m.age\n" +
                    "FROM villains AS v\n" +
                    "JOIN minions_villains AS mv on v.id = mv.villain_id\n" +
                    "JOIN minions AS m on mv.minion_id = m.id\n" +
                    "    where v.id = ?;";

            statement = connection.prepareStatement(query);

            statement.setInt(1, villainId);

            ResultSet resultSet = statement.executeQuery();

            printResultSet(resultSet, villainId);


        }

        private static void printResultSet(ResultSet resultSet, int villainId) throws SQLException {
            System.out.printf("Villain: %s%n", getEntityNameById(villainId, "villains"));

            int minionNumber = 1;
            while (resultSet.next()) {
                System.out.printf("%d. %s %d%n", minionNumber, resultSet.getString("name"), resultSet.getInt("age"));
                minionNumber++;
            }
        }
    }

    //Exercise 4
    static class ExerciseFour {
        private static void run() throws IOException, SQLException {

            //get minion data from console
            System.out.print("Minion: ");
            String[] minionTokens = reader.readLine().split("\\s+");
            String minionName = minionTokens[0];
            int minionAge = Integer.parseInt(minionTokens[1]);
            String town = minionTokens[2];

            //get villain data from console
            System.out.print("Villain: ");
            String villainName = reader.readLine();


            addTownIfDoesntExist(town);

            addVillainIfDoesntExist(villainName);

            addMinionToMinionsTable(minionName, minionAge, town);

            assignMinionToVillain(minionName, villainName);


        }

        private static void addTownIfDoesntExist(String town) throws SQLException {
            if (!checkIfEntityExistsByName(town, "towns")) {
                query = "INSERT INTO `towns` (`name`, `country`) VALUE (?, NULL)";

                statement = connection.prepareStatement(query);
                statement.setString(1, town);
                statement.execute();

                System.out.printf("Town %s was added to the database.%n", town);
            }
        }

        private static void addVillainIfDoesntExist(String villainName) throws SQLException {
            if (!checkIfEntityExistsByName(villainName, "villains")) {
                query = "INSERT INTO `villains` (`name`, `evilness_factor`) VALUE (?, 'evil')";

                statement = connection.prepareStatement(query);
                statement.setString(1, villainName);
                statement.execute();

                System.out.printf("Villain %s was added to the database.%n", villainName);

            }
        }

        private static void addMinionToMinionsTable(String minionName, int minionAge, String town) throws SQLException {
            int town_id = getEntityIdByName(town, "towns");
            query = "INSERT INTO `minions` (name, age, town_id) VALUE (?, ?, ?)";

            statement = connection.prepareStatement(query);
            statement.setString(1, minionName);
            statement.setInt(2, minionAge);
            statement.setInt(3, town_id);
            statement.execute();
        }

        private static void assignMinionToVillain(String minionName, String villainName) throws SQLException {
            // add `minion_id` and `villain_id` in the mapping table `minions_villains`

            int minion_id = getEntityIdByName(minionName, "minions");
            int villain_id = getEntityIdByName(villainName, "villains");
            query = "INSERT INTO `minions_villains` (`minion_id`, `villain_id`) VALUE (?, ?)";

            statement = connection.prepareStatement(query);
            statement.setInt(1, minion_id);
            statement.setInt(2, villain_id);
            statement.execute();

            System.out.printf("Successfully added %s to be minion of %s", minionName, villainName);
        }
    }

    //Exercise 5
    static class ExerciseFive {

        private static void run() throws IOException, SQLException {
            //get country data
            System.out.print("Enter Country: ");
            String country = (reader.readLine());

            // array to record cities from resultSet
            List<String> cityList = new ArrayList<>();

            if (!checkIfCountryExists(country)) {
                System.out.println("No town names were affected.");
            } else {
                changeToUpperCaseForCountry(country);

                collectCitiesFromCountryToList(country, cityList);

                printDataAsRequired(cityList);

            }
        }

        private static boolean checkIfCountryExists(String country) throws SQLException {
            query = "SELECT * FROM `towns` WHERE country = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }

        private static void changeToUpperCaseForCountry(String country) throws SQLException {
            query = "UPDATE `towns` SET `name` = UPPER (`name`) WHERE country = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, country);
            statement.execute();
        }

        private static void collectCitiesFromCountryToList(String country, List<String> affectedCities) throws SQLException {
            query = "SELECT * FROM `towns` WHERE country = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                affectedCities.add(resultSet.getString("name"));
            }
        }

        private static void printDataAsRequired(List<String> affectedCities) {
            String pluralOrSingle = affectedCities.size() == 1 ? "name was" : "names were";
            System.out.printf("%d town " + pluralOrSingle + " affected.%n", affectedCities.size());
            System.out.println("[" + String.join(", ", affectedCities) + "]");
        }

    }

    //Exercise 6* -> If no rows affected by given ID => Villain does't exist => rollback
    static class ExerciseSix {
        private static void run() throws IOException, SQLException {

            //get villain id
            System.out.print("Enter villain Id: ");
            int villain_id = Integer.parseInt(reader.readLine());

            //extract villain name by id for printing later
            String villain_name = getEntityNameById(villain_id, "villains");

            try {
                connection.setAutoCommit(false);

                int minionsAffected = deleteRelationshipAndCountAffectedMinions(villain_id);
                int villainsAffected = deleteVillainAndCountAffectedVillains(villain_id);

                if (villainsAffected == 0) {
                    System.out.println("No such villain was found");
                    connection.rollback();
                } else {
                    System.out.printf("%s was deleted%n", villain_name);
                    System.out.printf("%d minions released", minionsAffected);
                    connection.commit();
                }


            } catch (SQLException e) {
                connection.rollback();
            }


        }

        private static int deleteRelationshipAndCountAffectedMinions(int villain_id) throws SQLException {
            query = "DELETE FROM `minions_villains` WHERE `villain_id` = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, villain_id);
            return statement.executeUpdate();
        }

        private static int deleteVillainAndCountAffectedVillains(int villain_id) throws SQLException {
            query = "DELETE FROM `villains` WHERE `id` = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, villain_id);
            return statement.executeUpdate();
        }
    }

    //Exercise 7
    static class ExerciseSeven{
        private static void run() throws SQLException {
            //List to hold the names of the minions
            List<String> minionNames = new ArrayList<>();

            ResultSet resultSet = getMinionNamesFromTable();

            addMinionNamesToListFromResultSet(minionNames, resultSet);

            printNamesListInRequiredOrder(minionNames);

        }

        private static ResultSet getMinionNamesFromTable() throws SQLException {
            query = "SELECT `name` FROM `minions`";
            statement = connection.prepareStatement(query);
            statement.execute();
            return statement.getResultSet();
        }

        private static void addMinionNamesToListFromResultSet(List<String> minionNames, ResultSet resultSet) throws SQLException {
            while (resultSet.next()) {
                minionNames.add(resultSet.getString("name"));
            }
        }

        private static void printNamesListInRequiredOrder(List<String> minionNames) {
            // copied from Internet, it works. Will be better with while(), thought
            for (int start_index = 0, end_index = minionNames.size() - 1; start_index < end_index; start_index++, end_index--) {

                System.out.printf("%s%n%s%n", minionNames.get(start_index), minionNames.get(end_index));

            }
        }

    }

    //Exercise 8
    static class ExerciseEight {

        private static void run() throws IOException, SQLException {
            // get minions ids from console and add them to int array
            System.out.print("Enter minions Ids: ");
            String string = reader.readLine();

            int[] minions_ids = Arrays
                    .stream(string.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            for (int currentMinionId : minions_ids) {
                changeMinionNameToLower(currentMinionId);

               increaseMinionAgeByOne(currentMinionId);
            }

            printAllMinionsWithAge();

        }

        private static void changeMinionNameToLower(int currentMinionId) throws SQLException {
            query = "UPDATE minions SET name = LOWER(name)  WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, currentMinionId);
            statement.execute();
        }

        private static void increaseMinionAgeByOne(int currentMinionId) throws SQLException {
            query = "UPDATE minions SET age = age + 1 WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, currentMinionId);
            statement.execute();
        }

        private static void printAllMinionsWithAge() throws SQLException {
            query = "SELECT * FROM minions";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
            }
        }


    }

    //Exercise 9

    static class ExerciseNine {

        // Procedure Code
    /*
    DELIMITER $$
    CREATE PROCEDURE usp_get_older(minion_id INT)
    BEGIN
    UPDATE minions SET age = age + 1 WHERE id = minion_id;
    END $$
    DELIMITER ;
     */


        public static void run() throws IOException, SQLException {
            //get minion data from console
            System.out.print("Enter minion id: ");
            int minion_id = Integer.parseInt(reader.readLine());

            query = "CALL usp_get_older(?)";

            CallableStatement callableStatement = connection.prepareCall(query);

            callableStatement.setInt(1, minion_id);

            callableStatement.execute();

            printSelectedMinionData(minion_id);

        }

        private static void printSelectedMinionData(int minion_id) throws SQLException {
            query = "SELECT name, age FROM minions WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, minion_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%s %d", resultSet.getString("name"), resultSet.getInt("age"));
            }

        }


    }


    //COMMON METHODS

    private static boolean checkIfEntityExistsById(int entityId, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, entityId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();

    }

    private static boolean checkIfEntityExistsByName(String name, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE name = ?";

        statement = connection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();

    }

    private static String getEntityNameById(int entityId, String tableName) throws SQLException {
        query = "SELECT name FROM " + tableName + " WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, entityId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    private static int getEntityIdByName(String name, String tableName) throws SQLException {
        query = "SELECT `id` FROM " + tableName + " WHERE `name` = ?";

        statement = connection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();


        return resultSet.next() ? (resultSet.getInt("id")) : null;

    }


}

