package com.ccc.fizz.thread.cyclicba;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HorseRace {
    static final int FINISH_LINE = 75;

    private List<Horse> horses = new ArrayList<>();

    private ExecutorService exec = Executors.newCachedThreadPool();

    private CyclicBarrier barriers;

    public HorseRace(int nHorses, final int pause) {
        barriers = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                for (int i = 0 ; i < FINISH_LINE ; i++) {
                    sb.append("=");
                }
                System.out.println(sb);

                for (Horse horse : horses) {
                    System.out.println(horse.tracks());
                }

                for (Horse horse : horses) {
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + "won");
                        exec.shutdownNow();
                        return;
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println("sleep inter");
                }
            }
        });

        for (int i = 0 ; i < nHorses ; i++) {
            Horse horse = new Horse(barriers);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;

        new HorseRace(nHorses, pause);
    }
}
