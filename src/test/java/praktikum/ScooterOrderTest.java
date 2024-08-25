package praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.pages.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ScooterOrderTest {

    private final int index;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentalTime;
    private final String scooterColour;
    private final String comment;

    @Rule
    public DriverRule factory = new DriverRule();

    public ScooterOrderTest(Integer index, String firstName, String lastName, String address, String metroStation, String phone,
                            String deliveryDate, String rentalTime, String scooterColour, String comment) {
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalTime = rentalTime;
        this.scooterColour = scooterColour;
        this.comment = comment;
    }

    @Parameterized.Parameters

    public static Object[][] testOrderData() {
        return new Object[][] {
                {0,"Михаил", "Кузнецов", "Москва, Усачева, 3", "Сокольники", "+79277772535", "30.08.2024", "трое суток", "Черный жемчуг", "Позвонить за 15 минут"},
                {1, "Владимир", "Санников", "Москва, Фрунзенская набережная, 12", "Черкизовская", "+79175552211", "26.08.2024", "пятеро суток", "", ""},
        };
    }

    //Проверка оформления заказа
    @Test
    public void createOrder() {
        var orderPage = new MainPage(factory.getDriver())
            .open()
            .acceptCookies()
            .clickOnOrderButton(index)
            .fillWhoIsScooterForPage(firstName, lastName, address, metroStation, phone)
            .fillAboutRentalPage(deliveryDate, rentalTime, scooterColour, comment)
            .clickOnPlaceAnOrderButton()
            .clickOnYesButton();

        assertTrue("Заказ не оформлен", orderPage.orderIsProcessedIsVisible());
    }
}
