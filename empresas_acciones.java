package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import necesarios para BBDD 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// import control y formato de fechas
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class empresas_acciones
 */
@WebServlet("/empresas_acciones")
public class empresas_acciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String shead1 = "<!doctype html><html lang='en' class='no-js'>";
	private String shead2 = "<head><meta charset='utf-8'><meta http-equiv='x-ua-compatible' content='ie=edge'><meta name='viewport' content='width=device-width, initial-scale=1.0'>";    
	private String shead3 = "<title>TFG: Asistente Web para la defensa de Derechos RGPD</title><meta name='description' content='TFG: Asistente Web para la defensa de Derechos RGPD'>";
	private String shead4 = "<link rel='stylesheet' href='./css/style.css'></head>";
	
	private String sbody1 = "<body><header id='pageContent'>";
	private String sbody2 = "<div id='logo'><a href='https://www.unir.net/' target='_blank'><img src='./img/logo-unir1.svg' title='UNIR: Universidad Internacional de La Rioja' border='0'></a>TFG: Asistente Web Derechos RGPD</div>";
	private String sbody3 = "<nav><ul><li><a href='./index.html' title='Inicio - Home'>Inicio</a>";
	private String sbody4 = "<li><a href='./derechos_rgpd.html' title='Conocer los Derechos RGPD'>Derechos RGPD</a><li><a href='./reclamacion' title='Realizar una nueva Reclamación'>Realizar Reclamación</a>";
	private String sbody5 = "<li><a href='./empresas' title='Consultar Empresas'>Empresas</a><li><a href='./registro_usuarios.html' title='Registro nuevo usuario'>Registrarse</a></ul></nav></header>";
	private String sbody6 = "<section id='pageContentMain'><p><h1>TFG. Asistente web para la defensa de los derechos recogidos en el RGPD</h1><br/><p>";
	
	private String sfoot = "</main></section><footer id='pageContent'><p>&copy;2021. Trabajo Fin de Grado desarrollado por <strong>Ismael Caballero Méndez</strong>.</p></footer></body></html>";
	
	// Declaración de variables de control
	private String taccion = null;
	private String tvalidated = null;
	private String tcif = null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public empresas_acciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		response.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (sólo para mandar texto)
		PrintWriter out = null;
				
		try {
			out=response.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");
		}

		tvalidated=request.getParameter("tvalidated");
		taccion=request.getParameter("taccion");
		
		//Validamos que el usuario esté validado para hacer las acciones		
		if (tvalidated.equals("s")) { 
			if (taccion.equals("alta")) {
				// Devolver al usuario una página de insert SQL
				devolverPaginaAlta(request, response);				
			}
			else if (taccion.equals("form")) {
				// Devolver al usuario una página HTML con el formulario
				devolverPaginaHTML(response);
			}
		}
		else {
			out.println("<br/><br/><strong>ERROR:</strong> No tiene los permisos requeridos para realizar esta acción.<br/><br/><br/><br/><br/><br/>");
		}
	}	
			
	public void devolverPaginaHTML(HttpServletResponse resp) throws ServletException {
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		resp.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (sólo para mandar texto)
		PrintWriter out = null;		
		
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");			
		}
		
		// Se genera el contenido de la página HTML
		//HEAD
		out.println(shead1);
		out.println(shead2);    
		out.println(shead3);
		out.println(shead4);		
		
		//INICIO BODY Y MENU
		out.println(sbody1);
		out.println(sbody2);
		out.println(sbody3);
		out.println(sbody4);
		out.println(sbody5);				
		out.println(sbody6); 

		out.println("<main role='main'><h2>EMPRESAS</h2><p>");				
 		out.println("<br/><br/>Puede dar de alta una nueva Empresa.<br/><br/>");	
			
		out.println("<form name='frmadmin' method='post' action='empresas_acciones?tvalidated=s&taccion=alta'>");	
		
		out.println("<table style='float:left; border: 1px solid black; width:700px; border-collapse:collapse;'>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>CIF *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='cif' id='cif' placeholder='Escriba CIF' required></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMPRESA * </strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='razonsocial' id='razonsocial' value='' size='70px' placeholder='Escriba la Razón Social' required></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>TELÉFONO 1 *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='telefono1' id='telefono1' placeholder='Escriba Teléfono Principal' required></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>TELÉFONO 2</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='telefono2' id='telefono2' placeholder='Escriba otro Teléfono' ></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMAIL RGPD *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='emailrgpd' id='emailrgpd' value='' size='70px' placeholder='Escriba el Email RGPD' required></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMAIL RRHH</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='emailrrhh' id='emailrrhh' value='' size='70px' placeholder='Escriba el Email Dpto. RRHH'></td></tr>");
		out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMAIL DPTO. LEGAL</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='emaildptolegal' id='emaildptolegal' value='' size='70px' placeholder='Escriba el Email Dpto. Legal'></td></tr>");
		out.println("<input type='hidden' name='sestadoempresa' id='sestadoempresa' value='1'>");
		//out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ESTADO (0/1) *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='sestadoempresa' id='sestadoempresa' value='1' required><label><label></td></tr>");
		
		out.println("<tr><td colspan='2' style='text-align: center;'><br/><button type='submit' name='botonEnviar' id='Enviar' title='Alta datos'><p>Alta</p></button>");
		out.println("<input type='reset' NAME='botonLimpiar' VALUE='Limpiar' title='Limpiar datos'>");
		out.println("<br/><br/><p class='aviso'><br/><span class='obligatorio' style='text-align: left;'> * </span>los campos son obligatorios.<br/><br/></p></td></tr>");				
		out.println("</table></form>");
		
		out.println("<table style='float:left; border: 0px solid black; width:700px; border-collapse:collapse;'><tr><td width='250px' style='border: 0px solid black; padding:5px; margin:0px;'><br/>&nbsp;<br/></td></tr></table>");
		
		//PIE DE PÁGINA Y FIN BODY
		out.println(sfoot);

		// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
		out.flush();
		out.close();
	} // fin del método devolverPaginaHTML()
	
	public void devolverPaginaAlta(HttpServletRequest request, HttpServletResponse resp) throws ServletException {
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		resp.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (sólo para mandar texto)
		PrintWriter out = null;		
		
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");			
		}
		
		// Se genera el contenido de la página HTML
		//HEAD
		out.println(shead1);
		out.println(shead2);    
		out.println(shead3);
		out.println(shead4);		
		
		//INICIO BODY Y MENU
		out.println(sbody1);
		out.println(sbody2);
		out.println(sbody3);
		out.println(sbody4);
		out.println(sbody5);				
		out.println(sbody6); 

		out.println("<main role='main'><h2>EMPRESAS</h2><p>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");
			
			tcif = request.getParameter("cif");
			
			//Recuperamos registros de la tabla Empresas	
			Statement stmt = con.createStatement();							
			ResultSet rs = stmt.executeQuery("select idempresa from rgdp_empresas where cif = '" + tcif + "'");
			
			int total = 0;
			int seguiralta = 0; 
			
			while (rs.next()) {                
				//campo1 = rs.getInt(1);    // Id Empresa	
				total++;
			}
			
			if (total > 0) { //Se recupera registro
				out.println("<br/><br/><br/>Ya existe una empresa con este CIF, no se permite la duplicación de Empresas.<br/><br/><a href='javascript:history.back();'><strong>VOLVER<strong></a><br/><br/>&nbsp;<br/><br/>");
				seguiralta = 1; //Existe una empresa ya con ese CIF, evitar duplicados
			}
				
			rs.close();
			
			//No hay empresa existente con el mismo CIF, se realiza el ALTA de la nueva empresa
			if (seguiralta == 0) { 
				//utilizar prepareastatament añadiendo los parámetros y montando toda la query
				String query = "insert into rgdp_empresas (cif, razonsocial, emaildpto, emailrrhh, email_dptolegal, telefono1, telefono2, fecha_alta, fecha_ultmodificacion, estado_empresa) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
			    
			    String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			    
			    //recuperamos valores del formulario
			    preparedStmt.setString(1, request.getParameter("cif"));		    
			    preparedStmt.setString(2, request.getParameter("razonsocial"));
			    preparedStmt.setString(3, request.getParameter("emailrgpd"));
			    preparedStmt.setString(4, request.getParameter("emailrrhh"));
			    preparedStmt.setString(5, request.getParameter("emaildptolegal"));
			    preparedStmt.setString(6, request.getParameter("telefono1"));
			    preparedStmt.setString(7, request.getParameter("telefono2"));
			    preparedStmt.setString(8, timeStamp);
			    preparedStmt.setString(9, timeStamp);
			    preparedStmt.setString(10, request.getParameter("sestadoempresa"));
			    
			    // Ejecutar sentencia
			    preparedStmt.executeUpdate();
		    
				out.println("<br/><br/><br/>Registro dado de alta correctamente.<br/><br/><p style='text-align: center;'><a href='./empresas' title='VOLVER AL LISTADO DE EMPRESAS'>VOLVER AL LISTADO DE EMPRESAS</a><br/><br/><br/><br/>&nbsp;<br/><br/>");
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}	
		
		//PIE DE PÁGINA Y FIN BODY
		out.println(sfoot);

		// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
		out.flush();
		out.close();
	} // fin del método devolverPaginaAlta()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
