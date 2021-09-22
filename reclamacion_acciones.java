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
 * Servlet implementation class reclamacion_acciones
 */
@WebServlet("/reclamacion_acciones")
public class reclamacion_acciones extends HttpServlet {
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
	private String idderecho=null;
	private String idempresa=null;
	private String txtdninif=null;
	private String txtspassword=null;
	private String taccion=null;
	//private String svalidado = null;
	
	private String campo1 = null;
	private String campo2 = null;
	private String campo3 = null;
	private String campo4 = null;
	private String campo5 = null;
	private String campo6 = null;
	private String campo7 = null;
	private String campo8 = null;	
	private Integer campo9 = null;
	
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reclamacion_acciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Adquisición de los valores del formulario a través del objeto request
		idderecho=request.getParameter("idderecho");
		idempresa=request.getParameter("idempresa");
		txtdninif=request.getParameter("txtdninif");
		txtspassword=request.getParameter("txtspassword");
		taccion=request.getParameter("taccion");
		
		//Acción correcta, se continua el proceso de alta de una reclamación
		if (taccion.equals("alta")) {
			// Devolver página HTML con Formulario con los valores adquiridos
			devolverPaginaHTMLAlta(response);
		}
		else { //Error, acción no reconocida o válida
			// Devolver al usuario una página HTML con los valores adquiridos
			devolverPaginaHTML(response);
		}
	}
	
	public void devolverPaginaHTMLAlta(HttpServletResponse resp) throws ServletException {
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		resp.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (sólo para mandar texto)
		PrintWriter out = null;
		
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");			
			//out.println("Se ha producido una excepcion en esta página.");
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
				
		out.println("<main role='main'><h2>Reclamación sobre un derecho RGPD</h2><p>");
		
		//Mostramos formulario para que lo valide y termine de completar el usuario
		out.println("<br/><br/>Por favor, verifique la información siguiente antes de continuar.");
		
		//BLOQUE DEL DERECHO
		out.println("<br/><br/><br/><strong>DERECHO RECLAMADO: </strong>");

		String strderecho;
        
        // instrucción switch con tipo de datos int
        switch (idderecho) 
        {
            case "1":  strderecho = "Derecho de acceso";
                     break;
            case "2":  strderecho = "Derecho de rectificación";
                     break;
            case "3":  strderecho = "Derecho de oposición";
                     break;
            case "4":  strderecho = "Derecho de supresión";
                     break;
            case "5":  strderecho = "Derecho a la limitación del tratamiento";
                     break;
            case "6":  strderecho = "Derecho a la portabilidad";
                     break;
            case "7":  strderecho = "Derecho a no ser objeto de decisiones individuales automatizadas";
                     break;
            default: strderecho = "Derecho de acceso";
                     break;
        }
        out.println(strderecho + "<br/><br/>");
        
        //BLOQUE DE EMPRESA
        out.println("<br/><br/><strong>DATOS DE LA EMPRESA RECLAMADA</strong>");
        
        // Hacer Recordset para recuperar datos del campo idempresa
		try {
			//Conexión a BBDD y Ejecutar Sentencias SQL		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();		
			
			//Recuperamos registros de la tabla Empresas				
			ResultSet rs = stmt.executeQuery("select cif, razonsocial, telefono1, emaildpto from rgdp_empresas where idempresa = " + idempresa);
			
			while (rs.next()) {                
				campo1 = rs.getString(1);   // CIF
				campo2 = rs.getString(2);  // Empresa/Razón Social
				campo3 = rs.getString(3); // Teléfono
				campo4 = rs.getString(4); // Email Empresa
			}
			rs.close();
			
			out.println("<br/><br/><strong>Razón Social: </strong>" + campo2);
	        out.println("<br/><br/><strong>CIF: </strong>" + campo1);
	        out.println("<br/><br/><strong>Teléfono de Contacto: </strong>" + campo3);
	        out.println("<br/><br/><strong>Email de Contacto: </strong>" + campo4);
		
			//Cerramos la conexión a la Base de datos
			con.close();
			
		} catch (Exception e) {				
			out.println("<br>Error en la base de datos de Empresas.<br/><br/>");
		}
		
		//BLOQUE DEL INTERESADO/USUARIO
        out.println("<br/><br/><br/><br/><strong>DATOS DEL INTERESADO O AFECTADO</strong>");
        
        // Hacer Recordset para recuperar datos del campo idempresa
		try {
			//Conexión a BBDD y Ejecutar Sentencias SQL		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();		
			
			//Recuperamos registros de la tabla Usuarios				
			ResultSet rs = stmt.executeQuery("select nif, nombre, apellidos, email, idusuario from rgpd_usuarios where nif = '" + txtdninif + "' and usrpassword = '" + txtspassword + "'");
			
			while (rs.next()) {                
				campo5 = rs.getString(1);   // NIF
				campo6 = rs.getString(2);  // Nombre
				campo7 = rs.getString(3); // Apellidos
				campo8 = rs.getString(4); // Email 
				campo9 = rs.getInt(5); // Id Usuario
			}
			rs.close();
			
			out.println("<br/><br/><strong>Nombre Completo: </strong>" + campo6 + " " + campo7);
	        out.println("<br/><br/><strong>DNI/NIF: </strong>" + campo5);	        
	        out.println("<br/><br/><strong>Email del Interesado: </strong>" + campo8);
		 
	        
	        out.println("<br/><br/><br/><strong>* IMPORTANTE</strong>: Puede consultar el <a href='./reclamacion_historico?taccion=V&txtdninif=" + txtdninif + "&idusr=" + campo9 + "'><strong>histórico de sus reclamaciones</strong></a>.");
	        
			//Cerramos la conexión a la Base de datos
			con.close();
			
		} catch (Exception e) {				
			out.println("<br>Error en la base de datos de Usuarios.<br/><br/>");
		}
	        
		
		//Qué se solicita
		out.println("<br/><br/><form name='frmadmin' method='post' action='reclamacion_pdf?tvalidated=s&taccion=alta'>");
		
		//Campos Hidden
		out.println("<input type='hidden' name='campo1' id='campo1' value='" + campo1 + "'>");
		out.println("<input type='hidden' name='campo2' id='campo2' value='" + campo2 + "'>");
		out.println("<input type='hidden' name='campo3' id='campo3' value='" + campo3 + "'>");
		out.println("<input type='hidden' name='campo4' id='campo4' value='" + campo4 + "'>");
		out.println("<input type='hidden' name='campo5' id='campo5' value='" + campo5 + "'>");
		out.println("<input type='hidden' name='campo6' id='campo6' value='" + campo6 + "'>");
		out.println("<input type='hidden' name='campo7' id='campo7' value='" + campo7 + "'>");
		out.println("<input type='hidden' name='campo8' id='campo8' value='" + campo8 + "'>");
		out.println("<input type='hidden' name='idusuario' id='idusuario' value='" + campo9 + "'>");
		out.println("<input type='hidden' name='idderecho' id='idderecho' value='" + idderecho + "'>");
		out.println("<input type='hidden' name='idempresa' id='idempresa' value='" + idempresa + "'>");
		out.println("<input type='hidden' name='txtdninif' id='txtdninif' value='" + txtdninif + "'>");
		
		out.println("<table style='float:left; border: 0px solid black; width:700px; border-collapse:collapse;'>");
		out.println("<tr><td style='border: 0px solid black; padding:5px; margin:0px;'><label><br/><br/><strong>QUÉ SE SOLICITA *</strong> (Escriba que desea solicitar a la empresa)</label></td></tr>");				
		out.println("<tr><td style='border: 0px solid black; padding:5px;'><textarea cols='70' rows='10' name='txtsolicita' id='txtsolicita' required></textarea></td></tr>");
		
		out.println("<tr><td style='text-align: center;'><br/><button type='submit' name='botonEnviar' id='Enviar' title='Continuar Reclamación'><p>CONTINUAR >></p></button>");
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
	
	
	public void devolverPaginaHTML(HttpServletResponse resp) throws ServletException {
		// En primer lugar se establece el tipo de contenido MIME de la respuesta
		resp.setContentType("text/html");
		// Se obtiene un PrintWriter donde escribir (sólo para mandar texto)
		PrintWriter out = null;
		
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("Se ha producido una excepcion");			
			//out.println("Se ha producido una excepcion en esta página.");
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
		
		out.println("<main role='main'><h2>Reclamación sobre un derecho RGPD</h2><p>");		
		out.println("<br/><br/><br/><strong>ERROR:</strong> Acción no reconocida o válida por esta aplicación, por favor verifique sus datos. No tiene acceso.<br/><br/><a href='javascript:history.back();'><strong>VOLVER<strong></a><br/><br/>&nbsp;<br/><br/>&nbsp;<br/><br/>");
		
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
