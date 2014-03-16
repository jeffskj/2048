package com.github.jeffskj.twenty48

import java.awt.Point

class Board {
    private int size
    
    int maxValue = 0
    Map<Point, Number> board = [:]
    Set<Point> allTiles = [] as Set
    
    Board(int size=4) {
        this.size = size
        
        for (x in 0..size-1) {
            for (y in 0..size-1) {
                allTiles << new Point(x, y)
            }
        }
    }
    
    void setAt(Point p, int value) {
        if (onBoard(p)) {
            board[p] = value
        }
    }
    
    MoveResult move(Map<Point, Number> board=this.board, Direction dir) {
        def result = new MoveResult()
        
        for (y in dir.yTraverse) { 
            for (x in dir.xTraverse) { 
                def p = new Point(x, y)
                if (board[p]) {
                    def dest = dir.move(p)
                    while (onBoard(dest)) {
                        if (board[dest] && board[dest] == board[p]) {
                            def v = board[p] * 2
                            board[dest] = v
                            board.remove(p)
                            if (v > maxValue) {
                                maxValue = v
                            }
                            result.scores += v
                            result.moved = true
                            break
                        }
                        if (board[dest]) { break }
                        board[dest] = board[p]
                        board.remove(p)
                        p = dest              
                        dest = dir.move(dest)       
                        result.moved = true
                    }
                }
            }
        }
        return result
    }
    
    boolean isMoveAvailable() {
        if (!full) return true 
        
        // try to find first move that reduces the board size
        return Direction.values().any { move(board.clone(), it).moved }
    }
    
    boolean isFull() {
        return board.size() == allTiles.size()
    }
    
    Set<Point> getAvailableTiles() {
        return allTiles - board.keySet()        
    }
    
    void set(Point p, Integer value) {
        board[p] = value    
    }
    
    void set(int x, int y, Integer value) {
        set(new Point(x, y), value)
    }
    
    Integer get(int x, int y) {
        return board[new Point(x, y)]   
    }
    
    boolean onBoard(Point p) {  allTiles.contains(p) }
    
    String toString() {
        def str = ' |' + (0..size-1).collect { it.toString().center(5) }.join('|')
        str += '\n' + (0..size-1).collect { y ->
           "$y|" + (0..size-1).collect { x ->  (board[new Point(x, y)] ?: '').toString().center(5) }.join('|')
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

class MoveResult {
    boolean moved = false
    int scores = 0
}