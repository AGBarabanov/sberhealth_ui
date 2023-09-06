package ru.sberhealth.tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class ChoiceDoctor extends TestBase{


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
        $("[data-test-id = city-select-button]").click();
        $$(".CitySelectModal__cities-wrapper_1BfH span").find(text(city)).click();
        $("[data-test-id = search_geo_input]").click();
        $$(".v-autocomplete-list-item span").filter(visible).shouldHave(texts(districts));
    }

}
