package APP;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.modules.XMLResource;

public class App {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		// Carga del documento xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document documento = builder.parse(new File("resources/productos.xml"));
		// Preparación de xpath
		XPath xpath = XPathFactory.newInstance().newXPath();

		boolean exit = true;
		int opcion;
		String xPathExpression, xPathExpressionAUX;
		NodeList nodos, nodoAUX;

		do {
			Funciones.menu();
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc/denominacion";
				xPathExpressionAUX = "/productos/produc/precio";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);
				nodoAUX = (NodeList) xpath.evaluate(xPathExpressionAUX, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent() + ", "
							+ nodoAUX.item(i).getNodeName() + " : " + nodoAUX.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 2:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[denominacion[contains(., 'Placa Base')]]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 3:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[precio[text() > 60] and cod_zona[text() = 20]]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 4:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[denominacion[contains(., 'Memoria')] and cod_zona[text() = 10]]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				System.out.println("Hay " + nodos.getLength() + " productos «Memoria» de la zona 10.");
				System.out.println();
				break;

			case 5:
//				sum(/productos/produc[denominacion[contains(., 'Micro')]]/precio/text()) div count(/productos/produc[denominacion[contains(., 'Micro')]])
				break;

			case 6:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[number(stock_minimo) > number(stock_actual)]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 7:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc/*[number(../stock_minimo/text()) > number(../stock_actual/text()) and ../cod_zona/text() = 40]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo 1: ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 8:
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[precio = max(/productos/produc/precio)]";
				xPathExpressionAUX = "/productos/produc/denominacion";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);
				nodoAUX = (NodeList) xpath.evaluate(xPathExpressionAUX, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodoAUX.getLength(); i++) {

					System.out.print("Nodo "+i+": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 9:
//				/productos/produc[precio = min(/productos/produc[cod_zona = 20]/precio)]
				break;

			case 10:
//				/productos/produc[precio = max(/productos/produc[cod_zona = 10]/precio)]
				break;

			case 0:
				exit = false;
				break;

			default:
				throw new IllegalArgumentException("Valor erroneo: " + opcion);
			}
		} while (exit);

		System.out.println("FIN DEL PROGRAMA");
	}
}