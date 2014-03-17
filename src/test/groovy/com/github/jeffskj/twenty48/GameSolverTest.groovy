package com.github.jeffskj.twenty48;

import static org.junit.Assert.*

import org.junit.Test

class GameSolverTest {

    @Test
    public void testSolveTo() {
        def successes = 0
        def tries = 100
        tries.times {
            def game = new Game()
            def solver = new GameSolver(game)
            solver.solveTo(1024)
            if (!game.over) {
                successes++
            }
//            println game.board
            println "${game.over ? 'FAILED' : 'VICTORY!'}"
//            assertFalse(game.over)
        }
        println "SUCCESS RATE: ${successes}/${tries}"        
    }

}
