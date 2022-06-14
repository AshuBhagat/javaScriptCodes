// function for get details by Json Data
// <%@page import="org.json.JSONObject"%> on jsp page required
// on Login Servlet page create Session 
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
HttpSession session = request.getSession(true);
					session.setAttribute("employeeId", emp.getEmployeeId());
					session.setAttribute("empSessionFirstLastName", emp.getEmployeeFirstName()+" "+employee.getEmployeeLastName());
					session.setAttribute("department", emp.getEmployeeDepartment());

RequestDispatcher rd=request.getRequestDispatcher("index");
		            rd.forward(request, response);
//response.sendRedirect("index.jsp");
//*--------------end session on Login Servlet------------------*--//
public String getByEmpIdJsonData(int byEmployeeId) throws Exception
{
	PreparedStatement ps2=null;
	ResultSet rs2=null;
	JSONObject jobj=null;
	int sizeodata=0;
	try
	{
		con=MyCon.getConnection();
		String sql="select e.employeefirstname AS eFname,e.employeelastname AS eLname,e.employeepicpath AS profilePic ,e.employeeid AS empId,apl.* from employeetable e LEFT JOIN assignpermissionforlinks apl ON e.employeeid=apl.employeeid where e.employeeid=?";
		ps2=con.prepareStatement(sql);
		ps2.setInt(1, byEmployeeId);
		rs2=ps2.executeQuery();
		
		if(rs2.next())
		{
			jobj = new JSONObject();
			sizeodata=1;
			
			String employeeName=""; /// Employee Name first and last
			String employeePicPath="";
			int employeeId=0;
			
			int sNo=0;
			int empIdApp=0;
			boolean showClientPage=false;
			boolean showEnquiryPage=false;
			
			/****Dash Board Permission*****/
			boolean dashBoardAppointment=false;
			
			if(!Strings.isNullOrEmpty(rs2.getString("eFname"))){
				if(employeeName=="")
				{
					employeeName=rs2.getString("eFname");
				}else
				{
					employeeName=employeeName+" "+rs2.getString("eLname");
				}
			}
			if(!Strings.isNullOrEmpty(rs2.getString("eLname")))
			{
				if(employeeName=="")
				{
					employeeName=rs2.getString("eLname");
				}else
				{
					employeeName=employeeName+" "+rs2.getString("eLname");
				}
			}
			if(!Strings.isNullOrEmpty(rs2.getString("profilePic")))
			{
				employeePicPath=rs2.getString("profilePic");
			}if(!Strings.isNullOrEmpty(rs2.getString("empId")))
			{
				employeeId=rs2.getInt("empId");
			}
			if(!Strings.isNullOrEmpty(rs2.getString("sNo")))
			{
				sNo =rs2.getInt("sNo");
			}
			if(!Strings.isNullOrEmpty(rs2.getString("employeeId")))
			{
				empIdApp=rs2.getInt("employeeId");
			}
			if(!Strings.isNullOrEmpty(rs2.getString("showClientPage"))){ 
				showClientPage=rs2.getBoolean("showClientPage");
			}
			if(!Strings.isNullOrEmpty(rs2.getString("showEnquiryPage"))){ 
				showEnquiryPage=rs2.getBoolean("showEnquiryPage");
			}
			if(!Strings.isNullOrEmpty(rs2.getString("dashboardappointment"))){ 
				dashBoardAppointment=rs2.getBoolean("dashboardappointment");
			}
			
			jobj.put("employeeNameBoth",employeeName);
			jobj.put("employeePicPath", employeePicPath);
			jobj.put("empId", employeeId);
			jobj.put("sNo",sNo);
			jobj.put("employeeId",empIdApp);
			jobj.put("showClientPage",showClientPage);
			jobj.put("showEnquiryPage",showEnquiryPage);
			/****Dash Board Permission*****/
			jobj.put("dashBoardAppointment",dashBoardAppointment);
		return sizeodata > 0 ? jobj.toString() : null;
	}
	}catch(SQLException sq)
	{
		sq.printStackTrace();
		return null;
	}catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	finally {
		DbUtils.closeQuietly(rs2);
		DbUtils.closeQuietly(ps2);
		}
	return null;
}
