package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    public static final String SELECT_STAR_FROM = "SELECT * FROM ";
    public static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    public static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s;";
    public static final String DELETE_QUERY = "DELETE FROM %s WHERE %s;";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        if (value == null || (int) value <= 0) {
            return this.doInsert(entity, primary);
        }
        return this.doUpdate(entity, primary);
    }


    public Iterable<E> find(Class<E> table) {
        return null;
    }

    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    public E findFirst(Class<E> table) {
        return null;
    }

    public E findFirst(Class<E> table, String where) {
        return null;
    }

    //Utility methods

    private boolean doUpdate(E entity, Field primary) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> fieldNames = getFieldNames(entity);
        List<String> fieldValues = getFieldValues(entity);
        List<String> update = new ArrayList<>();
        for (int i = 0; i < fieldNames.size(); i++) {
            update.add(fieldNames.get(i) + " = " + fieldValues.get(i));
        }

        primary.setAccessible(true);
        String updateQuery = String.format(UPDATE_QUERY,
                tableName,
                update.toString().replaceAll("[\\[\\]]", ""),
                "id = " + primary.get(entity));
        return executeQuery(updateQuery);
    }

    private boolean doInsert(E entity, Field primary) throws SQLException {
        String tableName = getTableName(entity.getClass());
        List<String> fieldNames = getFieldNames(entity);
        List<String> fieldValues = getFieldValues(entity);
        String insertQuery = String.format(INSERT_QUERY, tableName, fieldNames.toString()
                .replaceAll("[\\[\\]]", ""), fieldValues.toString().replaceAll("[\\[\\]]", ""));
        return executeQuery(insertQuery);
    }

    private boolean executeQuery(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        return ps.execute();
    }

    private List<String> getFieldValues(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        Object value = f.get(entity);
                        return f.getType() == String.class || f.getType() == LocalDate.class ?
                                String.format("'%s'", value.toString()) :
                                value.toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                }).collect(Collectors.toList());
    }

    private List<String> getFieldNames(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    f.setAccessible(true);
                    return f.getAnnotation(Column.class).name();
                }).collect(Collectors.toList());
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private String getTableName(Class<?> entity) {
        Entity entityAnnotation = entity.getAnnotation(Entity.class);
        if (entityAnnotation != null && entityAnnotation.name().length() > 0) {
            return entityAnnotation.name();
        } else {
            return entity.getSimpleName();
        }
    }
}
