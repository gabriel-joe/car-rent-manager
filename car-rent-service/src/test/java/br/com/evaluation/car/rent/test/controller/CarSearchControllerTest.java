package br.com.evaluation.car.rent.test.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.json.JSONObject;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.evaluation.car.rent.domain.CarRent;
import br.com.evaluation.car.rent.domain.CarRentSearch;
import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.main.App;
import br.com.evaluation.car.rent.service.CarRentService;
import br.com.evaluation.car.rent.service.CarService;
import br.com.evaluation.car.rent.service.CategoryService;

/**
 * Test class for {@link CarSearchControllerTest}
 *
 * @author gabriel.santos
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= App.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("default")
public class CarSearchControllerTest {
	
	
	private static final String BASE_URL = "/rent/";
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private CarRentService carRentService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CategoryService categoryService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Test field dateFrom missing
	 * @throws Exception
	 */
	@Test
	public void test1FieldDateFromMissing() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateTo", "2019-11-05");
		jsonObject.put("loyalty", true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "search").contentType(MediaType.APPLICATION_JSON)
		        .content(jsonObject.toString(1)))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field dateTo missing
	 * @throws Exception
	 */
	@Test
	public void test1FieldDateToMissing() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateFrom", "2019-10-31");
		jsonObject.put("loyalty", true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "search").contentType(MediaType.APPLICATION_JSON)
		        .content(jsonObject.toString(1)))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field dateTo before dateFrom
	 * @throws Exception
	 */
	@Test
	public void test1DateToBeforeDateFrom() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateFrom", "2019-10-31");
		jsonObject.put("dateTo", "2019-10-30");
		jsonObject.put("loyalty", true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "search").contentType(MediaType.APPLICATION_JSON)
		        .content(jsonObject.toString(1)))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field dateTo before dateFrom
	 * @throws Exception
	 */
	@Test
	public void test2SaveDataToTest() throws Exception {
		categoryService.initData();
		Car car = new Car();
		car.setManufacturer("TESTE 1");
		car.setModelName("TESTE 1");
		car.setModelYear(2019);
		car.setCategory(categoryService.findById(1L));
		car.setWeekdayLoyaltyPrice(100);
		car.setWeekdayPrice(100);
		car.setWeekendPrice(100);
		car.setWeekendLoyaltyPrice(100);
		carService.save(car);
		car.setId(null);
		car.setManufacturer("TESTE 2");
		car.setModelName("TESTE 2");
		car.setCategory(categoryService.findById(2L));
		carService.save(car);
	}
	
	
	/**
	 * Test field dateTo before dateFrom
	 * @throws Exception
	 */
	@Test
	public void test3GetTheChepeast() throws Exception {
		CarRentSearch carRentSearch = new CarRentSearch();
		carRentSearch.setDateFrom(LocalDate.parse("2019-10-31"));
		carRentSearch.setDateTo(LocalDate.parse("2019-11-05"));
		carRentSearch.setLoyalty(true);
		CarRent carRent = carRentService.searchChepeastRent(carRentSearch);
		assertTrue(carRent.getCategoryId() == 2);
	}
	
	
	/**
	 * Test field dateTo before dateFrom
	 * @throws Exception
	 */
	@Test
	public void test3GetChepeastByRest() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateFrom", "2019-10-31");
		jsonObject.put("dateTo", "2019-11-01");
		jsonObject.put("loyalty", true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "search").contentType(MediaType.APPLICATION_JSON)
		        .content(jsonObject.toString(1)))
				.andExpect(status().isOk());
	}
	
	
}
