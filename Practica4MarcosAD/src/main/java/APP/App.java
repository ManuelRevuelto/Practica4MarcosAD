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
			menu();
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
				xPathExpression = "/productos/produc[precio = 170]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 9:
//				/productos/produc[precio = min(/productos/produc[cod_zona = 20]/precio)]
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[precio = 16]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
				break;

			case 10:
//				/productos/produc[precio = max(/productos/produc[cod_zona = 10]/precio)]
				// La expresion xpath de busqueda
				xPathExpression = "/productos/produc[precio = 10]";
				// Consultas
				nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

				for (int i = 0; i < nodos.getLength(); i++) {

					System.out.print("Nodo " + i + ": ");
					System.out.println(nodos.item(i).getNodeName() + " : " + nodos.item(i).getTextContent());

				}
				System.out.println();
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

	public static void menu() {
		System.out.println("\t************* ESCOJA LA OPCION QUE DESEA REALIZAR *************");
		System.out.println("1. Obtén los nodos denominación y precio de todos los productos.");
		System.out.println("2. Obtén los nodos de los productos que sean placas base.");
		System.out.println("3. Obtén los nodos de los productos con precio mayor de 60€ y de la zona 20.");
		System.out.println("4. Obtén el número de productos que sean memorias y de la zona 10.");
		System.out.println("5. Obtén la media de precio de los micros.");
		System.out.println("6. Obtén los datos de los productos cuyo stock mínimo sea mayor que su stock actual.");
		System.out.println(
				"7. Obtén el nombre del producto y el precio de aquellos cuyo stock mínimo sea mayor que su stock actual y sean de la zona 40.");
		System.out.println("8. Obtén el producto más caro.");
		System.out.println("9. Obtén el producto más barato de la zona 20.");
		System.out.println("10. Obtén el producto más caro de la zona 10.");
		System.out.println();
		System.out.println("0. Salir");
		System.out.print("Opcion: ");

	}
}

/*
 * case 11: ResourceIterator ri = xquery.query(
 * "for $v in distinct-values(/productos/produc/cod_zona) return ($v, count(/productos/produc[cod_zona = $v]))"
 * ) .getIterator();
 * 
 * while (ri.hasMoreResources()) { System.out.println(); XMLResource zona =
 * ((XMLResource) ri.nextResource()); System.out.print("La zona " +
 * zona.getContent()); if (ri.hasMoreResources()) { XMLResource count =
 * ((XMLResource) ri.nextResource()); System.out.println(" tiene " +
 * count.getContent() + " productos"); } } break;
 */
