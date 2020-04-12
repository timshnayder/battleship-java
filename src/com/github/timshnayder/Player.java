package com.github.timshnayder;

public class Player extends ConsoleProgram
{
    // These are the lengths of all of the ships.
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    private Ship[] ships = new Ship[5];
    private Grid my = new Grid();
    private Grid guesses = new Grid();

    public Player()
    {

    }

    public boolean chooseShipLocation(Ship s, int row, int col, int direction, boolean verbose)
    {
        s.setLocation(row, col);
        s.setDirection(direction);
        return my.addShip(s, verbose);
    }

    public void placeShips()
    {
        for(int length: SHIP_LENGTHS)
        {
            int direction;
            int row;
            int col;
            Ship s;
            do
            {
                direction = Randomizer.nextInt(2);
                row = Randomizer.nextInt(1, 10);
                col = Randomizer.nextInt(1, 10);
                s = new Ship(length);
            }
            while (!chooseShipLocation(s, row, col, direction, false));
        }
    }

    public void askShips()
    {
        for(int length: SHIP_LENGTHS)
        {
            int direction;
            int row;
            int col;
            String d;
            Ship s;
            do
            {
                my.printShips();
                System.out.println("Placing ship of length " + length);
                do{
                    d = readLine("Which direction is this ship?(H Horizontal / V Vertical): ");
                }while(!d.equals("V") && !d.equals("H") && !d.equals("v") && !d.equals("h"));
                direction = (d.equals("V") || d.equals("v"))? Ship.VERTICAL : Ship.HORIZONTAL;
                do{
                    String r = askForGuess("Location of ship: ");
                    row = r.charAt(0)-'A'+1;
                    if (row < 1 || row > 10)
                    {
                        row = r.charAt(0)-'a'+1;
                    }
                    col = Integer.parseInt(r.substring(1));
                }
                while (row < 1 || row > 10 || col < 1 || col > 10);
                s = new Ship(length);
            }
            while (!chooseShipLocation(s, row, col, direction, true));
        }
        my.printShips();
    }

    public boolean hasShip(int row, int col)
    {
        return my.hasShip(row, col);
    }

    // Mark a hit in this location by calling the markHit method
    // on the Location object.
    public void markHit(int row, int col)
    {
        guesses.markHit(row, col);
    }

    // Mark a miss on this location.
    public void markMiss(int row, int col)
    {
        guesses.markMiss(row, col);
    }

    public void printStatus()
    {
        guesses.printStatus();
    }

    public boolean alreadyGuessed(int row, int col)
    {
        return guesses.alreadyGuessed(row, col);
    }

    public int percentOfShipsSunk()
    {
        int hits = 0;
        for(int col = 1; col <= Grid.NUM_COLS; col++)
        {
            for(int row = 1; row <= Grid.NUM_ROWS; row++)
            {
                if(guesses.getStatus(row, col) == Location.HIT)
                {
                    hits++;
                }
            }
        }
        int l = 0;
        for(int length: SHIP_LENGTHS)
        {
            l += length;
        }
        return hits * 100 / l;
    }

    public String askForGuess(String question)
    {
        String guess;
        do
        {
            guess = readLine(question);
        }
        while (guess.length() < 2 || guess.length() > 3 || !Character.isLetter(guess.charAt(0)) || !Character.isDigit(guess.charAt(1)) || (guess.length() > 2 && !Character.isDigit(guess.charAt(2))));
        return guess;
    }
}
