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
 * Servlet implementation class empresas
 */
@WebServlet("/empresas")
public class empresas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String shead1 = "<!doctype html><html lang='en' class='no-js'>";
	private String shead2 = "<head><meta charset='utf-8'><meta http-equiv='x-ua-compatible' content='ie=edge'><meta name='viewport' content='width=device-width, initial-scale=1.0'>";    
	private String shead3 = "<title>TFG: Asistente Web para la defensa de Derechos RGPD</title><meta name='description' content='TFG: Asistente Web para la defensa de Derechos RGPD'>";
	private String shead4 = "<link rel='stylesheet' href='./css/style.css'></head>";
	
	private String sbody1 = "<body><header id='pageContent'>";
	private String sbody2 = "<div id='logo'><a href='https://www.unir.net/' target='_blank'><img src='./img/logo-unir1.svg' title='UNIR: Universidad Internacional de La Rioja' border='0'></a>TFG: Asistente Web Derechos RGPD</div>";
	private String sbody3 = "<nav><ul><li><a href='./index.html' title='Inicio - Home'>Inicio</a>";
	private String sbody4 = "<li><a href='./derechos_rgpd.html' title='Conocer los Derechos RGPD'>Derechos RGPD</a><li><a href='./reclamacion' title='Realizar una nueva Reclamaci�n'>Realizar Reclamaci�n</a>";
	private String sbody5 = "<li><a href='./empresas' title='Consultar Empresas'>Empresas</a><li><a href='./registro_usuarios.html' title='Registro nuevo usuario'>Registrarse</a></ul></nav></header>";
	private String sbody6 = "<section id='pageContentMain'><p><h1>TFG. Asistente web para la defensa de los derechos recogidos en el RGPD</h1><br/><p>";
	
	private String sfoot = "</main></section><footer id='pageContent'><p>&copy;2021. Trabajo Fin de Grado desarrollado por <strong>Ismael Caballero M�ndez</strong>.</p></footer></body></html>";
	
	// Declaraci�n de variables recogidas en el formulario		
	//private Integer campo1 = null;
	private String campo2 = null;
	private String campo3 = null;
	private String campo4 = null;
	private String campo5 = null;
	//private String campo6 = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public empresas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Devolver al usuario una p�gina HTML con los valores adquiridos
		devolverPaginaHTML(response);
	}
	
	public void devolverPaginaHTML(HttpServletResponse resp) throws ServletException {
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		resp.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (s�lo para mandar texto)
		PrintWriter out = null;
				
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");
		}
				
		// Se genera el contenido de la p�gina HTML
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
		
		out.println("<br/><br/>&nbsp;&nbsp;<a href='empresas_acciones?taccion=form&tvalidated=s'><strong>ALTA DE NUEVA EMPRESA</strong></a><br/><br/>");
		out.println("<br/>Listado de empresas guardadas en la aplicaci�n.<br/><br/>");			
		
		//Recuperamos los valores de la tabla de Empresas
		try {
			//Conexi�n a BBDD y Ejecutar Sentencias SQL		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();		
			
			//Recuperamos registros de la tabla Empresas				
			ResultSet rs = stmt.executeQuery("select idempresa, cif, razonsocial, telefono1, emaildpto, estado_empresa from rgdp_empresas where estado_empresa = 1 order by razonsocial");
			
			out.println("<table style='float:left; border: 1px solid black; width:850px; border-collapse:collapse;'><tr>");
			out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>CIF</strong></label></td>");
			out.println("<td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMPRESA</strong></label></td>");
			out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>TELEFONO</strong></label></td>");
			out.println("<td width='250px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMAIL RGPD</strong></label></td>");
			//out.println("<td width='100px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>ESTADO</strong></label></td></tr>");
			
			while (rs.next()) {                
				//campo1 = rs.getInt(1);    // Id Empresa
				campo2 = rs.getString(2);  // CIF
				campo3 = rs.getString(3); // Empresa/Raz�n Social
				campo4 = rs.getString(4); // Tel�fono
				campo5 = rs.getString(5); // Email Empresa
				//campo6 = rs.getString(6); // Estado Empresa
				
				//String sEstado = "No Activa";
				
				//if (campo6.equals("1")) {
				//	sEstado = "Activa";
				//}
				
				// Imprimir valores de columnas
				out.println("<tr><td width='150px' style='border: 1px solid black; padding:5px;'><label>" + campo2 + "</label></td>");
				out.println("<td width='250px' style='border: 1px solid black; padding:5px;'><label><strong>"  + campo3 + "</strong></label></td>");
				out.println("<td width='150px' style='border: 1px solid black; padding:5px;'><label>" + campo4 + "</label></td>");
				out.println("<td width='250px' style='border: 1px solid black; padding:5px;'><label>" + campo5 + "</label></td>");
				//out.println("<td width='100px' style='border: 1px solid black; padding:5px;'><label>" + sEstado + "</label></td></tr>");
			}
			rs.close();
			
			out.println("</table><br/><br/>&nbsp;<br/>");
			
			//Cerramos la conexi�n a la Base de datos
			con.close(); 	
			
		} catch (Exception e) {				
			out.println("<br>Error en la base de datos.<br/><br/>");
		}
			
		
		out.println("<table style='float:left; border: 0px solid black; width:700px; border-collapse:collapse;'><tr><td width='250px' style='border: 0px solid black; padding:5px; margin:0px;'><br/>&nbsp;<br/></td></tr></table>");
		
		//PIE DE P�GINA Y FIN BODY 
		out.println(sfoot);

		// Se fuerza la descarga del buffer y se cierra el PrintWriter, liberando recursos de esta forma.
		out.flush();
		out.close();
	} // fin del m�todo devolverPaginaHTML()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
