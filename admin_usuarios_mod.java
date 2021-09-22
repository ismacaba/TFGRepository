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

//import java.util.Calendar;

/**
 * Servlet implementation class admin_usuarios_mod
 */
@WebServlet("/admin_usuarios_mod")
public class admin_usuarios_mod extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Variables plantilla HTML
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
	private String taccion = null;
	private String tvalidated = null;
	private String idusuario = null;
	private String tdninif = null;	
	
	private String campo1 = null;
	private String campo2 = null;
	private String campo3 = null;
	private String campo4 = null;
	private Integer campo5 = null;
	private Integer campo6 = null;
	//private Integer campo7 = null;
	//private String campo8 = null;
	//private String campo9 = null;
	//private String campo10 = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin_usuarios_mod() {
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

		idusuario=request.getParameter("idusuario");
		tdninif=request.getParameter("tdninif");
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

		out.println("<main role='main'><h2>Área de ADMINISTRACIÓN de Usuarios.</h2><p>");
 		out.println("<br/><br/>Puede modificar los datos del usuario seleccionado.<br/><br/>");			
					
		//Recuperamos los valores de la tabla de Derechos
		try {
			//Conexión a BBDD y Ejecutar Sentencias INSERT INTO DEL NUEVO USUARIO		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();		
			
			//Recuperamos registros de la tabla Usuarios, validando que coincidan el Id y el NIF del usuario			
			String sql = "select nif, nombre, apellidos, email, estadousr, rol from rgpd_usuarios where idusuario = " + idusuario + " and nif = '" + tdninif + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			out.println("<form name='frmadmin' method='post' action='admin_usuarios_mod?tvalidated=s&taccion=modif'>");
			out.println("<input type='hidden' name='idusuario' value='" + idusuario + "' id='idusuario'>"); 
			
			int total = 0;
			
			while (rs.next()) {      
				campo1 = rs.getString(1);  // NIF/DNI
				campo2 = rs.getString(2); // Nombre				
				campo3 = rs.getString(3); // Apellidos
				campo4 = rs.getString(4); // Email
				campo5 = rs.getInt(5); // Estado
				campo6 = rs.getInt(6); // 	Rol (1: Usuario, 2: Administrador)
				
				//campo7 = rs.getInt(1);    // Id Usuario
				//campo8 = rs.getString(6); // Password
				//campo9 = rs.getString(9); // Fecha_Alta
				//campo10 = rs.getString(10); // Fecha Modificación				
				
				// Imprimir valores de columnas
				out.println("<table style='float:left; border: 1px solid black; width:700px; border-collapse:collapse;'>");
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>DNI/NIF *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='nif' id='nif' placeholder='Escriba DNI/NIF' value='" + campo1 + "' required></td></tr>");
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>NOMBRE * </strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='snombre' id='snombre' value='" + campo2 + "' size='70px' placeholder='Escriba el Nombre' required></td></tr>");
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>APELLIDOS *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='apellidos' id='apellidos' size='70px' placeholder='Escriba los Apellidos' value='" + campo3 + "' required></td></tr>");
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMAIL *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='semail' id='semail' value='" + campo4 + "' size='70px' placeholder='Escriba el Email' required></td></tr>");
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ROL *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='rol' id='rol' placeholder='Escriba el ROL de usuario' value='" + campo6 + "'> (1: Usuario, 2: Administrador)</td></tr>");				
				out.println("<tr><td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ESTADO (0/1) *</strong></label></td><td width='500px' style='border: 1px solid black; padding:5px;'><input type='text' name='sestadousuario' id='sestadousuario' value='" + campo5 + "' placeholder='Escriba el Estado' required> (0: No Activo, 1: Activo)</td></tr>");
				
				out.println("<tr><td colspan='2' style='text-align: center;'><br/><button type='submit' name='botonEnviar' id='Enviar' title='Modificar datos'><p>Modificar</p></button>");
				out.println("<input type='reset' NAME='botonLimpiar' VALUE='Limpiar' title='Limpiar datos'>");
				out.println("<br/><br/><p class='aviso'><br/><span class='obligatorio' style='text-align: left;'> * </span>los campos son obligatorios.<br/><br/></p></td></tr>");				
				out.println("</table></form>");
				
				total++;
			}
			
			if (total == 0) { //No se recupera ningún registro
				out.println("<br/><br/>No se ha podido recuperar ningún registro, compruebe los datos introducidos.<br/><br/>");
			}
			
			rs.close();
			
			//Cerramos la conexión a la Base de datos
			con.close();			
		
			
		} catch (Exception e) {
			out.println("<br>Error en la base de datos.<br/><br/>");
		}
		
		out.println("<table style='float:left; border: 0px solid black; width:700px; border-collapse:collapse;'><tr><td width='250px' style='border: 0px solid black; padding:5px; margin:0px;'><br/>&nbsp;<br/></td></tr></table>");
		
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

		out.println("<main role='main'><h2>Área de ADMINISTRACIÓN de Usuarios.</h2><p>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");
			
			//String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			
			//utilizar prepareastatament añadiendo los parámetros y montando toda la query
			String query = "update rgpd_usuarios set nif = ?, nombre = ?, apellidos = ?, email = ?, rol = ?, estadousr = ? where idusuario = ?";
		    PreparedStatement preparedStmt = con.prepareStatement(query);
		   
		    //recuperamos valores del formulario
		    preparedStmt.setString(1, request.getParameter("nif"));
		    preparedStmt.setString(2, request.getParameter("snombre"));
		    preparedStmt.setString(3, request.getParameter("apellidos"));
		    preparedStmt.setString(4, request.getParameter("semail"));
		    preparedStmt.setString(5, request.getParameter("rol"));
		    preparedStmt.setString(6, request.getParameter("sestadousuario"));
		    preparedStmt.setString(7, request.getParameter("idusuario"));		    
		    //preparedStmt.setString(8, timeStamp);  //Control fecha baja del usuario		    
		    
		    //out.println("<br/>sql: " + preparedStmt.toString()); 
		    	
		    // Ejecutar sentencia preparedstatement
		    preparedStmt.executeUpdate();
	    
			out.println("<br/><br/><br/>Registro actualizado correctamente.<br/><br/><p style='text-align: center;'><a href='admin_usuarios?taccion=V' title='VOLVER AL LISTADO DE USUARIOS'>VOLVER AL LISTADO DE USUARIOS</a><br/><br/><br/><br/>&nbsp;<br/><br/>");
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
