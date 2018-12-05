package demo.customerappservice;

import demo.entity.Customer;
import demo.repository.CustomerRepository;
import demo.resource.CustomerResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAppServiceApplicationTests {

	@Mock
	CustomerRepository customRepoMock;

	@InjectMocks
	private CustomerResource customerResource ;


	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsertCustomer() {
		long idVal=1;
		Customer cust=new Customer();
		cust.setId(idVal);

		when(customRepoMock.save(cust)).thenReturn(cust);

		ResponseEntity<Customer> custtest= customerResource.createCustomer(cust);
		assertEquals(201, custtest.getStatusCode().value());
	}

	@Test(expected=demo.exception.CreditRatingChkVioationException.class)
	public void testInsertCustomer_creditRatingValidate() {
		long idVal=1;
		Customer cust=new Customer();
		cust.setCreditRating(101);
		cust.setId(idVal);

		when(customRepoMock.save(cust)).thenReturn(cust);

		ResponseEntity<Customer> custtest= customerResource.createCustomer(cust);
		assertEquals(201, custtest.getStatusCode().value());
	}

	@Test
	public void testFindTheGreatestFromAllData() {
		long idVal=1;
		Customer cust=new Customer();
		cust.setId(idVal);
		cust.setFirstname("test");
		when(customRepoMock.findById(idVal)).thenReturn(java.util.Optional.ofNullable(cust));
		Resource<Customer> custtest= customerResource.retrieveCustomer(idVal);
		assertNotNull(custtest);
	}

	@Test(expected = demo.exception.CustomerNotFoundException.class)
	public void testFindTheGreatestFromAllData_notFound() {
		long idVal=1;
		long idValTest=2;
		Customer cust=new Customer();
		cust.setId(idVal);
		cust.setFirstname("test");
		when(customRepoMock.findById(idValTest)).thenReturn(java.util.Optional.ofNullable(cust));
		Resource<Customer> custtest= customerResource.retrieveCustomer(idVal);
		assertNotNull(custtest);
	}

	@Test
	public void testretrieveAllCustomers() {
		long idVal=1;
		Customer cust=new Customer();
		cust.setId(idVal);
		cust.setFirstname("test");
		List<Customer> customers=new ArrayList<>();
		((ArrayList) customers).add(cust);
		when(customRepoMock.findAll()).thenReturn(customers);
		List<Customer> custtest= customerResource.retrieveAllCustomers();
		assertNotNull(custtest);
	}

	@Test
	public void testUpdateCustomer() {
		long idVal=1;
		Customer cust=new Customer();
		cust.setId(idVal);
		cust.setFirstname("test");
		when(customRepoMock.findById(idVal)).thenReturn(java.util.Optional.ofNullable(cust));
		ResponseEntity<Object> custtest= customerResource.updateCustomer(cust, idVal);
		assertNotNull(custtest);
	}


}
