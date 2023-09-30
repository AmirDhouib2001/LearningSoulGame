
package lsg;

import lsg.armor.ArmorItem;
import lsg.armor.BlackWitchVeil;
import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.buffs.rings.DragonSlayerRing;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.talismans.MoonStone;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Lycanthrope;
import lsg.characters.Monster;
import lsg.consumables.Consumable;
import lsg.consumables.MenuBestOfV4;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;
import lsg.bags.MediumBag;
import lsg.exceptions.*;
import lsg.weapons.ShotGun;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;

import java.util.Scanner;

public class LearningSoulsGame {

	public static final String BULLET_POINT = "\u2219 " ;

	Scanner scanner = new Scanner(System.in) ;
	
	Hero hero ;
	Monster monster ;

	private void createExhaustedHero(){
		System.out.println("Create exhausted hero : ");
		hero = new Hero() ;

		// pour vider la vie
		hero.getHitWith(99) ;

		// pour depenser la stam
		hero.setWeapon(new Weapon("Grosse Arme", 0, 0, 1000, 100));
		try {
			hero.attack() ;
		} catch (WeaponNullException e) {
			e.printStackTrace();
		} catch (WeaponBrokenException e) {
			e.printStackTrace();
		} catch (StaminaEmptyException e) {
			e.printStackTrace();
		}

		System.out.println(hero);

	}

	private void init(){
		hero = new Hero() ;
		hero.setWeapon(new Sword());
		hero.setArmorItem(new DragonSlayerLeggings(), 1);
		hero.setRing(new RingOfDeath(), 1);
		hero.setRing(new DragonSlayerRing(), 2);

		hero.setConsumable(new Hamburger());

		monster = new Lycanthrope() ; // plus besoin de donner la skin et l'arme !
		monster.setTalisman(new MoonStone());
	}

	private void play(){
		init();
		fight1v1();
	}
	
	private void fight1v1(){

		refresh();
		
		Character agressor = hero ;
		Character target = monster ;
		int action ; // TODO sera effectivement utilise dans une autre version
		int attack, hit ;
		Character tmp ;

		while(hero.isAlive() && monster.isAlive()){ // ATTENTION : boucle infinie si 0 stamina...

			action = 1 ; // par defaut on lancera une attaque
			System.out.println();

			if(agressor == hero) {
				do {
					System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
					action = scanner.nextInt(); // GENERERA UNE ERREUR L'UTILISATEUR ENTRE AUTRE CHOSE QU'UN ENTIER (ON TRAITERA PLUS TARD)
				}while(action < 1 || action > 2) ;
				System.out.println();
			}

			if(action == 2){
				try {
					hero.consume();
				} catch (ConsumeNullException e) {
					System.out.println("IMPOSSIBLE ACTION : no consumable has been equiped !");
				} catch (ConsumeEmptyException e) {
					System.out.println("ACTION HAS NO EFFECT: " + e.getConsumable().getName() + " is empty !");
				} catch (ConsumeRepairNullWeaponException e) {
					System.out.println("IMPOSSIBLE ACTION : no weapon has been equiped !");
				}
				System.out.println();
			}else{
				try {
					attack = agressor.attack() ;
				} catch (WeaponNullException e) {
					System.out.println("WARNING : no weapon has been equiped !!!\n");
					attack = 0 ;
				} catch (WeaponBrokenException e) {
					System.out.println("WARNING : " + e.getMessage() + "\n");
					attack = 0 ;
				} catch (StaminaEmptyException e) {
					System.out.println("ACTION HAS NO EFFECT: no more stamina !!!\n");
					attack = 0 ;
				}
				hit = target.getHitWith(attack);
				System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)", agressor.getName(), target.getName(), agressor.getWeapon(), attack, hit);
				System.out.println();
				System.out.println();
			}

			refresh();
			
			tmp = agressor ;
			agressor = target ;
			target = tmp ;
			
		}
		
		Character winner = (hero.isAlive()) ? hero : monster ;
		System.out.println();
		System.out.println("--- " + winner.getName() + " WINS !!! ---");
		
	}
	
	private void refresh(){
		hero.printStats();
		hero.printArmor();
		hero.printRings();
		hero.printConsumable();
		hero.printWeapon();
		hero.printBag();

		System.out.println();
		System.out.println();

		monster.printStats();
		monster.printWeapon();
	}

	private void title(){
		System.out.println();
		System.out.println("###############################");
		System.out.println("#   THE LEARNING SOULS GAME   #");
		System.out.println("###############################");
		System.out.println();
	}

	private void testExceptions(){
//		hero.setWeapon(null);
//		hero.setConsumable(null);
//		hero.setConsumable(new RepairKit());

//		refresh();
//		System.out.println();
//		hero.setBag(null) ;
//		System.out.println();
//		try {
//			System.out.println();
//			hero.pickUp(new RingedKnightArmor());
//			System.out.println();
//			hero.pickUp(new BlackWitchVeil());
//			System.out.println();
//			hero.pickUp(new DragonSlayerLeggings());
//			hero.pickUp(new ShotGun());
//			hero.pickUp(new Sword());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		refresh();

		hero.setWeapon(new Weapon("Pelle cassee", 0, 100, 2, 0));
		fight1v1();

//		play();
	}

	public static void main(String[] args) {
		LearningSoulsGame lsg = new LearningSoulsGame() ;
		lsg.title();
		lsg.init() ;
		lsg.testExceptions();
	}

}
