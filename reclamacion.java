package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Todos estos import son necesarios para que compilen los news correspondientes para BBDD */
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class reclamacion
 */
@WebServlet("/reclamacion")
public class reclamacion extends HttpServlet {
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
				
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reclamacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
		out.println("<br/>Seleccione uno de los derechos RGPD, la empresa qué considera infractora y sus datos de identificación.<br/><br/>Si no encuentra la empresa, puede darla de alta en el sistema seleccionando este enlace: <a href='./empresas_acciones?taccion=form&tvalidated=s'>ALTA DE NUEVA EMPRESA</a>.");			
		
		//Recuperamos los valores de la tabla de Empresas
		try {
			out.println("<form name='frmreclamacion' method='post' action='reclamacion_acciones'><input type='hidden' value='alta' name='taccion' id='taccion'>");					
			out.println("<table style='float:left; border: 0px; width:600px; border-spacing: 5px; border-collapse: separate;'>");
			
			out.println("<tr><td width='150px'><label for='derecho'>Derecho RGPD: </label><span class='obligatorio'>*</span></td><td width='450px'>");
			
			out.println("<select name='idderecho' id='idderecho'><option value='1' selected>De acceso</option><option value='2'>De rectificación</option>");
			out.println("<option value='3'>De oposición</option><option value='4'>De supresión</option><option value='5'>A la limitación del tratamiento</option>");
			out.println("<option value='6'>A la portabilidad</option><option value='7'>Exclusión de decisiones individuales automatizadas</option></select></td></tr><br/>");
			
			
			out.println("<tr><td width='150px'><label for='empresa'>Empresa: </label><span class='obligatorio'>*</span></td><td width='450px'><select name='idempresa' id='idempresa'>");
			
			//Conexión a BBDD		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rgpd", "root", "MYSQLCarixma01");				
			Statement stmt = con.createStatement();	
			
			//Recuperamos registros de la tabla Derechos				
			ResultSet rs = stmt.executeQuery("select idempresa, razonsocial from rgdp_empresas where estado_empresa = 1 order by razonsocial ASC");
			while (rs.next()) {
				out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
			}
			rs.close();
			
			out.println("</td></tr><br/><br/><br/>");
			
			//Cerramos la conexión a la Base de datos
			con.close();
			
			
			out.println("<tr><td width='150px'>&nbsp;</td><td width='450px'>&nbsp;</td></tr>");
			out.println("<tr><td width='150px'><label for='nif'>DNI/NIF: </label><span class='obligatorio'>*</span></td><td width='450px'><input type='text' name='txtdninif' id='txtdninif' placeholder='Escriba su DNI/NIF' required></td></tr>");
			//out.println("<tr><td width='150px'><label for='email'>Email: <span class='obligatorio'>*</span></label></td><td width='450px'><input type='email' name='txtemail' id='txtemail' placeholder='Escriba su Email' required></td></tr>");			
			out.println("<tr><td width='150px'><label for='email'>Contraseña: <span class='obligatorio'>*</span></label></td><td width='450px'><input type='password' name='txtspassword' id='txtspassword' placeholder='Escriba su Contraseña' required></td></tr>");
			out.println("<tr><td colspan='2'><br/><button type='submit' name='botonEnviar' id='Enviar' title='Continuar con la Reclamación'><p>CONTINUAR >></p></button>&nbsp;&nbsp;<input type='reset' NAME='botonLimpiar' VALUE='Limpiar' title='Limpiar Datos'>");
			out.println("<br/><br/><p class='aviso'><br/><span class='obligatorio'> * </span>los campos son obligatorios.<br/><br/><br/><br/></p></td></tr></table></form>");
					
			
		} catch (Exception e) {
			//System.out.println(e);
			out.println("<br>Error en la base de datos.<br/><br/>");
		}
			
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
