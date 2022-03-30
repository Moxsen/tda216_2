package recipesearch;


import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class RecipeSearch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("recipesearch/resources/RecipeSearch");
        
        Parent root = FXMLLoader.load(getClass().getResource("recipe_search.fxml"), bundle);
        
        Scene scene = new Scene(root, 800, 500);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //test();

      launch(args);
    }

    static void test() {
        RecipeBackendController recipeBC = new RecipeBackendController();
        recipeBC.setCuisine("Frankrike");
        List<Recipe> recipes = recipeBC.getRecipes();
        for (Recipe recipe:recipes
        ) {
            if (recipe.getMatch() != 0) System.out.println(recipe.getName());
        }

    }
}
