
package recipesearch;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
import java.util.ResourceBundle;


public class RecipeSearchController implements Initializable {

    @FXML
    private FlowPane flowpane1;
    @FXML
    private ComboBox comboBoxIngrediens = new ComboBox();
    @FXML
    private ComboBox comboBoxCuisine = new ComboBox();
    @FXML
    private RadioButton radioButton = new RadioButton();
    @FXML
    private Spinner spinnerid = new Spinner();
    @FXML
    private Slider slider = new Slider();
    @FXML
    private Label timelabel = new Label();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RecipeBackendController backendController = new RecipeBackendController();
        updateRecipeList(backendController);

        /*for main ingredient */

        comboBoxIngrediens.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
        comboBoxIngrediens.getSelectionModel().select("Visa alla");

        comboBoxIngrediens.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setMainIngredient(newValue);
                updateRecipeList(backendController);
            }
        });

        /*for Cuisine*/

        comboBoxCuisine.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");
        comboBoxCuisine.getSelectionModel().select("Visa alla");

        comboBoxIngrediens.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setMainIngredient(newValue);
                updateRecipeList(backendController);
            }
        });

        /*for Radio button*/

        ToggleGroup difficultyToggleGroup;
        difficultyToggleGroup = new ToggleGroup();
        radioButton.setToggleGroup(difficultyToggleGroup);
        radioButton.setSelected(true);

        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    RecipeBackendController backendController = null;
                    backendController.setDifficulty(selected.getText());
                    updateRecipeList(backendController);
                }
            }
        });

        /* for spinner */
        //SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(.., .., .., ..);


        /* for slider */


    }




    private void updateRecipeList(RecipeBackendController recipeBC) {

        flowpane1.getChildren().clear();
        for (Recipe recipe : recipeBC.getRecipes()
        ) {
            flowpane1.getChildren().add(new RecipeListItem(recipe,this));
        }
    }
}

