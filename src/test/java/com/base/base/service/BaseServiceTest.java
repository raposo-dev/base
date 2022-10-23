package com.base.base.service;

import com.base.base.models.Contract;
import com.base.base.models.contractdetails.ContractDetails;
import com.base.base.models.contractdetails.Contracted;
import com.base.base.repository.ContractDetailsRepository;
import com.base.base.repository.ContractsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BaseServiceTest {

	public static final String CONTRACTED_NIF = "123";
	@InjectMocks
	BaseServiceImpl baseService;

	@Mock
	ContractsRepository contractsRepository;

	@Mock
	ContractDetailsRepository contractDetailsRepository;


	@BeforeEach
	void setUp() {
	}

	@Test
	void shouldReturnAllContracts() {
		List<Contract> contracts = List.of(createContracts(1));
		when(contractsRepository.findAll()).thenReturn(contracts);
		List<Contract> result = baseService.getAllContracts();
		assertEquals(contracts, result);
	}

	@Test
	void shouldReturnContractDetailsByNif() {
		ContractDetails contractDetails = createContractDetails(1, createContracted(CONTRACTED_NIF, "mock"));

		List<ContractDetails> t = List.of(contractDetails);
		when(contractDetailsRepository.findByContracted_Nif(CONTRACTED_NIF)).thenReturn(Optional.of(t));

		var result = baseService.getContractsByContractedNif(CONTRACTED_NIF);
		assertEquals(t, result);
	}

	@Test
	void shouldThrowExceptionWhenContractedNifNotFound() {
		when(contractDetailsRepository.findByContracted_Nif("mock")).thenReturn(Optional.empty());

		var result = assertThrows(IllegalArgumentException.class, () -> baseService.getContractsByContractedNif("mock"),
				"Expected getContractsByContractedNif to throw but didn't");

		assertTrue(result.getMessage().contains("mock"));
	}

	private Contracted createContracted(String nif, String description) {
		Contracted contracted = new Contracted();
		contracted.setNif(nif);
		contracted.setId(1);
		contracted.setDescription(description);

		return contracted;
	}

	private Contract createContracts(Integer id) {
		Contract contract = new Contract();
		contract.setId(id);
		return contract;
	}

	private ContractDetails createContractDetails(Integer id, Contracted contracted) {
		ContractDetails contractDetails = new ContractDetails();
		contractDetails.setId(id);
		contractDetails.setContracted(List.of(contracted));
		return contractDetails;
	}
}