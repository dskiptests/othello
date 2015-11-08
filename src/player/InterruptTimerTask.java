package player;

import java.util.TimerTask;

/**
 * Created by david on 2015-11-08.
 */
public class InterruptTimerTask extends TimerTask {

    private Thread theTread;

    public InterruptTimerTask(Thread theTread) {
        this.theTread = theTread;
    }

    @Override
    public void run() {
        theTread.interrupt();
    }

}