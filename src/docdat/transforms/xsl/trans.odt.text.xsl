<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"  xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" >
    <xsl:output xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" method="text" version="1.0" omit-xml-declaration="yes" encoding="iso-8859-1" indent="yes"/>
    <xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="leftbracket">&lt;</xsl:variable>
    <xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="rightbracket">&gt;</xsl:variable>
    <xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="doublequote">"</xsl:variable>
    <xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="ODTOfficeNS"> xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" </xsl:variable>
    <xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="ODTTextNS"> xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" </xsl:variable>
    <xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" match="/">

        <xsl:for-each select="office:document-content/office:body/office:text">
            <xsl:for-each select="text:p">
                <xsl:value-of select="normalize-space(.)"/> <!-- remove all the double spaces etc -->
                <xsl:text>
                </xsl:text>
            </xsl:for-each>
 
            <xsl:for-each select="text:list">
                <xsl:call-template name="showLine"/>
            </xsl:for-each>

        </xsl:for-each>
        
    </xsl:template>
    <xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showLine">
        <xsl:param name="lastpos" select="1"/>

        <xsl:variable name="pos" select="position()"/>
        <xsl:choose>

            <xsl:when test="name()='text:list'">

                <xsl:for-each select="text:list-item">
                 
                    <xsl:call-template name="showLine">
                        <xsl:with-param name="lastpos">
                            <xsl:value-of select="$pos"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:when>

            <xsl:when test="name()='text:list-item'">

                <xsl:for-each select="text:p">
                    <xsl:call-template name="showLine">
                        <xsl:with-param name="lastpos">
                            <!--xsl:value-of select="$pos"/-->
                            <xsl:value-of select="concat($lastpos  ,  '.'  ,  $pos)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
                <xsl:for-each select="text:list">
                    <xsl:call-template name="showLine">
                        <xsl:with-param name="lastpos">
                            <xsl:value-of select="$lastpos"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:when>

            <xsl:when test="name()='text:p'">
                <xsl:call-template name="getODTListNumber"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="normalize-space(.)"/> <!-- remove all the double spaces etc -->
                <xsl:text>
                </xsl:text>
            </xsl:when>

            <xsl:otherwise>
                    ?-
            </xsl:otherwise>
        </xsl:choose>


    </xsl:template>
    <xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" name="getODTListNumber">
        
        <xsl:for-each select="ancestor::text:list-item">
            <xsl:if test="name()='text:list-item'">
                <xsl:number level="multiple" count="text:list-item" from="text:list"/>
                <xsl:if test="position()!=last()">
                    <xsl:text>.</xsl:text>
                </xsl:if>
            </xsl:if>
        </xsl:for-each>

    </xsl:template>
</xsl:stylesheet>