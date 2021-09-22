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
 * Servlet implementation class reclamacion_historico
 */
@WebServlet("/reclamacion_historico")
public class reclamacion_historico extends HttpServlet {
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
		private String taccion=null;		
		private Integer campo1 = null;
		private Integer campo2 = null;
		private String campo3 = null;
		private String campo4 = null;
		private String idusr = null;
		private String srazonsocial = null;
		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reclamacion_historico() {
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
		idusr=request.getParameter("idusr");
		
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
		
		out.println("<main role='main'><h2>Histórico de Reclamaciones</h2><p>");
		
		//Validamos que el usuario esté validado para hacer las acciones		
		if (taccion.equals("V")) { 
			out.println("<br/><br/>Listado de reclamaciones realizadas.<br/><br/>");			
			
			//Recuperamos los valores de la tabla de Empresas
			try {
				//Conexión a BBDD y Ejecutar Sentencias SQL		
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
				Statement stmt = con.createStatement();		
				
				//Recuperamos registros				
				ResultSet rs = stmt.executeQuery("select idderecho, idempresa, fecha_reclamacion, texto_reclamacion from rgpd_reclamaciones where idusuario = " + idusr + " order by idreclamacion");
				
				out.println("<table style='float:left; border: 1px solid black; width:900px; border-collapse:collapse;'><tr>");
				out.println("<td width='150px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>DERECHO</strong></label></td>");
				out.println("<td width='200px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>EMPRESA</strong></label></td>");
				out.println("<td width='200px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>FECHA</strong></label></td>");				
				out.println("<td width='350px' style='border: 1px solid black; padding:5px; margin:0px;'><label><strong>SOLICITUD</strong></label></td></tr>");
				
				while (rs.next()) {                
					campo1 = rs.getInt(1);    // idderecho
					campo2 = rs.getInt(2);  // idempresa
					campo3 = rs.getString(3); // fecha_reclamacion
					campo4 = rs.getString(4); // texto_reclamacion					
					
					String strderecho;			        
			        // instrucción switch con tipo de datos int
			        switch (campo1) 
			        {
			            case 1:  strderecho = "Acceso";
			                     break;
			            case 2:  strderecho = "Rectificación";
			                     break;
			            case 3:  strderecho = "Oposición";
			                     break;
			            case 4:  strderecho = "Supresión";
			                     break;
			            case 5:  strderecho = "Limitación tratamiento";
			                     break;
			            case 6:  strderecho = "Portabilidad";
			                     break;
			            case 7:  strderecho = "No a decisiones automatizadas";
			                     break;
			            default: strderecho = "Acceso";
			                     break;
			        }
			        
					// Imprimir valores de columnas
					out.println("<tr><td width='150px' style='border: 1px solid black; padding:5px;'><label>" + strderecho + "</label></td>");
					
					Connection conemp = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
					Statement stmtemp = conemp.createStatement();
					
					ResultSet rsempresa = stmtemp.executeQuery("select razonsocial from rgdp_empresas where idempresa = " + campo2);
					while (rsempresa.next()) {  
						srazonsocial = rsempresa.getString(1);
						out.println("<td width='200px' style='border: 1px solid black; padding:5px;'><label>" + srazonsocial + "</label></td>");
						srazonsocial = null;
					}
					
					rsempresa.close();
					conemp.close();
					
					out.println("<td width='200px' style='border: 1px solid black; padding:5px;'><label><strong>"  + campo3 + "</strong></label></td>");
					
					//String txtSolicitud = campo4.substring(0, 10);									
					out.println("<td width='350px' style='border: 1px solid black; padding:5px;'><label>" + campo4 + "</label></td></tr>");					
				}
				rs.close(); 
				
				out.println("</table><br/><br/>&nbsp;<br/>");
				
				//Cerramos la conexión a la Base de datos
				con.close(); 	
				
			} catch (Exception e) {				
				out.println("<br>Error en la base de datos.<br/><br/>");
			}
		}
		else {
			out.println("<br/><br/><strong>ERROR:</strong> No se ha reconocido como usuario autorizado o no tiene los permisos requeridos. No tiene acceso.<br/><br/><br/><br/><br/><br/>");
		}	
		
		out.println("<br/>&nbsp;<br/><br/>&nbsp;<br/><br/>");
		out.println("<br/><br/><p style='text-align: left;'><a href='javascript:history.back();' title='VOLVER'>VOLVER</a><br/><br/>&nbsp;<br/>&nbsp;<br/><br/>");
		
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
