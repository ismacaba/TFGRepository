package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* import necesarios para BBDD */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class admin_derechos_mod
 */
@WebServlet("/admin_derechos_mod")
public class admin_derechos_mod extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Definir variables para la plantilla HTML, parámetros y de control
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
	
	private String taccion = null;
	private String tvalidated = null;
	private String idderecho = null;
	
	private Integer campo1 = null;
	private String campo2 = null;
	private String campo3 = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin_derechos_mod() {
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

		idderecho=request.getParameter("idderecho");
		tvalidated=request.getParameter("tvalidated");
		taccion=request.getParameter("taccion");
		
		//Validamos que el usuario esté validado para hacer las acciones		
		if (tvalidated.equals("s")) { 
			if (taccion.equals("modif")) {
				// Devolver al usuario una página HTML con Update de SQL
				devolverPaginaModificar(request, response);				
			}
			else if (taccion.equals("form")) {
				// Devolver al usuario una página HTML con el formulario
				devolverPaginaHTML(response);
			}
		}
		else {
			out.println("<br/><br/><strong>ERROR:</strong> No se ha reconocido como Administrador o no tiene los permisos requeridos. No tiene acceso.<br/><br/><br/><br/><br/><br/>");
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

		out.println("<main role='main'><h2>Área de ADMINISTRACIÓN de Derechos.</h2><p>");
 		out.println("<br/><br/>Puede modificar los datos del derecho seleccionado.<br/><br/>");			
					
		//Recuperamos los valores de la tabla de Derechos
		try {
			//Conexión a BBDD y Ejecutar Sentencias INSERT INTO DEL NUEVO USUARIO		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();		
			
			//Recuperamos registros de la tabla Derechos				
			String sql = "select * from rgdp_derechos where idderecho = " + idderecho;
			ResultSet rs = stmt.executeQuery(sql);
			
			out.println("<form name='frmadmin' method='post' action='admin_derechos_mod?tvalidated=s&taccion=modif'>");
			out.println("<input type='hidden' name='idderecho' value='" + idderecho + "' id='idderecho'>");
			out.println("<table style='float:left; border: 1px solid black; width:550px; border-collapse:collapse;'><tr>");
			out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ID_DERECHO</strong></label></td>");
			out.println("<td width='300px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>DERECHO RGPD *</strong></label></td>");
			out.println("<td width='100px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ESTADO (0/1) *</strong></label></td></tr>");
			
			while (rs.next()) {                
				campo1 = rs.getInt(1);    // Id Derecho
				campo2 = rs.getString(2);  // Derecho RGPD
				campo3 = rs.getString(3); // Estado Derecho
				
				// Imprimir valores de columnas
				out.println("<tr><td width='150px' style='border: 1px solid black; padding:5px;'><label><strong>" + campo1 + "</strong></label></td><td width='300px' style='border: 1px solid black; padding:5px;'><input type='text' size='60px' required name='sderecho' id='sderecho' value='" + campo2 +  "'></td><td width='100px' style='border: 1px solid black; padding:5px;'><input type='text' required name='sestadoderecho' id='sestadoderecho' value='" + campo3 + "'</td></tr>");
			}
			rs.close();
			
			out.println("<tr><td colspan='3' style='text-align: center;'><br/><button type='submit' name='botonEnviar' id='Enviar' title='Modificar datos'><p>Modificar</p></button>");
			out.println("<input type='reset' NAME='botonLimpiar' VALUE='Limpiar' title='Limpiar datos'>");
			out.println("<br/><br/><p class='aviso'><br/><span class='obligatorio' style='text-align: left;'> * </span>los campos son obligatorios.<br/><br/></p></td></tr>");
			
			out.println("</table></form><br/><br/>&nbsp;<br/><br/><br/>");

			//Cerramos la conexión a la Base de datos
			con.close();			
		
			
		} catch (Exception e) {
			out.println("<br>Error en la base de datos.<br/><br/>");
		}
		
		out.println("<p><br/><br/><br/>&nbsp;<br/><br/><br/><br/>&nbsp;<br/><br/></p>");
		
		//PIE DE PÁGINA Y FIN BODY
		out.println(sfoot);

		// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
		out.flush();
		out.close();
	} // fin del método devolverPaginaHTML()
	
	public void devolverPaginaModificar(HttpServletRequest request, HttpServletResponse resp) throws ServletException {
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

		out.println("<main role='main'><h2>Área de ADMINISTRACIÓN de Derechos.</h2><p>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");
			
			//utilizar prepareastatament añadiendo los parámetros y montando toda la query
			String query = "update rgdp_derechos set derechorgpd = ?, estadoderecho = ? where idderecho = ?";
		    PreparedStatement preparedStmt = con.prepareStatement(query);
		    
		    //recuperamos valores del formulario
		    preparedStmt.setString(1, request.getParameter("sderecho"));
		    preparedStmt.setString(2, request.getParameter("sestadoderecho"));
		    preparedStmt.setString(3, request.getParameter("idderecho"));
		    		    
		    // Ejecutar sentencia preparedstatement
		    preparedStmt.executeUpdate();
	    
			out.println("<br/><br/><br/>Registro actualizado correctamente.<br/><br/><p style='text-align: center;'><a href='admin_derechos?taccion=V' title='VOLVER AL LISTADO DE DERECHOS'>VOLVER AL LISTADO DE DERECHOS</a><br/><br/><br/><br/>&nbsp;<br/><br/>");
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}	
		
		//PIE DE PÁGINA Y FIN BODY
		out.println(sfoot);

		// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
		out.flush();
		out.close();
	} // fin del método devolverPaginaModificar()
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
