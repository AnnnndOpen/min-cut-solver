import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 

@WebServlet("/mincutServlet")
@MultipartConfig
public class mincutServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String vertice=request.getParameter("vertice");
		int vn=0;
		String costType=request.getParameter("costType");
		int costT;
		if (costType==null){
			costT=0;
		}
		else
			costT=1;
		try{
			vn=Integer.parseInt(vertice);
		}catch (NumberFormatException e){
			request.setAttribute("inValid", "Please input an integer as the vertice number.");
			request.getRequestDispatcher("homePage.jsp").forward(request, response);
		}
		if (vn<=1){
			request.setAttribute("inValid", "The vertice number should be greater than one.");
			request.getRequestDispatcher("homePage.jsp").forward(request, response);
		}
			
		Part filePart=request.getPart("dataFile");
		String fileName=Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		if (fileName.isEmpty()){
			request.setAttribute("inValid", "Please upload the data file.");
			request.getRequestDispatcher("homePage.jsp").forward(request, response);
		}
		InputStream fileContent=filePart.getInputStream();
		File file1=new File("tmpInput.csv");
		Files.copy(fileContent,file1.toPath());
		parseInput pi=new parseInput(String.valueOf(file1),vn);
		int parseResult=0;
		try{
			parseResult=pi.parseData(costT);
			if (parseResult==0){
				if (pi.testConnection()==false){
					request.setAttribute("inValid", "Invalid input, the graph is not connected");
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
				}
				else{
					ansType ans=pi.calculateMinCut();
					response.setContentType("text/txt");
				    response.setHeader("Content-Disposition", "attachment; filename=\"solution.txt\"");
					OutputStream fileOut=response.getOutputStream();
					String outstring="The min-cut cost is "+Double.toString(ans.minCutValue)+'\n';
					fileOut.write(outstring.getBytes());
					outstring="Removed edge list(edgeID):\n";
					fileOut.write(outstring.getBytes());
					outstring="\n";
					for (int i=0;i<ans.Vset.size();i++){
						fileOut.write(Integer.toString(ans.Vset.get(i)).getBytes());
						fileOut.write(outstring.getBytes());
					}
					fileOut.flush();
					fileOut.close();
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
				}
			}
			else if (parseResult==-1){
				request.setAttribute("inValid", "The weight value must be positive.");
				request.getRequestDispatcher("homePage.jsp").forward(request, response);
			}
			else if (parseResult==-2){
				request.setAttribute("inValid", "csv file format is wrong.");
				request.getRequestDispatcher("homePage.jsp").forward(request, response);
			}
			else if (parseResult==-3){
				request.setAttribute("inValid", "Input data error, fail to read the data.");
				request.getRequestDispatcher("homePage.jsp").forward(request, response);
			}
			else if (parseResult==-4){
				request.setAttribute("inValid", "Invalid vertice number.");
				request.getRequestDispatcher("homePage.jsp").forward(request, response);
			}
		}
		catch(Exception e){
		}
		finally{
			file1.delete();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			doGet(request, response);
	}
}