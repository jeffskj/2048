package com.github.jeffskj.twenty48

import java.awt.Point

class Board {
    private static final int SIZE = 4
    
    Map<Point, Number> board = [:]
    
    void setAt(Point p, int value) {
        if (onBoard(p)) {
            board[p] = value
        }
    }
    
    void move(Direction dir) {
        for (y in dir.yTraverse) { 
            for (x in dir.xTraverse) { 
                def p = new Point(x, y)
                if (board[p]) {
                    def dest = dir.move(p)
                    while (onBoard(dest)) {
                        if (board[dest] && board[dest] == board[p]) {
                            board[dest] = board[p] * 2
                            board.remove(p)
                            break
                        }
                        if (board[dest]) { break }
                        board[dest] = board[p]
                        board.remove(p)
                        p = dest              
                        dest = dir.move(dest)       
                    }
                }
            }
        }
    }
    
    void set(int x, int y, Integer value) {
        board[new Point(x, y)] = value
    }
    
    Integer get(int x, int y) {
        return board[new Point(x, y)]   
    }
    
    boolean onBoard(Point p) {  p.x < SIZE && p.y < SIZE && p.x >= 0 && p.y >= 0 }
    
    String toString() {
        def str = ' |' + (0..SIZE-1).collect { it.toString().center(5) }.join('|')
        str += '\n' + (0..SIZE-1).collect { y ->
           "$y|" + (0..SIZE-1).collect { x ->  (board[new Point(x, y)] ?: '').toString().center(5) }.join('|')
        }.join('\n')
        return str
    }
}

enum Direction { 
    UP(0, -1, 0..3, 3..0), 
    RIGHT(1, 0, 3..0, 0..3), 
    DOWN(0, 1, 0..3, 3..0), 
    LEFT(-1, 0, 0..3, 0..3)
    
    int x, y;
    Range xTraverse, yTraverse
    
    Direction(x, y, xTraverse, yTraverse) { this.x = x; this.y = y; this.xTraverse = xTraverse; this.yTraverse = yTraverse }
    
    Point move(Point start) {
        return new Point(start.x + x as int, start.y + y as int)
    } 
}
