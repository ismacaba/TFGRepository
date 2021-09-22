package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class administrador_acciones
 */
@WebServlet("/administrador_acciones")
public class administrador_acciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Declaración de variables recogidas en el formulario
	private String dninif=null;
	private String email=null;
	private String taccion=null;
	private String svalidado = null;
	
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
    public administrador_acciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		svalidado = "no";
		
		// Adquisición de los valores del formulario a través del objeto request
		dninif=request.getParameter("txtdninif");
		email=request.getParameter("txtemail");
		
		// para controlar el menú principal una vez validado el Administrador
		taccion=request.getParameter("taccion"); 
		if (taccion.equals("V")) {
			svalidado = "si";
		}

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
		
		//Validamos los datos introducidos desde el formulario del administrador		
		if (dninif.equals("12345678A")) {
			if (email.equals("admin@icaballerotfg.com")) {
				svalidado = "si";				
		    }			
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

		out.println("<main role='main'><article><h2>Área de ADMINISTRACIÓN del Proyecto.</h2><p>");
		
		if (svalidado.equals("si")) {
			out.println("<br/>Bienvenido <strong>ADMINISTRADOR</strong>, puede acceder a las siguientes opciones:");
			out.println("<br/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href='./admin_derechos?taccion=V'>1- Administrar Derechos RGPD</a>");
			out.println("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href='./admin_empresas?taccion=V'>2- Administrar Empresas</a>");
			out.println("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href='./admin_usuarios?taccion=V'>3- Administrar Usuarios</a><br/><br/><br/><br/>");			
		}
		else {
			out.println("<br/><br/><strong>ERROR:</strong> Usuario no reconocido como administrador del sistema. No tiene acceso.<br/><br/><a href='javascript:history.back();'>Volver</a><br/><br/><br/><br/>");
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
