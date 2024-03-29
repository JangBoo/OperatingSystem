// Source code for semaphore class:
class Semaphore
{
    private int value;



    public Semaphore(int value)
    {

        //semaphore can not be initialized to a negative value
        if (value >= 0){
            this.value = value;
        }else {

            throw new IllegalArgumentException("Semaphore can not be initialized to a negative value");
        }

    }
    public Semaphore()
    {
        this(0);
    }
    public synchronized void Wait()
    {
        //

        this.value--;

        if (this.value < 0)
        {
            try
            {

                wait();


            } catch(InterruptedException e)
            {
                System.out.println ("Semaphore::Wait() - caught InterruptedException: " + e.getMessage() );
                e.printStackTrace();
            }
        }

    }

    //
    public synchronized void Signal()
    {
        ++this.value;
        notify();

    }
    public synchronized void P()
    {
        this.Wait();
    }
    public synchronized void V()
    {
        this.Signal();
    }
}