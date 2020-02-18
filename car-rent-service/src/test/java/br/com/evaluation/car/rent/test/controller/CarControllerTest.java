package br.com.evaluation.car.rent.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.evaluation.car.rent.controller.CarController;
import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.entity.Category;
import br.com.evaluation.car.rent.main.App;
import br.com.evaluation.car.rent.repository.CarRepository;
import br.com.evaluation.car.rent.repository.CategoryRepository;

/**
 * Test class for {@link CarController}
 *
 * @author gabriel.santos
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= App.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("default")
public class CarControllerTest {

	private static final String BASE_URL = "/car/";
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Category category;
	
	/**
	 * Test save category
	 * @throws Exception
	 */
	@Test
	public void test01SaveCategory() throws Exception {
		category = new Category();
		category.setName("TYPE 1");
		categoryRepository.save(category);
		assertNotNull(categoryRepository.findById(1L));
	}
	
	
	/**
	 * Test field manufacturer missing
	 * @throws Exception
	 */
	@Test
	public void test02FieldManufacturerMissing() throws Exception {
		Car car = loadCarObject();
		car.setManufacturer(null);
	    
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field modelName missing
	 * @throws Exception
	 */
	@Test
	public void test03FieldModelNameMissing() throws Exception {
		Car car = loadCarObject();
		car.setModelName(null);
	    
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test modelYear field missing
	 * @throws Exception
	 */
	@Test
	public void test04FieldModalYearMissing() throws Exception {
		Car car = loadCarObject();
		car.setModelYear(null);
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test Category field missing
	 * @throws Exception
	 */
	@Test
	public void test05CategoryFieldMissing() throws Exception {
		Car car = loadCarObject();
		car.setCategory(null);
	    
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	
	/**
	 * Test save Car without missing fields
	 * @throws Exception
	 */
	@Test
	public void test06SaveCar() throws Exception {
		Car car = loadCarObject();
	    
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isOk());
		
	}
	
	/**
	 * Test save Car without missing fields
	 * @throws Exception
	 */
	@Test
	public void test07SaveTheSameCar() throws Exception {
		Car car = loadCarObject();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		mvc.perform(post(BASE_URL + "save").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
		
	}
	
	/**
	 * Test save Car with inexistent category
	 * @throws Exception
	 */
	@Test
	public void test08SaveCarWithNonExistCategory() throws Exception {
		Car car = loadCarObject();
		Category category = new Category();
		category.setId(200L);
		car.setCategory(category);
		
	    String requestJson = getRequestJson(car);
		mvc.perform(post(BASE_URL + "save").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
		
	}


	private String getRequestJson(Car car) throws JsonProcessingException {
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(car);
		return requestJson;
	}

	
	/**
	 * Test car registry existence
	 * @throws Exception
	 */
	@Test
	public void test09CarRegistryExistance() throws Exception {
		Car car = carRepository.findByManufacturerAndModelNameAndModelYear("BMW", "BMW 2.0", 2019);
		assertNotNull(car);
	}
	
	/**
	 * Test find by category ID
	 * @throws Exception
	 */
	@Test
	public void test10FindByCategoryID() throws Exception {
		MvcResult mvcResult = mvc.perform(get(BASE_URL + "findByCategory/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());
	}
	
	/**
	 * Test find by inexistent category ID
	 * @throws Exception
	 */
	@Test
	public void test10FindByInexistentCategoryID() throws Exception {
		MvcResult mvcResult = mvc.perform(get(BASE_URL + "findByCategory/-1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(), "[]");
	}
	
	
	/**
	 * Test delete car by id
	 * @throws Exception
	 */
	@Test
	public void test11DeleteCarById() throws Exception {
		mvc.perform(delete(BASE_URL + "delete/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	/**
	 * Test delete car by id
	 * @throws Exception
	 */
	@Test
	public void test12CheckRegistryDeleted() throws Exception {
		Car car = carRepository.findByManufacturerAndModelNameAndModelYear("BMW", "BMW 2.0 2019", 2019);
		assertNull(car);
	}
	
	/**
	 * Method aux to load same object
	 * @return
	 */
	private Car loadCarObject() {
		Car car = new Car();
		category = categoryRepository.findById(1L);
		car.setManufacturer("BMW");
		car.setModelName("BMW 2.0");
		car.setModelYear(2019);
		car.setWeekdayLoyaltyPrice(100);
		car.setWeekendLoyaltyPrice(100);
		car.setWeekdayPrice(100);
		car.setWeekendPrice(100);
		car.setCategory(category);
		return car;
	}
}
