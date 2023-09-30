package lsg.graphics.widgets.characters.statbars;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lsg.graphics.ImageFactory;
import lsg.graphics.widgets.texts.GameLabel;

public class StatBar extends BorderPane{

    private static double DEFAULT_WIDTH = 350 ;
    private static double DEFAULT_HEIGHT = 100 ;

    private ImageView avatar ;
    private ProgressBar lifeBar ;
    private ProgressBar stamBar ;
    private GameLabel name ;

    public StatBar() {
        this.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        this.setStyle("-fx-border-color: red");

        avatar = new ImageView() ;
        this.setLeft(avatar);
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(DEFAULT_HEIGHT);
        avatar.setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD)[0]);

        VBox center = new VBox() ;
        this.setCenter(center);

        name = new GameLabel("Name") ;
        name.setStyle("-fx-font-size: 33px");
        center.getChildren().add(name) ;

        lifeBar = new ProgressBar() ;
        center.getChildren().add(lifeBar) ;
        lifeBar.setMaxWidth(Double.MAX_VALUE);
        lifeBar.setStyle("-fx-accent: red");

        stamBar = new ProgressBar() ;
        center.getChildren().add(stamBar) ;
        stamBar.setMaxWidth(Double.MAX_VALUE);
        stamBar.setStyle("-fx-accent: greenyellow");

        // pour peaufiner
        stamBar.maxWidthProperty().bind(center.widthProperty().multiply(0.8));
        stamBar.setMinHeight(0);
        stamBar.setPrefHeight(15);
 }

    public ImageView getAvatar() {
        return avatar;
    }

    public GameLabel getName() {
        return name;
    }

    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public ProgressBar getStamBar() {
        return stamBar;
    }

    public void flip(){
        this.setScaleX(-getScaleX());
        name.setScaleX(-name.getScaleX());
    }
}
