/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.resolvers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.transform.dom.DOMResult;
import org.xml.sax.SAXException;
import tupt.clients.CategoryClient;
import tupt.clients.ProductClient;
import tupt.clients.SupplierClient;
import tupt.constants.PathConstant;
import tupt.entities.Category;
import tupt.entities.Product;
import tupt.entities.Supplier;
import tupt.generated.Materials;
import tupt.utils.JAXBUtil;

/**
 *
 * @author sherl
 */
public class DataResolver implements Serializable {

    public void saveDomResultToDatabase(DOMResult domResult, String realPath) throws JAXBException, SAXException, UnsupportedEncodingException {
        Materials materials = new Materials();
        String xsdPath = realPath + PathConstant.CONFIG_SCHEMAS.get(1);
        materials = (Materials) JAXBUtil.unmarshal(materials.getClass(), domResult.getNode(), xsdPath);

        SupplierClient supplierClient = new SupplierClient();
        CategoryClient categoryClient = new CategoryClient();
        ProductClient productClient = new ProductClient();

        Supplier supplier = supplierClient.findByName(Supplier.class, materials.getSupplier());
        if (supplier == null) {
            supplier = new Supplier();
            supplier.setId(0);
            supplier.setName(materials.getSupplier());
            supplier.setWebsite(materials.getWebsite());

            // insert supplier to DB
            supplier = supplierClient.createSupplier_XML(supplier, Supplier.class);
            System.out.println(supplier.toString());
        }

        List<tupt.generated.Product> products = materials.getProduct();
        for (tupt.generated.Product prod : products) {

            // insert category to DB
            List<Category> listCategoryFromDB = categoryClient.findAll_XML();
            Category category = null;
            if (!listCategoryFromDB.isEmpty()) {
                for (Category cat : listCategoryFromDB) {
                    if (cat.getName().equals(prod.getCategory())) {
                        category = cat;
                        break;
                    }
                }
                if (category == null) {
                    category = new Category();
                    category.setId(0);
                    category.setName(prod.getCategory());
                    
                    category = categoryClient.createCategory_XML(category, Category.class);
                }
            } else {
                category = new Category();
                category.setId(0);
                category.setName(prod.getCategory());

                category = categoryClient.createCategory_XML(category, Category.class);
                System.out.println(category.toString());
            }

            Product product = new Product();
            product.setId(0);
            product.setName(prod.getName().toUpperCase());
            product.setCategory(category);
            product.setImageUrl(prod.getImageUrl());
            product.setSize(prod.getSize());
            product.setColor(prod.getColor());
            product.setPrice((int) prod.getPrice());
            product.setUnit(prod.getUnit());
            product.setSupplier(supplier);

            // insert product to DB
            product = productClient.createProduct_XML(product, Product.class);
            System.out.println(product.toString());
        }
    }
}
