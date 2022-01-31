package mk.finki.ukim.milenichinja;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MilenichinjaApplicationTests {

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

    //get tests
    @Test
    public void testGetPets() throws Exception {
        MockHttpServletRequestBuilder petRequest = MockMvcRequestBuilders.get("/petsList");
        this.mockMvc.perform(petRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("petList"))
                .andExpect(MockMvcResultMatchers.view().name("mainPages/pets.html"));
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
    public void testGetDonationCauses() throws Exception {
        MockHttpServletRequestBuilder petRequest = MockMvcRequestBuilders.get("/causes");
        this.mockMvc.perform(petRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("causesList"))
                .andExpect(MockMvcResultMatchers.view().name("mainPages/donationCauses"));
    }

    @Test
    public void testGetLoginPage() throws Exception {
        MockHttpServletRequestBuilder petRequest = MockMvcRequestBuilders.get("/login");
        this.mockMvc.perform(petRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("posts/login"));
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        MockHttpServletRequestBuilder petRequest = MockMvcRequestBuilders.get("/register");
        this.mockMvc.perform(petRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("cityList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("worksAtList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("admin"))
                .andExpect(MockMvcResultMatchers.view().name("posts/register"));
    }

    //delete tests

    @Test
    public void testDeletePet() throws Exception {
        Pet pet = this.petService.save("petname", Type.DOG, "testBreed", Gender.FEMALE,"descr",c1.getId(),"url",appAdmin,"2020-04-04").get();
        MockHttpServletRequestBuilder petDeleteRequest = MockMvcRequestBuilders
                .delete("/petsList/deletePet/" + pet.getId());

        this.mockMvc.perform(petDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/petsList"));
    }

    @Test
    public void testDeleteDonationCause() throws Exception {
        DonationCause dc = this.donationCauseService.save("test","url", pets,0.1,"name",1).get();
        MockHttpServletRequestBuilder dcDeleteRequest = MockMvcRequestBuilders
                .delete("/causes/deleteCause/" + dc.getId());

        this.mockMvc.perform(dcDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/causes"));
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


    //add tests

    @Test
    public void testGetAddPet() throws Exception
    {
        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.get("/petsList/add-form");
        this.mockMvc.perform(userRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("today"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("petList"))
                .andExpect(MockMvcResultMatchers.view().name("posts/addPet"));
    }

//    @Test
//    public void testAdd() throws Exception
//    {
//        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.post("/centers/add")
//                .param("ime", "name")
//                .param("vid", "DOG")
//                .param("rasa", "rasa")
//                .param("pol", "FEMALE")
//                .param("opis","opis")
//                .param("id_centar",c1.getId().toString())
//                .param("DoB","2020-04-04")
//                .param("url_slika","url");
//
//        this.mockMvc.perform(userRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/petsList"));
//    }

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

    @Test
    public void testGetAddDCPage() throws Exception
    {
        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.get("/causes/add-form");
        this.mockMvc.perform(userRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("cityList"))
                .andExpect(MockMvcResultMatchers.view().name("posts/addCenter"));
    }

    @Test
    public void testAddDC() throws Exception
    {
        MockHttpServletRequestBuilder userRequest = MockMvcRequestBuilders.post("/causes/add")
                .param("address", "ad")
                .param("city", "Skopje")
                .param("url", "url");

        this.mockMvc.perform(userRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/centers"));
    }

    @Test
    void contextLoads() {
    }

}
