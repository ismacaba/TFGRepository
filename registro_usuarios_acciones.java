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

// import control de fechas y formato
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class registro_usuarios_acciones
 */
//@WebServlet(description = "Acciones para el registro de usuarios", urlPatterns = { "/registro_usuarios_acciones" })
@WebServlet("/registro_usuarios_acciones")
public class registro_usuarios_acciones extends HttpServlet {
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
	
	// Declaración de variables recogidas en el formulario
	/*
	private String txtnombre=null;
	private String txtapellidos=null;
	private String dninif=null;
	private String semail=null;
	private String spassword=null;
	*/
	private String taccion=null;
	private String tnif = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registro_usuarios_acciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		taccion=request.getParameter("taccion");
		 
		if (taccion.equals("alta")) {
			// Devolver al usuario una página de insert SQL
			devolverPaginaAlta(request, response);				
		}
		else {
			// Devolver al usuario una página HTML con el formulario
			devolverPaginaHTML(response);
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

		out.println("<main role='main'><h2>Registro de Usuarios</h2><p>");				
 		out.println("<br/><br/>No se reconoce la acción que desea realizar o no está permitida, por favor, compruebe sus datos. <br/><br/>");	
 		out.println("<br/><br/><a href='javascript:history.back();'><strong>VOLVER</strong></a><br/><br/>");
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

		out.println("<main role='main'><h2>Registro de Usuarios</h2><p>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");			
			
			tnif = request.getParameter("dninif");
			
			//Recuperamos registros de la tabla Empresas	
			Statement stmt = con.createStatement();							
			ResultSet rs = stmt.executeQuery("select idusuario from rgpd_usuarios where nif = '" + tnif + "'");
			
			int total = 0;
			int seguiralta = 0; 
			
			while (rs.next()) {                
				//campo1 = rs.getInt(1);    // Id Usuario	
				total++;
			}
			
			if (total > 0) { //Se recupera registro
				out.println("<br/><br/><br/>Ya existe un usuario con este DNI/NIF, no se permite la duplicación de usuarios.<br/><br/><a href='javascript:history.back();'><strong>VOLVER<strong></a><br/><br/>&nbsp;<br/><br/>");
				seguiralta = 1; //Existe un usuario ya con ese NIF, evitar duplicados
			}
				
			rs.close();
			
			//No hay usuario existente con el mismo NIF, se realiza el ALTA del nuevo usuario
			if (seguiralta == 0) {
				//utilizar prepareastatament añadiendo los parámetros y montando toda la query
				String query = "insert into rgpd_usuarios (nif, nombre, apellidos, email, usrpassword, estadousr, rol, fecha_alta) values (?, ?, ?, ?, ?, ?, ?, ?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
			    
			    String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			    
			    //recuperamos valores del formulario
			    preparedStmt.setString(1, request.getParameter("dninif"));		    
			    preparedStmt.setString(2, request.getParameter("txtnombre"));
			    preparedStmt.setString(3, request.getParameter("txtapellidos"));
			    preparedStmt.setString(4, request.getParameter("semail"));
			    preparedStmt.setString(5, request.getParameter("spassword"));
			    preparedStmt.setString(6, "1");
			    preparedStmt.setString(7, "1");
			    preparedStmt.setString(8, timeStamp);
			    
			    //out.println("<br/>sql: " + preparedStmt.toString());
			    
			    // Ejecutar sentencia
			    preparedStmt.executeUpdate();
		    
				out.println("<br/><br/><br/>Registro dado de alta correctamente.<br/><br/><p style='text-align: center;'><a href='./index.html' title='VOLVER AL INICIO'>VOLVER AL INICIO</a><br/><br/><br/><br/>&nbsp;<br/><br/>");
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
