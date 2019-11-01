// Source code for semaphore class:

class Semaphore
{
    private int value;
    public Semaphore(int value)
    {
        if(value>=0)
        this.value = value;

    }
    public Semaphore()
    {this.value = 0;
    }
    public synchronized void Wait()
    {
        while (this.value <= 0)
        {
            this.value--;
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.out.println ("Semaphore::Wait() - caught InterruptedException: " + e.getMessage() );
                e.printStackTrace();
            }
        }

    }
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
