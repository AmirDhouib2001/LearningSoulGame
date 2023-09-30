package lsg.graphics.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import lsg.graphics.widgets.characters.statbars.StatBar;

public class HUDPane extends BorderPane {

    private MessagePane messagePane ;
    private StatBar heroStatBar, monserStatBar ;

    public HUDPane() {
        this.setPadding(new Insets(80, 10, 10, 10));
        buildCenter() ;
        buildTop() ;
    }

    public MessagePane getMessagePane() {
        return messagePane;
    }

    public StatBar getHeroStatBar() {
        return heroStatBar;
    }

    public StatBar getMonserStatBar() {
        return monserStatBar;
    }

    private void buildTop(){
        BorderPane borderPane = new BorderPane() ;
        this.setTop(borderPane);

        heroStatBar = new StatBar() ;
        borderPane.setLeft(heroStatBar);

        monserStatBar = new StatBar() ;
        borderPane.setRight(monserStatBar);
        monserStatBar.flip();
    }

    private void buildCenter(){
        messagePane = new MessagePane() ;
        this.setCenter(messagePane);
    }
}
