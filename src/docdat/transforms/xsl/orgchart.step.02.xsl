<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="leftbracket">&lt;</xsl:variable>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="rightbracket">&gt;</xsl:variable>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="doublequote">"</xsl:variable>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="default_minxoffset" select="50"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="default_minyoffset" select="25"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="orgx" select="100"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="orgy" select="100"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="offsetWorkstationX" select="100"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="txtXoffset" select="20"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="zoneoffset" select="600"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationWidth" select="150"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationHeight" select="50"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationWidth10th" select="$workstationWidth div 10"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationWidth50th" select="$workstationWidth div 2"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationHeight50th" select="$workstationHeight div 2"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="workstationspacing" select="20"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="portWidth" select="20"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="portHeight" select="20"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="portWidth50th" select="$portWidth div 2"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="diagramRows">
    <xsl:value-of select="count(//*[@id and ( name()='zone' or name='firewall' or name()='system')])"/>
  </xsl:variable>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="diagramSpace" select="100"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="detailOrgY" select="$orgy+((4+$diagramRows)*$workstationHeight)+$diagramSpace"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="default_ox" select="10"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="default_oy" select="50"/>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="default_height" select="50"/>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" match="/">
        <xsl:apply-templates select="chart"/>
    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" match="chart">

        <xsl:element name="svg">
            <xsl:attribute name="width">100%</xsl:attribute>
            <xsl:attribute name="height">100%</xsl:attribute>
            <xsl:attribute name="version">1.1</xsl:attribute>
            <xsl:attribute name="zoomAndPan">magnify</xsl:attribute>

            <xsl:call-template name="showStemLayer"/>
            <xsl:call-template name="showLeafLayer"/>
      <!--xsl:call-template name="showTestOrder" /-->

        </xsl:element>
 
    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showTestOrder">
        <xsl:for-each select="leaf">
            <item>
                <xsl:value-of select="@id"/>:
                <xsl:value-of select="name()"/>
            </item>
        </xsl:for-each>
    </xsl:template>
<xsl:variable xmlns:xsl="http://www.w3.org/1999/XSL/Transform" name="isVertical">
        <xsl:choose>
            <xsl:when test="//leaf[@id='0' and @orientation='vertical']">
                <xsl:value-of select="'true'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'false'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="confirmId">
        <xsl:param name="id"/>
        <xsl:variable name="cnt" select="count(/chart//*[@id=$id])"/>
        <xsl:choose>
            <xsl:when test="$cnt != 1">
                <xsl:value-of select="$id"/>-
                <xsl:value-of select="$cnt"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$id"/>
            </xsl:otherwise>
        </xsl:choose>


    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="showCircle">
        <xsl:param name="cx" select="0"/>
        <xsl:param name="cy" select="0"/>
        <xsl:param name="radius" select="10"/>
        <xsl:param name="stroke" select="'black'"/>
        <xsl:param name="stroke-width" select="1"/>
        <xsl:param name="fill" select="'red'"/>

        <circle>
            <xsl:attribute name="cx">
                <xsl:value-of select="$cx"/>
            </xsl:attribute>
            <xsl:attribute name="cy">
                <xsl:value-of select="$cy"/>
            </xsl:attribute>
            <xsl:attribute name="r">
                <xsl:value-of select="$radius"/>
            </xsl:attribute>
            <xsl:attribute name="stroke">
                <xsl:value-of select="$stroke"/>
            </xsl:attribute>
            <xsl:attribute name="stroke-width">
                <xsl:value-of select="$stroke-width"/>
            </xsl:attribute>
            <xsl:attribute name="fill">
                <xsl:value-of select="$fill"/>
            </xsl:attribute>
        </circle>



    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showConnector">
        <xsl:param name="ox"/>
        <xsl:param name="oy"/>
        <xsl:param name="cx" select="0"/>
        <xsl:param name="cy" select="0"/>

        <xsl:param name="minXOffset" select="25"/>
        <xsl:param name="minYOffset" select="25"/>


        <xsl:variable name="half-height" select="@h div 2"/>
        <xsl:variable name="half-height-parent" select="../@h div 2"/>
        <xsl:variable name="half-width" select="@w div 2"/>
<!-- draw from child to parent -->
        <xsl:choose>

            <xsl:when test="name()='leaf'">
                <!-- top of child  -->
                <xsl:variable name="l_x1">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="@x - $half-width"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@x"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>

                <!-- top of child  -->
                <xsl:variable name="l_y1">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="@y"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@y - $half-height"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>


                <!-- bottom of parent-->
                <xsl:variable name="l_x2">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="preceding-sibling::*[1]/@x + (following-sibling::*/@w div 2)"/>
                        </xsl:when>

                        <xsl:when test="@child='0'">
<!-- left child -->
                            <xsl:value-of select="../@x"/>
                        </xsl:when>

                        <xsl:when test="@child != '0'">
<!-- right child -->
                            <xsl:choose>

                                <xsl:when test="../@symbol-type='condition'">
                                    <xsl:value-of select="../@x"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="preceding-sibling::*[1]/@x"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>

                        <xsl:otherwise>

                            <xsl:value-of select="preceding-sibling::*[1]/@x"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <!-- bottom of parent-->
                <xsl:variable name="l_y2">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="preceding-sibling::*[1]/@y"/>
                        </xsl:when>

                        <xsl:when test="@child='0'">
                            <xsl:value-of select="../@y + $half-height-parent"/>
                        </xsl:when>

                        <xsl:when test="@child != '0'">

                            <xsl:choose>
                                <xsl:when test="../@symbol-type='condition'">
                                    <xsl:value-of select="../@y + $half-height-parent"/> <!-- needst to be parents half height -->
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="preceding-sibling::*[1]/@y + (preceding-sibling::*[1]/@h div 2 )"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>

                        <!-- xsl:when test="../@symbol-type='condition'">
                            <xsl:value-of select="../@y + $half-height-parent" />
                        </xsl:when -->

                        <xsl:otherwise>
                            <!-- xsl:value-of select="../@y + (../@h div 2 )" / -->
                            <xsl:value-of select="preceding-sibling::*[1]/@y + (preceding-sibling::*[1]/@h div 2 )"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>


                <xsl:if test="parent::leaf[@id]">

                    <xsl:choose>
                        <xsl:when test="@element-type='stub'">
             <!-- stem line -->
                            <xsl:call-template name="showTick">
                                <xsl:with-param name="x1" select="@x"/>
                                <xsl:with-param name="y1" select="@y + $half-height"/>
                                <xsl:with-param name="x2" select="$l_x2"/>
                                <xsl:with-param name="y2" select="$l_y2"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
             <!-- stem line -->
                            <xsl:call-template name="showTick">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1"/>
                                <xsl:with-param name="x2" select="$l_x2"/>
                                <xsl:with-param name="y2" select="$l_y2"/>
                            </xsl:call-template>
          <!--  destination pointer -->


                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:if>

                <xsl:if test="@to">


                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">



                            <xsl:call-template name="showLineTo">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1 + @h"/>
                                <xsl:with-param name="id" select="@to"/>
                                <xsl:with-param name="color" select="'#ff0000'"/>
                            </xsl:call-template>


                        </xsl:when>
                        <xsl:otherwise>

                            <xsl:call-template name="showLineTo">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1 + @h"/>
                                <xsl:with-param name="id" select="@to"/>
                                <xsl:with-param name="color" select="'#ff0000'"/>
                            </xsl:call-template>

                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>


          <!-- bottom -->
                <xsl:if test="child::leaf[@id]">
                    <xsl:if test="@element-type!='stub'">


                        <xsl:choose>
                            <xsl:when test="$isVertical='true'">

                                <xsl:call-template name="showCircle">
                                    <xsl:with-param name="cx" select="@x + $half-width"/>
                                    <xsl:with-param name="cy" select="@y"/>
                                    <xsl:with-param name="radius" select="3"/>
                                    <xsl:with-param name="fill" select="'#00ff00'"/>
                                </xsl:call-template>

                            </xsl:when>
                            <xsl:otherwise>
                                <!-- top pointer -->
                                <xsl:call-template name="showPointerSymbol">
                                    <xsl:with-param name="cx" select="@x"/>
                                    <xsl:with-param name="cy" select="@y - $half-height"/>
                                    
                                    <xsl:with-param name="radius" select="3"/>
                                    <xsl:with-param name="direction" select="'down'"/>
                                    <xsl:with-param name="fill" select="'#00ff00'"/>
                                </xsl:call-template>

                            </xsl:otherwise>
                        </xsl:choose>



                    </xsl:if>

                </xsl:if>
            </xsl:when>

            <xsl:otherwise>

            </xsl:otherwise>

        </xsl:choose>
    <!--
      Show Org Chart
      or show all symbols
      -->
        <xsl:for-each select="leaf">
            <xsl:call-template name="showConnector">

                <xsl:with-param name="ox" select="$ox"/>
                <xsl:with-param name="oy" select="$oy"/>
                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="minXOffset" select="$minXOffset"/>
                <xsl:with-param name="minYOffset" select="$minYOffset"/>


            </xsl:call-template>

        </xsl:for-each>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showConnectors">
        <xsl:variable name="l_symbol" select="'stem'"/>

        <xsl:for-each select="leaf">

            <xsl:call-template name="showConnector">
                <xsl:with-param name="ox" select="@x"/>
                <xsl:with-param name="oy" select="@y"/>

                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="minXOffset" select="@minxoffset"/>
                <xsl:with-param name="minYOffset" select="@minyoffset"/>

            </xsl:call-template>

        </xsl:for-each>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showDiamond">
        <xsl:param name="cx" select="0"/>
        <xsl:param name="cy" select="0"/>
        <xsl:param name="radius" select="10"/>
        <xsl:param name="stroke" select="'#000099'"/>
        <xsl:param name="stroke-width" select="1"/>
        <xsl:param name="fill" select="'#000099'"/>
        <xsl:param name="fill-opacity" select="0.1"/>

        <polygon>
            <xsl:attribute name="cx">
                <xsl:value-of select="$cx"/>
            </xsl:attribute>
            <xsl:attribute name="cy">
                <xsl:value-of select="$cy"/>
            </xsl:attribute>

            <xsl:attribute name="stroke">
                <xsl:value-of select="$stroke"/>
            </xsl:attribute>
            <xsl:attribute name="stroke-width">
                <xsl:value-of select="$stroke-width"/>
            </xsl:attribute>
            <xsl:attribute name="fill">
                <xsl:value-of select="$fill"/>
            </xsl:attribute>
            
            <xsl:attribute name="fill-opacity">
                <xsl:value-of select="$fill-opacity"/>
            </xsl:attribute>
           <xsl:attribute name="points">
                <xsl:value-of select="$cx"/>,<xsl:value-of select="$cy - $radius"/> <xsl:value-of select="' '"/>
                <xsl:value-of select="$cx + $radius"/>,<xsl:value-of select="$cy"/> <xsl:value-of select="' '"/>
                <xsl:value-of select="$cx"/>,<xsl:value-of select="$cy + $radius"/> <xsl:value-of select="' '"/>
                <xsl:value-of select="$cx - $radius"/>,<xsl:value-of select="$cy"/> 
                
            </xsl:attribute>
        </polygon>






    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showLeafLayer">
        <xsl:variable name="l_symbol" select="'leaf'"/>

        <xsl:for-each select="leaf">



      <!-- then start reading through hierarchy -->
            <xsl:call-template name="showSymbols">
                <xsl:with-param name="symbol" select="$l_symbol"/>
                <xsl:with-param name="ox" select="@x"/>
                <xsl:with-param name="oy" select="@y"/>
      <!-- xsl:with-param name="cx" select="$maxwidth"/ -->
                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="minXOffset" select="@minxoffset"/>
                <xsl:with-param name="minYOffset" select="@minyoffset"/>
      <!--xsl:with-param name="maxwidth" select="$maxwidth"/ -->

            </xsl:call-template>

        </xsl:for-each>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showLineTo">
        <xsl:param name="x1"/>
        <xsl:param name="y1"/>
        <xsl:param name="id"/>
        <xsl:param name="color" select="'#00ff00'"/>

        <xsl:for-each select="//*[@id=$id or @name=$id]">
            <xsl:variable name="h" select="@height div 2"/>
            <xsl:call-template name="showTick">
                <xsl:with-param name="x1" select="$x1"/>
                <xsl:with-param name="y1" select="$y1"/>
                <xsl:with-param name="x2" select="@x"/>
                <xsl:with-param name="y2" select="@y - $h"/>
                <xsl:with-param name="color" select="$color"/>
            </xsl:call-template>

            <xsl:call-template name="showCircle">
                <xsl:with-param name="cx" select="$x1"/>
                <xsl:with-param name="cy" select="$y1"/>
                <xsl:with-param name="radius" select="3"/>
                <xsl:with-param name="fill" select="'#00ff00'"/>
            </xsl:call-template>

        </xsl:for-each>

    <!-- path id="quadcurveABC" d="M 100 350 q 150 -300 300 0" stroke="blue" stroke-width="1" fill="none"/ -->


    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="showPointerSymbol">
        <xsl:param name="cx" select="0"/>
        <xsl:param name="cy" select="0"/>
        <xsl:param name="symbol" select="'pointer'"/>
        <xsl:param name="direction" select="'right'"/>
        <xsl:param name="radius" select="10"/>
        <xsl:param name="stroke" select="'black'"/>
        <xsl:param name="stroke-width" select="1"/>
        <xsl:param name="fill" select="'red'"/>

        <xsl:choose>
            <xsl:when test="$symbol='pointer' and $direction='down'">
                <polygon>
                    <xsl:attribute name="points">
                        <xsl:value-of select="$cx - $radius"/>,
                        <xsl:value-of select="$cy - $radius"/>,
                        <xsl:value-of select="$cx + $radius"/>,
                        <xsl:value-of select="$cy - $radius"/>,
                        <xsl:value-of select="$cx"/>,
                        <xsl:value-of select="$cy + $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">fill:
                        <xsl:value-of select="$fill"/>;stroke:
                        <xsl:value-of select="$stroke"/>;stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </polygon>
            </xsl:when>
            <xsl:when test="$symbol='pointer' and $direction='right'">
                <polygon>
                    <xsl:attribute name="points">
                        <xsl:value-of select="$cx+$radius"/>,
                        <xsl:value-of select="$cy"/>,
                        <xsl:value-of select="$cx - $radius"/>,
                        <xsl:value-of select="$cy+$radius"/>,
                        <xsl:value-of select="$cx - $radius"/>,
                        <xsl:value-of select="$cy - $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">fill:
                        <xsl:value-of select="$fill"/>;stroke:
                        <xsl:value-of select="$stroke"/>;stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </polygon>
            </xsl:when>

            <xsl:when test="$symbol='pointer' and $direction='left'">
                <polygon>
                    <xsl:attribute name="points">
                        <xsl:value-of select="$cx - $radius"/>,
                        <xsl:value-of select="$cy"/>,
                        <xsl:value-of select="$cx + $radius"/>,
                        <xsl:value-of select="$cy + $radius"/>,
                        <xsl:value-of select="$cx + $radius"/>,
                        <xsl:value-of select="$cy - $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">fill:
                        <xsl:value-of select="$fill"/>;stroke:
                        <xsl:value-of select="$stroke"/>;stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </polygon>
            </xsl:when>



            <xsl:when test="$symbol='terminate' and $direction='right'">

                <xsl:element name="line">
                    <xsl:attribute name="x1">
                        <xsl:value-of select="$cx - $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="y1">
                        <xsl:value-of select="$cy - ($radius * 2)"/>
                    </xsl:attribute>
                    <xsl:attribute name="x2">
                        <xsl:value-of select="$cx - $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="y2">
                        <xsl:value-of select="$cy + ($radius * 2)"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">stroke:rgb(99,99,99);stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </xsl:element>

                <xsl:element name="line">
                    <xsl:attribute name="x1">
                        <xsl:value-of select="$cx"/>
                    </xsl:attribute>
                    <xsl:attribute name="y1">
                        <xsl:value-of select="$cy - $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="x2">
                        <xsl:value-of select="$cx"/>
                    </xsl:attribute>
                    <xsl:attribute name="y2">
                        <xsl:value-of select="$cy + $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">stroke:rgb(99,99,99);stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </xsl:element>

                <xsl:element name="line">
                    <xsl:attribute name="x1">
                        <xsl:value-of select="$cx + $radius"/>
                    </xsl:attribute>
                    <xsl:attribute name="y1">
                        <xsl:value-of select="$cy"/>
                    </xsl:attribute>
                    <xsl:attribute name="x2">
                        <xsl:value-of select="$cx + $radius + 1"/>
                    </xsl:attribute>
                    <xsl:attribute name="y2">
                        <xsl:value-of select="$cy"/>
                    </xsl:attribute>
                    <xsl:attribute name="style">stroke:rgb(99,99,99);stroke-width:
                        <xsl:value-of select="$stroke-width"/>
                    </xsl:attribute>
                </xsl:element>

            </xsl:when>
        </xsl:choose>



    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showPointerSymbolById">
        <xsl:param name="id"/>
        <xsl:for-each select="/chart//*[@id=$id]">
            <xsl:call-template name="showPointerSymbol">

                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="symbol" select="'pointer'"/>
                <xsl:with-param name="direction" select="'right'"/>
                <xsl:with-param name="radius" select="10"/>
                <xsl:with-param name="stroke" select="'black'"/>
                <xsl:with-param name="stroke-width" select="1"/>
                <xsl:with-param name="fill" select="'red'"/>
            </xsl:call-template>
        </xsl:for-each>


    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showRect">
        <xsl:param name="x" select="0"/>
        <xsl:param name="y" select="0"/>
        <xsl:param name="txt" select="'label'"/>
        <xsl:param name="txt-lines" select="''"/>
        <xsl:param name="w" select="20"/>
        <xsl:param name="h" select="20"/>
        <xsl:param name="rx" select="0"/>
        <xsl:param name="ry" select="0"/>
        <xsl:param name="textrotate" select="0"/>
        <xsl:param name="font-size" select="10"/>
        <xsl:param name="fill" select="'#ffffff'"/>
        <xsl:param name="stroke" select="'#00ff00'"/>
        <xsl:param name="stroke-width" select="1"/>
        <xsl:param name="align" select="'left'"/>


        <xsl:variable name="txt-height" select="$font-size"/>
        <xsl:variable name="txt-width" select="$font-size"/>

        <xsl:variable name="half-height" select="$h div 2"/>
        <xsl:variable name="half-width" select="$w div 2"/>

        <xsl:variable name="cx" select="$x - $half-width"/>
        <xsl:variable name="cy" select="$y - $half-height"/>

        <xsl:variable name="tx">
            <xsl:choose>
                <xsl:when test="$align = 'center'">
                    <xsl:value-of select="$cx "/>
                </xsl:when>
                <xsl:when test="$align = 'left'">
                    <xsl:value-of select="$cx + (1.5 * $txt-width)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="0"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="ty">
            <xsl:choose>
                <xsl:when test="$align = 'center'">
                    <xsl:value-of select="$cy"/>
                </xsl:when>
                <xsl:when test="$align = 'left'">
                    <xsl:value-of select="($cy)+ (1.5 * $txt-height) "/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="0"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>


        <xsl:element name="rect">
            <xsl:attribute name="x">
                <xsl:value-of select="$cx"/>
            </xsl:attribute>
            <xsl:attribute name="y">
                <xsl:value-of select="$cy"/>
            </xsl:attribute>
            <xsl:attribute name="rx">
                <xsl:value-of select="$rx"/>
            </xsl:attribute>
            <xsl:attribute name="ry">
                <xsl:value-of select="$ry"/>
            </xsl:attribute>
            <xsl:attribute name="width">
                <xsl:value-of select="$w"/>
            </xsl:attribute>
            <xsl:attribute name="height">
                <xsl:value-of select="$h"/>
            </xsl:attribute>

            <xsl:attribute name="style">fill:
                <xsl:value-of select="$fill"/>;stroke-width:
                <xsl:value-of select="$stroke-width"/>;fill-opacity:0.1;stroke:
                <xsl:value-of select="$stroke"/>
            </xsl:attribute>
        </xsl:element>

    <!-- xsl:if test="$txt != ''">

      <xsl:call-template name="showText">
        <xsl:with-param name="x" select="$tx"/>
        <xsl:with-param name="y" select="$ty"/>
        <xsl:with-param name="txt" select="$txt"/>
        <xsl:with-param name="textrotate" select="$textrotate"/>
        <xsl:with-param name="font-size" select="$font-size"/>
      </xsl:call-template>

    </xsl:if -->
    <!--
      txt-desc is set of line tags from a description
    -->
        <xsl:if test="$txt-lines != ''">
            <xsl:for-each select="exsl:node-set($txt-lines)/description/line">

                <xsl:call-template name="showText">
                    <xsl:with-param name="x" select="$tx"/>
                    <xsl:with-param name="y" select="$ty + ((position () - 1  ) * (1.5 * $txt-height))"/>

                    <xsl:with-param name="txt" select="text()"/>
                    <xsl:with-param name="textrotate" select="$textrotate"/>
                    <xsl:with-param name="font-size" select="$font-size"/>
                </xsl:call-template>

            </xsl:for-each>
        </xsl:if>
    <!-- xsl:if test="$txt-lines != ''">
      <xsl:for-each select="exsl:node-set($txt-lines)/description/line">

        <xsl:call-template name="showText">
          <xsl:with-param name="x" select="$tx"/>
          <xsl:with-param name="y" select="$ty + ((position ()  ) * (1.5 * $txt-height))"/>

          <xsl:with-param name="txt" select="text()"/>
          <xsl:with-param name="textrotate" select="$textrotate"/>
          <xsl:with-param name="font-size" select="$font-size"/>
        </xsl:call-template>

      </xsl:for-each>
    </xsl:if -->
    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showStemLayer">
        <xsl:variable name="l_symbol" select="'stem'"/>

        <xsl:for-each select="leaf">

    <!-- xsl:variable name="maxwidth">
      <xsl:call-template name="getActualWidth">
        <xsl:with-param name="id" select="@id"/>
      </xsl:call-template>
    </xsl:variable -->

            <xsl:call-template name="showSymbols">
                <xsl:with-param name="symbol" select="$l_symbol"/>
                <xsl:with-param name="ox" select="@x"/>
                <xsl:with-param name="oy" select="@y"/>
      <!--xsl:with-param name="cx" select="$maxwidth"/ -->
                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="minXOffset" select="@minxoffset"/>
                <xsl:with-param name="minYOffset" select="@minyoffset"/>
      <!--xsl:with-param name="maxwidth" select="$maxwidth"/ -->

            </xsl:call-template>

        </xsl:for-each>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showStep">
        <xsl:param name="cx"/>
        <xsl:param name="cy"/>
        <xsl:param name="w" select="0"/>
        <xsl:param name="h" select="0"/>
        <xsl:param name="rx" select="10"/>
        <xsl:param name="ry" select="10"/>
        <xsl:param name="txt" select="''"/>
        <xsl:param name="txt-lines" select="''"/>
        <xsl:param name="textrotate" select="0"/>
        <xsl:param name="font-size" select="10"/>
        <xsl:param name="fill" select="'#cccc99'"/>
        <xsl:param name="element-type" select="''"/>
        <xsl:param name="stroke" select="'#ff0000'"/>
        <xsl:choose>
            <xsl:when test="$element-type='stub'">
                    <!-- stubs are placeholders that act as spacers -->
                <xsl:call-template name="showCircle">
                    <xsl:with-param name="cx" select="$cx"/>
                    <xsl:with-param name="cy" select="$cy"/>
                    <xsl:with-param name="radius" select="3"/>
                    <xsl:with-param name="stroke" select="'black'"/>
                    <xsl:with-param name="stroke-width" select="1"/>
                    <xsl:with-param name="fill" select="'#cccccc'"/>
                </xsl:call-template>
            </xsl:when>

            <xsl:otherwise>
                <xsl:call-template name="showRect">
                    <xsl:with-param name="x" select="$cx"/>
                    <xsl:with-param name="y" select="$cy"/>
                    <xsl:with-param name="w" select="$w"/>
                    <xsl:with-param name="h" select="$h"/>
                    <xsl:with-param name="rx" select="$rx"/>
                    <xsl:with-param name="ry" select="$ry"/>
                    <xsl:with-param name="txt" select="$txt"/>
                    <xsl:with-param name="txt-lines" select="$txt-lines"/>
                    <xsl:with-param name="font-size" select="$font-size"/>
                    <xsl:with-param name="textrotate" select="$textrotate"/>
                    <xsl:with-param name="fill" select="$fill"/>
                    <xsl:with-param name="stroke" select="$stroke"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>



    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" xmlns:exsl="http://exslt.org/common" name="showSymbols">
    <!-- xsl:param name="level" select="1"/ -->
    <!-- xsl:param name="pos" select="1" / -->
        <xsl:param name="symbol" select="'leaf'"/>
        <xsl:param name="ox"/>
        <xsl:param name="oy"/>
        <xsl:param name="cx" select="0"/>
        <xsl:param name="cy" select="0"/>

        <xsl:param name="minXOffset" select="25"/>
        <xsl:param name="minYOffset" select="25"/>

        <xsl:param name="maxlevels"/>
  <!-- xsl:param name="maxwidth"/ -->

        <xsl:variable name="half-height" select="@h div 2"/>
        <xsl:variable name="half-width" select="@w div 2"/>

        <xsl:variable name="statusStroke">
            <xsl:choose>
        <!-- xsl:when test="@status='complete' or @status='completed' or @status='finished' or @status='resolved' or @status='inactive' or @status='false' or @status='no' or @status='done'  "-->
                <xsl:when test="@status='complete' ">
                    <xsl:value-of select="'#cccccc'"/>
                </xsl:when>
                <xsl:when test="@status != 'complete' ">
                    <xsl:value-of select="'#000099'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'#cccc99'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
    <!-- xsl:variable name="desc-lines">
      <xsl:choose>
        <xsl:when test="description">
          <xsl:copy-of select="description"/>
        </xsl:when>
        <xsl:otherwise>
          <description>
            <line/>
          </description>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable -->

        <xsl:variable name="txt">
            <xsl:call-template name="confirmId">
                <xsl:with-param name="id">
                    <xsl:value-of select="@id"/>
                </xsl:with-param>
            </xsl:call-template>

        </xsl:variable>

        <xsl:choose>

            <xsl:when test="$symbol='leaf' and name()='leaf'  ">

        <!-- test>
          <xsl:value-of select="$desc-lines"/>
        </test>
        <test>
          <xsl:copy-of select="description"/>
        </test -->

            <!-- show the symbol -->
                <xsl:call-template name="showStep">
                    <xsl:with-param name="cx" select="@x"/>
                    <xsl:with-param name="cy" select="@y"/>
                    <xsl:with-param name="w" select="@w"/>
                    <xsl:with-param name="h" select="@h"/>
                    <xsl:with-param name="txt" select="$txt"/>
                    <xsl:with-param name="stroke" select="$statusStroke"/>

                    <xsl:with-param name="txt-lines">
                        <xsl:choose>
                            <xsl:when test="description">
                                <xsl:copy-of select="description"/>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:with-param>

                    <xsl:with-param name="element-type" select="@element-type"/>

                </xsl:call-template>

            </xsl:when>

            <xsl:when test="$symbol='stem' and name()='leaf'">
        <!--xsl:variable name="l_x1" select="@x"/ -->
                <xsl:variable name="l_x1">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="@x - $half-width"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@x"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>

        <!--xsl:variable name="l_y1" select="@y - $half-height"/ -->
                <xsl:variable name="l_y1">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="@y"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@y - $half-height"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>

        <!-- xsl:variable name="l_x2" select="../@x"/ -->

        <!-- back to parent -->
                <xsl:variable name="l_x2">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="../@x + (../@w div 2)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="../@x"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

        <!--xsl:variable name="l_y2" select="../@y + (../@h div 2 )"/-->

        <!-- back to parent -->
                <xsl:variable name="l_y2">
                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">
                            <xsl:value-of select="../@y"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="../@y + (../@h div 2 )"/>
                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:variable>


          <!-- xsl:variable name="l_x2" select="$cx" /--> <!-- back to parent -->
          <!--xsl:variable name="l_y2" select="$cy + $half-height" /--> <!-- back to parent -->

                <xsl:if test="parent::leaf[@id]">

                    <xsl:choose>
                        <xsl:when test="@element-type='stub'">
             <!-- stem line -->
                            <xsl:call-template name="showTick">
                                <xsl:with-param name="x1" select="@x"/>
                                <xsl:with-param name="y1" select="@y + $half-height"/>
                                <xsl:with-param name="x2" select="$l_x2"/>
                                <xsl:with-param name="y2" select="$l_y2"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
             <!-- stem line -->
                            <xsl:call-template name="showTick">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1"/>
                                <xsl:with-param name="x2" select="$l_x2"/>
                                <xsl:with-param name="y2" select="$l_y2"/>
                            </xsl:call-template>
          <!--  destination pointer -->
                            <xsl:choose>
                                <xsl:when test="$isVertical='true'">
                                    <xsl:call-template name="showPointerSymbol">
                                        <xsl:with-param name="cx" select="@x - (@w div 2)"/>
                                        <xsl:with-param name="cy" select="@y"/>
                                        <xsl:with-param name="radius" select="3"/>
                                        <xsl:with-param name="direction" select="'right'"/>
                                        <xsl:with-param name="fill" select="'#00ff00'"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="showPointerSymbol">
                                        <xsl:with-param name="cx" select="@x"/>
                                        <xsl:with-param name="cy" select="@y - $half-height"/>
                                        <xsl:with-param name="radius" select="3"/>
                                        <xsl:with-param name="direction" select="'down'"/>
                                        <xsl:with-param name="fill" select="'#00ff00'"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>

                        </xsl:otherwise>
                    </xsl:choose>

                </xsl:if>

                <xsl:if test="@to">


                    <xsl:choose>
                        <xsl:when test="$isVertical='true'">



                            <xsl:call-template name="showLineTo">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1 + @h"/>
                                <xsl:with-param name="id" select="@to"/>
                                <xsl:with-param name="color" select="'#ff0000'"/>
                            </xsl:call-template>


                        </xsl:when>
                        <xsl:otherwise>

                            <xsl:call-template name="showLineTo">
                                <xsl:with-param name="x1" select="$l_x1"/>
                                <xsl:with-param name="y1" select="$l_y1 + @h"/>
                                <xsl:with-param name="id" select="@to"/>
                                <xsl:with-param name="color" select="'#00ff00'"/>
                            </xsl:call-template>

                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>


          <!-- bottom -->
                <xsl:if test="child::leaf[@id]">
                    <xsl:if test="@element-type!='stub'">


                        <xsl:choose>
                            <xsl:when test="$isVertical='true'">

                                <xsl:call-template name="showCircle">
                                    <xsl:with-param name="cx" select="@x + $half-width"/>
                                    <xsl:with-param name="cy" select="@y"/>
                                    <xsl:with-param name="radius" select="3"/>
                                    <xsl:with-param name="fill" select="'#00ff00'"/>
                                </xsl:call-template>

                            </xsl:when>
                            <xsl:otherwise>

                                <xsl:call-template name="showPointerSymbol">
                                    <xsl:with-param name="cx" select="@x"/>
                                    <xsl:with-param name="cy" select="@y - $half-height"/>
                                    <xsl:with-param name="radius" select="3"/>
                                    <xsl:with-param name="direction" select="'down'"/>
                                    <xsl:with-param name="fill" select="'#00ff00'"/>
                                </xsl:call-template>

                            </xsl:otherwise>
                        </xsl:choose>



                    </xsl:if>

                </xsl:if>
            </xsl:when>

            <xsl:otherwise>

            </xsl:otherwise>

        </xsl:choose>
    <!--
      Show Org Chart
      or show all symbols
      -->
        <xsl:for-each select="leaf">
            <xsl:call-template name="showSymbols">
        <!-- xsl:with-param name="level" select="$l_nextlevel" / -->
        <!--xsl:with-param name="pos" select="$pos + 1" /-->
                <xsl:with-param name="symbol" select="$symbol"/>
                <xsl:with-param name="ox" select="$ox"/>
                <xsl:with-param name="oy" select="$oy"/>
                <xsl:with-param name="cx" select="@x"/>
                <xsl:with-param name="cy" select="@y"/>
                <xsl:with-param name="minXOffset" select="$minXOffset"/>
                <xsl:with-param name="minYOffset" select="$minYOffset"/>
      <!-- xsl:with-param name="maxwidth" select="$maxwidth"/ -->

            </xsl:call-template>

        </xsl:for-each>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="showText">
        <xsl:param name="x"/>
        <xsl:param name="y"/>
        <xsl:param name="txt"/>
        <xsl:param name="textrotate" select="0"/>
        <xsl:param name="font-family" select="'Arial'"/>
        <xsl:param name="font-size" select="10"/>

        <xsl:element name="text">
            <xsl:attribute name="x">
                <xsl:value-of select="$x"/>
            </xsl:attribute>

            <xsl:attribute name="y">
                <xsl:value-of select="$y"/>
            </xsl:attribute>
            <xsl:attribute name="style">font-family:
                <xsl:value-of select="$font-family"/>;font-size:
                <xsl:value-of select="$font-size"/>px;
            </xsl:attribute>
            <xsl:if test="$textrotate != 0">
                <xsl:attribute name="transform">rotate(
                    <xsl:value-of select="$textrotate"/>,
                    <xsl:value-of select="$x"/>,
                    <xsl:value-of select="$y"/>)
                </xsl:attribute>
            </xsl:if>

            <xsl:value-of select="$txt"/>
        </xsl:element>

    </xsl:template>
<xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/2000/svg" name="showTick">
        <xsl:param name="x1"/>
        <xsl:param name="y1"/>
        <xsl:param name="x2"/>
        <xsl:param name="y2"/>
        <xsl:param name="linewidth" select="1"/>
        <xsl:param name="color" select="'#0000ff'"/>
                <!-- pointer to text description of time and item -->
        <xsl:element name="line">
            <xsl:attribute name="x1">
                <xsl:value-of select="$x1"/>
            </xsl:attribute>
            <xsl:attribute name="y1">
                <xsl:value-of select="$y1"/>
            </xsl:attribute>
            <xsl:attribute name="x2">
                <xsl:value-of select="$x2"/>
            </xsl:attribute>
            <xsl:attribute name="y2">
                <xsl:value-of select="$y2"/>
            </xsl:attribute>
      <!-- xsl:attribute name="style">stroke:rgb(99,99,99);stroke-width: -->
            <xsl:attribute name="style">
                <xsl:value-of select="concat('stroke:',$color,';')"/>
                <xsl:value-of select="concat('stroke-width:',$linewidth,';')"/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template></xsl:stylesheet>