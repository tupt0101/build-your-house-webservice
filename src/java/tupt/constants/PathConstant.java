/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.constants;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sherl
 */
public class PathConstant {

    public static final String GENERATED_PACKAGE_NAME = "tupt.generated";

    public static final String CONFIG_XML = "assets/configs/xml/materials.xml";
    
    public static final String CONFIG_XSL_VLXD24H_CATEGORY = "assets/configs/xsl/vlxd24h_category.xsl";
    public static final String CONFIG_XSL_DONGTAM_CATEGORY = "assets/configs/xsl/dongtam_category.xsl";
    
    public static final String CONFIG_XSL_VLXD24H = "assets/configs/xsl/vlxd24h.xsl";
    public static final String CONFIG_XSL_DONGTAM = "assets/configs/xsl/dongtam.xsl";

    public static final List<String> CONFIG_SCHEMAS_FOR_GENERATE = Arrays.asList(
            "web/assets/configs/xsd/product.xsd",
//            "web/assets/configs/xsd/category.xsd",
            "web/assets/configs/xsd/materials.xsd"
    );
    
    public static final List<String> CONFIG_SCHEMAS = Arrays.asList(
            "assets/configs/xsd/product.xsd",
//            "assets/configs/xsd/category.xsd",
            "assets/configs/xsd/materials.xsd"
    );

    public static final List<String> CONFIG_XSL = Arrays.asList(
            CONFIG_XSL_VLXD24H,
            CONFIG_XSL_DONGTAM
    );
    
    public static final List<String> CONFIG_XSL_CATE = Arrays.asList(
            CONFIG_XSL_VLXD24H_CATEGORY,
            CONFIG_XSL_DONGTAM_CATEGORY
    );

    public static final List<String> CONFIG_HREF = Arrays.asList(
            "vlxd24h_href",
            "dongtam_href"
    );
}
