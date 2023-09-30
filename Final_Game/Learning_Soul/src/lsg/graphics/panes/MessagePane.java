package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

public class MessagePane extends VBox {

    private static final Duration ANIMATION_DURATION = Duration.millis(3000) ;
    private static final double ANIMATION_TRANSLATION = -200 ;

    public MessagePane() {
        setAlignment(Pos.CENTER);
    }

    public void showMessage(String msg){
        GameLabel text = new GameLabel(msg) ;
        getChildren().add(text) ;

        TranslateTransition tt = new TranslateTransition(ANIMATION_DURATION) ;
        tt.setByY(ANIMATION_TRANSLATION);

        FadeTransition ft = new FadeTransition(ANIMATION_DURATION) ;
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(tt, ft) ;
        pt.setNode(text);
        pt.setCycleCount(1);
        pt.setOnFinished((event)->{
            this.getChildren().remove(text);
        });
        pt.play();
    }
}
