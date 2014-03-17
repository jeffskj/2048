package com.github.jeffskj.twenty48;

import static org.junit.Assert.*

import org.junit.Test

class GameSolverTest {

    @Test
    public void testSolveTo() {
        def game = new Game()
        def solver = new GameSolver(game)
        solver.solveTo(256)
        println game.board
        println "WON: $game.over"
    }

}
