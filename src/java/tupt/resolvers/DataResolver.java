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
import tupt.clients.TagClient;
import tupt.clients.TagProductClient;
import tupt.constants.PathConstant;
import tupt.entities.Category;
import tupt.entities.Product;
import tupt.entities.Supplier;
import tupt.entities.Tag;
import tupt.entities.TagProduct;
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
        TagClient tagClient = new TagClient();
        TagProductClient tagProductClient = new TagProductClient();

        List<Tag> listTagFromDB = tagClient.findAll_XML();

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
            product.setUrl(prod.getUrl());
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

            // gắn tag cho product
            if (category.getName().toLowerCase().contains("gạch men")) {
                // loại gạch
                if (product.getName().toLowerCase().contains("men")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("gạch men")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setProductID(product);
                            tagProduct.setTagID(tag);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: gach men");
                        }
                    }
                } else if (product.getName().toLowerCase().contains("granite")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("gạch granite")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setProductID(product);
                            tagProduct.setTagID(tag);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: gach granite");
                        }
                    }
                }
                
                // màu trung tính
                if (product.getColor().toLowerCase().contains("đen")
                        || product.getColor().toLowerCase().contains("xám")
                        || product.getColor().toLowerCase().contains("nâu")
                        || product.getColor().toLowerCase().contains("trắng")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("trung tính")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setProductID(product);
                            tagProduct.setTagID(tag);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: trung tinh");
                        }
                    }
                    // màu tươi sáng
                } else if (product.getColor().toLowerCase().contains("kem")
                        || product.getColor().toLowerCase().contains("vàng")
                        || product.getColor().toLowerCase().contains("xanh")
                        || product.getColor().toLowerCase().contains("hồng")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("tươi sáng")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: tuoi sang");
                        }
                    }
                    // sân vườn
                }
                if (product.getColor().toLowerCase().contains("sân")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("sân vườn")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: san vuon");
                        }
                    }
                }
                if (product.getColor().toLowerCase().contains("lá")
                        || product.getColor().toLowerCase().contains("cỏ")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("cỏ")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: co");
                        }
                    }
                } else if (product.getColor().toLowerCase().contains("sỏi đá")
                        || product.getColor().toLowerCase().contains("khối đá")
                        || product.getColor().toLowerCase().contains("sỏi,")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("sỏi đá")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: soi da");
                        }
                    }
                }
                if (product.getColor().toLowerCase().contains("vân đá")
                        || product.getColor().toLowerCase().contains("vân")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("vân đá")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: van da");
                        }
                    }
                } else if (product.getColor().toLowerCase().contains("họa")
                        || product.getColor().toLowerCase().contains("hoa")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("họa tiết")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: hoa tiet");
                        }
                    }
                }
                if (product.getColor().toLowerCase().contains("kim cương")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("sang trọng")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: sang trong");
                        }
                    }
                }
                // mệnh kim
                if (product.getColor().toLowerCase().contains("trắng")
                        || product.getColor().toLowerCase().contains("ghi")
                        || product.getColor().toLowerCase().contains("xám")
                        || product.getColor().toLowerCase().contains("vàng")
                        || product.getColor().toLowerCase().contains("cam")
                        || product.getColor().toLowerCase().contains("nâu")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("mệnh kim")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: menh kim");
                        }
                    }
                }
                // mệnh mộc
                if (product.getColor().toLowerCase().contains("xanh")
                        || product.getColor().toLowerCase().contains("xanh lá")
                        || product.getColor().toLowerCase().contains("đen")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("mệnh mộc")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: menh moc");
                        }
                    }
                }
                //mệnh thủy
                if (product.getColor().toLowerCase().contains("trắng")
                        || product.getColor().toLowerCase().contains("ghi")
                        || product.getColor().toLowerCase().contains("xám")
                        || product.getColor().toLowerCase().contains("đen")
                        || product.getColor().toLowerCase().contains("xanh")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("mệnh thủy")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: menh thuy");
                        }
                    }
                }
                //mệnh hỏa
                if (product.getColor().toLowerCase().contains("đỏ")
                        || product.getColor().toLowerCase().contains("hồng")
                        || product.getColor().toLowerCase().contains("tím")
                        || product.getColor().toLowerCase().contains("xanh lá")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("mệnh hỏa")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: menh hoa");
                        }
                    }
                }
                //mệnh thổ
                if (product.getColor().toLowerCase().contains("vàng")
                        || product.getColor().toLowerCase().contains("cam")
                        || product.getColor().toLowerCase().contains("nâu")
                        || product.getColor().toLowerCase().contains("đỏ")
                        || product.getColor().toLowerCase().contains("hồng")
                        || product.getColor().toLowerCase().contains("tím")) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("mệnh thổ")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: menh tho");
                        }
                    }
                }
                String sizeStr[] = product.getSize().split("x");
                if (sizeStr[0].equals(sizeStr[1])) {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("gạch vuông")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: gach vuong");
                        }
                    }
                } else {
                    for (Tag tag : listTagFromDB) {
                        if (tag.getName().equals("gạch chữ nhật")) {
                            TagProduct tagProduct = new TagProduct();
                            tagProduct.setId(0);
                            tagProduct.setTagID(tag);
                            tagProduct.setProductID(product);

                            tagProductClient.createTagProduct_XML(tagProduct, TagProduct.class);
                            System.out.println("tag inserted: gach chu nhat");
                        }
                    }
                }
            }
        }
    }
}
