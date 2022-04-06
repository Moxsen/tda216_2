package recipesearch;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;





public class RecipeListItem extends AnchorPane {
        private RecipeSearchController parentController;
        private Recipe recipe;
        @FXML
        private Label stektegglabel;
        @FXML
        private ImageView pict = new ImageView();

        public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            stektegglabel.setText(recipe.getName());
            pict.setImage(recipe.getFXImage());

            this.recipe = recipe;
            this.parentController = recipeSearchController;
        }
    @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(recipe);
    }
}



