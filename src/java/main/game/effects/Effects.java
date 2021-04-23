package main.game.effects;

import main.game.effects.buffs.Buff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 6. 5. 2020
 * Time: 14:22
 */
public class Effects {
    private List<Effect> effects;

    /**
     * The Constructor of Effects.
     */
    public Effects() {
        this.effects = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Adds effect to the list.
     * @param effect The given effect.
     */
    public void add(Effect effect) {
        this.effects.add(effect);
    }

    /**
     * Gets an index from list.
     * @param index The given index.
     * @return The index.
     */
    public Effect get(int index) {
        return this.effects.get(index);
    }

    /**
     * Adds effect on certain index in the list.
     * @param index The given index.
     * @param effect The given effect.
     */
    void put(int index, Effect effect) {
        this.effects.add(index, effect);
    }

    /**
     * Gets last index used.
     * @return The last index used.
     */
    public int getLastIndexUsed() {
        return this.effects.size() - 1;
    }

    /**
     * Removes effect from the index in the list.
     * @param index The given index.
     */
    void remove(int index) {
        this.effects.remove(index);
    }

    /**
     * Removes effect from the list.
     * @param effect The given effect.
     */
    public void remove(Effect effect) {
        this.effects.remove(effect);
    }

    /**
     * Checks if the list is empty.
     * @return True or false.
     */
    public boolean isEmpty() {
        return effects.isEmpty();
    }

    /**
     * Gets the list of effects.
     * @return The list of effects.
     */
    public List<Effect> getList() {
        return effects;
    }

    /**
     * Checks if any buff is found.
     * @return Returns buff if buff is found, otherwise returns null.
     */
    public Buff getBuff() {
        for (Effect e : this.getList()) {
            if (e.isAbleToPickUp()) {
                return (Buff) e;
            }
        }
        return null;
    }
}
