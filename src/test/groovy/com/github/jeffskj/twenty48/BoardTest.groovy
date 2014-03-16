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
        b.set(0,0, 2)
        b.move(Direction.RIGHT)
        println b.board
        assertEquals(1, b.board.size())
        assertEquals(2, b.get(3, 0))
    }
    
    @Test
    void canMergeIfSameValue() {
        b.set(0,0, 2)
        b.set(2,0, 2)
        b.move(Direction.LEFT)
        println b
        
        assertEquals(1, b.board.size())
        assertEquals(4, b.get(0, 0))
        assertEquals(4, b.maxValue)
    }
    
    @Test
    void mergesAfterMovingBothPieces() {
        b.set(0,0, 4)
        b.set(0,1, 4)
        def result = b.move(Direction.DOWN)
        
        println b
        
        assertEquals(8, result.scores)
        
        assertEquals(1, b.board.size())
        assertEquals(8, b.get(0,3))
    }
    
    @Test
    void canStopIfNotSameValue() {
        b.board[new Point(0, 0)] = 2
        b.board[new Point(2, 0)] = 4
        println b
        assertTrue(b.move(Direction.RIGHT).moved)
        
        println ''
        println b
        assertEquals(2, b.board.size())
        assertEquals(2, b.board[new Point(2, 0)])
        assertEquals(4, b.board[new Point(3, 0)])
    }
    
    @Test
    void canFindAvailableTiles() {
        b.set(0,0, 2)
        b.set(0,1, 2)
        println b.availableTiles
        assertEquals(14, b.availableTiles.size())
        assertFalse(b.availableTiles.contains(new Point(0,0)))
        assertFalse(b.availableTiles.contains(new Point(0,1)))
        println b.availableTiles[3]
    }
    
    @Test
    void canDetectAvailableMoves() {
        assertFalse(b.full)
        b.allTiles.each { b.set(it, 2) }
        assertTrue(b.full)
        assertTrue(b.moveAvailable)
        
        4.times{ b.set(it, it, 4) }
        b.set(0, 2, 4)
        b.set(1, 3, 4)
        b.set(2, 0, 4)
        
        assertTrue(b.moveAvailable)
        
        b.set(3, 1, 4)
        
        println b
        
        assertFalse(b.moveAvailable)
    }
}
