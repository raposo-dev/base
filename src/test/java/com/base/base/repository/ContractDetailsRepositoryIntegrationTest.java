package com.base.base.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.base.base.models.Contracts;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration-test")
public class ContractDetailsRepositoryIntegrationTest {

  @Autowired ContractsRepository contractsRepository;

  @Test
  public void contractShouldBeSaved() {
    Contracts contracts = mockedContracts();
    contractsRepository.save(contracts);

    Contracts contracts2 = contractsRepository.findById(20).orElseThrow(RuntimeException::new);

    assertNotNull(contracts2);
    assertEquals(contracts.getContracted(), contracts2.getContracted());
    assertEquals(contracts.getContracting(), contracts2.getContracting());
    assertEquals(contracts.getInitialContractualPrice(), contracts2.getInitialContractualPrice());
    assertEquals(contracts.getObjectBriefDescription(), contracts2.getObjectBriefDescription());
  }

  private Contracts mockedContracts() {

    BigDecimal bd = new BigDecimal("6360.10");
    bd = bd.setScale(2, RoundingMode.UNNECESSARY);

    Contracts contracts = new Contracts();
    contracts.setContractingProcedureType("Ajuste Direto Regime Geral");
    contracts.setContracting("Câmara Municipal da Lousã");
    contracts.setContracted("Ana Sofia Francisco Tomás");
    contracts.setObjectBriefDescription(
        "Elaboração das fichas de mão-de-obra, de máquinas e viaturas, para posterior inserção na aplicação informática – Contabilidade de Custos");
    contracts.setInitialContractualPrice(bd);
    contracts.setId(20);
    return contracts;
  }
}
