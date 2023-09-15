package ru.sberhealth.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

public class ChoiceDoctor extends TestBase{

    //Параметризованный тест с @MethodSource
    static Stream<Arguments> checkСities(){
        return Stream.of(
                Arguments.of("Ульяновск", List.of("Железнодорожный", "Заволжский", "Засвияжский", "Ленинский")),
                Arguments.of("Саранск", List.of("Ленинский", "Октябрьский", "Пролетарский"))
        );
    }

    @MethodSource
    @ParameterizedTest
    @Feature("Страница 'Прием врача в клинике'")
    @Story("Выбор определенного врача в своем городе")
    @Owner("BarabanovAG")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Ссылка на сайт", url = "https://docdoc.ru/")
    @Tags({@Tag("BLOCKER"), @Tag("REGRESS")})
    @DisplayName("Проверка наличия районов при выборе своего города")
    void checkСities(String city, List<String> districts){
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openChoiceDoctorPage();
        steps.choiceCity(city);
        steps.searchDistrictsOnTheCity(districts);

        steps.takeScreenshot();
    }


    @ParameterizedTest
    @Feature("Страница 'Прием врача в клинике'")
    @Story("Выбор определенного врача в своем городе")
    @Owner("BarabanovAG")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Ссылка на сайт", url = "https://docdoc.ru/")
    @DisplayName("Проверка отображения названия врачей, при выборе специальности")
    @CsvFileSource(
            resources = "/NamesOfSpecialties.csv"
    )
    @Tags({@Tag("CRITICAL"), @Tag("REGRESS")})
    public void choiceOfSpecialty(String specialty, String doctor){
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openChoiceDoctorPage();
        steps.searchDoctor(specialty);
        steps.shouldSeeSelectedDoctorOnSEOContent(doctor);

        steps.takeScreenshot();
    }

    @Feature("Страница 'Прием врача в клинике'")
    @Story("Выбор определенного врача в своем городе")
    @Owner("BarabanovAG")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Ссылка на сайт", url = "https://docdoc.ru/")
    @DisplayName("Проверка отображения специальности доктора в его карточке")
    @Test
    @Tags({@Tag("CRITICAL"), @Tag("REGRESS")})
    void checkCardDoctor(){
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openChoiceDoctorPage();
        steps.searchDoctor("Акушер");
        steps.shouldSeeDoctorOnCardDetails("Акушер");

        steps.takeScreenshot();
    }

    @Feature("Страница 'Прием врача в клинике'")
    @Story("Выбор определенного врача в своем городе")
    @Owner("BarabanovAG")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Ссылка на сайт", url = "https://docdoc.ru/")
    @DisplayName("Проверка выбора города")
    @ParameterizedTest
    @ValueSource(strings = {"Альметьевск", "Анапа"})
    @Tags({@Tag("CRITICAL"), @Tag("REGRESS")})
    void checkChoiceCity(String argument){
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openChoiceDoctorPage();
        steps.choiceCity(argument);
        steps.shouldSeeSelectedCity(argument);

        steps.takeScreenshot();
    }
}
