package fr.flastar.magiqolsky.screens.data;

public class TimerData {
    private boolean isStarted = false;
    private boolean isPaused = false;
    private long lastStartTime;
    private long accumulatedTime = 0;

    public void startTimer() {
        if (!isStarted) {
            isStarted = true;
            isPaused = false;
            accumulatedTime = 0;
            lastStartTime = System.currentTimeMillis();
        }
    }

    public void stopTimer() {
        isStarted = false;
        isPaused = false;
        accumulatedTime = 0;
    }

    public void togglePauseTimer() {
        if (!isStarted) return;

        if (!isPaused) {
            accumulatedTime += System.currentTimeMillis() - lastStartTime;
        } else {
            lastStartTime = System.currentTimeMillis();
        }
        isPaused = !isPaused;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public long getTotalElapsedTime() {
        if (!isStarted) {
            return 0;
        }

        if (isPaused) {
            return accumulatedTime;
        }

        return accumulatedTime + (System.currentTimeMillis() - lastStartTime);
    }
}
