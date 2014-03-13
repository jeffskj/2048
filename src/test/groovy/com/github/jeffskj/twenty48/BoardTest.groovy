package com.github.jeffskj.twenty48

import static org.junit.Assert.*

import java.awt.Point

import org.junit.Test

class BoardTest {
    Board b = new Board()
    
    @Test
    void canDetectBoardEdge() {
        assertFalse(b.onBoard(new Point(5, 0)))
        assertFalse(b.onBoard(new Point(4, 0)))
        assertFalse(b.onBoard(new Point(2, 4)))
        assertFalse(b.onBoard(new Point(1, 5)))
        
        assertFalse(b.onBoard(new Point(-1, 2)))
        assertFalse(b.onBoard(new Point(1, -2)))
        
        assertTrue(b.onBoard(new Point(3, 0)))
        assertTrue(b.onBoard(new Point(1, 3)))
        
    }
    
    @Test
    void canMoveToEdgeofBoard() {
        b.board[new Point(0, 0)] = 2
        b.move(Direction.RIGHT)
        println b.board
        assertEquals(1, b.board.size())
        assertEquals(2, b.board[new Point(3, 0)])
    }
    
    @Test
    void canMergeIfSameValue() {
        b.board[new Point(0, 0)] = 2
        b.board[new Point(2, 0)] = 2
        b.move(Direction.LEFT)
        println b
        
        assertEquals(1, b.board.size())
        assertEquals(4, b.board[new Point(0, 0)])
        
        b.board[new Point(0, 1)] = 4
        b.move(Direction.DOWN)
        
        println ''
        println b
        
        assertEquals(1, b.board.size())
        assertEquals(8, b.board[new Point(0, 3)])
    }
    
    @Test
    void canStopIfNotSameValue() {
        b.board[new Point(0, 0)] = 2
        b.board[new Point(2, 0)] = 4
        println b
        b.move(Direction.RIGHT)
        println ''
        println b
        assertEquals(2, b.board.size())
        assertEquals(2, b.board[new Point(2, 0)])
        assertEquals(4, b.board[new Point(3, 0)])
    }
}
