package ru.sberhealth.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class ChoiceDoctor extends TestBase{

    //Параметризованный тест с @MethodSource
    static Stream<Arguments> checkСities(){
        return Stream.of(
                Arguments.of("Ульяновск", List.of("Железнодорожный", "Заволжский", "Засвияжский", "Ленинский")),
                Arguments.of("Саранск", List.of("Ленинский", "Октябрьский", "Пролетарский"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка наличия районов {1} при выборе своего города {0}")
    @Tags({@Tag("BLOCKER"), @Tag("REGRESS")})
    void checkСities(String city, List<String> districts){
        open("https://docdoc.ru/");
        $("[data-test-id = city-select-button]").click();
        $$(".CitySelectModal__cities-wrapper_1BfH span").find(text(city)).click();
        $("[data-test-id = search_geo_input]").click();
        $$(".v-autocomplete-list-item span").filter(visible).shouldHave(texts(districts));
    }


    @ParameterizedTest(name = "Проверка отображения названия врачей {1}, при выборе специальности {0}")
    @CsvFileSource(
            resources = "/NamesOfSpecialties.csv"
    )
    @Tags({@Tag("CRITICAL"), @Tag("REGRESS")})
    public void choiceOfSpecialty(String specialty, String doctor){
        open("https://docdoc.ru/");

        $("#autocomplete__placeholder").click();
        $$(".v-autocomplete-list").find((text(specialty))).click();
        $("[data-test-id = search_button]").click();
        $("[data-test-id=top_content_seo]").shouldHave(text(doctor));
        $$(".text-content__breadcrumbs-item").find(text(doctor));
    }

    @DisplayName("Проверка отображения специальности доктора в его карточке")
    @Test
    @Tags({@Tag("CRITICAL"), @Tag("REGRESS")})
    void checkCardDoctor(){
        open("https://docdoc.ru/");

        $("#autocomplete__placeholder").click();
        $$(".v-autocomplete-list").find((text("Акушер"))).click();
        $("[data-test-id = search_button]").click();

        $$("[data-test-id = doctor-list-page-card-details__specialities]").find((text("Акушер")));


    }
}
