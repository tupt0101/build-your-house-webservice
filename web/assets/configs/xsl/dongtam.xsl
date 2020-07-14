<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:config="http://tupt0101.github.io/xml/config"
                xmlns="http://tupt0101.github.io/xsd/materials">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="config:Materials">
        <xsl:variable name="document" select="document(@dongtam_href)"/>
        <xsl:variable name="products" select="$document//div[contains(@class, 'products elements-grid')]"/>
        <xsl:variable name="maxPage" select="$document//div[@class='products-footer']/nav/ul/li[last()-1]/a"/>
        <xsl:variable name="categoryName" select="$document//div[@class='shop-title-wrapper']/h1"/>
        
        <xsl:element name="Materials" xmlns="http://tupt0101.github.io/xsd/materials">
            <xsl:attribute name="supplier">Dong Tam</xsl:attribute>
            <xsl:attribute name="website">https://dongtamshop.com/</xsl:attribute>
            <xsl:attribute name="maxPage">
                <xsl:value-of select="$maxPage"/>
            </xsl:attribute>
            
            <xsl:call-template name="getProductNode">
                <xsl:with-param name="products" select="$products"/>
                <xsl:with-param name="categoryName" select="$categoryName"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="getProductNode">
        <xsl:param name="products"/>
        <xsl:param name="categoryName"/>

        <xsl:for-each select="$products/div[contains(@class, 'product-grid-item')]">
            <xsl:variable name="name" select="h3[@class='product-title']"/>
            <xsl:variable name="url" select="div[@class='product-element-top']/a/@href"/>
            <xsl:variable name="image" select="div/a/img/@src"/>
            <xsl:variable name="size" select="div/div[@class='dt-prod-attr'][1]/span"/>
            <xsl:variable name="color" select="div/div[@class='dt-prod-attr'][2]/span"/>
            <xsl:variable name="price" select="p[@class='price']/span"/>
            <xsl:variable name="unit" select="p[@class='price']/span/span"/>
            <xsl:element name="Product" xmlns="http://tupt0101.github.io/xsd/product">
                <xsl:element name="Name">
                    <xsl:value-of select="$name"/>
                </xsl:element>
                <xsl:element name="Url">
                    <xsl:value-of select="$url"/>
                </xsl:element>
                <xsl:element name="Category">
                    <xsl:value-of select="$categoryName"/>
                </xsl:element>
                <xsl:element name="ImageUrl">
                    <xsl:value-of select="$image"/>
                </xsl:element>
                <xsl:element name="Size">
                    <xsl:choose>
                        <xsl:when test="not($size)">
                            <xsl:value-of select="'NG'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="translate($size, ' cm', '')"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
                <xsl:element name="Color">
                    <xsl:choose>
                        <xsl:when test="not($color)">
                            <xsl:value-of select="'NG'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$color"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
                <xsl:element name="Price">
                    <xsl:value-of select="translate(substring-before($price, ' '), '.', '')"/>
                </xsl:element>
                <xsl:element name="Unit">
                    <xsl:value-of select="translate($unit, ' /', '')"/>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="normalize-price">
        <xsl:param name="price"/>
        <xsl:variable name="normalizedSpace" select="normalize-space($price)"/>
        <xsl:variable name="replaceCharacter" select="translate($normalizedSpace,' vnđ &#160;', '')"/>

        <xsl:variable name="length" select="string-length($replaceCharacter)"/>
        <xsl:variable name="removeLast3Digit" select="substring($replaceCharacter, 1, $length - 3)"/>

        <xsl:value-of select="$removeLast3Digit"/>
    </xsl:template>
</xsl:stylesheet>