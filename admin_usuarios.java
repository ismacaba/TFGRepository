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
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class admin_usuarios
 */
@WebServlet("/admin_usuarios")
public class admin_usuarios extends HttpServlet {
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
	private String taccion=null;
	private Integer campo1 = null;
	private String campo2 = null;
	private String campo3 = null;	
	private String campo4 = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin_usuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Adquisición de los valores del formulario a través del objeto request
				taccion=request.getParameter("taccion");				
				
				// Devolver al usuario una página HTML con los valores adquiridos
				devolverPaginaHTML(response);
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
				
				//Validamos que el usuario esté validado para hacer las acciones		
				if (taccion.equals("V")) { 
					out.println("<br/><br/>Listado de usuarios guardados en la aplicación, seleccione el que desea modificar.<br/><br/>");			
					
					//Recuperamos los valores de la tabla de Usuarios
					try {
						//Conexión a BBDD y Ejecutar Sentencias SQL		
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
						Statement stmt = con.createStatement();		 
						 
						//Recuperamos registros de la tabla Usuarios				
						ResultSet rs = stmt.executeQuery("select idusuario, nif, nombre, apellidos, estadousr from rgpd_usuarios order by nombre");
						
						out.println("<table style='float:left; border: 1px solid black; width:700px; border-collapse:collapse;'><tr>");
						out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>DNI/NIF</strong></label></td>");
						out.println("<td width='400px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>NOMBRE COMPLETO</strong></label></td>");
						out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ESTADO</strong></label></td></tr>");
						
						while (rs.next()) {                
							campo1 = rs.getInt(1);    // Id Usuario
							campo2 = rs.getString(2);    // DNI/NIF
							campo3 = rs.getString(3) + " " + rs.getString(4) ;  // Nombre y Apellidos
							campo4 = rs.getString(5); // Estado
							
							// Imprimir valores de columnas
							out.println("<tr><td width='150px' style='border: 1px solid black; padding:5px;'><label>" + campo2 + "</label></td><td width='400px' style='border: 1px solid black; padding:5px;'><label><strong><a href='admin_usuarios_mod?idusuario=" + campo1 +  "&tdninif=" + campo2 + "&tvalidated=s&taccion=form'>"  + campo3 + "</a></strong></label></td><td width='100px' style='border: 1px solid black; padding:5px;'><label>" + campo4 + "</label></td></tr>");
						}
						rs.close();
						
						out.println("</table><br/>&nbsp;<br/><br/>");
						
						//Cerramos la conexión a la Base de datos
						con.close(); 			
					
						
					} catch (Exception e) {				
						out.println("<br>Error en la base de datos.<br/><br/>");
					}
				}
				else {
					out.println("<br/><br/><strong>ERROR:</strong> No se ha reconocido como Administrador o no tiene los permisos requeridos. No tiene acceso.<br/><br/><br/><br/><br/><br/>");
				}	
				
				out.println("<table style='float:left; border: 0px solid black; width:700px; border-collapse:collapse;'><tr><td width='750px' style='border: 0px solid black; padding:5px; margin:0px;'><br/>&nbsp;<br/><br/><p style='text-align: left;'><a href='administrador_acciones?taccion=V&txtdninif=&txtemail=' title='VOLVER AL MENÚ PRINCIPAL DEL ADMINISTRADOR'>VOLVER AL MENÚ PRINCIPAL DEL ADMINISTRADOR</a><br/><br/>&nbsp;<br/>&nbsp;<br/><br/></td></tr></table>");
				
				//PIE DE PÁGINA Y FIN BODY 
				out.println(sfoot); 

				// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
				out.flush();
				out.close();
			} // fin del método devolverPaginaHTML()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
