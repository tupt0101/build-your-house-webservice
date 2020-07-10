<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:config="http://tupt0101.github.io/xml/config"
                xmlns="http://tupt0101.github.io/xsd/materials">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="config:Materials">
        <xsl:variable name="document" select="document(@vlxd24h_href)"/>
        <xsl:variable name="categories" select="$document//div[contains(@class, 'cate-menu-content')]"/>
        
        <!-- Category name --> 
        <xsl:variable name="vatlieutho" select="'VẬT LIỆU THÔ'"/>
        <xsl:variable name="gachblock" select="'GẠCH BLOCK'"/>
        <xsl:variable name="bonnuoc" select="'BỒN NƯỚC INOX, BỒN NHỰA'"/>
        <xsl:variable name="vlxdkhac" select="'CÁC LOẠI VLXD KHÁC'"/>
        
        <xsl:element name="Materials" xmlns="http://tupt0101.github.io/xsd/materials">
            <xsl:attribute name="supplier">Vat lieu xay dung 24h</xsl:attribute>
            <xsl:attribute name="website">http://vatlieuxaydung24h.vn/</xsl:attribute>
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$vatlieutho"/>
                <xsl:with-param name="categoryDisplayName" select="'VẬT LIỆU THÔ'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$gachblock"/>
                <xsl:with-param name="categoryDisplayName" select="'GẠCH BLOCK'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$bonnuoc"/>
                <xsl:with-param name="categoryDisplayName" select="'BỒN NƯỚC INOX, BỒN NHỰA'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$vlxdkhac"/>
                <xsl:with-param name="categoryDisplayName" select="'CÁC LOẠI VLXD KHÁC'"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="getCategoryNode">
        <xsl:param name="categories"/>
        <xsl:param name="categoryName"/>
        <xsl:param name="categoryDisplayName"/>

        <xsl:element name="Category">
            <xsl:attribute name="name">
                <xsl:value-of select="$categoryDisplayName"/>
            </xsl:attribute>
            <xsl:for-each select="$categories//li">
                <!--<xsl:if test="not(preceding::li[contains(@class, 'menu-item') and text() = current()/text()])">-->
                <xsl:if test="a[contains(span, $categoryName)]">
                    <xsl:element name="Link">
                        <xsl:value-of select="concat('http://vatlieuxaydung24h.vn', a/@href)"/>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    
    
</xsl:stylesheet>