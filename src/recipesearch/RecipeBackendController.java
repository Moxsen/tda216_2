package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.List;

public class RecipeBackendController {

    String cuisine =  null;
    String mainIngredient = null;
    String difficulty = null;
    int maxPrice = 0;
    int maxTime = 0;

    public RecipeBackendController() {
    }
    public List<Recipe> getRecipes() {
        SearchFilter emptySearchFilter = new SearchFilter(difficulty,maxTime,cuisine,maxPrice,mainIngredient);
        return RecipeDatabase.getSharedInstance().search(emptySearchFilter);

    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }


}
