import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = Mockito.spy(new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(20,30)));
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(12,35));
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = Mockito.spy(new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(18,0)));
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(20,15,12));
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void total_cost_for_items_added_by_the_user_should_equal_to_sum_of_the_item_cost_in_menu() throws itemNotFoundException {
        restaurant = new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(18,0));
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Roll",100);
        List<String> i = new ArrayList<>();
        i.add("Sweet corn soup");
        i.add("Vegetable lasagne");
        assertEquals(restaurant.getTotalCost(i),269+119);
    }

    @Test
    public void total_cost_for_items_added_by_the_user_should_not_equal_to_sum_of_the_other_item_cost_in_menu() throws itemNotFoundException {
        restaurant = new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(18,0));
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Roll",100);
        List<String> i = new ArrayList<>();
        i.add("Sweet corn soup");
        i.add("Vegetable lasagne");
        assertNotEquals(restaurant.getTotalCost(i),269+100);
    }

    @Test
    public void total_cost_should_be_0_when_no_item_is_added() throws itemNotFoundException {
        restaurant = new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(18,0));
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Roll",100);
        List<String> i = new ArrayList<>();
        assertEquals(restaurant.getTotalCost(i),0);
    }

    @Test
    public void item_not_found_exception_should_be_thrown_if_user_add_an_item_out_of_the_menu_to_calculate_the_total_cost() throws itemNotFoundException {
        restaurant = new Restaurant("Restro1","Patna",LocalTime.of(10,0),LocalTime.of(18,0));
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Roll",100);
        List<String> i = new ArrayList<>();
        i.add("Sweet corn soup");
        i.add("Pasta");
        assertThrows(itemNotFoundException.class,()->{
            restaurant.getTotalCost(i);
        });
    }
}