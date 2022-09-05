import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    @BeforeEach
    void init() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<</
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime mockedTime = LocalTime.parse("11:00:00"); // this time is between opening and closing time
        Restaurant spyRest = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        Mockito.when(spyRest.getCurrentTime()).thenReturn(mockedTime);
        boolean restStatus = spyRest.isRestaurantOpen();
        assertEquals(true,restStatus);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime mockedTime = LocalTime.parse("09:00:00"); // this time is before opening  time
        Restaurant spyRest = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        Mockito.when(spyRest.getCurrentTime()).thenReturn(mockedTime);
        boolean restStatus = spyRest.isRestaurantOpen();
        assertEquals(false, restStatus);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Calculate Menu Total (Part 3)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void selecting_menu_item_should_add_the_item_name_to_selected_list(){
        restaurant.selectOrDeselectMenuItem("Sweet corn soup", true);
        assertEquals(true, restaurant.getSelectedMenuItemList().contains("Sweet corn soup"));
    }

    @Test
    public void de_selecting_menu_item_should_remove_the_item_name_from_selected_list(){
        //first add the item to selected List
        restaurant.selectOrDeselectMenuItem("Sweet corn soup", true);

        //secondly remove the item from selected list
        restaurant.selectOrDeselectMenuItem("Sweet corn soup", false);
        assertEquals(false, restaurant.getSelectedMenuItemList().contains("Sweet corn soup"));
    }

    @Test
    public void calculate_total_should_return_the_total_price_of_selected_items(){
        //first select the menu items or add the menu item to selected List
        restaurant.selectOrDeselectMenuItem("Sweet corn soup", true);
        restaurant.selectOrDeselectMenuItem("Vegetable lasagne", true);
        //check if total is 119 + 269 = 388
        assertEquals(388, restaurant.calculateTotalPriceOfSelectedItems());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<Calculate Menu Total (Part 3)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}