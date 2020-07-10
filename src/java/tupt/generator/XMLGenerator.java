/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.generator;

import tupt.constants.PathConstant;
import tupt.utils.JAXBUtil;

/**
 *
 * @author sherl
 */
public class XMLGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("----------------------------");
        System.out.println("GENERATE-PROCESS STARTS!");
        System.out.println("----------------------------");
        for (String schema : PathConstant.CONFIG_SCHEMAS_FOR_GENERATE) {
            System.out.println("Generating file " + schema + "...");
            try {
                JAXBUtil.generateClassFromSchema(schema, "");
                System.out.println("Object is generated!");
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("Error at XMLGenerator: " + e);
            }
            System.out.println("");
        }
        System.out.println("----------------------------");
        System.out.println("GENERATE-PROCESS FINISHES!");
        System.out.println("----------------------------");
    }
    
}
