class BaseThread extends Thread
{
    /*
     * Data members
     */
    public static int iNextTID = 1; // Preserves value across all instances
    protected int iTID;
    public BaseThread()
    {
        this.iTID = iNextTID;
        iNextTID++;
    }
    public BaseThread(int piTID)
    {
        this.iTID = piTID;
    }
}
