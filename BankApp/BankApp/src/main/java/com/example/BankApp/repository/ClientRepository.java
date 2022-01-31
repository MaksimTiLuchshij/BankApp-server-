package com.example.BankApp.repository;

import com.example.BankApp.model.Client;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class ClientRepository implements IRestRepository<Client> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"client_id\", \"name\", \"lastname\", \"phonenumber\", \"e_mail\" " +
            "FROM \"client\" " +
            "ORDER BY \"client_id\"";

    private static String selectByIdQuery = "SELECT \"client_id\", \"name\", \"lastname\", \"phonenumber\", \"e_mail\" " +
            "FROM \"client\" " +
            "WHERE \"client_id\" = ?";

    private static String insertQuery = "INSERT INTO \"client\"(\"name\", \"lastname\", \"phonenumber\", \"e_mail\") " +
            "VALUES (?, ?, ?, ?) " +
            "RETURNING \"client_id\", \"name\", \"lastname\", \"phonenumber\", \"e_mail\"";

    private static String updateQuery = "UPDATE \"client\" " +
            "SET \"name\" = ?, \"lastname\" = ?, \"phonenumber\" = ?, \"e_mail\" = ? " +
            "WHERE \"client_id\" = ? " +
            "RETURNING \"client_id\", \"name\", \"lastname\", \"phonenumber\", \"e_mail\"";

    private static String deleteQuery = "DELETE FROM \"client\" " +
            "WHERE \"client_id\" = ? " +
            "RETURNING \"client_id\", \"name\", \"lastname\", \"phonenumber\", \"e_mail\"";

    public ClientRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Client[] select() {
        ArrayList<Client> values = new ArrayList<Client>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Client(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3),
                    rowSet.getString(4),
                    rowSet.getString(5)
            ));
        }
        Client[] result = new Client[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Client select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Client(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }

    @Override
    public Client insert(Client entity) {
        Object[] params = new Object[] { entity.getName(), entity.getLastName(), entity.getPhoneNumber(), entity.getEmail() };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Client(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }


    @Override
    public Client update(Integer id, Client entity) {
        Object[] params = new Object[] { entity.getName(), entity.getLastName(), entity.getPhoneNumber(), entity.getEmail(), id };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Client(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }

    @Override
    public Client delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Client(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }
}
