<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:config="http://tupt0101.github.io/xml/config"
                xmlns="http://tupt0101.github.io/xsd/materials">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="config:Materials">
        <xsl:variable name="document" select="document(@dongtam_href)"/>
        <xsl:variable name="categories" select="$document//div[contains(@class, 'categories-menu-dropdown')]"/>
        
        <!-- Category name --> 
        <xsl:variable name="gachmen" select="'GẠCH MEN'"/>
        <xsl:variable name="gachbong" select="'GẠCH BÔNG'"/>
        <xsl:variable name="ngoimau" select="'NGÓI MÀU'"/>
        <xsl:variable name="thietbivesinh" select="'THIẾT BỊ VỆ SINH'"/>
        <xsl:variable name="sphotro" select="'SẢN PHẨM HỖ TRỢ'"/>
        
        <xsl:element name="Materials" xmlns="http://tupt0101.github.io/xsd/materials">
            <xsl:attribute name="supplier">Dong Tam</xsl:attribute>
            <xsl:attribute name="website">https://dongtamshop.com/</xsl:attribute>
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$gachmen"/>
                <xsl:with-param name="categoryDisplayName" select="'GẠCH MEN VA GẠCH GRANITE'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$gachbong"/>
                <xsl:with-param name="categoryDisplayName" select="'GẠCH BÔNG'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$ngoimau"/>
                <xsl:with-param name="categoryDisplayName" select="'NGÓI MÀU'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$thietbivesinh"/>
                <xsl:with-param name="categoryDisplayName" select="'THIẾT BỊ VỆ SINH'"/>
            </xsl:call-template>
            
            <xsl:call-template name="getCategoryNode">
                <xsl:with-param name="categories" select="$categories"/>
                <xsl:with-param name="categoryName" select="$sphotro"/>
                <xsl:with-param name="categoryDisplayName" select="'SẢN PHẨM HỖ TRỢ'"/>
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
            <xsl:for-each select="$categories//li[contains(@class, 'menu-item')]">
                <!--<xsl:if test="not(preceding::li[contains(@class, 'menu-item') and text() = current()/text()])">-->
                <xsl:if test="a[contains(span, $categoryName)]">
                    <xsl:element name="Link">
                        <xsl:value-of select="a/@href"/>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    
    
</xsl:stylesheet>