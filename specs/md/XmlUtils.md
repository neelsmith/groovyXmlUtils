# Utilities for working with XML parsed with groovy's XmlParser #


Groovy's native `XmlParser` class is a fast and easy way to work with in-memory parses of XML data.  The `XmlUtils` class in this package provides methods for some common recursive tasks applied to the `groovy.util.Node` object that groovy's `XmlParser` creates.

## Collect all text contained in a tree

One common task is to collect all text content for a tree or subtree:  that is, all text contained in a node and any of the nodes recursively contained by it.  In the simplest case, that simply requires collecting in document order the String value of text nodes.
In XML, white space outside of text nodes is not significant. The convention of the `XmlNode` class is to reduce spaces between elements to a single space character.


@openex@

### Examples ###

Given the following well-formed XML fragment,

<code concordion:set="#il1">&lt;l n="1">Sing, goddess, the rage of &lt;persName n="urn:cite:hmt:pers.pers1">Achilles&lt;/persName>&lt;/l></code>


We can extract the text content <strong concordion:assertEquals="collectText(#il1)">Sing, goddess, the rage of Achilles</strong>

Extra spaces in the XML document are regularized to a single white space character, so the following well-formed XML fragment


<code concordion:set="#extrawhite">&lt;l n="1">Sing, goddess, the rage of  &lt;persName     n="urn:cite:hmt:pers.pers1"       >Achilles&lt;/persName>     &lt;/l>
</code>

while using white space differently, produces the identical string <strong concordion:assertEquals="collectText(#extrawhite)">Sing, goddess, the rage of Achilles</strong>

@closeex@

### Magic markup ###





## Planned for next release: serialize a node

The task of re-serializing a node and its contents to XML is tedious.  One compilication is that in some contexts, it may be desirable to include explicit XML namespace definitions if serializing a subtree when the XML namespace is defined in a containing element.
