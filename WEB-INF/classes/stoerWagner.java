import java.util.ArrayList;

public class stoerWagner{
	double conValue[][];
	int vertice;
	boolean dV[];
	double minValue;
	int minValueV;
	int combined[];
	ArrayList<Integer> Vset;
	public stoerWagner(int v,double tmp[][]){
		vertice=v;
		conValue=tmp;
		dV=new boolean[vertice];
		for (int i=0;i<vertice;i++)
			dV[i]=false;
		minValue=Double.MAX_VALUE;
		Vset=new ArrayList<Integer>();
	}
	
	public void test(){
		for (int i=0;i<vertice;i++){
			for (int j=0;j<vertice;j++)
			{
				System.out.print(conValue[i][j]);
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}
	public ansType calculateMinCut(){
		double edgeList[][];
		edgeList=new double[vertice][vertice];
		int edgeList2[][];
		edgeList2=new int[vertice][vertice];
		int elp[]=new int[vertice];
		double tmp;
		int tmp2;
		combined=new int[vertice];
		for (int i=0;i<vertice;i++)
		{
			combined[i]=-1;
			elp[i]=0;
			for (int j=0;j<vertice;j++){
				edgeList[i][j]=conValue[i][j];
				edgeList2[i][j]=j;
			}
			for (int k=0;k<vertice;k++)
				for (int l=k+1;l<vertice;l++){
					if (edgeList[i][k]<edgeList[i][l]){
						tmp=edgeList[i][k];
						edgeList[i][k]=edgeList[i][l];
						edgeList[i][l]=tmp;
						tmp2=edgeList2[i][k];
						edgeList2[i][k]=edgeList2[i][l];
						edgeList2[i][l]=tmp2;
					}
				}
		}
		boolean added[]=new boolean[vertice];
		boolean deleted[]=new boolean[vertice];
		for (int i=0;i<vertice;i++)
			deleted[i]=false;
		for (int i=0;i<vertice-1;i++){
			for (int j=1;j<vertice;j++)
				added[j]=false;
			added[0]=true;
			int v=vertice-i-1;
			while (v>0){
				double mThisRound=0;
				int mv=0;
				int ms=0;
				for (int l=0;l<vertice;l++){
					if (added[l]==true){
						while ((elp[l]<vertice)&&((added[edgeList2[l][elp[l]]]==true)||(deleted[edgeList2[l][elp[l]]]==true))){
							elp[l]++;
						}
						if (elp[l]<vertice)
							if (edgeList[l][elp[l]]>mThisRound){
								mThisRound=edgeList[l][elp[l]];
								mv=edgeList2[l][elp[l]];
								ms=l;
							}
					}
				}
				added[mv]=true;
				if (v==1){
					double tmpsum=0;
					for (int l=0;l<vertice;l++)
						if (deleted[edgeList2[mv][l]]==false)
							tmpsum+=edgeList[mv][l];
					combined[mv]=ms;
					deleted[mv]=true;
					if (tmpsum<minValue){
						minValue=tmpsum;
						minValueV=mv;
					}
					for (int l=0;l<vertice;l++){
						if ((deleted[edgeList2[mv][l]]==false)&&(edgeList2[mv][l]!=ms)){
							int nextV=edgeList2[mv][l];
							double toAdd=edgeList[mv][l];
							int k;
							for (k=0;k<vertice;k++)
								if (edgeList2[ms][k]==nextV)
									break;
							edgeList[ms][k]+=toAdd;
							while (k>0){
								if (edgeList[ms][k]>edgeList[ms][k-1]){
									tmp=edgeList[ms][k];
									edgeList[ms][k]=edgeList[ms][k-1];
									edgeList[ms][k-1]=tmp;
									tmp2=edgeList2[ms][k];
									edgeList2[ms][k]=edgeList2[ms][k-1];
									edgeList2[ms][k-1]=tmp2;
									k--;
								}
								else
									break;
							}
							elp[ms]=0;
							for (k=0;k<vertice;k++)
								if (edgeList2[nextV][k]==ms)
									break;
							edgeList[nextV][k]+=toAdd;
							while (k>0){
								if (edgeList[nextV][k]>edgeList[nextV][k-1]){
									tmp=edgeList[nextV][k];
									edgeList[nextV][k]=edgeList[nextV][k-1];
									edgeList[nextV][k-1]=tmp;
									tmp2=edgeList2[nextV][k];
									edgeList2[nextV][k]=edgeList2[nextV][k-1];
									edgeList2[nextV][k-1]=tmp2;
									k--;
								}
								else
									break;
							}
							elp[nextV]=0;
						}
					}
				}
				v--;
			}
		}
		getset(minValueV);
		ansType myAns=new ansType();
		myAns.Vset=Vset;
		myAns.minCutValue=minValue;
		return myAns;
	}
	public void getset(int node){
		Vset.add(node);
		for (int i=0;i<vertice;i++)
			if (combined[i]==node){
				getset(i);
			}
	}
}