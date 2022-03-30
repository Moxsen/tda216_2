
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    @FXML
    private FlowPane flowpane1;
    @FXML
    private ComboBox comboBoxIngrediens;
    @FXML
    private ComboBox comboBoxCuisine;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RecipeBackendController recipeBC = new RecipeBackendController();
        updateRecipeList(recipeBC);
        comboBoxIngrediens.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
        comboBoxCuisine.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
    }

    private void updateRecipeList(RecipeBackendController recipeBC) {

        flowpane1.getChildren().clear();
        for (Recipe recipe : recipeBC.getRecipes()
        ) {
            flowpane1.getChildren().add(new RecipeListItem(recipe,this));
        }
    }
}