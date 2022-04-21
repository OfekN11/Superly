package BusinessTests;

import BusinessLayer.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;


public class CategoryTests {
    @Test
    public void testGetName(){
        try
        {
            Category category = new Category("Milk", new HashSet<>(), new LinkedList<>(), null);
            Assertions.assertEquals(true, category.getName()=="Milk");
        }
        catch (Exception e)
        {
            Assertions.fail("Category name is not created properly or getName does not work");
        }
    }
}
