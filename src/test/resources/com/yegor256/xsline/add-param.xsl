<?xml version="1.0"?>
<!--
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="add-param" version="2.0">
  <!--
  Adds $param as @param attribute to all nodes.
  -->
  <xsl:strip-space elements="*"/>
  <xsl:param name="param"/>
  <xsl:template match="node()" priority="1">
    <xsl:copy>
      <xsl:attribute name="param">
        <xsl:value-of select="$param"/>
      </xsl:attribute>
      <xsl:value-of select="."/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
