package com.github.timshnayder;

public class Grid
{
    private Location[][] grid;

    // Constants for number of rows and columns.
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;

    // Create a new Grid. Initialize each Location in the grid
    // to be a new Location object.
    public Grid()
    {
        grid = new Location[NUM_COLS][NUM_ROWS];
        for(int col = 0; col < NUM_COLS; col++)
        {
            for(int row = 0; row < NUM_ROWS; row++)
            {
                grid[col][row] = new Location();
            }
        }
    }

    // Mark a hit in this location by calling the markHit method
    // on the Location object.
    public void markHit(int row, int col)
    {
        get(row, col).markHit();
    }

    // Mark a miss on this location.
    public void markMiss(int row, int col)
    {
        get(row, col).markMiss();
    }

    // Set the status of this location object.
    public void setStatus(int row, int col, int status)
    {
        get(row, col).setStatus(status);
    }

    // Get the status of this location in the grid
    public int getStatus(int row, int col)
    {
        return get(row, col).getStatus();
    }

    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col)
    {
        return !get(row, col).isUnguessed();
    }

    // Set whether or not there is a ship at this location to the val
    public void setShip(int row, int col, boolean val)
    {
        get(row, col).setShip(val);
    }

    // Return whether or not there is a ship here
    public boolean hasShip(int row, int col)
    {
        return get(row, col).hasShip();
    }


    // Get the Location object at this row and column position
    public Location get(int row, int col)
    {
        return grid[col-1][row-1];
    }

    // Return the number of rows in the Grid
    public int numRows()
    {
        return NUM_ROWS;
    }

    // Return the number of columns in the grid
    public int numCols()
    {
        return NUM_COLS;
    }


    // Print the Grid status including a header at the top
    // that shows the columns 1-10 as well as letters across
    // the side for A-J
    // If there is no guess print a -
    // If it was a miss print a O
    // If it was a hit, print an X
    // A sample print out would look something like this:
    //
    //   1 2 3 4 5 6 7 8 9 10
    // A - - - - - - - - - -
    // B - - - - - - - - - -
    // C - - - O - - - - - -
    // D - O - - - - - - - -
    // E - X - - - - - - - -
    // F - X - - - - - - - -
    // G - X - - - - - - - -
    // H - O - - - - - - - -
    // I - - - - - - - - - -
    // J - - - - - - - - - -
    public void printStatus()
    {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for(int row = 1; row <= NUM_ROWS; row++)
        {
            System.out.print((char)('A'+row-1) + " ");
            for(int col = 1; col <= NUM_COLS; col++)
            {
                if(!alreadyGuessed(row, col))
                {
                    System.out.print("- ");
                }
                else if(getStatus(row,col) == Location.MISSED)
                {
                    System.out.print("O ");
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    // Print the grid and whether there is a ship at each location.
    // If there is no ship, you will print a - and if there is a
    // ship you will print a X. You can find out if there was a ship
    // by calling the hasShip method.
    //
    //   1 2 3 4 5 6 7 8 9 10
    // A - - - - - - - - - -
    // B - X - - - - - - - -
    // C - X - - - - - - - -
    // D - - - - - - - - - -
    // E X X X - - - - - - -
    // F - - - - - - - - - -
    // G - - - - - - - - - -
    // H - - - X X X X - X -
    // I - - - - - - - - X -
    // J - - - - - - - - X -
    public void printShips()
    {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for(int row = 1; row <= NUM_ROWS; row++)
        {
            System.out.print((char)('A'+row-1) + " ");
            for(int col = 1; col <= NUM_COLS; col++)
            {
                if(!hasShip(row, col))
                {
                    System.out.print("- ");
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    /**
     * This method can be called on your own grid. To add a ship
     * we will go to the ships location and mark a true value
     * in every location that the ship takes up.
     */
    public boolean addShip(Ship s, boolean verbose)
    {
        if(s.getDirection() == Ship.VERTICAL)
        {
            if(s.getRow() + s.getLength()-1 > NUM_ROWS)
            {
                if(verbose)
                {
                    System.out.println("Ship is on the shore. Try Again.");
                }
                return false;
            }
            if(s.getCol() > NUM_COLS)
            {
                if(verbose)
                {
                    System.out.println("Ship is on the shore. Try Again.");
                }
                return false;
            }
            for(int row = s.getRow(); row < s.getRow() + s.getLength(); row++)
            {
                if(hasShip(row,s.getCol()))
                {
                    if(verbose)
                    {
                        System.out.println("This place is already occupied by another ship. Try Again.");
                    }
                    return false;
                }
            }
            for(int row = s.getRow(); row < s.getRow() + s.getLength(); row++)
            {
                setShip(row,s.getCol(), true);
            }
            return true;
        }
        if(s.getDirection() == Ship.HORIZONTAL)
        {
            if(s.getCol() + s.getLength()-1 > NUM_COLS)
            {
                if(verbose)
                {
                    System.out.println("Ship is on the shore. Try Again.");
                }
                return false;
            }
            if(s.getRow() > NUM_ROWS)
            {
                if(verbose)
                {
                    System.out.println("Ship is on the shore. Try Again.");
                }
                return false;
            }
            for(int col = s.getCol(); col < s.getCol() + s.getLength(); col++)
            {
                if(hasShip(s.getRow(), col))
                {
                    if(verbose)
                    {
                        System.out.println("This place is already occupied by another ship. Try Again.");
                    }
                    return false;
                }
            }
            for(int col = s.getCol(); col < s.getCol() + s.getLength(); col++)
            {
                setShip(s.getRow(), col, true);
            }
            return true;
        }
        return false;

    }

}

