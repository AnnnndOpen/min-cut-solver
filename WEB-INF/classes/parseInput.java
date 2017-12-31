import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class parseInput{
	String fileName;
	int vertice;
	ArrayList<edge> edgeList;
	double conValue[][];
	boolean f[];
	public parseInput(String fn,int v){
		fileName=fn;
		vertice=v;
		edgeList=new ArrayList<edge>();
		conValue=new double[v][v];
		f=new boolean[vertice];
	}
	public int parseData(int costT){//return value case -4:invalid vertice number -3:fail to read the data or data type is wrong  -2:csv format is wrong  -1:negative value 0:job finished
		String delimiter=",";
		BufferedReader fileReader=null;
		edge tmp;
		int v1,v2;
		double weight;
		try{
			String line="";
			fileReader=new BufferedReader(new FileReader(fileName));
			line=fileReader.readLine();
			String[] elements=line.split(delimiter);
			if (!((elements[0].equals("edgeID"))&&(elements[1].equals("vertice1"))&&(elements[2].equals("vertice2"))&&(elements[3].equals("weight"))))
				return (-2);
			while ((line=fileReader.readLine())!=null)
			{
				elements=line.split(delimiter);
				v1=Integer.parseInt(elements[1])-1;
				v2=Integer.parseInt(elements[2])-1;
				weight=Double.parseDouble(elements[3]);
				if ((v1<0)||(v1>=vertice)||(v2<0)||(v2>=vertice))
					return -4;
				if (costT==1)
					weight=1;
				else
					if (weight<=0)
						return -1;
				tmp=new edge(v1,v2,weight);
				edgeList.add(tmp);
			}
		}
		catch (Exception e){
			return (-3);
		}
		finally{
			try{
				fileReader.close();
			}catch(IOException e){
				return (-3);
			}
		}
		return 0;
	}
	public boolean testConnection()
	{
		for (int i=0;i<vertice;i++){
			for (int j=0;j<vertice;j++)
				conValue[i][j]=0;
		}
		for (int i=0;i<edgeList.size();i++){
			conValue[edgeList.get(i).v1][edgeList.get(i).v2]+=edgeList.get(i).weight;
			conValue[edgeList.get(i).v2][edgeList.get(i).v1]+=edgeList.get(i).weight;
		}
		for (int i=0;i<vertice;i++)
			f[i]=false;
		search(0);
		for (int i=0;i<vertice;i++)
			if (f[i]==false)
				return false;
		return true;
	}
	public void search(int nu){
		f[nu]=true;
		for (int i=0;i<vertice;i++)
			if(conValue[nu][i]>0)
				if (f[i]==false)
				{
					f[i]=true;
					search(i);
				}
	}
	public ansType calculateMinCut(){
		stoerWagner mySW=new stoerWagner(vertice,conValue);
		ansType Vans=mySW.calculateMinCut();
		ansType finalAns=new ansType();
		boolean ingroup[]=new boolean[vertice];
		for (int i=0;i<vertice;i++)
			ingroup[i]=false;
		for (int i=0;i<Vans.Vset.size();i++){
			ingroup[Vans.Vset.get(i)]=true;
		}
		finalAns.minCutValue=Vans.minCutValue;
		for (int i=0;i<edgeList.size();i++)
			if (((ingroup[edgeList.get(i).v1]==true)&&(ingroup[edgeList.get(i).v2]==false))||
			((ingroup[edgeList.get(i).v2]==true)&&(ingroup[edgeList.get(i).v1]==false)))
				finalAns.Vset.add(i+1);
		finalAns.minCutValue=Vans.minCutValue;
		return finalAns;
	}
}