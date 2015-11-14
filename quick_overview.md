---
layout: page
title: "Quick overview"
---


Construct an XmlNode from a parsed groovy node, or from raw XML text:

    XmlNode n = new XmlNode("<div><l>I met a traveller from an antique land</l><l>Who said...</l></div>")

Get the text content of a specified node, or default to the root node,

    String txt = n.collectText()

and the resulting value of `txt` is

    I met a traveller from an antique land Who said...

Optionally, define tokenizing markup that overrides default white space normalization.  E.g., for the XML

    <w>Part<unclear>iall</unclear>y</w>

you might not want the default result


    Part iall y

Override this by defining any combination of XML namespace, local element name, attribute name and attribute value as "magic tokenizing markup." The following example leaves names space, attribute name and attribute value unspecified, but says that any element with local name `w` is a tokenizing sequence:

    XmlNode n = new XmlNode("<w>Part<unclear>iall</unclear>y</w>")

    n.setMagic("","w","","")

so `collectText()` now yields

    Partially

Here is an example that tokenizes only on `seg` elements in the TEI namespace when they have a value of `token` on a `type` attribute:

    XmlNode  n = new XmlNode("<p xmlns='http://www.tei-c.org/ns/1.0'><seg type='token'>Part<unclear>iall</unclear>y</seg> distinct</p>")

    n.setMagic("http://www.tei-c.org/ns/1.0", "w", "type", "token")

In this case, `collectText()` produces

    Partially distinct
