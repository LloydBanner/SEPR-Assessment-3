package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class ZeprInputProcessor implements InputProcessor {

    protected Vector2 mousePosition = new Vector2(0, 0);
    private Player player = Player.getInstance();

    @Override
    public boolean keyDown(int keycode) {
        // Adding inputs for WASD as movement in the x and y axis.
        if (keycode == Input.Keys.W) {
            player.velocity.y = player.speed;
        }
        if (keycode == Input.Keys.S) {
            player.velocity.y = - player.speed;
        }
        if (keycode == Input.Keys.D) {
            player.velocity.x = player.speed;
        }
        if (keycode == Input.Keys.A) {
            player.velocity.x = - player.speed;
        }
        if (keycode == Input.Keys.ESCAPE) {
            player.currentLevel.isPaused = !(player.currentLevel.isPaused);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // This causes the player to stop moving in a certain direction when the corresponding key is released.
        if (keycode == Input.Keys.W || keycode == Input.Keys.S) {
            player.velocity.y = 0;
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            player.velocity.x = 0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.attack = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.attack = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY());
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
