// Source code for stack manager:


import CharStackExceptions.CharStackEmptyException;
import CharStackExceptions.CharStackFullException;
import CharStackExceptions.CharStackInvalidAceessException;

public class StackManager
{
    // The Stack
    private static CharStack stack = new CharStack();
    private static final int NUM_ACQREL = 4; // Number of Producer/Consumer threads
    private static final int NUM_PROBERS = 1; // Number of threads dumping stack
    public static int iThreadSteps = 3; // Number of steps they take
    // Semaphore declarations. Insert your code in the following:
    //...
    //**********************
    public static Semaphore mutex = new Semaphore(1);

    public static Semaphore consumer = new Semaphore(0);

    // The main()
    public static void main(String[] argv)
    {
        // Some initial stats...
        try
        {
            System.out.println("Main thread starts executing.");
            System.out.println("Initial value of top = " + stack.getTop() + ".");
            System.out.println("Initial value of stack top = " + stack.pick() + ".");
            System.out.println("Main thread will now fork several threads.");
        }
        catch(CharStackEmptyException e)
        {
            System.out.println("Caught exception: StackCharEmptyException");
            System.out.println("Message : " + e.getMessage());
            System.out.println("Stack Trace : ");
            e.printStackTrace();
        }
                    /*
                   * The birth of threads
                    */
        Consumer ab1 = new Consumer();
        Consumer ab2 = new Consumer();
        System.out.println ("Two Consumer threads have been created.");
        Producer rb1 = new Producer();
        Producer rb2 = new Producer();
        System.out.println ("Two Producer threads have been created.");
        CharStackProber csp = new CharStackProber();
        System.out.println ("One CharStackProber thread has been created.");
                  /*
                 * start executing
                  */
        ab1.start();
        rb1.start();
        ab2.start();
        rb2.start();
        csp.start();
                 /*
                  * Wait by here for all forked threads to die
                 */
        try
        {
            ab1.join();
            ab2.join();
            rb1.join();
            rb2.join();
            csp.join();
            // Some final stats after all the child threads terminated...
            System.out.println("System terminates normally.");
            System.out.println("Final value of top = " + stack.getTop() + ".");
            System.out.println("Final value of stack top = " + stack.pick() + ".");
            System.out.println("Final value of stack top-1 = " + stack.getAt(stack.getTop() - 1) + ".");
            //System.out.println("Stack access count = " + stack.getAccessCounter());
        }
        catch(InterruptedException e)
        {
            System.out.println("Caught InterruptedException: " + e.getMessage());
            System.exit(1);
        }
        catch(Exception e)
        {
            System.out.println("Caught exception: " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
            System.out.println("Stack Trace : ");
            e.printStackTrace();
        }
    } // main()
    /*
    * Inner Consumer thread class
    */
    static class Consumer extends BaseThread
    {
        private char copy; // A copy of a block returned by pop()
        public void run()
        {

            System.out.println ("Consumer thread [TID=" + this.iTID + "] starts executing.");
            for (int i = 0; i < StackManager.iThreadSteps; i++)
            {//System.out.println("start for loop, consumer  i:  " +i + ", thread : "+ this.iTID +"going into waiting"  );
                // Insert your code in the following:
                // ...

                try {
                    consumer.Wait();
                    mutex.Wait();
                    this.copy = stack.pop();//pop top

                }catch (CharStackEmptyException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("Consumer thread [TID=" + this.iTID + "] pops character =" + this.copy);
                    mutex.Signal();
                    consumer.Signal();
                }
            }
            System.out.println ("Consumer thread [TID=" + this.iTID + "] terminates.");
        }
    } // class Consumer
    /*
   * Inner class Producer
    */
    static class Producer extends BaseThread
    {
        private char block; // block to be,. returned
        public void run()
        {
            System.out.println ("Producer thread [TID=" + this.iTID + "] starts executing.");
            for (int i = 0; i < StackManager.iThreadSteps; i++)
            {//System.out.println("start for loop, producer  i  :" +i + " thread  "+ this.iTID+"going into waiting"  );
                // Insert your code in the following:

                try {
                    mutex.Wait();
                    char top = stack.pick();//checkon the top
                    this.block = (char)(top + 1);//get  character to push
                    stack.push(this.block);//

                } catch (CharStackEmptyException ee) {
                    this.block = 'a';//if empty, assign a character to push
                    try {
                        stack.push(this.block);//try to push 'a' into stack
                    } catch (CharStackFullException e) {
                        e.printStackTrace();
                    }
                } catch (CharStackFullException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Producer thread [TID=" + this.iTID + "] pushes character =" + this.block);
                    mutex.Signal();
                    consumer.Signal();
                }
            }

            System.out.println("Producer thread [TID=" + this.iTID + "] terminates.");


        }
    } // class Producer
    /*
   * Inner class CharStackProber to dump stack contents
    */
    static class CharStackProber extends BaseThread
    {
        public void run()
        {
            System.out.println("CharStackProber thread [TID=" + this.iTID + "] starts executing.");
            for (int i = 0; i < 2 * StackManager.iThreadSteps; i++)
            { //System.out.println("start for loop, charstack i: " + i + ", thread: "+ this.iTID + " going into waiting");
                // Insert your code in the following. Note that the stack state must be
                // printed in the required format.
                // ...
                // ***********************
                try {
                    mutex.Wait();
                    String s = "";
                    s += "Stack S = (";
                    for (int x = 0; x < stack.getSize(); x++)
                    {
                        if (x == (stack.getSize() -1)) {
                            s += "[";
                            s += stack.getAt(x);
                            s += "]";
                            s += ")";
                        } else {
                            s += "[";
                            s += stack.getAt(x);
                            s += "]";
                            s += ",";
                        }
                    }
                    System.out.println(s);
                } catch (CharStackInvalidAceessException e) {
                    e.printStackTrace();
                } finally {
                    mutex.Signal();
                }
            }
        }

    } // class CharStackProber
} // class StackManager