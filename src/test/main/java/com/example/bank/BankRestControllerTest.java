package com.example.bank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BankRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getListNumberCardTest() throws Exception {
        mockMvc.perform(get("/bank/allCard"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" +
                        "{" +
                        "\"id\":1," +
                        "\"number\":\"4276 1604 9834 5534\"" +
                        "}" +
                        "]"));
    }

    @Test
    public void getBalanceFromCardNumberAccessTest() throws Exception {
        String card_number = "4276160498345534";
        mockMvc.perform(get("/bank/balance/{card_number}", card_number))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("100.88")));
    }

    @Test
    public void getBalanceFromCardNumberCrashTest() throws Exception {
        String card_number = "card_number";
        mockMvc.perform(get("/bank/balance/{card_number}", card_number))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Карты с таким номером нет")));
    }

    @Test
    public void creatNewCardTestAccessTest() throws Exception {
        String score_number = "1020304050506060707070605";
        String expected_card_number = "1111222233334445";

        mockMvc.perform(put("/bank/createCard/{score_number}", score_number))
                .andExpect(status().isCreated())
                .andExpect(content().string(expected_card_number));
    }

    @Test
    public void creatNewCardCrashTest() throws Exception {
        String score_number = "score_number";
        mockMvc.perform(put("/bank/createCard/{score_number}", score_number))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Данного счета не существует"));
    }

    @Test
    public void incrementBalanceFromCardNumberAccessTest() throws Exception {
        String card_number = "4276160498345534";
        String sumIncrement = "100";
        String expectedSum = "200.88";
        mockMvc.perform(post("/bank/increaseCard")
                .param("card_number", card_number)
                .param("sumIncrement", sumIncrement))
                .andExpect(status().isAccepted())
                .andExpect(content().string(expectedSum));
    }

    @Test
    public void incrementBalanceFromCardNumberCrashTest() throws Exception {
        String card_number = "card_number";
        String sumIncrement = "100";
        mockMvc.perform(post("/bank/increaseCard")
                .param("card_number", card_number)
                .param("sumIncrement", sumIncrement))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Карты с данным номером не существует"));
    }

//    <--------------------> 2 ЭТАП <-------------------->

    @Test
    public void addNewLegalPersonAccessTest() throws Exception {
        String address = "202";
        String type = "OAO";
        String title = "MTC";

        mockMvc.perform(put("/bank/addLegalPerson")
                .param("address", address)
                .param("type", type)
                .param("title", title))
                .andExpect(status().isCreated())
                .andExpect(content().string("11111111112222222223"));
    }

    @Test
    public void addNewLegalPersonCrashTest() throws Exception {
        String address = "9389E5736D205746";
        String type = "OOO";
        String title = "Sberbank";

        mockMvc.perform(put("/bank/addLegalPerson")
                .param("address", address)
                .param("type", type)
                .param("title", title))
                .andExpect(status().isConflict())
                .andExpect(content().string("Данное юридисеское лицо уже зарагестрировано"));
    }

    @Test
    public void getListLegalPersonTest() throws Exception {
        mockMvc.perform(get("/bank//allLegal"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("" +
                        "[{\"title\":\"Tinkoff\",\"score_number\":[\"763849348138478\"]}," +
                        "{\"title\":\"Sberbank\",\"score_number\":[\"018486147512746\"]}," +
                        "{\"title\":\"Yandex\",\"score_number\":[\"983467346237646\"]}]"));
    }

    @Test
    public void incrementBalanceFromLegalScoreAccessTest() throws Exception {
        String score_number = "018486147512746";
        String sumIncrement = "100";
        String expectedSum = "422.22";
        mockMvc.perform(post("/bank/increaseLegal")
                .param("score_number", score_number)
                .param("sumIncrement", sumIncrement))
                .andExpect(status().isAccepted())
                .andExpect(content().string(expectedSum));
    }

    @Test
    public void incrementBalanceFromLegalScoreCrashTest() throws Exception {
        String score_number = "score_number";
        String sumIncrement = "100";
        mockMvc.perform(post("/bank/increaseLegal")
                .param("score_number", score_number)
                .param("sumIncrement", sumIncrement))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Юридического лица с данным номером не существует"));
    }
}
