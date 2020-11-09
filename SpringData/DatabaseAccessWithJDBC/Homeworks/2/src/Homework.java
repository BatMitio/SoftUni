import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Homework {

    private static final  String
            DB_URL ="jdbc:mysql://localhost:3306/";
    private static final String  MINIONS_TABLE_NAME = "minions_db";

    private static final String SET_COMPLIANT_TIME_ZONE = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private Connection connection;

    private BufferedReader reader;

    public Homework() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setConnection(String user, String password) {
        Properties properties = new Properties();

        properties.setProperty("user", user);
        properties.setProperty("password", password);

        try {
            connection = DriverManager
                    .getConnection(DB_URL + MINIONS_TABLE_NAME + SET_COMPLIANT_TIME_ZONE, properties);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }


    }

    public void getVillainsNamesEx2() throws SQLException {

        String query = "SELECT v.name, COUNT(mv.minion_id) AS 'count' FROM villains AS v\n" +
                "JOIN minions_villains  mv ON v.id = mv.villain_id\n" +
                "GROUP BY v.id\n" +
                "HAVING count > 15\n" +
                "ORDER BY count DESC";

        PreparedStatement preparedStatement;
        ResultSet resultSet = null;

        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        while (resultSet.next()){

            System.out.printf("%s %d %n", resultSet.getString(1),
                    resultSet.getInt((2)));
        }

    }

    public void MinionNamesEx3() throws IOException, SQLException {

        System.out.printf("Enter id:");
        int villainId = Integer.parseInt(reader.readLine());

        String villaintName = getEntityNameById(villainId, "villains");
        if(villaintName == null){
            System.out.printf("No villain with ID %d exists in the database. %n", villainId);
            return;
        }

        System.out.printf("Villain: %s%n", villaintName);

        String query = "SELECT m.name, m.age FROM minions AS m\n" +
                "                              JOIN minions_villains AS mv\n" +
                "                                   ON m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();

        int counter = 1;
        while (resultSet.next()){
            System.out.printf("%d. %s %d%n", counter++, resultSet.getString("name"),
                    resultSet.getInt("age"));
        }

    }



    private String getEntityNameById(int entityId, String tableName) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement statement = connection
                .prepareStatement(query);

        statement.setInt(1, entityId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;

    }

    public void AddMinionEx4() throws IOException, SQLException {
        System.out.println("Minions and Villains info - name, age, town name (as in example 2 line together):" );

        String[] minionInfo = reader.readLine().split("\\s+");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String townName = minionInfo[3];

        String[] villainInfo = reader.readLine().split("\\s+");
        String villainName = villainInfo[1];


        int town_id = getEntityIdByName(townName, "towns");
        int minion_id = getEntityIdByName(minionName, "minions");
        int villain_id = getEntityIdByName(villainName, "villains");


        if (town_id < 0 ) {
            insertEntryInTowns(townName);
            System.out.printf("Town %s was added to the database.%n", townName);
        }


        if (minion_id < 0){
            insertEntryInMinions(minionName, minionAge);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
        if (villain_id < 0){
            insertEntryInVillain(villainName);
            System.out.printf("Successfully added %s to be minion of Gru.%n", minionName);
        }


        minion_id = getEntityIdByName(minionName, "minions");
        villain_id = getEntityIdByName(villainName, "villains");
        insertMinionVillainRelation(minion_id, villain_id);



    }

    private void insertMinionVillainRelation(int minion_id, int villain_id) throws SQLException {
        String query = "INSERT INTO minions_villains(minion_id, villain_id) \n" +
                "VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, minion_id);
        statement.setInt(2, villain_id);
        statement.execute();

    }

    private void insertEntryInVillain(String villainName) throws SQLException {
        String query = "INSERT INTO villains(`name`)\n" +
                "VALUES (?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, villainName);

         statement.execute();
    }

    private void insertEntryInMinions(String minionName, int minionAge) throws SQLException {
        String query = "INSERT INTO minions(`name`, age)\n" +
                "VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.execute();


    }

    private void insertEntryInTowns(String townName) throws SQLException {
        String query = "INSERT INTO towns(name) VALUE (?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, townName);
        statement.executeUpdate();

    }

    private int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entityName);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    public void changeTownNameCasingEx5() throws IOException, SQLException {
        System.out.printf("Enter country name:");
        String countryName = reader.readLine();
        String query = "UPDATE towns SET `name` = UPPER(`name`) WHERE country = ? ";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, countryName);

        int townsModified =  statement.executeUpdate();

        String queryOutput = "SELECT `name` FROM towns\n" +
                "WHERE country = ?";
        PreparedStatement statementOutput = connection.prepareStatement(queryOutput);
        statementOutput.setString(1, countryName);
        ResultSet resultSet = statementOutput.executeQuery();

        StringBuilder output = new StringBuilder();
        String prefix = "[";

        while (resultSet.next()){
            output.append(prefix);
            prefix = ", ";
            output.append(resultSet.getString(1));

        }

        output.append("]");



        if (townsModified > 0){
            System.out.printf("%d town names were affected.%n", townsModified);
            System.out.println(output.toString());
        } else {
            System.out.println("No town names were affected.");
        }




    }


    public void increaseAgeWithStoredProcedureEx9() throws IOException, SQLException {
        System.out.printf("Enter minion ID:");
        int minion_id = Integer.parseInt(reader.readLine());

        String query = "CALL usp_get_older(?)";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, minion_id);
        callableStatement.execute();

        String queryOutput = "SELECT `name`, age FROM minions\n" +
                "WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(queryOutput);
        preparedStatement.setInt(1, minion_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d", resultSet.getString("name"), resultSet.getInt("age"));
        }


    }

    public void increaseMinionsAgeEx8() throws IOException, SQLException {
        System.out.printf("Enter Minion IDs (separated by space):");
        String inputIDs = reader.readLine().trim();
        inputIDs = inputIDs.replaceAll("\\s+", ",");


        String query = String.format("UPDATE minions\n" +
                "SET age = age + 1\n" +
                "WHERE id in (%s)", inputIDs);



        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();

        String queryOutput = "SELECT LOWER(`name`), age FROM minions";

        PreparedStatement preparedStatementOutput = connection.prepareStatement(queryOutput);
        ResultSet resultSet =  preparedStatementOutput.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d %n", resultSet.getString(1), resultSet.getInt("age"));
        }

        System.out.println();


    }

    public void printAllMinionNamesEx7() throws SQLException {

        String query = "SELECT `name` FROM minions";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> minion_names = new ArrayList<>();

        while (resultSet.next()){
            minion_names.add(resultSet.getString("name"));
        }

        for (int i = 0; i < minion_names.size() / 2; i++) {
            System.out.println(minion_names.get(i));
            System.out.println(minion_names.get(minion_names.size() - 1 - i));

        }





    }
}
