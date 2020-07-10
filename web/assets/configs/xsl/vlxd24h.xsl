<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:config="http://tupt0101.github.io/xml/config"
                xmlns="http://tupt0101.github.io/xsd/materials">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="config:Materials">
        <xsl:variable name="document" select="document(@vlxd24h_href)"/>
        <xsl:variable name="products" select="$document//div[contains(@class, 'productlist-content')]"/>
        <xsl:variable name="maxPage" select="$document//div[@class='PageNavigation']/a[last()-2]"/>
        <xsl:variable name="categoryName" select="$document//div[contains(@class, 'productlist-title')]/h1"/>
        
        <xsl:element name="Materials" xmlns="http://tupt0101.github.io/xsd/materials">
            <xsl:attribute name="supplier">VLXD 24h</xsl:attribute>
            <xsl:attribute name="website">http://vatlieuxaydung24h.vn/</xsl:attribute>
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

        <xsl:for-each select="$products//li">
            <xsl:variable name="name" select="div/strong/a"/>
            <xsl:variable name="image" select="div/a/img/@src"/>
            <xsl:variable name="size" select="'NG'"/>
            <xsl:variable name="color" select="'NG'"/>
            <xsl:variable name="price" select="div/div/span/em"/>
            <xsl:variable name="unit" select="div/div/span/em/span"/>
            <xsl:element name="Product" xmlns="http://tupt0101.github.io/xsd/product">
                <xsl:element name="Name">
                    <xsl:value-of select="$name"/>
                </xsl:element>
                <xsl:element name="Category">
                    <xsl:value-of select="$categoryName"/>
                </xsl:element>
                <xsl:element name="ImageUrl">
                    <xsl:value-of select="$image"/>
                </xsl:element>
                <xsl:element name="Size">
                    <xsl:value-of select="$size"/>
                </xsl:element>
                <xsl:element name="Color">
                    <xsl:value-of select="$color"/>
                </xsl:element>
                <xsl:element name="Price">
                    <xsl:choose>
                        <xsl:when test="$price=''">
                            <xsl:value-of select="'0'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="translate(substring-before($price, ' '), '.', '')"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
                <xsl:element name="Unit">
                    <xsl:choose>
                        <xsl:when test="not($unit)">
                            <xsl:value-of select="'NG'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="translate($unit, ' / ', '')"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>