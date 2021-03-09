package APP;

import java.util.Objects;
import java.util.Scanner;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

public class XPath {

	private static Scanner sc = new Scanner(System.in);
	private static Database db;
	private static XPathQueryService xpath;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			db = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
			Collection col = Objects
					.requireNonNull(db.getCollection("exist://localhost:8080/exist/xmlrpc/db/coleccion", "admin", ""));
			xpath = Objects.requireNonNull((XPathQueryService) col.getService(XPathQueryService.SERVICE_NAME, null));

			ResourceIterator ri;
			boolean exit = true;
			int opcion;
			do {
				menu();
				opcion = sc.nextInt();

				switch (opcion) {
				case 1:
					ri = xpath.query("/productos/produc/*[self::denominacion or self::precio]").getIterator();
					while (ri.hasMoreResources()) {
						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println(res.getContent());
						System.out.println();
					}
					System.out.println();
					break;

				case 2:
					ri = xpath.query("/productos/produc[denominacion[contains(., 'Placa Base')]]").getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
						System.out.println();
					}
					System.out.println();
					break;

				case 3:
					ri = xpath.query("/productos/produc[precio[text() > 60] and cod_zona[text() = 20]]").getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
						System.out.println();
					}
					System.out.println();
					break;

				case 4:
					ri = xpath.query(
							"count(/productos/produc[denominacion[contains(., 'Memoria')] and cod_zona[text() = 10]])")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println("Hay " + nodo.getContent() + " productos «Memoria» de la zona 10.");
						System.out.println();
					}
					System.out.println();
					break;

				case 5:
					ri = xpath.query(
							"sum(/productos/produc[denominacion[contains(., 'Micro')]]/precio/text()) div count(/productos/produc[denominacion[contains(., 'Micro')]])")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println("La media de los precios de los microprocesadores es " + nodo.getContent());
						System.out.println();
					}
					System.out.println();
					break;

				case 6:
					ri = xpath.query("/productos/produc[number(stock_minimo) > number(stock_actual)]").getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());

						NodeList nodosProduc = nodo.getContentAsDOM().getChildNodes();

						for (int i = 0; i < nodosProduc.getLength(); ++i) {
							Node n = nodosProduc.item(i);
							if (n.getLocalName() != null) {
								System.out.println(n.getLocalName() + " = " + n.getTextContent());
							}
						}
					}
					System.out.println();
					break;

				case 7:
					ri = xpath.query(
							"/productos/produc/*[(self::denominacion or self::precio) and number(../stock_minimo/text()) > number(../stock_actual/text()) and ../cod_zona/text() = 40]/text()")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println("nombre = " + nodo.getContent());

						if (ri.hasMoreResources()) {
							XMLResource n2 = ((XMLResource) ri.nextResource());
							System.out.println("precio = " + n2.getContent());
						}
					}
					System.out.println();
					break;

				case 8:
					ri = xpath.query("/productos/produc[precio = max(/productos/produc/precio)]").getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
					}
					System.out.println();
					break;

				case 9:
					ri = xpath.query("/productos/produc[precio = min(/productos/produc[cod_zona = 20]/precio)]")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
					}
					System.out.println();
					break;

				case 10:
					ri = xpath.query("/productos/produc[precio = max(/productos/produc[cod_zona = 10]/precio)]")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
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

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | XMLDBException e) {
			e.printStackTrace();
		}

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
