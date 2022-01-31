package com.example.BankApp.repository;

import com.example.BankApp.model.BankAccount;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class BankAccountRepository implements IRestRepository<BankAccount> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"bankaccount_id\", \"numberbankacc\", \"balance\", \"client_id\" " +
            "FROM \"bankaccount\" " +
            "ORDER BY \"bankaccount_id\"";

    private static String selectByIdQuery = "SELECT \"bankaccount_id\", \"numberbankacc\", \"balance\", \"client_id\" " +
            "FROM \"bankaccount\" " +
            "WHERE \"bankaccount_id\" = ?";

    private static String selectByClientIdQuery = "SELECT \"bankaccount_id\", \"numberbankacc\", \"balance\", \"client_id\" " +
            "FROM \"bankaccount\" " +
            "WHERE \"bankaccount_id\" = ?";

    private static String insertQuery = "INSERT INTO \"bankaccount\"(\"numberbankacc\", \"balance\", \"client_id\") " + "VALUES (?, ?, ?) " +
            "RETURNING \"bankaccount_id\",\"numberbankacc\", \"balance\", \"client_id\"";

    private static String updateQuery = "UPDATE \"bankaccount\" " +
            "SET \"numberbankacc\" = ?, \"balance\" = ?, \"client_id\" = ? " +
            "WHERE \"bankaccount_id\" = ? " +
            "RETURNING \"bankaccount_id\",\"numberbankacc\", \"balance\", \"client_id\"";

    private static String updateBalanceQuery = "UPDATE \"bankaccount\" " +
            "SET \"balance\" = ? " +
            "WHERE \"bankaccount_id\" = ? " +
            "RETURNING \"bankaccount_id\",\"numberbankacc\", \"balance\", \"client_id\"";

    private static String deleteQuery = "DELETE FROM \"bankaccount\" " +
            "WHERE \"bankaccount_id\" = ? " +
            "RETURNING \"bankaccount_id\",\"numberbankacc\", \"balance\", \"client_id\"";

    private static String selectBalance = "SELECT \"balance\" " +
            "FROM \"bankaccount\" " +
            "WHERE \"bankaccount_id\" = ?";

    public BankAccountRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }
//  Добавить select для банковского баланса и считывать с него
    @Override
    public BankAccount[] select() {
        ArrayList<BankAccount> values = new ArrayList<BankAccount>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new BankAccount(
                    rowSet.getInt(1),
                    rowSet.getLong(2),
                    rowSet.getDouble(3),
                    rowSet.getInt(4)
            ));
        }
        BankAccount[] result = new BankAccount[values.size()];
        result = values.toArray(result);
        return result;
    }



    @Override
    public BankAccount select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new BankAccount(
                rowSet.getInt(1),
                rowSet.getLong(2),
                rowSet.getDouble(3),
                rowSet.getInt(4)
        );
    }

    public BankAccount[] selectByClientId(Integer clientId) {
        ArrayList<BankAccount> values = new ArrayList<BankAccount>();
        Object[] params = new Object[]{clientId};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByClientIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new BankAccount(
                    rowSet.getInt(1),
                    rowSet.getLong(2),
                    rowSet.getDouble(3),
                    rowSet.getInt(4)
            ));
        }
        BankAccount[] result = new BankAccount[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public BankAccount insert(BankAccount entity) {
        Object[] params = new Object[]{entity.getNumberBankAcc(), entity.getBalance(), entity.getIdClient()};
        int[] types = new int[]{Types.BIGINT, Types.DOUBLE, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new BankAccount(
                rowSet.getInt(1),
                rowSet.getLong(2),
                rowSet.getDouble(3),
                rowSet.getInt(4)
        );
    }

    @Override
    public BankAccount update(Integer id, BankAccount entity) {
        Object[] params = new Object[] { entity.getNumberBankAcc(), entity.getBalance(), entity.getIdClient(), id };
        int[] types = new int[] { Types.BIGINT, Types.DECIMAL, Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new BankAccount(
                rowSet.getInt(1),
                rowSet.getLong(2),
                rowSet.getDouble(3),
                rowSet.getInt(4)
        );
    }

    @Override
    public BankAccount delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new BankAccount(
                rowSet.getInt(1),
                rowSet.getLong(2),
                rowSet.getDouble(3),
                rowSet.getInt(4)
        );
    }

    public BankAccount updateBalance(Integer id,double result) {
        Object[] params = new Object[]{result, id};
        int[] types = new int[]{Types.DECIMAL, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateBalanceQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new BankAccount(
                rowSet.getInt(1),
                rowSet.getLong(2),
                rowSet.getDouble(3),
                rowSet.getInt(4)
        );
    }
}