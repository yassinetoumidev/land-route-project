package com.land.route.app;

import com.land.route.app.controllers.CountryController;
import com.land.route.app.exceptions.CountryNotFoundException;
import com.land.route.app.exceptions.CrossNotFoundException;
import com.land.route.app.model.Route;
import com.land.route.app.services.CountryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {
    @Autowired
    private CountryController countryController;

    @Test
    void contextLoads() {
        assertThat(countryController).isNotNull();
    }
   @Autowired
    private MockMvc mockMvc;

    @Test
    public void crossNotFound() throws Exception {
        String src = "USA";
        String dist = "TUN";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/routing/"+ src+ "/"+ dist)
                .contentType(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals("There is no land crossing", result.getResolvedException().getMessage()));
    }



    @Test
    public void countryNotFound() throws Exception {
        String src = "ABC";
        String dest = "ABC";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/routing/"+ src+ "/"+ dest)
                .contentType(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        {
                            String res =  result.getResolvedException().getMessage();
                            assertTrue(res.equals("Origin country "+src+" not found") || res.equals("Destination country "+dest+" not found") );
                        }
                     );
    }


    @Test
    public void routingFound() throws Exception {
        String src = "EGY";
        String dist = "TUN";
        List<String> paths = new LinkedList<>();
        paths.add("EGY");
        paths.add("LBY");
        paths.add("TUN");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/routing/"+ src+ "/"+ dist)
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",  notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.route" ,  Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.route" ,  Matchers.equalTo(paths)));



    }
}
