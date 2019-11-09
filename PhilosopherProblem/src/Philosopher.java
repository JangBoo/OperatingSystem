
public class Philosopher extends BaseThread
{
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - Then sleep() for a random interval.
     * - The print that they are done eating.
     */
    public void eat()
    {
        try
        {
            // ...
            System.out.println("Philosopher " + iTID + " started to eat");
            sleep((long)(Math.random() * TIME_TO_WASTE));
            System.out.println("Philosopher " + iTID + " finished eating");
            // ...
        }
        catch(InterruptedException e)
        {
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - Then sleep() for a random interval.
     * - The print that they are done thinking.
     */
    public void think()
    {
        try
        {
            System.out.println("Philosopher " + iTID + " started thinking");
            sleep((long)(Math.random() * TIME_TO_WASTE));
            System.out.println("Philosopher " + iTID + " finished thinking");
        }
        catch(InterruptedException e)
        {
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }

    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - Say something brilliant at random
     * - The print that they are done talking.
     */
    public void talk()
    {
        // ...


        System.out.println("Philosopher " + iTID + " started talking");
        saySomething();
        System.out.println("Philosopher " + iTID + " finished talking");


        // ...
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run()
    {
        for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
        {try {
            DiningPhilosophers.soMonitor.pickUp(getTID());

            eat();

            DiningPhilosophers.soMonitor.putDown(getTID());

            think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
            if(Math.random() < 0.5) // A random decison
            {
                // Some monitor ops down here...
                DiningPhilosophers.soMonitor.requestTalk(getTID());
                talk();
                DiningPhilosophers.soMonitor.endTalk(getTID());
                // ...
            }
        } catch (InterruptedException e) {
            System.err.println("Philosopher.run():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }

        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething()
    {
        String[] astrPhrases =
                {
                        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                        "You know, true is false and false is true if you think of it",
                        "2 + 2 = 5 for extremely large values of 2...",
                        "If thee cannot speak, thee must be silent",
                        "My number is " + getTID() + ""
                };

        System.out.println
                (
                        "Philosopher " + getTID() + " says: " +
                                astrPhrases[(int)(Math.random() * astrPhrases.length)]
                );
    }
}

// EOF
