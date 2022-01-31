package com.example.BankApp.repository;

import com.example.BankApp.model.Client;
import com.example.BankApp.model.Worker;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class WorkerRepository implements IRestRepository<Worker> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"worker_id\", \"name\", \"lastname\" " +
            "FROM \"worker\" " +
            "ORDER BY \"worker_id\"";

    private static String selectByIdQuery = "SELECT \"worker_id\", \"name\", \"lastname\" " +
            "FROM \"worker\" " +
            "WHERE \"worker_id\" = ?";

    private static String insertQuery = "INSERT INTO \"worker\"(\"name\", \"lastname\") " +
            "VALUES (?, ?) " +
            "RETURNING \"worker_id\", \"name\", \"lastname\"";

    private static String updateQuery = "UPDATE \"worker\" " +
            "SET \"name\" = ?, \"lastname\" = ? " +
            "WHERE \"worker_id\" = ? " +
            "RETURNING \"worker_id\", \"name\", \"lastname\"";

    private static String deleteQuery = "DELETE FROM \"worker\" " +
            "WHERE \"worker_id\" = ? " +
            "RETURNING \"worker_id\", \"name\", \"lastname\"";

    public WorkerRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Worker[] select() {
        ArrayList<Worker> values = new ArrayList<Worker>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Worker(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            ));
        }
        Worker[] result = new Worker[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Worker select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Worker(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Worker insert(Worker entity) {
        Object[] params = new Object[] { entity.getName(), entity.getLastName()};
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Worker(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }


    @Override
    public Worker update(Integer id, Worker entity) {
        Object[] params = new Object[] { entity.getName(), entity.getLastName(), id };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Worker(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Worker delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Worker(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }
}
