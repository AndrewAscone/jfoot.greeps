package com.github.curriculeon.greeps;

import com.github.git_leon.RandomUtils;
import com.github.git_leon.jfoot.sprite.SpriteSensorDecorator;
import greenfoot.Actor;

/**
 * A Greep is an alien creature that likes to collect tomatoes.
 *
 * @author (your name here)
 * @version 0.1
 */
public class Greep extends Creature {
    /**
     * Create a creature at its ship.
     *
     * @param ship
     */
    public Greep(Spaceship ship) {
        super(ship);
        setImage(getCurrentImage());
    }

    //Determines how Greep will act
    @Override
    protected void behave() {
        //If Greep has a tomato
        if (isCarryingTomato()) {
            //If the Greep is at the ship
            if (isAtShip()) {
                //Drop tomato in ship, adding it to ship storage
                dropTomato();
            } else { //If Greep has a tomato but *isn't* at the ship yet
                //Greep will use turnTowardsHome method to face towards the ship
                turnTowardsHome();
            }
        }
        //If Greep doesn't have a tomato, keep moving
        move();
    }

    private Boolean isToLeft(Actor actor) {
        int currentRotation = getRotation();
        turnTowards(actor);
        int relativeAngle = getRotation();
        if (relativeAngle > 180)
            return true;
        setRotation(currentRotation);
        return false;
    }

    private GreepSpit getIntersectingSpit() {
        return (GreepSpit) getOneIntersectingObject(GreepSpit.class);
    }

    private boolean isAtSpit(String colorName) {
        GreepSpit potentialSpit = (GreepSpit) getOneIntersectingObject(GreepSpit.class);
        if (potentialSpit != null) {
            return potentialSpit.getColor().equalsIgnoreCase(colorName);
        }
        return false;
    }

    //Checks if a Greep is waiting for help with taking a tomato
    public Boolean isWaitingForAssistance() {
        //Boolean check if Greep is at a tomato pile AND is not carrying one already
        return isAtTomatoes() && !isCarryingTomato();
    }


    //Check if a Greep is waiting to help load a tomato onto another Greep
    public Boolean isWaitingToAssist() {
        //If Greep is at a tomato pile
        if (isAtTomatoes()) {
            //For each Greep surrounding the tomato pile
            for (Greep greep : getSurroundingTomatoPile().getIntersectingObjects(Greep.class)) {
                //If a Greep *isn't* carrying a tomato
                if (!greep.isCarryingTomato()) {
                    //Then isWaitingToAssist is true
                    return true;
                }
            }
        }
        //If Greep is not at a tomato pile, isWaitingToAssist is false
        return false;
    }


    public void waitForTomatoLoadingAssistance() {
        turnTowards(getSurroundingTomatoPile());
        move();
        loadTomato();
    }


    //Check if Greep is returning to ship
    public Boolean isReturningToShip() {
        //Check if Greep is carrying a tomato to determine outcome
        return isCarryingTomato();
    }


    public void returnToShip() {
        turnTowardsHome(3);
        move();
    }


    //Determine if Greep should be looking for tomatoes
    public Boolean shouldSeekTomatoPile() {
        //If Greep *is* carrying a tomato, it should not seek more tomatoes
        //If Greep *isn't* carraying a tomato, it should look for a tomato pile
        return !isCarryingTomato();
    }


    public void seekTomatoPile() {
        move();
    }


    public void turnRandomDegrees() {
        turnRandomDegrees(15, 90);
    }


    public void turnRandomDegrees(int minimumTurn, int maximumTurn) {
        turn(RandomUtils.createInteger(minimumTurn, maximumTurn));
    }


    public void turnRandomly(int minimumTurn, int maximumTurn, float likelihoodOfTurn) {
        if (minimumTurn > maximumTurn) {
            Integer temp;
            temp = maximumTurn;
            maximumTurn = minimumTurn;
            minimumTurn = temp;
        }
        if (RandomUtils.createBoolean(likelihoodOfTurn)) {
            turnRandomDegrees(minimumTurn, maximumTurn);
        }
    }

    /**
     * Is there any food here where we are? If so, try to load some!
     */

    public void checkFood() {
        // check whether there's a tomato pile here
        if (isAtTomatoes()) {
            loadTomato();
            // Note: this attempts to load a tomato onto *another* Greep. It won't
            // do anything if we are alone here.
        }
    }


    /**
     * This method specifies the image we want displayed at any time. (No need
     * to change this for the competition.)
     */

    public String getCurrentImage() {
        if (isCarryingTomato())
            return "greep-with-food.png";
        else
            return "greep.png";
    }

    /**
     * Create a Greep with its home space ship.
     */

    public static String getAuthorName() {
        return "Anonymous";
    }
}