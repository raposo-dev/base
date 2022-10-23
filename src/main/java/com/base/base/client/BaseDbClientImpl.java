package com.base.base.client;

import com.base.base.models.Contract;
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
    public void insertContracts(List<Contract> contractList) {
        String sql =
                "MERGE INTO contract\n"
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
                                preparedStatement.setInt(1, contractList.get(i).getId());
                                preparedStatement.setString(2, contractList.get(i).getContractingProcedureType());
                                preparedStatement.setDate(3, contractList.get(i).getPublicationDate());
                                preparedStatement.setString(4, contractList.get(i).getContracting());
                                preparedStatement.setString(5, contractList.get(i).getContracted());
                                preparedStatement.setString(6, contractList.get(i).getObjectBriefDescription());
                                preparedStatement.setBigDecimal(
                                        7, contractList.get(i).getInitialContractualPrice());
                                preparedStatement.setDate(8, contractList.get(i).getSigningDate());
                            }

                            public int getBatchSize() {
                                return contractList.size();
                            }
                        });
    }

    @Override
    public List<Contract> getListofIdsNotInContractDetails() {
        String sql =
                "SELECT contract.id as id\n"
                        + "FROM contract\n"
                        + "LEFT JOIN\n"
                        + "    contract_details cd on contract.id = cd.id\n"
                        + "WHERE cd.id is null";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Contract.class));
    }
}
