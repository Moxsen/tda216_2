
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
import se.chalmers.ait.dat215.lab2.Ingredient;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class RecipeSearchController implements Initializable {

    @FXML
    private FlowPane flowpane1;
    @FXML
    private ComboBox mainIngredientComboBox;
    @FXML
    private ComboBox cuisineComboBox;
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
    @FXML
    private ImageView cuisineflag;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label discriptionlabel;
    @FXML
    private Label tillagninglabel;
    @FXML
    private Label instructionlabel;
    @FXML
    private Label ingredienser;
    @FXML
    private Label ingredinserlabel;






    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();
    RecipeBackendController backendController = new RecipeBackendController();

    // Intilize views and components
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Recipe recipe : backendController.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }
        updateRecipeList(backendController);

        initializeRecipeSearchView();
        initializeRecipeDetailView();
    }

    // Init main view
    private void initializeRecipeSearchView() {
        /*for main ingredient */
        initializeIngredientComboBox(mainIngredientComboBox);

        /*for Cuisine*/
        initializeCuisineComboBox(cuisineComboBox);

        /*for Radio button*/
        initializeRadioButtons(radiobutton1, radiobutton2, radiobutton3, radiobutton4);

        /* for spinner */
        initializeSpinner(spinnerid);

        /* for slider */
        initializeSlider(slider);
    }

    private void initializeSlider(Slider slider) {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                int value = (newValue.intValue() / 10) * 10;
                backendController.setMaxPrice(value);
                updateRecipeList(backendController);
                updateTimeLabel(value);
            }
        });

        updateTimeLabel(slider.valueProperty().intValue());
    }

    private void initializeSpinner(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 600, 0, 10));
        spinner.setEditable(true);

        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
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

    }

    private void initializeRadioButtons(RadioButton radioButton1, RadioButton radioButton2, RadioButton radioButton3, RadioButton radioButton4) {
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(difficultyToggleGroup);
        radioButton2.setToggleGroup(difficultyToggleGroup);
        radioButton3.setToggleGroup(difficultyToggleGroup);
        radioButton4.setToggleGroup(difficultyToggleGroup);

        radioButton1.setSelected(true);

        setImageRadioButton("recipesearch/resources/icon_difficulty_easy.png", radioButton2);
        setImageRadioButton("recipesearch/resources/icon_difficulty_medium.png", radioButton3);
        setImageRadioButton("recipesearch/resources/icon_difficulty_hard.png", radioButton4);

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
    }

    private void initializeCuisineComboBox(ComboBox comboBox) {
        comboBox.getItems().addAll(
                "Visa alla",
                "Sverige",
                "Grekland",
                "Indien",
                "Asien",
                "Afrika",
                "Frankrike"
        );
        comboBox.getSelectionModel().select("Visa alla");
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setCuisine(newValue);
                updateRecipeList(backendController);
            }
        });
        populateCuisineComboBox();
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

    private void initializeRecipeDetailView() {
        initalizeFoodImageView();
        initalizeCuisineImageView();
        initializeRecipeInstructionText();
        IntializeRecipeDetails();

    }

    //Init detailed view
    private void IntializeRecipeDetails() {

    }

    private void initializeRecipeInstructionText() {

    }

    private void initalizeCuisineImageView() {
    }

    private void initalizeFoodImageView() {

    }

    // Initialization utils
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
                                icon = getMainIngredientImage(item);
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

    private void populateCuisineComboBox() {
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
                                icon = getCuisineImage(item);

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
        cuisineComboBox.setButtonCell(cellFactory.call(null));
        cuisineComboBox.setCellFactory(cellFactory);
    };

    public Image getCuisineImage(String cuisine) {
        String iconPath;
        switch (cuisine) {
            case "Sverige":
                iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Tyskland":
                iconPath = "RecipeSearch/resources/icon_flag_germany.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Grekland":
                iconPath = "RecipeSearch/resources/icon_flag_greece.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Afrika":
                iconPath = "RecipeSearch/resources/icon_flag_africa.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Asien":
                iconPath = "RecipeSearch/resources/icon_flag_asia.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Frankrike":
                iconPath = "RecipeSearch/resources/icon_flag_france.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Indien":
                iconPath = "RecipeSearch/resources/icon_flag_india.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
        }
        return null;
    }

    private Image getMainIngredientImage(String item) {
        String iconPath;
        switch (item) {
            case "Kött":
                iconPath = "RecipeSearch/resources/icon_main_meat.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Fisk":
                iconPath = "RecipeSearch/resources/icon_main_fish.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Kyckling":
                iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Vegetarisk":
                iconPath = "RecipeSearch/resources/icon_main_veg.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
        }
        return null;
    }

    private void setImageRadioButton(String s, RadioButton radioButton2) {
        ImageView imageViewDiffEasy = new ImageView(s);
        imageViewDiffEasy.setFitHeight(12);
        imageViewDiffEasy.setPreserveRatio(true);
        radioButton2.setGraphic(imageViewDiffEasy);
    }

    private void updateTimeLabel(Integer time) {
        timelabel.setText(time.toString() + " min");
    }

    public void populateRecipeDetailView(Recipe recipe) {
        detailslabel.setText(recipe.getName());
        detailsimage.setImage(recipe.getFXImage());
        cuisineflag.setImage(getCuisineImage(recipe.getCuisine()));
        image1.setImage(getMainIngredientImage(recipe.getMainIngredient()));
        descriptionlabel.setText(recipe.getDescription());
        instructonlabel.setText(recipe.getInstruction());
        System.out.println(concatenateStrings(recipe.getIngredients(), "\n"));

        System.out.println(recipe.getDescription());
        System.out.println(recipe.getInstruction());
        label1.setText(Integer.toString(recipe.getTime()));
        label2.setText(Integer.toString(recipe.getPrice()));

    }

    private String concatenateStrings(List<Ingredient> listIngredients, String divider) {
        String result = "";
        for (Ingredient ingredient : listIngredients) {
            result = result + ingredient + divider;

        }
        return result;
    }

    // View interactions
    public void closeRecipeView(){
        recipedetails.toBack();
    }

    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipedetails.toFront();
    }



    // Recipe
    private void updateRecipeList(RecipeBackendController recipeBC) {
        //System.out.println(recipeBC);
        flowpane1.getChildren().clear();

        for (Recipe recipe : recipeBC.getRecipes()) {
            flowpane1.getChildren().add(recipeListItemMap.get(recipe.getName()));
        }
    }
}

