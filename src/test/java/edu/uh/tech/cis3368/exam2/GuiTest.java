package edu.uh.tech.cis3368.exam2;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.security.Key;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.assertContext;
import static org.testfx.api.FxAssert.verifyThat;

public class GuiTest extends ApplicationTest {

    public static final String SOFTWARE_PROJECT_MANAGEMENT = "Software Project Management";

    @Before
    public void setUpClass() throws Exception{
        ApplicationTest.launch(edu.uh.tech.cis3368.exam2.Exam2.class);
    }




    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    public <T extends Node> T find(final String query){
        return(T) lookup(query).queryAll().iterator().next();
    }

    @Test
    public void clickOnImBored(){
        clickOn(SOFTWARE_PROJECT_MANAGEMENT);
        drag(MouseButton.PRIMARY);
        dropTo("#professorImage");
        WaitForAsyncUtils.sleep(2,TimeUnit.SECONDS);
        clickOn("I am bored!");
        WaitForAsyncUtils.sleep(2,TimeUnit.SECONDS); // probably a better way to wait
        verifyThat("#professorInterests",ListViewMatchers.hasItems(0));
    }

    @Test
    public void addingNewInterestToInterestList(){
        var name = "Machine Learning";
        TextField tf = find("#interestName");
        tf.setText(name);
        clickOn("Add Interest");
        WaitForAsyncUtils.sleep(2,TimeUnit.SECONDS);
        ListView<ResearchInterest> list = find("#interestList");
        assertTrue("New item was not found in list",list.getItems().stream()
                .anyMatch(x -> x.getName().equals(name)));


    }

    @Test
    public void testDragDropInterest(){
        clickOn(SOFTWARE_PROJECT_MANAGEMENT);
        drag(MouseButton.PRIMARY);
        dropTo("#professorImage");
        WaitForAsyncUtils.sleep(2,TimeUnit.SECONDS);
        ListView<ResearchInterest> list = find("#interestList");
        assertTrue("Item was found in list",list.getItems().stream()
                .noneMatch(x -> x.getName().equals(SOFTWARE_PROJECT_MANAGEMENT)));

    }
}
