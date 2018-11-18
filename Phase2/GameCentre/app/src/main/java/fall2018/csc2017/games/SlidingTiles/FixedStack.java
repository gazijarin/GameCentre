package fall2018.csc2017.games.SlidingTiles;

/*
 * This code is from StackOverflow user Calvin
 * Original code found here: https://stackoverflow.com/questions/7727919/creating-a-fixed-size-stack
 */

import java.util.Stack;

/**
 * A stack that always has a fixed max size
 * Removes the older item when adding to a full size FixedStack
 * Other works as a regular stack
 *
 * @param <T>, the type of objects to be stored in the FixedStack
 */
public class FixedStack<T> extends Stack<T> {
    /**
     * The maximum size of this FixedStack
     */
    private int maxSize;

    /**
     * Initializes this FixedStack
     *
     * @param size the size of this FixedStack
     */
    public FixedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        if (maxSize == 0) {
            return null;
        }
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }
}
