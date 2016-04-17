/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo;

import pccatalogo.models.Product;

/**
 *
 * @author Adrian
 */
public class Database {
/**
* Devuelve todos los productos de la categoría
*
* @param cat la categoría buscada
* @return la lista de productos
*/
public static List<Product> getProductByCategory(Product.Category cat)
/**
* Busca los productos de una categoría que se encuentra en el rango de
* precios indicado
*
* @param cat la categoría buscada
* @param minPrice el precio mínimo (en euros)
* @param maxPrice el precio máximo (en euros)
* @param available si true, sólo se devuelven productos con existencias
* @return la lista de productos que cumplen los criterios
*/
public static List<Product> getProductByCategoryAndPrice(Product.Category cat,
double minPrice, double maxPrice, boolean available)
/**
* Busca los productos en una categoría cuya descripción contiene la cadena
* indicada
*
* @param cat la categoría buscada
* @param substring la subcadena a buscar
* @param available si true, sólo se devuelven productos con existencias
* @return la lista de productos que cumplen los criterios
*/
public static List<Product> getProductByCategoryAndDescription(Product.Category
cat, String substring, boolean available)
/**
* Busca los productos en una categoría cuya descripción contiene la cadena
* indicada, su precio está en el rango indicado, y tiene o no
* disponibilidad
*
* @param cat la categoría buscada
* @param substring la subcadena a buscar
* @param minPrice el precio mínimo (en euros)
* @param maxPrice el precio máximo (en euros)
* @param available si true, sólo se devuelven productos con existencias
* @return la lista de productos que cumplen los criterios
*/
public static List<Product>
getProductByCategoryDescriptionAndPrice(Product.Category cat, String substring,
double minPrice, double maxPrice, boolean available)
}
