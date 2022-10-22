package com.base.base.client;

import com.base.base.models.Contracts;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BaseDbClientImpl implements BaseDbClient {
    final JdbcTemplate jdbcTemplate;

    public BaseDbClientImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertContracts(List<Contracts> contractsList) {
        String sql =
                "MERGE INTO contracts\n"
                        + "(id, contracting_procedure_type, publication_date, contracting, contracted,"
                        + " object_brief_description, initial_contractual_price, signing_date)\n"
                        + "    VALUES (\n"
                        + "            ?,?,?,?,?,?,?,?\n"
                        + "           )";
        int[] result =
                jdbcTemplate.batchUpdate(
                        sql,
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement preparedStatement, int i)
                                    throws SQLException {
                                preparedStatement.setInt(1, contractsList.get(i).getId());
                                preparedStatement.setString(2, contractsList.get(i).getContractingProcedureType());
                                preparedStatement.setDate(3, contractsList.get(i).getPublicationDate());
                                preparedStatement.setString(4, contractsList.get(i).getContracting());
                                preparedStatement.setString(5, contractsList.get(i).getContracted());
                                preparedStatement.setString(6, contractsList.get(i).getObjectBriefDescription());
                                preparedStatement.setBigDecimal(
                                        7, contractsList.get(i).getInitialContractualPrice());
                                preparedStatement.setDate(8, contractsList.get(i).getSigningDate());
                            }

                            public int getBatchSize() {
                                return contractsList.size();
                            }
                        });
        return result.length;
    }

    @Override
    public List<Contracts> getListofIdsNotInContractDetails() {
        String sql =
                "SELECT contracts.id as id\n"
                        + "FROM contracts\n"
                        + "LEFT JOIN\n"
                        + "    contract_details cd on contracts.id = cd.id\n"
                        + "WHERE cd.id is null";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Contracts.class));
    }
}
