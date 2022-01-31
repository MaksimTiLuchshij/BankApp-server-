package com.example.BankApp.repository;

import com.example.BankApp.model.Transaction;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class TransactionRepository implements IRestRepository<Transaction> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\" " +
            "FROM \"transaction\" " +
            "ORDER BY \"transaction_id\"";

    private static String selectByIdQuery = "SELECT \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\" " +
            "FROM \"transaction\" " +
            "WHERE \"transaction_id\" = ?";

    private static String selectByWorkerIdQuery = "SELECT \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\" " +
            "FROM \"transaction\" " +
            "WHERE \"worker_id\" = ?";

    private static String insertQuery = "INSERT INTO \"transaction\"(\"worker_id\", \"bankaccount_id\", \"amount\", \"status\") " +
            "VALUES (?, ?, ?, ?) " +
            "RETURNING \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\"";

    private static String updateQuery = "UPDATE \"transaction\" " +
            "SET \"worker_id\" = ?, \"bankaccount_id\" = ?, \"amount\" = ?, \"status\" = ? " +
            "WHERE \"transaction_id\" = ? " +
            "RETURNING \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\"";

    private static String deleteQuery = "DELETE FROM \"transaction\" " +
            "WHERE \"transaction_id\" = ? " +
            "RETURNING \"transaction_id\", \"worker_id\", \"bankaccount_id\", \"amount\", \"status\"";

    public TransactionRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Transaction[] select() {
        ArrayList<Transaction> values = new ArrayList<Transaction>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Transaction(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getDouble(4),
                    rowSet.getString(5)
            ));
        }
        Transaction[] result = new Transaction[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Transaction select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Transaction(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getDouble(4),
                rowSet.getString(5)
        );
    }

    public Transaction[] selectByWorkerId(Integer worker_id) {
        ArrayList<Transaction> values = new ArrayList<Transaction>();
        Object[] params = new Object[] { worker_id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByWorkerIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Transaction(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getDouble(4),
                    rowSet.getString(5)
            ));
        }
        Transaction[] result = new Transaction[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Transaction insert(Transaction entity) {
        Object[] params = new Object[] { entity.getIdWorker(), entity.getIdBankAccount(), entity.getAmount(), entity.getStatus()};
        int[] types = new int[] { Types.INTEGER, Types.BIGINT, Types.DOUBLE, Types.CHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Transaction(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getDouble(4),
                rowSet.getString(5)
        );
    }

    @Override
    public Transaction update(Integer id, Transaction entity) {
        Object[] params = new Object[] { entity.getIdWorker(), entity.getIdBankAccount(), entity.getAmount(), entity.getStatus(), id };
        int[] types = new int[] { Types.INTEGER, Types.BIGINT, Types.DOUBLE, Types.CHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Transaction(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getDouble(4),
                rowSet.getString(5)
        );
    }


    @Override
    public Transaction delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Transaction(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getDouble(4),
                rowSet.getString(5)
        );
    }
}
