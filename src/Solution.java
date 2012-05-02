import java.util.*;
public class Solution {

	static int n; //Discs, Pegs
	static int k;
	static int hanoi[];
	static int result[];
	static Random rand;
	
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		rand = new Random();
		n = in.nextInt();//Discs
		k = in.nextInt();//Pegs
		hanoi = new int[n];
		result = new int[n];
		for(int i = 0; i < n; i ++)
			hanoi[i] = in.nextInt()-1;
		for(int i = 0; i < n; i ++)
			result[i] = in.nextInt()-1;
		printAll();
		try {
			for(int i = n - 1; i >= 0; i --)
				move(i, result[i]);
		}
		catch (Exception e) {
			System.out.println("\nErrore: operazione non valida " + e);
			print();
			e.printStackTrace();
		}		
	}
	
	public static void move(int disc, int to) throws Exception{
		if(hanoi[disc] == to)
			return;
		
		int top = onTop(hanoi[disc]);
		int topTo = onTop(to);
		int up = upTo(disc);
		int upTo = upTo(disc, to);
		
		if(top != disc)
			move(up, moveWhere(up, to));
		if(topTo < disc)
			move(upTo, moveWhere(up, to));
		try{
			operation(disc, to);
		}
		catch (StackOverflowError e){
			System.out.println("Error: too many iterations. Maybe the problem is not solvable.");
		}
		catch (Exception e){
			move(disc, to);
		}
	}

	private static void operation(int disc, int to) throws Exception {
		if(operationPermitted(disc, to))
			hanoi[disc] = to;
		System.out.println("\nDisc " + disc + " to: " + (to+1));
		print();
	}
	
	private static int moveWhere(int disc, int notHere) throws Exception{
		int offset = result[disc];
		Vector<Integer> optimal = new Vector<Integer>();
		Vector<Integer> soso = new Vector<Integer>();
		for(int i = 0; i < k; i ++)
			if((offset+i)%k != notHere && (offset+i)%k != hanoi[disc]){
				if(upTo(disc, (offset+i)%k) < disc)
					soso.add((offset+i)%k);
				else
					optimal.add((offset+i)%k);
			}
		if(!optimal.isEmpty())
			return optimal.get(rand.nextInt(optimal.size()));
		if(!soso.isEmpty())
			return soso.get(rand.nextInt(soso.size()));
		throw new Exception("Errore: nessun posto dove spostare " + disc);
	}
	
	private static boolean operationPermitted(int disc, int to) throws Exception{
		for(int i = 0; i < disc; i++)
			if(onTop(hanoi[disc]) != disc)
				throw new Exception("Not on top: " + disc);
		for(int i = 0; i < n; i++)
			if(onTop(to) < disc)
				throw new Exception("Too big disc: " + disc + " on " + to);
		return true;						
	}

	public static int onTop(int peg) throws Exception{
		for(int i = 0; i < n; i++)
			if(hanoi[i] == peg)
				return i;
		return n;
	}
	
	private static int upTo(int disc, int to) {
		for(int i = disc - 1; i >= 0; i--)
			if(hanoi[i] == to)
				return i;
		return disc;
	}
	
	public static int upTo(int disc){
		for(int i = disc - 1; i >= 0; i--)
			if(hanoi[i] == hanoi[disc])
				return i;
		return disc;
	}
	
	public static void print(){
		System.out.println();
		for(int j = 0; j < k; j++){
			System.out.print("\nPeg " + (j+1) + ":--- ");
			for(int i = n-1; i >= 0; i--)
				if(hanoi[i] == j)
					System.out.print(i + " ");
		}
	}
	
	public static void printAll(){
		System.out.println("\nPegs: " + k + " Discs: " + n + "\nTo:\n");
		for(int j = 0; j < k; j++){
			System.out.print("\nPeg " + (j+1) + ":--- ");
			for(int i = n-1; i >= 0; i--)
				if(result[i] == j)
					System.out.print(i + " ");
		}
		System.out.println("\nNow:");
		print();
	}
	
	public static int casuale(){
		return rand.nextInt()%2 == 1 ? -1 : 1;
	}
}
