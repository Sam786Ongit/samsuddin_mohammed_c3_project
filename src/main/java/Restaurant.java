import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    private List<String>selectedMenuItemList = new ArrayList<String>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime = this.getCurrentTime();
        if(currentTime.isAfter(this.openingTime) && currentTime.isBefore(this.closingTime)){
            return true;
        }
        return false; // if the above condition fails then return false
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return this.menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    /** When an item is selected , the name of the item and True value is passed to this function.
     *  when an item is deselected the name and false value is passed to this function
     * */
    public void selectOrDeselectMenuItem(String itemName, boolean selectStatus){
        if(selectStatus == true) {
            if(!selectedMenuItemList.contains(itemName))
                selectedMenuItemList.add(itemName);
        } else {
            selectedMenuItemList.remove(itemName);
        }
    }

    /** Loop through the selected items and get their price and add it to total price*/
    public int calculateTotalPriceOfSelectedItems(){
        int totalCost = 0;
        for (String name: selectedMenuItemList) {
            for(Item item: menu) {
                if(item.getName().equals(name)){
                    // if the selected item matches the item in Menu list
                    // add its price to the total Variable created above
                    totalCost += item.getPrice();
                }
            }
        }
        return totalCost;
    }

    public List<String> getSelectedMenuItemList() {
        return selectedMenuItemList;
    }
}
