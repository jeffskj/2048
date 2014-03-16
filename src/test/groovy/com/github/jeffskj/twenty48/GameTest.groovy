package com.github.jeffskj.twenty48

import static org.junit.Assert.*

import org.junit.Test

class GameTest {
    Game g = new Game()

    @Test
    void initializesBoard() {
        assertEquals(2, g.board.board.size())
    }
    
    @Test
    void canDetectVictory() {
        g.board.maxValue = 2048
        g.move(Direction.DOWN)
        assertTrue(g.won)
    }
    
    @Test
    void canDetectFailure() {
        g.board.metaClass.isMoveAvailable { false }
        g.move(Direction.DOWN)
        assertTrue(g.over)
    }
    
    @Test
    void addsTileAfterSuccessfulMove() {
        def called = false
        g.metaClass.addRandomTile = { called = true }
        g.move(Direction.DOWN)
        assertTrue(called)        
    }
    
    @Test
    void addsScores() {
        g.board.metaClass.move = { Direction d -> new MoveResult(scores: 100, moved: true) }
        g.move(Direction.DOWN)
        assertEquals(100, g.score)
    }
}
