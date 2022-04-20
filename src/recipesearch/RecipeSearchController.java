
package recipesearch;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class RecipeSearchController implements Initializable {

    @FXML
    private FlowPane flowpane1;
    @FXML
    private ComboBox mainIngredientComboBox;
    @FXML
    private ComboBox comboBoxCuisine;
    @FXML
    private RadioButton radiobutton1;
    @FXML
    private RadioButton radiobutton2;
    @FXML
    private RadioButton radiobutton3;
    @FXML
    private RadioButton radiobutton4;
    @FXML
    private Spinner spinnerid;
    @FXML
    private Slider slider;
    @FXML
    private Label timelabel;
    @FXML
    private ImageView detailsimage;
    @FXML
    private Button detailsbutton;
    @FXML
    private Label detailslabel;
    @FXML
    private AnchorPane recipedetails;
    @FXML
    private AnchorPane searchview;

    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();
    RecipeBackendController backendController = new RecipeBackendController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Recipe recipe : backendController.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }
        updateTimeLabel(slider.valueProperty().intValue());
        updateRecipeList(backendController);

        /*for main ingredient */

        initializeIngredientComboBox();

        /*for Cuisine*/

        comboBoxCuisine.getItems().addAll(
                "Visa alla",
                "Sverige",
                "Grekland",
                "Indien",
                "Asien",
                "Afrika",
                "Frankrike"
        );
        comboBoxCuisine.getSelectionModel().select("Visa alla");
        comboBoxCuisine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setCuisine(newValue);
                updateRecipeList(backendController);
            }
        });

        /*for Radio button*/
        ImageView imageViewDiffEasy = new ImageView("recipesearch/resources/icon_difficulty_easy.png");
        imageViewDiffEasy.setFitHeight(12);
        imageViewDiffEasy.setPreserveRatio(true);
        radiobutton2.setGraphic(imageViewDiffEasy);

        ImageView imageViewDiffMedium = new ImageView("recipesearch/resources/icon_difficulty_medium.png");
        imageViewDiffMedium.setFitHeight(12);
        imageViewDiffMedium.setPreserveRatio(true);
        radiobutton3.setGraphic(imageViewDiffMedium);

        ImageView imageViewDiffHard = new ImageView("recipesearch/resources/icon_difficulty_hard.png");
        imageViewDiffHard.setFitHeight(12);
        imageViewDiffHard.setPreserveRatio(true);
        radiobutton4.setGraphic(imageViewDiffHard);

        ToggleGroup difficultyToggleGroup;
        difficultyToggleGroup = new ToggleGroup();
        radiobutton1.setToggleGroup(difficultyToggleGroup);
        radiobutton2.setToggleGroup(difficultyToggleGroup);
        radiobutton3.setToggleGroup(difficultyToggleGroup);
        radiobutton4.setToggleGroup(difficultyToggleGroup);
        radiobutton1.setSelected(true);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    backendController.setDifficulty(selected.getText());
                    updateRecipeList(backendController);
                }
            }
        });

        /* for spinner */
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 600, 0, 10);
        spinnerid.setValueFactory(valueFactory);
        spinnerid.setEditable(true);
        spinnerid.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                backendController.setMaxPrice(newValue);
                updateRecipeList(backendController);
            }
        });

        spinnerid.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //focusgained - do nothing
                } else {
                    Integer value = Integer.valueOf(spinnerid.getEditor().getText());
                    backendController.setMaxPrice(value);
                    updateRecipeList(backendController);
                }

            }
        });
        /* for slider */

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                int value = (newValue.intValue() / 10) * 10;
                backendController.setMaxPrice(value);
                updateRecipeList(backendController);
                updateTimeLabel(value);
            }
        });

    }

    private void initializeIngredientComboBox(ComboBox comboBox) {
        comboBox.getItems().addAll(
                "Visa alla",
                "Kött",
                "Fisk",
                "Kyckling",
                "Vegetarisk");

        comboBox.getSelectionModel().select("Visa alla");
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setMainIngredient(newValue);
                updateRecipeList(backendController);
            }
        });
        populateMainIngredientComboBox();
    }

    private void populateMainIngredientComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {

                                    case "Kött":
                                        iconPath = "RecipeSearch/resources/icon_main_meat.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Fisk":
                                        iconPath = "RecipeSearch/resources/icon_main_fish.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Kyckling":
                                        iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Vegetarisk":
                                        iconPath = "RecipeSearch/resources/icon_main_veg.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        mainIngredientComboBox.setButtonCell(cellFactory.call(null));
        mainIngredientComboBox.setCellFactory(cellFactory);
    };

    public void populateRecipeDetailView(Recipe recipe) {
        detailslabel.setText(recipe.getName());
        detailsimage.setImage(recipe.getFXImage());
    }

    @FXML
    public void closeRecipeView(){
        recipedetails.toBack();
    }

    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipedetails.toFront();
    }



    private void updateTimeLabel(Integer time) {
        timelabel.setText(time.toString() + " min");
    }


    private void updateRecipeList(RecipeBackendController recipeBC) {
        //System.out.println(recipeBC);
        flowpane1.getChildren().clear();

        for (Recipe recipe : recipeBC.getRecipes()
        ) {
            //System.out.println(recipe.getPrice());
            flowpane1.getChildren().add(recipeListItemMap.get(recipe.getName()));
        }

    }
}

