package com.base.base.repository;

import com.base.base.models.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration-test")
public class ContractDetailsRepositoryIntegrationTest {

  @Autowired
  ContractsRepository contractsRepository;

  @Test
  public void contractShouldBeSaved() {
    Contract contract = mockedContracts();
    contractsRepository.save(contract);

    Contract contract2 = contractsRepository.findById(20).orElseThrow(RuntimeException::new);

    assertNotNull(contract2);
    assertEquals(contract.getContracted(), contract2.getContracted());
    assertEquals(contract.getContracting(), contract2.getContracting());
    assertEquals(contract.getInitialContractualPrice(), contract2.getInitialContractualPrice());
    assertEquals(contract.getObjectBriefDescription(), contract2.getObjectBriefDescription());
  }

  private Contract mockedContracts() {

    BigDecimal bd = new BigDecimal("6360.10");
    bd = bd.setScale(2, RoundingMode.UNNECESSARY);

    Contract contract = new Contract();
    contract.setContractingProcedureType("Ajuste Direto Regime Geral");
    contract.setContracting("Câmara Municipal da Lousã");
    contract.setContracted("Ana Sofia Francisco Tomás");
    contract.setObjectBriefDescription(
            "Elaboração das fichas de mão-de-obra, de máquinas e viaturas, para posterior inserção na aplicação informática – Contabilidade de Custos");
    contract.setInitialContractualPrice(bd);
    contract.setId(20);
    return contract;
  }
}
