package ru.sberhealth.tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class WebSteps {
    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot(){
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Переход на страницу 'Прием врача в клинике'")
    public void openChoiceDoctorPage(){
        open("https://docdoc.ru/");
    }

    @Step("Выбор врача {doctor} в своем городе")
    public void searchDoctor(String doctor){
        $("#autocomplete__placeholder").click();
        $$(".v-autocomplete-list").find((text(doctor))).click();
        $("[data-test-id = search_button]").click();
    }

    @Step("Проверка что {doctor} написано в карточке врача")
    public void shouldSeeDoctorOnCardDetails(String doctor){
        $$("[data-test-id = doctor-list-page-card-details__specialities]").find((text(doctor)));
    }

    @Step("Выбор города {argument}")
    public void choiceCity(String argument){
        $("[data-test-id = city-select-button]").click();
        $$(".CitySelectModal__cities-wrapper_1BfH span").find(text(argument)).click();
    }

    @Step("Проверка что отображается выбранный город {argument}")
    public void shouldSeeSelectedCity(String argument){
        $("[data-test-id = city-select-button]").shouldHave(text(argument));
    }

    @Step("Проверка отображения {argument} в карточке для SEO ")
    public void shouldSeeSelectedDoctorOnSEOContent(String argument){
        $("[data-test-id=top_content_seo]").shouldHave(text(argument));
        $$(".text-content__breadcrumbs-item").find(text(argument));
    }

    @Step("Проверка отображения райнов {districts}")
    public void searchDistrictsOnTheCity(List<String> districts){
        $("[data-test-id = search_geo_input]").click();
        $$(".v-autocomplete-list-item span").filter(visible).shouldHave(texts(districts));
    }
}
