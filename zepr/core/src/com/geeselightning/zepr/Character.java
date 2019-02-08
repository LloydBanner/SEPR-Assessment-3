package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.abs;

public class Character extends Sprite {

    Vector2 velocity = new Vector2(); // 2D vector
    public float speed;
    int health = 100;
    // direction is a bearing in radians
    double direction = 0;
    Level currentLevel;
    // All characters start ready to hit.
    float hitRefresh = 2;
    int maxHealth;

    public Character(Sprite sprite, Vector2 spawn, Level currentLevel) {
        super(sprite);
        setX(spawn.x);
        setY(spawn.y);
        this.currentLevel = currentLevel;
    }

    public double getDirection() {
        return direction;
    }

    public double getHealth() {
        return health;
    }
    
    public void attack(Character character, float delta) {
    	// Implemented in higher classes
    }

    /**
     * Uses circles with diameter to determine if this character collides with the passed character.
     *
     * @param character Character to check if this collides with
     * @return boolean true if they collide, false otherwise
     */
    public boolean collidesWith(Character character) {
        // Circles less buggy than character.getBoundingRectangle()
        double diameter = 10;
        double distanceBetweenCenters = (Math.pow(getCenter().x - character.getCenter().x, 2)
                + Math.pow(getCenter().y - character.getCenter().y, 2));
        return (0 <= distanceBetweenCenters && distanceBetweenCenters <= Math.pow(diameter, 2));
    }
    
    /**
     * Added by Shaun of the Devs
     * Uses distance between this Character and another character in the x axis to decide how they collide.
     *
     * @param character Character to check if this collides with
     * @return boolean true if they collide, false otherwise
     */
    public boolean collidesWithX(Character character) {
    	double distanceX = Math.pow(getCenter().x - character.getCenter().x, 2);
    	return (0 <= distanceX);
    }
    
    /**
     * Added by Shaun of the Devs
     * Uses distance between this Character and another character in the y axis to decide how they collide.
     *
     * @param character Character to check if this collides with
     * @return boolean true if they collide, false otherwise
     */
    public boolean collidesWithY(Character character) {
    	double distanceY = Math.pow(getCenter().y - character.getCenter().y, 2);
    	return (0 <= distanceY);
    }

    @Override
    public void draw(Batch batch) {
        // Calls the update method of the character. To update its properties, i.e. position.
        // The method is given the parameter delta so it can calculate the character new position.
        update(Gdx.graphics.getDeltaTime());

        super.draw(batch);

        setRotation((float) Math.toDegrees(- direction));
    }

    // hitRange has to be passed by the subclass from the canHit method.
    protected boolean canHitGlobal(Character character, int hitRange) {
        double directionToCharacter = this.getDirectionTo(character.getCenter());
        double angle = abs(directionToCharacter - direction);
        double distance = this.getCenter().sub(character.getCenter()).len();

        if (angle < 0.8 && distance < hitRange) {
            return true;
        } else {
            return false;
        }
    }

    public Vector2 getCenter() {
        return new Vector2(getX() + (getHeight() / 2), getY() + (getWidth() / 2));
    }

    /**
     * Finds the direction (in radians) that an object is in relative to the character.
     *
     * @param coordinate 2d vector representing the position of the object
     * @return bearing   double in radians of the bearing from the character to the coordinate
     */
    public double getDirectionTo(Vector2 coordinate) {
        Vector2 charCenter = new Vector2(this.getX() + (getWidth()/ 2),
                this.getY()+ (getHeight() / 2));

        // atan2 is uses the signs of both variables the determine the correct quadrant (relative to the character) of the
        // result.
        // Modulus 2pi of the angle must be taken as the angle is negative for the -x quadrants.
        // The angle must first be displaced by 2pi because the Java modulus function can return a -ve value.

        return(Math.atan2((coordinate.x - charCenter.x), (coordinate.y - charCenter.y)) + (2 * Math.PI))
                % (2 * Math.PI);
    }

    /**
     * Calculates a normailized vector that points torwards given coordinate.
     *
     * @param coordinate Vector2 representing the position of the object
     * @return normalised Vector2 that from this will point towards given coordinate
     */
    public Vector2 getDirNormVector(Vector2 coordinate) {
        Vector2 charCenter = new Vector2(this.getX() + (getWidth() / 2),
                this.getY() + (getHeight() / 2));
        // create vector that is the difference between character and the coordinate, and return it normalised
        Vector2 diffVector = new Vector2((coordinate.x - charCenter.x), (coordinate.y - charCenter.y));
        return diffVector.nor();
    }

    /**
     * This method updates all the characters properties.
     *
     * @param delta the time passed since the last frame (render() call)
     */
    public void update(float delta) {
        // Update x, y position of character.
        // New position is the old position plus the distance moved as a result of the velocity
        float oldX = getX(), oldY = getY();

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);

        // Get all characters in the currentLevel
        ArrayList<Character> otherCharacters = currentLevel.getCharacters();
        // Remove this character otherwise it will collide with itself
        otherCharacters.remove(this);

        for (Character character : otherCharacters) {
            if (collidesWith(character)) {
            	// Added to check X and Y collisions separately
            	if (collidesWithX(character)) {
            		setX(oldX);
            	}
                if (collidesWithY(character)) {
                	setY(oldY);
                }
            }
        }

        // List of all corners of sprite, added extra points in the centre of the edges
        ArrayList<Vector2> spritePoints = new ArrayList<Vector2>(Arrays.asList(new Vector2(getX(), getY()),
                new Vector2(getX() + getWidth(), getY()), new Vector2(getX(), getY() + getHeight()),
                new Vector2(getX() + getWidth(), getY() + getHeight()), new Vector2(getX() + getWidth()/2, getY() + getHeight()),
                new Vector2(getX() + getWidth(), getY() + getHeight()/2), new Vector2(getX(), getY() + getHeight()/2),
                new Vector2(getX() + getWidth()/2, getY())));
        
        // Old corners
        // Added to improve collisions by allowing better checks
        ArrayList<Vector2> oldPoints = new ArrayList<Vector2>(Arrays.asList(new Vector2(oldX, oldY),
                new Vector2(oldX + getWidth(), oldY), new Vector2(oldX, oldY + getHeight()),
                new Vector2(oldX + getWidth(), oldY + getHeight()), new Vector2(oldX + getWidth()/2, oldY + getHeight()),
                new Vector2(oldX + getWidth(), oldY + getHeight()/2), new Vector2(oldX, oldY + getHeight()/2),
                new Vector2(oldX + getWidth()/2, oldY)));

        // Make sure non of the corners goto a blocked region of the map
        for (int n = 0; n < spritePoints.size(); n++) {
        	// Changed to check X and Y separately to improve collisions
            if (currentLevel.isBlocked(spritePoints.get(n).x, oldPoints.get(n).y)) {
                setX(oldX);
            }
            if (currentLevel.isBlocked(oldPoints.get(n).x, spritePoints.get(n).y)) {
                setY(oldY);
            }
        }

    }

    // Decreases health by value of dmg
    public void takeDamage(int dmg){
        health -= dmg;
    }
}
