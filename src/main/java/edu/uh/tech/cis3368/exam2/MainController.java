package edu.uh.tech.cis3368.exam2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    public Button btnClickMe;
    public Button btnScratch;
    @FXML
    private TextField interestName;

    @FXML
    private ListView<ResearchInterest> professorInterests;

    @FXML
    private ListView<ResearchInterest> interestList;

    @FXML
    private ImageView professorImage;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ResearchInterestRepository researchInterestRepository;

    private static final DataFormat INTEREST_LIST = new DataFormat("cis3368/researchInterest");
    private Professor professor;

    public void onDragDetected(MouseEvent mouseEvent) {

        int selected = interestList.getSelectionModel().getSelectedIndices().size();
        System.out.println(String.format("%d selected",selected));
        if(selected > 0){
            Dragboard dragboard = interestList.startDragAndDrop(TransferMode.MOVE);
            ArrayList<ResearchInterest> selectedItems = new ArrayList<>(interestList.getSelectionModel().getSelectedItems());
            ClipboardContent content = new ClipboardContent();
            content.put(INTEREST_LIST,selectedItems);
            dragboard.setContent(content);
            mouseEvent.consume();
        } else {
            System.out.println("nothing selected");
            mouseEvent.consume();
        }


    }

    public void onDragDone(DragEvent dragEvent) {
        System.out.println("Drag done detected");
        TransferMode tm = dragEvent.getAcceptedTransferMode();
        if(tm == TransferMode.MOVE) {
            removeSelectedItems();
        }
        dragEvent.consume();

        researchInterestRepository.findAll().forEach(System.out::println);
    }

    public void onDragDropped(DragEvent dragEvent) {
        boolean dragCompleted = false;
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasContent(INTEREST_LIST)) {
            ArrayList<ResearchInterest> researchInterests = (ArrayList<ResearchInterest>) dragboard.getContent(INTEREST_LIST);
            researchInterests.forEach(researchInterest -> {
                researchInterest.setProfessor(professor);
                professor.addResearchInterest(researchInterest);
            });
            professorRepository.save(professor);
            professorInterests.getItems().addAll(researchInterests);
            dragCompleted = true;
        }
        dragEvent.setDropCompleted(dragCompleted);
        dragEvent.consume();
    }

    public void onDragOver(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if(dragboard.hasContent(INTEREST_LIST)){
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        professor = new Professor();
        professor.setLastName("Detillier");
        professor.setOffice("123 T2");
        professorRepository.save(professor);

        ResearchInterest researchInterest = new ResearchInterest();
        researchInterest.setName("Software Project Management");
        researchInterestRepository.save(researchInterest);
        interestList.getItems().add(researchInterest);

    }

    private void removeSelectedItems() {
        ObservableList selectedFleas = interestList.getSelectionModel().getSelectedItems();
        interestList.getItems().removeAll(selectedFleas);
        interestList.getSelectionModel().clearSelection();
    }

    /**
     * Clears the "My Interests" list
     * */
    public void doGetBored(ActionEvent actionEvent) {
        professorInterests.getItems().clear();

        //prints to console
        System.out.println("Interests are cleared from the 'My Interest' list");
    }

    /**
     * Populates the "Interest" list when the button is clicked
     * */
    public void doAddInterest(ActionEvent actionEvent) {
        //saves new interest
        ResearchInterest researchInterest = new ResearchInterest();
        researchInterest.setName(interestName.getText());
        researchInterestRepository.save(researchInterest);

        //clears the textbox
        interestName.setText(null);
        //adds interest to the list
        interestList.getItems().add(researchInterest);

        //prints to console
        System.out.println("New Interest are added on the 'Interest' list");
    }

    /**
     * This code makes the button move; therefore, I commented it out to get
     * the button to stop moving.
     * */
    /**public void doMouseOver(MouseEvent mouseEvent) {
        double move = 100;
        var rhs = btnClickMe.getScene().getWidth();
        Bounds bounds = btnClickMe.getBoundsInParent();
        if(bounds.getMinX() > bounds.getWidth()){
            btnClickMe.relocate(bounds.getMinX() - move,bounds.getMinY());
        } else if(bounds.getMinX() < bounds.getWidth()){
            btnClickMe.relocate(rhs - bounds.getWidth(),bounds.getMinY());
        }
     //deleted from main.fxml file onMouseEntered="#doMouseOver"

    }*/

    /**
     * Bonus: button does not move and it prints out the string:
     * "Give me 5 points!"
     * */
    public void btnGood(ActionEvent actionEvent) {
        //gives an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Give me 5 points!");
        alert.showAndWait();

        //prints to console
        System.out.println("Give me 5 points!");

    }
}
