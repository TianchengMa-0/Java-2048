File header: This file talks about game 2048 and code on it. 

Author: Tiancheng Ma
Login: cs8bwi20dp
Date: 2020 February 1st

1.In 2048 game,  users use "wasd" to slide numbered tiles, tiles will be combined if they have same value and there will be a new tile added on empty position until the whole board is full. The goal is to have a tile numbered 2048.
2.These magic number help us understand what path to code should we go, avoid using magic number in our own code. We can test our code by changing these numbers and introduce magic number just one time to save time. Also, it defines variable for other files and we can check all magic number in a single file instead of going through every file.
3.We may want to make changes to deep copy to get some result but don't want to change the original board.