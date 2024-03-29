package com.base.base.service;

import com.base.base.client.BaseDbClient;
import com.base.base.client.BaseHttpClient;
import com.base.base.models.Contracts;
import com.base.base.models.contractdetails.ContractDetails;
import com.base.base.repository.ContractDetailsRepository;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BaseIngestionServiceTest {
	private static final String BASE_URL_RESULTS = "http://base.url/results";
	private static final String BASE_URL_CONTRACTS = "http://base.url/contracts";
	private static final String BASE_CONTRACTS_MOCKED_RETURN =
			"[{\"contracted\":\"Ana Sofia Francisco Tomás\",\"contracting\":\"Câmara Municipal da Lousã\",\"contractingProcedureType\":\"Ajuste Direto Regime Geral\",\"publicationDate\":\"14-08-2008\",\"objectBriefDescription\":\"Elaboração das fichas de mão-de-obra, de máquinas e viaturas, para posterior inserção na aplicação informática – Contabilidade de Custos\",\"initialContractualPrice\":\"6.360,10 €\",\"signingDate\":null,\"id\":20}]";
	private static final String BASE_CONTRACT_DETAILS_MOCKED_RETURN =
			"{\"increments\":false,\"contractFundamentationType\":\"Não Preenchido\",\"frameworkAgreementProcedureId\":\"Não aplicável.\",\"documents\":[],\"directAwardFundamentationType\":\"Não aplicável\",\"ambientCriteria\":false,\"invitees\":[],\"publicationDate\":\"14-08-2008\",\"observations\":null,\"contractingProcedureUrl\":null,\"endOfContractType\":\"Cumprimento integral do contrato\",\"totalEffectivePrice\":\"6.360,10 €\",\"announcementId\":-1,\"contestants\":[],\"closeDate\":\"29-09-2008\",\"causesDeadlineChange\":null,\"causesPriceChange\":null,\"frameworkAgreementProcedureDescription\":\"Não aplicável.\",\"contracted\":[{\"nif\":\"226962032\",\"description\":\"Ana Sofia Francisco Tomás\",\"id\":46}],\"contracting\":[{\"nif\":\"501121528\",\"description\":\"Câmara Municipal da Lousã\",\"id\":45}],\"contractingProcedureType\":\"Ajuste Direto Regime Geral\",\"executionDeadline\":\"61 dias\",\"contractTypeCS\":false,\"executionPlace\":\"\",\"centralizedProcedure\":null,\"cpvs\":\"\",\"objectBriefDescription\":\"Elaboração das fichas de mão-de-obra, de máquinas e viaturas, para posterior inserção na aplicação informática – Contabilidade de Custos\",\"income\":false,\"nonWrittenContractJustificationTypes\":\"\",\"initialContractualPrice\":\"6.360,10 €\",\"contractStatus\":null,\"contractTypes\":\"\",\"signingDate\":null,\"cocontratantes\":false,\"description\":null,\"id\":20}";
	@InjectMocks
	BaseIngestionServiceImpl baseService;

	@Mock
	BaseHttpClient baseHttpClient;

	@Mock
	BaseDbClient baseDbClient;


	@Mock
	ContractDetailsRepository contractDetailsRepository;

	@Mock
	Document document;


	@Captor
	private ArgumentCaptor<ContractDetails> listContractDetailsArgumentCaptor;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(baseService, "baseUrlResults", BASE_URL_RESULTS);
		ReflectionTestUtils.setField(baseService, "baseUrlContracts", BASE_URL_CONTRACTS);
	}

	private BufferedReader bufferedReaderFromString(String string) throws IOException {
		InputStream stubInputStream = IOUtils.toInputStream(string, "UTF-8");
		return new BufferedReader(new InputStreamReader(stubInputStream));
	}

	@Test
	public void verifyInsertContractDetails() throws IOException {
		List<Contracts> listIdsNotInContractDetails = new ArrayList<>();
		Contracts contractsIds = new Contracts();
		contractsIds.setId(20);
		listIdsNotInContractDetails.add(contractsIds);

		ContractDetails contractDetails = mapContractDetails();

		when(baseDbClient.getListofIdsNotInContractDetails()).thenReturn(listIdsNotInContractDetails);
		URL mockedUrlContracts = new URL(BASE_URL_CONTRACTS + "/" + contractsIds.getId());
		when(baseHttpClient.getBaseResponseBufferedReader(mockedUrlContracts))
				.thenReturn(bufferedReaderFromString(BASE_CONTRACT_DETAILS_MOCKED_RETURN));

		baseService.insertContractDetails();

		verify(contractDetailsRepository).save(listContractDetailsArgumentCaptor.capture());
		assertEquals(contractDetails.getId(), listContractDetailsArgumentCaptor.getValue().getId());
	}

	private Contracts mapContracts() {

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

	private ContractDetails mapContractDetails() {

		ContractDetails contractDetails = new ContractDetails();

		contractDetails.setId(20);
		return contractDetails;
	}
}
