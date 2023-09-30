package lsg;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.exceptions.StaminaEmptyException;
import lsg.exceptions.WeaponBrokenException;
import lsg.exceptions.WeaponNullException;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.HUDPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.characters.renderers.CharacterRenderer;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.characters.statbars.StatBar;
import lsg.weapons.Sword;

public class LearningSoulsGameApplication extends Application{

    public static final String TITLE = "Learning Souls Game" ;

    public static final double DEFAULT_SCENE_WIDTH = 1200 ;
    public static final double DEFAULT_SCENE_HEIGHT = 800 ;

    private Scene scene ;
    private AnchorPane root;

    private TitlePane gameTitle ;
    private CreationPane creationPane ;
    private AnimationPane animationPane ;
    private HUDPane hudPane ;

    private String heroName ;

    private Hero hero ;
    private HeroRenderer heroRenderer ;

    private Zombie zombie ;
    private ZombieRenderer zombieRenderer ;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle(TITLE);
        root = new AnchorPane() ;
        scene = new Scene(root, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

        stage.setResizable(false);
        stage.setScene(scene);
        buildUI() ;
        addListeners() ;

        stage.show();

        startGame() ;
    }

    private void addListeners(){
        creationPane.getNameField().setOnAction((event -> {
            heroName = creationPane.getNameField().getText() ;
            System.out.println("Nom du héro : " + heroName);
            if(!heroName.isEmpty()){
                root.getChildren().remove(creationPane);
                gameTitle.zoomOut((event1 -> play()));
            }
        }));
    }

    private void startGame(){
        gameTitle.zoomIn((event -> {
            creationPane.fadeIn((event1 -> {
                ImageFactory.preloadAll((() -> {
                    System.out.println("Pré-chargement des images terminé") ;
                })) ;
            }));
        }));
    }

    private void play(){
        root.getChildren().add(animationPane) ;
        root.getChildren().add(hudPane) ;
        createHero();
        createMonster((event -> {
            System.out.println("FIGHT !");
            hudPane.getMessagePane().showMessage("FIGHT !");

            heroRenderer.attack((event1 -> {
                try {
                    zombie.getHitWith(hero.attack()) ;
                } catch (Exception e) {
                    hudPane.getMessagePane().showMessage(e.getMessage());
                    e.printStackTrace();
                }
            }));


        }));
    }

    private void createHero(){
        hero = new Hero(heroName) ;
        hero.setWeapon(new Sword());

        heroRenderer = animationPane.createHeroRenderer() ;
        heroRenderer.goTo(root.getWidth()*0.5 - heroRenderer.getFitWidth()*0.65, null);

        StatBar bar = hudPane.getHeroStatBar() ;
        bar.getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD)[0]);
        bar.getName().setText(hero.getName());

        hudPane.getHeroStatBar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());

        bar.getLifeBar().progressProperty().bind(hero.lifeRateProperty());
        bar.getStamBar().progressProperty().bind(hero.staminaRateProperty());
    }

    private void createMonster(EventHandler<ActionEvent> finishedHandler){
        zombie = new Zombie() ;
        zombieRenderer = animationPane.createZombieRenderer() ;
        zombieRenderer.goTo(root.getWidth()*0.5 - zombieRenderer.getBoundsInLocal().getWidth() * 0.15, finishedHandler);

        StatBar bar = hudPane.getMonserStatBar();;
        bar.getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ZOMBIE_HEAD)[0]);
        bar.getAvatar().setRotate(30);
        bar.getName().setText(zombie.getName());
        bar.getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
        bar.getStamBar().progressProperty().bind(zombie.staminaRateProperty());
    }

    private void buildUI(){
        scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css")) ;

        gameTitle = new TitlePane(scene, LearningSoulsGameApplication.TITLE) ;
        AnchorPane.setTopAnchor(gameTitle, 0.0);
        AnchorPane.setLeftAnchor(gameTitle, 0.0);
        AnchorPane.setRightAnchor(gameTitle, 0.0);
        root.getChildren().addAll(gameTitle) ;

        creationPane = new CreationPane() ;
        creationPane.setOpacity(0);
        AnchorPane.setTopAnchor(creationPane, 0.0);
        AnchorPane.setLeftAnchor(creationPane, 0.0);
        AnchorPane.setRightAnchor(creationPane, 0.0);
        AnchorPane.setBottomAnchor(creationPane, 0.0);
        root.getChildren().addAll(creationPane) ;

        animationPane = new AnimationPane(root) ;

        hudPane = new HUDPane() ;
        AnchorPane.setTopAnchor(hudPane, 0.0);
        AnchorPane.setLeftAnchor(hudPane, 0.0);
        AnchorPane.setRightAnchor(hudPane, 0.0);
        AnchorPane.setBottomAnchor(hudPane, 0.0);

    }

}
