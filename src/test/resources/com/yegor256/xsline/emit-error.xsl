<?xml version="1.0"?>
<!--
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="emit-error" version="2.0">
  <!--
  Doesn't transform, but only emits a fatal error.
  -->
  <xsl:strip-space elements="*"/>
  <xsl:template match="node()" priority="1">
    <xsl:message terminate="yes">
      <xsl:text>oopsie...</xsl:text>
    </xsl:message>
  </xsl:template>
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
