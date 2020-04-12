package com.github.timshnayder;

public class BattleShip {

    public static void main(String[] args) {
        int turns = 0;

        Player me = new Player();
        Player cpu = new Player();

        cpu.placeShips();
        me.askShips();
        //me.placeShips();

        while(true)
        {
            turns++;
            me.printStatus();

            int row;
            int col;
            System.out.println("Turn #" + turns + ". CPU has sunk " + cpu.percentOfShipsSunk() + "%. You have sunk " + me.percentOfShipsSunk() + "%.");
            do
            {
                String guess = me.askForGuess("Guess where opponent's ship is: ");
                row = guess.charAt(0)-'A'+1;
                if (row < 1 || row > 10)
                {
                    row = guess.charAt(0)-'a'+1;
                }
                col = Integer.parseInt(guess.substring(1));
            }
            while (row < 1 || row > 10 || col < 1 || col > 10 || me.alreadyGuessed(row, col));

            if(cpu.hasShip(row, col))
            {
                System.out.println("Hit!");
                me.markHit(row,col);
                if(me.percentOfShipsSunk() == 100)
                {
                    System.out.println("Congratulations! You have sunk all CPU ships!");
                    break;
                }
            }
            else
            {
                System.out.println("Miss");
                me.markMiss(row,col);
            }

            do{
                row = Randomizer.nextInt(1, 10);
                col = Randomizer.nextInt(1, 10);
            }while(cpu.alreadyGuessed(row, col));

            if(me.hasShip(row, col))
            {
                System.out.println("CPU Hit at " + (char)('A' + row - 1) + col + "!");
                cpu.markHit(row,col);
                if(cpu.percentOfShipsSunk() == 100)
                {
                    System.out.println("Oh no! The cpu has sunk all of your ships! You lose.");
                    break;
                }
            }
            else
            {
                System.out.println("CPU missed at " + (char)('A' + row - 1) + col);
                cpu.markMiss(row,col);
            }
        }
    }
}
