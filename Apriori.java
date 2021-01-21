import java.util.*;
import java.io.*;
public class Apriori {
	// self joining 
	// except last item in item set find all items matching with remaining items 
	static public List<String> selfJoin(List<String> lks)
	{
		List<String> ck=new ArrayList<String>();
		if(lks.size()==1)
			return lks;
		//Removing commas in lk so as to find subsets effectively
		for(int i=0;i<lks.size();i++)
		{
			for(int j=i+1;j<lks.size();j++)
			{
				if(lks.get(i).substring(0, lks.get(i).length()-1).equals(lks.get(j).substring(0, lks.get(j).length()-1)))
				{
					ck.add(lks.get(i)+lks.get(j).charAt(lks.get(j).length()-1));
				}
			}
		}
		return ck;
	}
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter no. of transactions");
		int n=Integer.parseInt(sc.nextLine());
		String trans[][]=new String[n][];
		List<String> inp=new ArrayList<String>();
		String input[]=new String[n];
		System.out.println("Note: Enter only number in items of transactions,no string or characters are allowed.Enter only number as items ex:1,2,3 \n\n");
		for(int i=0;i<n;i++)
		{
			System.out.println("Enter transaction "+(i+1)+" items(Separated with comma):");
			input[i]=sc.nextLine();
			inp.add(input[i]);
			String []parts=input[i].split(",");
			input[i]=" ";
			trans[i]=parts;
		}	
		List<String> tmp1=new ArrayList<String>();
		String lkt="";
			for(int i=0;i<inp.size();i++)
			{
				lkt=inp.get(i);
				StringTokenizer stk=new StringTokenizer(lkt,",");
				String tt="",kk="";
				while(stk.hasMoreElements())
				{
					tt=stk.nextToken();
					kk=kk.concat(tt);
				}
				tmp1.add(kk);
			}
			inp=tmp1;
		
		System.out.println("The Items entered are:");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			System.out.print(trans[i][j]+" ");
			System.out.println();
		}
	
		System.out.println("Enter support count value:");
		int supc=0;
		supc=Integer.parseInt(sc.nextLine());
		//HashMap to store the items and its count
		while(true)
		{
		System.out.println("Options:");
		System.out.println("1.Normal Method");
		System.out.println("2.Hash-Based Implementation");
//		System.out.println("3.Dynamic item set counting");
//		System.out.println("4.Partitioning");
		System.out.println("Enter your choice:");
		int op;
		op=sc.nextInt();
		if(op==1)
		{
		//	Date d1 = new Date();
		HashMap<String,Integer> tb=new HashMap<>();	
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			{
				if (tb.containsKey(trans[i][j]))
				{
					int v=tb.get(trans[i][j]);
					v++;
					tb.put(trans[i][j], v);
				}
				else
					tb.put(trans[i][j],1);
			}
		}
		
		// If items are having count less than supc [Minimum support] then removing them
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			{
				try
				{
					int v=tb.get(trans[i][j]);
				if(v<supc){
					tb.remove(trans[i][j]);
				}
				}
				catch(Exception e){
					
				}
			}
		}
	
		List<String> lk=new ArrayList<String>();
		System.out.println("Single Frequent Item sets:");
		System.out.println(tb);
		
		List<Map<String, Integer>> L = new ArrayList<Map<String, Integer>>(); 
		// adding single frequent item sets for lk
		for (String key: tb.keySet()) {
		    lk.add(key);
		}
		
		List<String> ck=new ArrayList<String>();
		List<String> print=new ArrayList<String>();
		  System.out.println("Frequent Items are:");
		while(!lk.isEmpty())
		{
			ck=selfJoin(lk);
			// removing the duplicates in ck
			// pruning items in ck 
			ck=prune(ck,lk);
			// here i am getting duplicates in ck so removing them
			lk=find(ck,supc,inp,n);
			print=lk;
			for(int k = 0; k < print.size(); k++)
			{
	            System.out.println(print.get(k));
			}	
		}
			System.out.print("Exited Successfully!!");
		}
		else if(op==2)
		{
//			Date d1 = new Date();
			HashMap<String,Integer> tb=new HashMap<>();	
			for(int i=0;i<inp.size();i++)
			{
				String k=inp.get(i);
				for(int j=0;j<k.length();j++)
				{
					if (tb.containsKey(k.charAt(j)+""))
					{
						int v=tb.get(k.charAt(j)+"");
						v++;
						tb.put(k.charAt(j)+"", v);
					}
					else
						tb.put(k.charAt(j)+"",1);
				
					for(int h=j+1;h<k.length();h++)
					{
						String sub=k.charAt(j)+""+k.charAt(h);
					if (tb.containsKey(sub))
					{
						int v=tb.get(sub);
						v++;
						tb.put(sub, v);
					}
					else
						tb.put(sub,1);
					}
				}		
			}
			HashMap<String,Integer> tb1=new HashMap<>();
			List<String> lk=new ArrayList<String>();
			for(String str:tb.keySet())
			{
					int v=tb.get(str);
				if(v>=supc){
					tb1.put(str, v);
					if(str.length()==2)
					{
						lk.add(str);
					}
				}
				
			}
			tb=tb1;
			System.out.println(tb);
			List<String> ck=new ArrayList<String>();
			List<String> print=new ArrayList<String>();
			  System.out.println("Frequent Items are:");
			while(!lk.isEmpty())
			{
				ck=selfJoin(lk);
				// removing the duplicates in ck
				// pruning items in ck 
				ck=prune(ck,lk);
				// here i am getting duplicates in ck so removing them
				lk=find(ck,supc,inp,n);
				print=lk;
				for(int k = 0; k < print.size(); k++)
				{
		            System.out.println(print.get(k));
				}	
			}

				System.out.print("Exited Successfully!!");
		}
		}
	}
// Pruning the items 
	//1 find sub sets of items
	//2 then find whether subsets are present in lk or not
	private static List<String> prune(List<String> cks,List<String> lks) {
		List<String> tempy1=new ArrayList();
		List<String> temp1=new ArrayList();
		List<String> temp2=new ArrayList();
		//Removing commas in lk so as to find subsets effectively
		int x=0;
		for(int i=0;i<cks.size();i++)
		{
			String temp=cks.get(i);
			for(int j=0;j<temp.length();j++)
			{
				x=0;
				if(lks.contains((temp.substring(0, j))+(temp.substring(j+1, temp.length()))))
				{
					x=1;
				}
				else
				{
					x=0;
					break;
				}
			}
			if(x==1)
			{
				tempy1.add(temp);
			}
			
		}
		return tempy1;
	}
	
	// here finding the count of items in ck and checking whether it is greater than min supc or not
	static List<String> find(List<String> cks, int supc, List<String> inp,int n)
	{
		// Finding 
		HashMap<String,Integer> tb=new HashMap<>();
		List<String> tmp=new ArrayList<String>();
		int cnt=0;
		String t;
		for(int i=0;i<cks.size();i++)
		{
			cnt=0;
			t=cks.get(i);
			String sarr[]=new String[1000];
			int in=0;
			for(int j=0;j<t.length();j++)
			{
				sarr[in]=t.charAt(j)+"";
				in++;
			}
			int count=0;
			int h=in;
				List<String> l1=new ArrayList<String>();
				l1=inp;
				int x=0;
				for(int k=0;k<l1.size();k++)
				{
				for(int j=0;j<in;j++)
				{
					if((l1.get(k)).contains(sarr[j]))
						x=1;
					else 
					{ 
					x=0; 
					break; 
					}
				}
				if(x==1)
					count++;
				}
			if(count>=supc)
			{
				tmp.add(t);
			}	
		}
	return tmp;
	}
}