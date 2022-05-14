package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.Category;
import Domain.BusinessLayer.Inventory.Product;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoryDAO extends DataMapper<Category> {

    private final static Map<String, Category> Category_IDENTITY_MAP = new HashMap<>();
    private final static int ID_COLUMN = 1;
    private final static int NAME_COLUMN = 2;
    private final static int PARENT_COLUMN = 3;

    public CategoryDAO(){
        super("Category");
    }

    @Override
    protected Map getMap() {
        return Category_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected Category buildObject(ResultSet instanceResult) throws Exception {
        return new Category(instanceResult.getInt(ID_COLUMN),
                instanceResult.getString(NAME_COLUMN),
                new HashSet<>(), //might cause bugs later
                new ArrayList<>(), //might cause bugs later
                get(Integer.toString(instanceResult.getInt(PARENT_COLUMN))));
    }

    public int remove(Object id) {
        return remove(id);
    }

    @Override
    public void insert(Category instance) {
        try {
            Integer parentID = 0;
            if (instance.getParentCategory()!=null)
                parentID=instance.getParentCategory().getID();
            insert(Arrays.asList(instance.getID(),
                    instance.getName(),
                    parentID));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateParentCategory(int category, int newParent) {
        try {
            updateProperty(Integer.toString(category), PARENT_COLUMN,newParent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateName(int category, String newName) {
        try {
            updateProperty(Integer.toString(category), NAME_COLUMN,newName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
