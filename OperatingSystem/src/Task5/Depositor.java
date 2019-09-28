package Task5;
/**
 * Class Depositor
 * Implements Depositor thread class. 
 *
 * @author Malek Barhoush, mbarhoush@hotmail.com;  
 * 
 *
 * $Revision: 1.0 $ 
 * $Last Revision Date: 2012/09/03  
 */

public class Depositor extends Thread {
	private Account account ;
	public Depositor(Account account){
		this.account = account;
	} 
	
	public void run(){
		for (int i=0;i<10000000;i++)
		{
			account.debosit(10);
		/*			
 		try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
	}

}
