package APP;

import java.util.Objects;
import java.util.Scanner;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

public class XQuery {
	private static Scanner sc = new Scanner(System.in);
	private static Database db;
	private static XQueryService xquery;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			db = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
			Collection col = Objects
					.requireNonNull(db.getCollection("exist://localhost:8080/exist/xmlrpc/db/coleccion", "admin", ""));
			xquery = Objects.requireNonNull((XQueryService) col.getService(XQueryService.SERVICE_NAME, null));

			ResourceIterator ri;
			boolean exit = true;
			int opcion;
			do {
				menu();
				opcion = sc.nextInt();

				switch (opcion) {
				case 1:
					ri = xquery.query(
							"for $v in distinct-values(/productos/produc/cod_zona) return ($v, count(/productos/produc[cod_zona = $v]))")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource zona = ((XMLResource) ri.nextResource());
						System.out.print("La zona " + zona.getContent());
						if (ri.hasMoreResources()) {
							XMLResource count = ((XMLResource) ri.nextResource());
							System.out.println(" tiene " + count.getContent() + " productos");
						}
					}

					ri = xquery.query(
							"for $suc in /sucursales/sucursal return (data($suc/@codigo), count($suc/cuenta[data(@tipo)='AHORRO']), count($suc/cuenta[data(@tipo)='PENSIONES']))")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.print("La sucursal " + nodo.getContent());

						if (ri.hasMoreResources()) {
							XMLResource n2 = (XMLResource) ri.nextResource();
							System.out.print(" tiene " + n2.getContent() + " cuentas tipo AHORRO y ");
						}

						if (ri.hasMoreResources()) {
							XMLResource n2 = (XMLResource) ri.nextResource();
							System.out.println(n2.getContent() + " cuentas tipo PENSIONES");
						}
					}
					System.out.println();
					break;

				case 2:
					ri = xquery.query(
							"for $v in distinct-values(/productos/produc/cod_zona) return element{ 'zona' || $v }{ /productos/produc[cod_zona = $v]/denominacion }")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
					}
					System.out.println();
					break;

				case 3:
					ri = xquery.query(
							"for $v in distinct-values(/productos/produc/cod_zona) return ($v, /productos/produc[precio = max(/productos/produc[cod_zona = $v]/precio)]/denominacion/text())")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.print("En la zona " + nodo.getContent());
						if (ri.hasMoreResources()) {
							XMLResource den = ((XMLResource) ri.nextResource());
							System.out.println(", el producto más caro es " + den.getContent());
						}
					}
					System.out.println();
					break;

				case 4:
					ri = xquery.query("(<placa>{/productos/produc/denominacion[contains(., 'Placa Base')]}</placa>,"
							+ "<micro>{/productos/produc/denominacion[contains(., 'Micro')]}</micro>,"
							+ "<memoria>{/productos/produc/denominacion[contains(., 'Memoria')]}</memoria>,"
							+ "<otros>{/productos/produc/denominacion[not(contains(., 'Memoria') or contains(., 'Micro') or contains(., 'Placa Base'))]}</otros>)")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.println(nodo.getContent());
					}
					System.out.println();
					break;

				case 5:
					ri = xquery.query(
							"/sucursales/sucursal/cuenta[data(@tipo) = 'PENSIONES' and aportacion = max(/sucursales/sucursal/cuenta/aportacion)]")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();

						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println(res.getContent());
					}
					System.out.println();
					break;

				case 6:
					ri = xquery.query(
							"for $suc in /sucursales/sucursal return (data($suc/@codigo), count($suc/cuenta[data(@tipo)='AHORRO']), count($suc/cuenta[data(@tipo)='PENSIONES']))")
							.getIterator();
					while (ri.hasMoreResources()) {
						System.out.println();
						XMLResource nodo = ((XMLResource) ri.nextResource());
						System.out.print("La sucursal " + nodo.getContent());

						if (ri.hasMoreResources()) {
							XMLResource n2 = (XMLResource) ri.nextResource();
							System.out.print(" tiene " + n2.getContent() + " cuentas tipo AHORRO y ");
						}

						if (ri.hasMoreResources()) {
							XMLResource n2 = (XMLResource) ri.nextResource();
							System.out.println(n2.getContent() + " cuentas tipo PENSIONES");
						}
					}
					System.out.println();
					break;

				case 7:
					ri = xquery.query(
							"for $suc in /sucursales/sucursal return (data($suc/@codigo), $suc/director/text(), $suc/poblacion/text(), sum($suc/cuenta/saldodebe), sum($suc/cuenta/saldohaber))")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();

						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println("Código: " + res.getContent());

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Director: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Población: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Total saldodebe: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Total saldohaber: " + res.getContent());
						}
					}
					System.out.println();
					break;

				case 8:
					ri = xquery.query(
							"for $suc in /sucursales/sucursal[count(cuenta) > 3] return (data($suc/@codigo), $suc/director/text(), $suc/poblacion/text())")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();

						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println("Código: " + res.getContent());

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Director: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Población: " + res.getContent());
						}
					}
					System.out.println();
					break;

				case 9:
					ri = xquery.query(
							"for $suc in /sucursales/sucursal return (data($suc/@codigo), $suc/cuenta[saldodebe = max($suc/cuenta/saldodebe)]/*/text())")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();

						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println("Cuenta con más saldodebe de la sucursal " + res.getContent());

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Nombre: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Número: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Saldohaber: " + res.getContent());
						}

						if (ri.hasMoreResources()) {
							res = (XMLResource) ri.nextResource();
							System.out.println("Saldodebe: " + res.getContent());
						}
					}
					System.out.println();
					break;

				case 10:
					ri = xquery.query(
							"/sucursales/sucursal/cuenta[data(@tipo) = 'PENSIONES' and aportacion = max(/sucursales/sucursal/cuenta/aportacion)]")
							.getIterator();

					while (ri.hasMoreResources()) {
						System.out.println();

						XMLResource res = (XMLResource) ri.nextResource();
						System.out.println(res.getContent());
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
