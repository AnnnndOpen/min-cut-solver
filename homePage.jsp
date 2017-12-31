<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import="java.util.*"%> 
<%@ page import="java.io.*" %>
<% 
	String inValid=(String)request.getAttribute("inValid");
%>
<html>
	<head>
		<title>min-cut problem homepage</title>
		<style>
			.container{
				position:relative;
				text-align: center;
			}
			.image{
				position:fixed;
			}
			.background{
				width: 100%;
				height: auto;
				opacity: 0.4;
				z-index: -1;
			}	
			.title1{
				border: 3px solid green;
				background-color: powderblue;
				position:relative;
				font-size: 30px;
			}
			.description{
				font-size: 25px;
				position:relative;
				text-align: left;
				padding-left: 5%;
				padding-right: 5%;
			}
			.uploadtext{
				font-size:25px;
				position:relative;
				text-align: left;
				padding-left: 5%;
				padding-right: 5%;
			}
			.answerIncorrect{
				color:red;
				font-size:25px;
				position:relative;
				text-align: left;
				padding-left: 5%;
				padding-right: 5%;
			}
			.answer{
				color:blue;
				font-size:25px;
				position:relative;
				text-align: left;
				padding-left: 5%;
				padding-right: 5%;
			}
			.atcenter{
				position:relative;
				text-align: center;
			}
			.button1{
				background-color: white;
				color: black;
				border: 2px solid green;
				-webkit-transition-duration: 0.2s;
    			transition-duration: 0.2s;
    			cursor: pointer;
			}
			.button1:hover{
				background-color: green;
				color: white;
			}
			.button2{
				background-color: white;
				color: black;
				border: 2px solid black;
				-webkit-transition-duration: 0.2s;
    			transition-duration: 0.2s;
    			cursor: pointer;
			}
			.button2:hover{
				border: 2px solid green;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div class="image">
				<img class="background" src="source/background2.jpg">
			</div>
			<div class="title1">
				<div>
					<h2>Min-Cut Problem Solver</h2>
				</div>
			</div>
			<div class="container">
				<div class="atcenter">
					<p class="description">
						The minimum cuts (or simply mincut) is a basic graph theory and the algorithms 
						to solve these kinds of problems have been widely used in different fields 
						such as Material Distribution and Circuit-breaking Protector.
						You may check the basic definition of mincut problems on 
						<a href="https://en.wikipedia.org/wiki/Minimum_cut">Wikipedia</a>.
					</p>
					<p class="description">
						This website provides a mincut problem solver. Given an undirected graph, it will
						find which edges must be removed at least to disconnect the graph into two subgraphs. 
						This algorithm uses the edges' weight value as the cost, the optimal solution should keep 
						the cost as low as possible.
					</p>
					<p class="description">
						Please upload a 'csv' file as the input data as the edge lists and input the vertice number.  
						An optimal solution based on weight-cost will be calculated in return. You may use the check-box 
						to apply removed edge numbers as the cost function.
					</p>
				</div>
				<div class="uploadtext">
					<form method="post" action="mincutServlet" enctype="multipart/form-data">
						Please input the vertice number: 
						<input type="text" name="vertice" maxlength="4" style="width:50px;"/><br>
						<input type="checkbox" name="costType" value="weight"> Calculate with removed edge numbers<br>
						Please upload a data file for the edges:
						<input type="file" name="dataFile" value="Browse..." id="fileChooser" class="button2"/>
						<input type="submit" value="Upload" class="button1"/>
					</form>
				</div>	
				<% 
					if (request.getAttribute("inValid")!=null)
					{
						out.println("<div class="+'"'+"answerIncorrect"+'"'+'>');
						out.println(inValid);
						out.println("</div>");
					}
				%>
			</div>
		</div>
	</body>
</html>