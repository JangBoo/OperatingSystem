import java.util.ArrayList;

/**
 * Created by jangh on 2019-11-08.
 */

public class Monitor
{
    /*
     * ------------
     * Data members
     * ------------
     */
    private static int [] chopsticks;
    private static boolean can_talk;
    private ArrayList<Integer> waitlist = new ArrayList<>();

    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers)
    {
        // TODO: set appropriate number of chopsticks based on the # of philosophers
        //n(chopstic)=n(philosopher)
        chopsticks = new int [piNumberOfPhilosophers];
        for (int i=0; i<chopsticks.length; i++){
            chopsticks[i] = 1;
        }
        can_talk = true;
        for(int i =0; i < chopsticks.length;i++) {
            waitlist.add(i, i+1);
        }

    }

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID) throws InterruptedException
    {
        // ...
        int idx = piTID -1 ;
        while(true){
            if (waitlist.contains(piTID)) {

                //check the availability
                if ( chopsticks.length >1 && chopsticks[idx] == 1 && chopsticks[(idx+1) % chopsticks.length] == 1 ) {


                    chopsticks[idx] = 0;
                    chopsticks[(idx+1) % chopsticks.length] =0;
                    System.out.println("Philosopher "+ piTID + " took this chopsticks,  " + idx+", " + (idx+1) % chopsticks.length);

                    //change eating turn to  next philosopher
                    for(int i = 0;i < waitlist.size();i++) {
                        if (waitlist.get(i) == piTID) {
                            waitlist.remove(i);
                        }
                    }
                    break;

                } else {
                    //System.out.println("Philosopher " + piTID + " is waiting for the chopsticks " + idx +", " + (idx+1) % chopsticks.length);
                    wait();
                }


                //if there is nobody in the list , the waitlist will be reset
            } else if (waitlist.isEmpty()) {

                for(int i =0; i < chopsticks.length;i++) {
                    waitlist.add(i, i+1);
                }
                continue;

                //you have been eaten ,but somebody still waiting to eat,so you have to wait()
            } else {
                wait();
            }
        }
    }

    /**
     * When a given philosopher's done eating, they put the chopstiks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID)
    {
        int idx = piTID - 1;
        if ( chopsticks [idx] == 0 && chopsticks [(idx+1) % chopsticks.length] == 0) {

            //drop chopsticks
            chopsticks[idx] = 1;
            chopsticks[(idx + 1) % chopsticks.length] = 1;

            notifyAll();
        }
        // } else {
        //   System.err.println("Chopsticks are already put down " + idx + ", " + (idx+1)% chopsticks.length);
        // }
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk(final int piTID)throws InterruptedException
    {
        while(true) {

            //check nobody is talking and this philosopher is not eating (you can not talking and eating at same time)
            if (can_talk ) {

                //everyone else can not talk now
                can_talk = false;

                break;

            } else {
                wait();
            }
        }
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk(final int piTID)
    {
        // ...
        can_talk = true;
        notifyAll();
    }
}
