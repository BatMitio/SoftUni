import java.sql.*;
import java.util.*;

public class Homework {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_SCHEMA = "minions_db";
    private static final String TIMEZONE_FLAG = "?serverTimezone=UTC";

    private Connection connection;

    public void setConnection(String username, String password) {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_SCHEMA + TIMEZONE_FLAG, properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getVillainsNamesEx2() {
        String queryStatement = "SELECT v.`name`, COUNT(mv.`minion_id`) AS `minions_count`\n" +
                "FROM `villains` AS v\n" +
                "JOIN `minions_villains` AS mv\n" +
                "ON v.`id` = mv.`villain_id`\n" +
                "GROUP BY v.`id`\n" +
                "HAVING `minions_count` > 15\n" +
                "ORDER BY `minions_count` DESC;";
        try {
            PreparedStatement ps = connection.prepareStatement(queryStatement);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("minions_count"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getMinionNamesEx3() {
        Scanner sc = new Scanner(System.in);
        int villainId = Integer.parseInt(sc.nextLine());

        try {
            PreparedStatement getVillainNameStatement =
                    connection.prepareStatement("SELECT `name`\n" +
                            "FROM `villains`\n" +
                            "WHERE `id` = ?;");
            getVillainNameStatement.setInt(1, villainId);
            ResultSet rs = getVillainNameStatement.executeQuery();
            String villainName = "";
            if (rs.next())
                villainName = rs.getString("name");
            else {
                System.out.printf("No villain with ID %d exists in the database.%n", villainId);
                return;
            }


            PreparedStatement getVillainMinions =
                    connection.prepareStatement("SELECT m.`name`, m.`age`\n" +
                            "FROM `minions_villains` AS mv\n" +
                            "JOIN `minions` AS m\n" +
                            "ON m.`id` = mv.`minion_id`\n" +
                            "WHERE mv.`villain_id` = ?;");
            getVillainMinions.setInt(1, villainId);
            rs = getVillainMinions.executeQuery();
            int c = 1;
            System.out.printf("Villain: %s%n", villainName);
            while (rs.next()) {
                System.out.printf("%d. %s %d%n", c, rs.getString("name"), rs.getInt("age"));
                ++c;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getEntityIdByName(String tableName, String entityName){
        String query = String.format("SELECT id FROM %s WHERE name = ?",tableName);
        try {
            PreparedStatement queryStatement = connection.prepareStatement(query);
            queryStatement.setString(1, entityName);
            ResultSet rs = queryStatement.executeQuery();
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void addMinionEx4() {
        //Reading the input data
        Scanner sc = new Scanner(System.in);
        String[] data = sc.nextLine().split("\\s+");
        String minionName = data[1];
        int minionAge = Integer.parseInt(data[2]);
        String minionTown = data[3];
        data = sc.nextLine().split("\\s+");
        String villainName = data[1];

        try {
            //Checking for town
            int townId = getEntityIdByName("towns", minionTown);
            if(townId == -1){
                //Adding the town is it doesn't exist
                PreparedStatement addTown =
                        connection.prepareStatement("INSERT INTO `towns` (`name`)\n" +
                                "VALUES\n" +
                                "(?);");
                addTown.setString(1, minionTown);
                addTown.executeUpdate();
                System.out.printf("Town %s was added to the database.%n", minionTown);
            }
            townId = getEntityIdByName("towns", minionTown);

            //Checking for villain
            int villainId = getEntityIdByName("villains", villainName);
            if (villainId == -1) {
                //Adding the villain is he doesn't exist
                PreparedStatement addVillain =
                        connection.prepareStatement("INSERT INTO `villains` (`name`, `evilness_factor`)\n" +
                                "VALUES\n" +
                                "(?, 'evil');");
                addVillain.setString(1, villainName);
                addVillain.executeUpdate();
                System.out.printf("Villain %s was added to the database.%n", villainName);
            }

            //Getting the id of the villain either if we've added him or not
            villainId = getEntityIdByName("villains", villainName);


            int minionId = getEntityIdByName("minions", minionName);
            if (minionId == -1) {
                //Adding the minion
                PreparedStatement addMinion =
                        connection.prepareStatement("INSERT INTO `minions` (`name`, `age`, `town_id`)\n" +
                                "VALUES\n" +
                                "(?, ?, ?);");
                addMinion.setString(1, minionName);
                addMinion.setInt(2, minionAge);
                addMinion.setInt(3, townId);
                addMinion.executeUpdate();
            }

            //Getting the id of the minion either if we've added him or not
            minionId = getEntityIdByName("minions", minionName);

            //Checking if there is already a relation between the villain and minion
            PreparedStatement checkForMinionVillainRelation =
                    connection.prepareStatement("SELECT *\n" +
                            "FROM `minions_villains`\n" +
                            "WHERE `minion_id` = ? AND `villain_id` = ?;");
            checkForMinionVillainRelation.setInt(1, minionId);
            checkForMinionVillainRelation.setInt(2, villainId);
            ResultSet rs = checkForMinionVillainRelation.executeQuery();
            if(!rs.next()){
                //Finally making the minion being servant to the villain by adding record to the many-to-many relationship mapping table
                PreparedStatement makeMinionServantToVillain =
                        connection.prepareStatement("INSERT INTO `minions_villains` VALUES (?, ?);");
                makeMinionServantToVillain.setInt(1, minionId);
                makeMinionServantToVillain.setInt(2, villainId);
                makeMinionServantToVillain.executeUpdate();
                System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changeTownNamesCasingEx5(){
        Scanner sc = new Scanner(System.in);
        String country = sc.nextLine();
        try {
            PreparedStatement getNotUpdatedTowns =
                connection.prepareStatement("SELECT `name`\n" +
                        "FROM `towns`\n" +
                        "WHERE `country` = ?");
            getNotUpdatedTowns.setString(1, country);
            ResultSet rs = getNotUpdatedTowns.executeQuery();
            List<String> towns = new ArrayList<>();

            while (rs.next()){
                towns.add(rs.getString("name").toUpperCase());
            }

            if(towns.size() <= 0){
                System.out.println("No town names were affected.");
                return;
            }

            PreparedStatement makeUpperCase =
                    connection.prepareStatement("UPDATE `towns`\n" +
                            "SET `name` = upper(`name`)\n" +
                            "WHERE `country` = ?;");
            makeUpperCase.setString(1, country);
            makeUpperCase.executeUpdate();

            System.out.printf("%s town names were affected.%n", towns.size());
            System.out.println(towns.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeVillainEx6(){
        Scanner sc = new Scanner(System.in);
        int villainId = Integer.parseInt(sc.nextLine());

        try {
            connection.setAutoCommit(false);
            PreparedStatement villainExists =
                    connection.prepareStatement("SELECT `name`\n" +
                            "FROM `villains`\n" +
                            "WHERE `id` = ?;");
            villainExists.setInt(1,villainId);
            ResultSet rs = villainExists.executeQuery();
            if(!rs.next()){
                System.out.println("No such villain was found");
                connection.rollback();
                return;
            }
            String villainName = rs.getString("name");

            System.out.printf("%s was deleted%n", villainName);
            PreparedStatement removeRelation =
                    connection.prepareStatement("DELETE FROM `minions_villains`\n" +
                            "WHERE `villain_id` = ?;");
            removeRelation.setInt(1,villainId);
            int removedEntities = removeRelation.executeUpdate();
            System.out.printf("%d minions released%n", removedEntities);

            PreparedStatement deleteVillain =
                    connection.prepareStatement("DELETE FROM `villains`\n" +
                            "WHERE `id` = ?;");
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printAllMinionsNamesEx7(){
        try {
            PreparedStatement getMinionsNames =
                    connection.prepareStatement("SELECT `name`\n" +
                            "FROM `minions`;");
            ResultSet rs = getMinionsNames.executeQuery();
            List<String> names = new ArrayList<>();
            while (rs.next()){
                names.add(rs.getString("name"));
            }

            for (int i = 0; i < names.size() / 2; i++) {
                System.out.println(names.get(i));
                System.out.println(names.get(names.size() - i - 1));
            }
            if(names.size() % 2 == 1)
                System.out.println(names.get(names.size() / 2 + 1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void increaseMinionsAgeEx8(){
        Scanner sc = new Scanner(System.in);
        int[] ids = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        try {
            PreparedStatement updateMinion =
                    connection.prepareStatement("UPDATE `minions`\n" +
                            "SET `name` = lower(`name`), `age` = `age` + 1\n" +
                            "WHERE `id` = ?;");
            for (int i = 0; i < ids.length; i++) {
                updateMinion.setInt(1, ids[i]);
                updateMinion.executeUpdate();
            }
            PreparedStatement getMinions =
                    connection.prepareStatement("SELECT `id`, `name`, `age`\n" +
                            "FROM `minions`;");
            ResultSet rs = getMinions.executeQuery();
            while (rs.next()){
                System.out.printf("%d %s %d%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void increaseAgeStoredProcedureEx9(){
        Scanner sc = new Scanner(System.in);
        int minionId = Integer.parseInt(sc.nextLine());
        try {
            PreparedStatement updateMinion =
                    connection.prepareStatement("CALL usp_get_older(?)");
            updateMinion.setInt(1, minionId);
            updateMinion.executeUpdate();
            PreparedStatement getUpdatedMinion =
                    connection.prepareStatement("SELECT `name`, `age`\n" +
                            "FROM `minions`\n" +
                            "WHERE `id` = ?;");
            getUpdatedMinion.setInt(1,minionId);
            ResultSet rs = getUpdatedMinion.executeQuery();
            rs.next();
            System.out.printf("%s %s%n", rs.getString("name"), rs.getInt("age"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
