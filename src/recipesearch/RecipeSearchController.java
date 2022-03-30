
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    private RadioButton radioButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RecipeBackendController recipeBC = new RecipeBackendController();
        updateRecipeList(recipeBC);
        comboBoxIngrediens.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
        comboBoxIngrediens.getSelectionModel().select("Visa alla");

        comboBoxCuisine.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
        comboBoxCuisine.getSelectionModel().select("Visa alla");


        /*ToggleGroup difficultyToggleGroup = new ToggleGroup();
        radioButton.setToggleGroup(difficultyToggleGroup);
        radioButton.setSelected(true);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    RecipeBackendController backendController = null;
                    backendController.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        }); */
    }

    private void updateRecipeList(RecipeBackendController recipeBC) {

        flowpane1.getChildren().clear();
        for (Recipe recipe : recipeBC.getRecipes()
        ) {
            flowpane1.getChildren().add(new RecipeListItem(recipe,this));
        }
    }
}