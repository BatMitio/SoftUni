package constants;

public final class Const {
    public static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    public static final String DATABASE_NAME = "soft_uni";
    public static final String LOAD_DATABASE_DRIVER_MESSAGE = "DB Driver successfully loaded: '%s'";
    public static final String CREATE_DATABASE_MESSAGE = "Successfully created DB with URL: '%s%s'";
    public static final String SET_DATABASE_CONNECTION_MESSAGE = "Successfully connected to DB with URL: '%s%s'";
    public static final String TASKS =
            """
                    \u001B[46m\u001B[1m\u001B[30mList of tasks to run:\u001B[0m
                    
                    \u001B[46m\u001B[1m\u001B[30m02\u001B[0m Change Casing;
                    
                    \u001B[46m\u001B[1m\u001B[30m03\u001B[0m Contains Employee;
                    
                    \u001B[46m\u001B[1m\u001B[30m04\u001B[0m Employees with Salary Over 50 000;
                    
                    \u001B[46m\u001B[1m\u001B[30m05\u001B[0m Employees from Department;
                    
                    \u001B[46m\u001B[1m\u001B[30m06\u001B[0m Adding a New Address and Updating Employee;
                    
                    \u001B[46m\u001B[1m\u001B[30m07\u001B[0m Addresses with Employee Count;
                    
                    \u001B[46m\u001B[1m\u001B[30m08\u001B[0m Get Employee with Project;
                    
                    \u001B[46m\u001B[1m\u001B[30m09\u001B[0m Find Latest 10 Projects;
                    
                    \u001B[46m\u001B[1m\u001B[30m10\u001B[0m Increase Salaries;
                    
                    \u001B[46m\u001B[1m\u001B[30m11\u001B[0m Find Employees by First Name;
                    
                    \u001B[46m\u001B[1m\u001B[30m12\u001B[0m Employees Maximum Salaries;
                    
                    \u001B[46m\u001B[1m\u001B[30m13\u001B[0m Remove Towns;
                    Enter a task number or type "q" to terminate program:\s""";
    public static final String INVALID_TASK_NUMBER = "Invalid task number entered " + "\u274C";
    public static final String QUIT = "q";
    public static final String QUIT_MESSAGE = "Bye! See you next time " + "\uD83D\uDE04" + " " + "\u2764";
    public static final String DELIMITER = "-".repeat(100);
    public static final String TOWNS_PRINTABLE = "Towns:";
    public static final String IS_NAME_CONTAINED_PRINTABLE = "The name is contained in DB:";
    public static final String EMPLOYEES_PRINTABLE = "Employees:";
    public static final String EMPLOYEES_DEPARTMENTS_SALARIES_PRINTABLE = "%s %s from Research and Development - $%.2f";
    public static final String ADDED_ADDRESS_MESSAGE = "Address with text 'Vitoshka 15' added to DB";
    public static final String NOT_ADDED_ADDRESS_MESSAGE = "Address with text 'Vitoshka 15' already exists in DB";
    public static final String NULL_EMPLOYEE_MESSAGE = "No such employee with %s exists in DB";
    public static final String NOT_NULL_EMPLOYEE_MESSAGE = "Successfully updated address of employee %s %s with ID %d";
    public static final String ADDRESSES_PRINTABLE = "%s, %s - %d employees";
    public static final String SINGLE_EMPLOYEE_PRINTABLE = "%s %s - %s";
    public static final String SINGLE_PROJECT_PRINTABLE = "        %s";
    public static final String PROJECT_NAME_PRINTABLE = "Project name: %s";
    public static final String PROJECT_DESCRIPTION_PRINTABLE = "        Project Description: %s";
    public static final String PROJECT_START_DATE_PRINTABLE = "        Project Start Date: %s";
    public static final String PROJECT_END_DATE_PRINTABLE = "        Project End Date: %s";
    public static final String EMPLOYEE_SALARY_PRINTABLE = "%s %s ($%.2f)";
    public static final String EMPLOYEE_INFO_PRINTABLE = "%s %s - %s - ($%.2f)";
    public static final String DEPARTMENT_SALARY_PRINTABLE = "%s %.2f";
    public static final String ADDRESSES_DELETED_PRINTABLE = "%d address%s in %s deleted";
    public static final String NULL_TOWN_MESSAGE = "No such town with name '%s' exists in DB";
}