package main.dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.game.Game;
import main.game.GameBoard;
import main.images.ImageLoader;

import java.util.LinkedList;

import static main.Constants.*;
import static main.Utils.changeStage;
import static main.dialogs.Dialog.closeDialog;
import static main.game.player.GameCharacter.*;
import static main.images.ImagePaths.*;
import static main.game.GameController.game;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 23. 4. 2020
 * Time: 20:42
 */
public class GameConfigDialogController {

    public ChoiceBox<Integer> NumberOfPlayersChoiceBox;
    public ChoiceBox<Integer> NumberOfRoundsChoiceBox;
    public ChoiceBox<Integer> NumberOfAIOpponentsChoiceBox;
    public ChoiceBox<String> GameBoardChoiceBox;

    public ImageView Player1_Pic;
    public ChoiceBox<String> Player1_CharacterSelect;
    public TextField Player1_UsernameTextBox;

    public ImageView Player2_Pic;
    public ChoiceBox<String> Player2_CharacterSelect;
    public TextField Player2_UsernameTextBox;

    public ImageView ContinueButton;
    public ImageView BackButton;

    public Label Player2_UsernameLabel;
    public Label Player2_CharacterLabel;

    private static boolean listen;

    private static final String LEVEL_1 = "Level 1";
    private static final String LEVEL_2 = "Level 2";
    private static final String LEVEL_3 = "Level 3";
    private static final String LEVEL_4 = "Level 4";
    private static final String LEVEL_5 = "Level 5";
    private static final String RANDOM = "Random";

    @FXML
    public void initialize() {

        boolean AIdone = false;
        setImages();

        if (AIdone) {
            setPlayer2Visible(false);
            setChoiceBoxes();
            listen = true;
            game = null;

            setNumberOfPlayersChangeListener();
            setPlayerCharacterSelectChangeListeners();
            setNumberOfRoundsChangeListener();

        } else {
            setPlayer2Visible(true);
            setImages();
            setChoiceBoxes2();
            listen = true;
            game = null;

            setPlayerCharacterSelectChangeListeners();
            setNumberOfRoundsChangeListener();
        }
    }



    // SETTERS

    /**
     * Creates the choice box number of rounds listener that controls values in the choice box of maps.
     */
    private void setNumberOfRoundsChangeListener() {
        NumberOfRoundsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != 1) {
                    LinkedList<String> maps = new LinkedList<>();
                    maps.add(RANDOM);
                    setChoiceBox(maps, GameBoardChoiceBox, RANDOM);
                } else {
                    LinkedList<String> maps = new LinkedList<>();
                    maps.add(LEVEL_1);
                    maps.add(LEVEL_2);
                    maps.add(LEVEL_3);
                    maps.add(LEVEL_4);
                    maps.add(LEVEL_5);
                    maps.add(RANDOM);
                    String prevChoice = GameBoardChoiceBox.getValue();
                    setChoiceBox(maps, GameBoardChoiceBox, prevChoice);
                }
            }
        });
    }

    /**
     * Controls that selected players from the choice box are not duplicated.
     */
    private void setPlayerCharacterSelectChangeListeners() {
        Player1_CharacterSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (listen) {
                    listen = false;
                    setPlayerPic(newValue, Player1_Pic);
                    updateCharacterChoiceBox(Player2_CharacterSelect, newValue);
                    listen = true;
                }
            }
        });

        Player2_CharacterSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (listen) {
                    listen = false;
                    setPlayerPic(newValue, Player2_Pic);
                    updateCharacterChoiceBox(Player1_CharacterSelect, newValue);
                    listen = true;
                }
            }
        });
    }

    /**
     * Controls number of AI players in the choice box depending on number of local players.
     */
    private void setNumberOfPlayersChangeListener() {
        NumberOfPlayersChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue observable, Integer oldValue, Integer newValue) {
                if (newValue.equals(2)) {

                    if (NumberOfAIOpponentsChoiceBox.getValue().equals(3)) {
                        setChoiceBox(new int[]{0,1,2}, NumberOfAIOpponentsChoiceBox, 0);
                    } else {
                        setChoiceBox(new int[]{0,1,2}, NumberOfAIOpponentsChoiceBox);
                    }

                    setPlayer2Visible(true);

                    listen = false;

                    LinkedList<String> otherGameCharacters = getNames();
                    otherGameCharacters.remove(Player1_CharacterSelect.getValue());
                    setChoiceBox(otherGameCharacters, Player2_CharacterSelect, otherGameCharacters.get(0));
                    setPlayerPic(Player2_CharacterSelect.getValue(), Player2_Pic);

                    otherGameCharacters = getNames();
                    otherGameCharacters.remove(Player2_CharacterSelect.getValue());
                    setChoiceBox(otherGameCharacters, Player1_CharacterSelect);

                    listen = true;

                } else {

                    if (NumberOfAIOpponentsChoiceBox.getValue().equals(0)) {
                        setChoiceBox(new int[]{1,2,3}, NumberOfAIOpponentsChoiceBox, 1);
                    } else {
                        setChoiceBox(new int[]{1,2,3}, NumberOfAIOpponentsChoiceBox);
                    }

                    listen = false;
                    setChoiceBox(getNames(), Player1_CharacterSelect);
                    listen = true;

                    setPlayer2Visible(false);
                }
            }
        });
    }

    /**
     * Sets the visibility of player 2.
     */
    private void setPlayer2Visible(boolean visible) {
        Player2_CharacterSelect.setVisible(visible);
        Player2_CharacterLabel.setVisible(visible);
        Player2_Pic.setVisible(visible);
        Player2_UsernameLabel.setVisible(visible);
        Player2_UsernameTextBox.setVisible(visible);
    }

    /**
     * Sets images to scene.
     */
    private void setImages() {
        BackButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
        ContinueButton.setImage(ImageLoader.loadImage(CONTINUE_BUTTON));
    }

    /**
     * Sets the choice boxes when AI is available.
     */
    private void setChoiceBoxes() {
        setChoiceBox(new int[]{1,2}, NumberOfPlayersChoiceBox, 1);
        setChoiceBox(new int[]{1,2,3,5,10,15,20}, NumberOfRoundsChoiceBox, 1);
        setChoiceBox(new int[]{1,2,3}, NumberOfAIOpponentsChoiceBox, 1);

        LinkedList<String> characters = getNames();
        setChoiceBox(characters, Player1_CharacterSelect, characters.get(0));
        characters.remove(0);
        setChoiceBox(characters, Player2_CharacterSelect, characters.get(1));

        LinkedList<String> maps = new LinkedList<>();
        maps.add(LEVEL_1);
        maps.add(LEVEL_2);
        maps.add(LEVEL_3);
        maps.add(LEVEL_4);
        maps.add(LEVEL_5);
        maps.add(RANDOM);

        setChoiceBox(maps, GameBoardChoiceBox, LEVEL_1);

        String character = Player1_CharacterSelect.getValue();
        setPlayerPic(character, Player1_Pic);
    }

    /**
     * Sets the choice boxes when AI is not available.
     */
    private void setChoiceBoxes2() {
        setChoiceBox(new int[]{2}, NumberOfPlayersChoiceBox, 2);
        setChoiceBox(new int[]{1,2,3,5,10,15,20}, NumberOfRoundsChoiceBox, 1);
        setChoiceBox(new int[]{0}, NumberOfAIOpponentsChoiceBox, 0);

        LinkedList<String> characters = getNames();
        String char2 = characters.get(1);
        characters.remove(1);
        setChoiceBox(characters, Player1_CharacterSelect, characters.get(0));
        characters.remove(0);
        characters.add(char2);
        setChoiceBox(characters, Player2_CharacterSelect, char2);

        LinkedList<String> maps = new LinkedList<>();
        maps.add(LEVEL_1);
        maps.add(LEVEL_2);
        maps.add(LEVEL_3);
        maps.add(LEVEL_4);
        maps.add(LEVEL_5);
        maps.add(RANDOM);

        setChoiceBox(maps, GameBoardChoiceBox, LEVEL_1);

        String character = Player1_CharacterSelect.getValue();
        setPlayerPic(character, Player1_Pic);

        character = Player2_CharacterSelect.getValue();
        setPlayerPic(character, Player2_Pic);
    }

    /**
     * Sets the choice box values to given values and sets the selected value to previously selected value.
     * @param values The given values.
     * @param choiceBox The choice box that gets to be set.
     */
    private void setChoiceBox(int[] values, ChoiceBox<Integer> choiceBox) {
        setChoiceBox(values, choiceBox, choiceBox.getValue());
    }

    /**
     * Sets the choice box values to given values and sets the selected value to given selected value.
     * @param values The given values.
     * @param choiceBox The choice box that gets to be set.
     * @param selectedValue The given selected value.
     */
    private void setChoiceBox(int[] values, ChoiceBox<Integer> choiceBox, int selectedValue) {
        ObservableList<Integer> list = createObservableList(values);
        choiceBox.setItems(list);
        choiceBox.setValue(selectedValue);
    }

    /**
     * Sets the choice box values to given values and sets the selected value to previously selected value.
     * @param values The given values.
     * @param choiceBox The choice box that gets to be set.
     */
    private void setChoiceBox(LinkedList<String> values, ChoiceBox<String> choiceBox) {
        setChoiceBox(values, choiceBox, choiceBox.getValue());
    }

    /**
     * Sets the choice box values to given values and sets the selected value to given selected value.
     * @param array The given values.
     * @param choiceBox The choice box that gets to be set.
     * @param selectedValue The given selected value.
     */
    private void setChoiceBox(LinkedList<String> array, ChoiceBox<String> choiceBox, String selectedValue) {
        ObservableList<String> list = createObservableList(array);
        choiceBox.setItems(list);
        choiceBox.setValue(selectedValue);
    }

    /**
     * Sets the given player's picture depending on the given character.
     * @param character The given character. (BLUE/WHITE/..)
     * @param pic The given player's picture.
     */
    private void setPlayerPic(String character, ImageView pic) {
        switch (character) {
            case "Red":
                pic.setImage(RED_CHARACTER.getProfilePic(true));
                break;
            case "Blue":
                pic.setImage(BLUE_CHARACTER.getProfilePic(true));
                break;
            case "Black":
                pic.setImage(BLACK_CHARACTER.getProfilePic(true));
                break;
            case "White":
                pic.setImage(WHITE_CHARACTER.getProfilePic(true));
                break;
            default:
                // nothing
        }
    }



    // UPDATES:

    /**
     * Updates the character choice box depending on the value that has to be removed.
     * @param choiceBox The given character choice box
     * @param valueToRemove The value that has to be removed
     */
    private void updateCharacterChoiceBox(ChoiceBox<String> choiceBox, String valueToRemove) {
        LinkedList<String> otherGameCharacters = getNames();
        otherGameCharacters.remove(valueToRemove);

        setChoiceBox(otherGameCharacters, choiceBox);
    }



    // OBSERVABLE LISTS:

    /**
     * Creates an Observable list for choice box.
     * @param array Integer values.
     * @return The created list.
     */
    private ObservableList<Integer> createObservableList(int[] array) {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * Creates an Observable list for choice box.
     * @param array String values.
     * @return The created list.
     */
    private ObservableList<String> createObservableList(LinkedList<String> array) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(array);
        return list;
    }



    // BACK BUTTON

    /**
     * Closes dialog and resets game stats.
     * @param mouseEvent not used
     */
    public void backButtonClick(MouseEvent mouseEvent) {
        game = null;
        closeDialog();
    }

    /**
     * Changes backButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseEntered(MouseEvent mouseEvent) {
        BackButton.setImage(ImageLoader.loadImage(BACK_BUTTON_CLICK));
    }

    /**
     * Changes backButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseExited(MouseEvent mouseEvent) {
        BackButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
    }



    // CONTINUE BUTTON

    /**
     * Changes scene from Menu to Game and gets chosen values to start the game.
     * @param mouseEvent not used
     */
    public void continueButtonClick(MouseEvent mouseEvent) {
        GameBoard gameBoard;
        switch (GameBoardChoiceBox.getValue()) {
            case LEVEL_1:
                gameBoard = new GameBoard(MAP_LEVEL_1);
                break;
            case LEVEL_2:
                gameBoard = new GameBoard(MAP_LEVEL_2);
                break;
            case LEVEL_3:
                gameBoard = new GameBoard(MAP_LEVEL_3);
                break;
            case LEVEL_4:
                gameBoard = new GameBoard(MAP_LEVEL_4);
                break;
            case LEVEL_5:
                gameBoard = new GameBoard(MAP_LEVEL_5);
                break;
            case RANDOM:
                gameBoard = new GameBoard();
                break;
            default:
                gameBoard = new GameBoard(MAP_LEVEL_1);
        }

        if (NumberOfPlayersChoiceBox.getValue() == 1) {
            game = new Game(
                    NumberOfAIOpponentsChoiceBox.getValue(),
                    NumberOfRoundsChoiceBox.getValue(),
                    gameBoard,
                    findGameCharacter(Player1_CharacterSelect.getValue()),
                    Player1_UsernameTextBox.getText()
            );
        } else {
            game = new Game(
                    NumberOfAIOpponentsChoiceBox.getValue(),
                    NumberOfRoundsChoiceBox.getValue(),
                    gameBoard,
                    findGameCharacter(Player1_CharacterSelect.getValue()),
                    findGameCharacter(Player2_CharacterSelect.getValue()),
                    Player1_UsernameTextBox.getText(),
                    Player2_UsernameTextBox.getText()
            );
        }

        changeStage(FXML_GAME);
        closeDialog();
    }

    /**
     * Changes continueButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void continueButtonOnMouseEntered(MouseEvent mouseEvent) {
        ContinueButton.setImage(ImageLoader.loadImage(CONTINUE_BUTTON_CLICK));
    }

    /**
     * Changes continueButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void continueButtonOnMouseExited(MouseEvent mouseEvent) {
        ContinueButton.setImage(ImageLoader.loadImage(CONTINUE_BUTTON));
    }
}
