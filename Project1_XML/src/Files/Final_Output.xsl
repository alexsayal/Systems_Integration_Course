<?xml version="1.0" encoding="UTF-8"?>
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
	<style>
		body{
		font-family:"Palatino Linotype", "Book Antiqua", Palatino,
		serif;
		background-color: rgb(220,230,240);
		}

		.wrap {
		width:1200px;
		margin:0 auto;
		}

		.left {
		float: left;
		width: 60%;
		}

		.right {
		float: right;
		width: 40%;
		}

		table.sample {
		border-width: 4px;
		border-spacing: 2px;
		border-style: outset;
		border-color: gray;
		border-collapse: separate;
		padding: 3px;
		}
		table.sample th {
		border-width: 1px;
		padding: 1px;
		border-style: dotted;
		border-color: gray;
		-moz-border-radius: ;
		}
		table.sample td {
		border-width: 1px;
		padding: 1px;
		border-style: dotted;
		border-color: gray;
		-moz-border-radius: ;
		}
	</style>

	<head>
		<title> Interests Catalog </title>
	</head>

	<body>
		<h1 align="center"> Current Interests in Catalog</h1>
		<div class="wrap">
			<div class="right">
				<h2 align="center">
					<u>Statistics</u>
				</h2>

				<table class="sample" align="center">
					<tr>
						<th>Total Number of Researchers</th>
						<td align="center">
							<xsl:value-of select="catalog_summary/numresearchers" />
						</td>
					</tr>
					<tr>
						<th>Total Number of Citations  </th>
						<td align="center">
							<xsl:value-of select="catalog_summary/numcitations" />
						</td>
					</tr>

					<xsl:for-each select="//topname">
						<xsl:choose>
							<xsl:when test="position()=1">
								<tr>
									<th rowspan="3">Top 3 Researchers  </th>
									<td align="center">
										<xsl:value-of select="." />
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<td align="center">
										<xsl:value-of select="." />
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>

				</table>

			</div>
			<div class="left">
				<h2 align="center">
					<u>Interests</u>
				</h2>
				<table class="sample" align="center">
					<tr>
						<td>
							<b> Name of Interest</b>
						</td>
						<td align="center">
							<b> Researchers </b>
						</td>
						<td align="center">
							<b> University </b>
						</td>
						<td align="center">
							<b> No Citations </b>
						</td>
					</tr>

					<xsl:for-each select="//interest">
						<tr>
							<xsl:variable name="size"
								select="count(./researcher_summary/res/name_res)"></xsl:variable>
							<xsl:choose>
								<xsl:when test="$size>1">
									<td rowspan="{$size}">
										<xsl:value-of select="name_int" />
									</td>

									<xsl:for-each select="./researcher_summary/res/name_res">
										<xsl:if test="position()=1">
											<td align="center">
												<xsl:value-of select="." />
											</td>
											<td align="center">
												<xsl:value-of select="../uni_res" />
											</td>
											<td align="center">
												<xsl:value-of select="sum(..//cited)" />
											</td>
										</xsl:if>
										<xsl:if test="position()>1">
											<tr>
												<td align="center">
													<xsl:value-of select="." />
												</td>
												<td align="center">
													<xsl:value-of select="../uni_res" />
												</td>
												<td align="center">
													<xsl:value-of select="sum(..//cited)" />
												</td>
											</tr>
										</xsl:if>

									</xsl:for-each>

								</xsl:when>
								<xsl:otherwise>
									<td>
										<xsl:value-of select="name_int" />
									</td>

									<xsl:for-each select="./researcher_summary/res/name_res">
										<td align="center">
											<xsl:value-of select="." />
										</td>
									</xsl:for-each>
									<td align="center">
										<xsl:value-of select="./researcher_summary/res/uni_res" />
									</td>
									<xsl:for-each select="./researcher_summary/res">
										<td align="center">
											<xsl:value-of select="sum(.//cited)" />
										</td>
									</xsl:for-each>
								</xsl:otherwise>
							</xsl:choose>

						</tr>

					</xsl:for-each>

				</table>
			</div>
		</div>
	</body>
</html>