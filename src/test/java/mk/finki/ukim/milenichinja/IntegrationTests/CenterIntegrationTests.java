package mk.finki.ukim.milenichinja.IntegrationTests;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CenterIntegrationTests {
    MockMvc mockMvc;

    @Autowired
    AppUserService userService;

    @Autowired
    CenterService centerService;

    @Autowired
    DonationCauseService donationCauseService;

    @Autowired
    DonationService donationService;

    @Autowired
    ValuteService valuteService;

    @Autowired
    AdoptionService adoptionService;

    @Autowired
    PetService petService;

    private static Center c1;
    private static Pet p1;
    List<Integer> pets = new ArrayList();
    private static AppUser appUser;
    private static AppUser appAdmin;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {
            c1 = centerService.save("addr", City.Skopje,"url").get();
            p1 = this.petService.save("petname", Type.DOG, "testBreed", Gender.FEMALE,"descr",c1.getId(),"url",appAdmin,"2020-04-04").get();
            pets.add(p1.getId());

            String user = "user";
            String admin = "admin";

            appUser=userService.registerUser(user, user, user, City.Skopje, user, user,user, Role.ROLE_USER);
            appAdmin=userService.registerUser(admin, admin, admin, City.Skopje, admin,admin,admin, Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testGetCenters() throws Exception {
        MockHttpServletRequestBuilder petRequest = MockMvcRequestBuilders.get("/centers");
        this.mockMvc.perform(petRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("centerList"))
                .andExpect(MockMvcResultMatchers.view().name("mainPages/centers"));
    }

    @Test
    public void testDeleteCenter() throws Exception {
        Center center = this.centerService.save("test",City.Skopje,"url").get();
        MockHttpServletRequestBuilder centerDeleteRequest = MockMvcRequestBuilders
                .delete("/centers/deleteCenter/" + center.getId());

        this.mockMvc.perform(centerDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/centers"));
    }

    @Test
    public void testGetAddCenterPage() throws Exception
    {
        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.get("/centers/add-form");
        this.mockMvc.perform(userRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("cityList"))
                .andExpect(MockMvcResultMatchers.view().name("posts/addCenter"));
    }

    @Test
    public void testAddCenter() throws Exception
    {
        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.post("/centers/add")
                .param("address", "ad")
                .param("city", "Skopje")
                .param("url", "url");

        this.mockMvc.perform(userRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/centers"));
    }
}
